package redgear.geocraft.generation;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import net.minecraft.nbt.NBTTagCompound;
import redgear.core.mod.ModUtils;
import redgear.core.util.SimpleItem;
import redgear.geocraft.api.IMine;
import redgear.geocraft.api.IMineRegistry;
import redgear.geocraft.mines.MineCylinder;
import redgear.geocraft.mines.MineTrace;

public class MineRegistry implements IMineRegistry {
	public Set<IMine> mines = Collections.newSetFromMap(new ConcurrentHashMap<IMine, Boolean>());
	public Set<NewOre> newOres = new HashSet<NewOre>();
	public Map<SimpleItem, Boolean> ignoreOres = new HashMap<SimpleItem, Boolean>(); //If that boolean is true, gen it like normal, false means do nothing.

	public final boolean genTrace; //Generate trace veins for ANY ores
	public final float volumeModifier; //more or less ore in a mine
	public final float densityModifier; //rarity of mines
	public final int defaultDensityRate; //used for creating default values
	public final boolean useDimensions = false;
	public final boolean useBiomes = false;

	public long genHash = 0;
	public static NBTTagCompound ores = new NBTTagCompound();

	public MineRegistry(ModUtils util) {
		final String l1 = "Level1";

		genTrace = util.getBoolean(l1, "genTrace",
				"Setting this to false will disable all trace ore generation, overriding the individual ore settings");
		volumeModifier = (float) util.getDouble(l1, "volumeModifier",
				"Changes the number of veins in each mine for ALL ores. It is a multiplier. "
						+ "Larger numbers mean more ore, smaller means less.", 1);
		densityModifier = (float) util.getDouble(l1, "densityModifier", "Changes the rarities of ALL ores. "
				+ "It is a multiplier. Larger numbers mean further apart , smaller means closer together.", 1);
		//this.useDimensions =  util.getBoolean(l1, "useDimensions", "Setting this to true and running Minecraft will open up the option to change what dimensions mines can spawn in.", false);
		//this.useBiomes =  util.getBoolean(l1, "useBiomes", "Setting this to true and running Minecraft will open up the option to change mine rarities based on biomes", false);

		defaultDensityRate = 4;
	}

	/**
	 * Checks for new ores, adds up new ores, generates trace and normal veins
	 * if needed
	 * 
	 * @param blockId
	 * @param blockMeta
	 * @param numberOfBlocks
	 * @param targetId
	 * @param x
	 * @param y
	 * @param z
	 */
	public boolean checkForNew(int blockId, int blockMeta, int numberOfBlocks, int targetId) {
		SimpleItem block = new SimpleItem(blockId, blockMeta);

		if (ignoreOres.containsKey(block))
			return ignoreOres.get(block);
		else {
			NewOre newOre = getNewOre(block);

			if (newOre == null) {
				newOre = new MineRegistry.NewOre(block, new SimpleItem(targetId), numberOfBlocks);
				newOres.add(newOre);
			} else
				newOre.addData(numberOfBlocks);
			return false;
		}
	}

	public void addNewOres() {
		if (newOres.size() > 0)
			for (NewOre newOre : newOres)
				if (newOre.numberOfVeins > 0 && newOre.numberOfOres > 0)
					if (!checkForDefaults(newOre.block))
						addNewOre(newOre.block, newOre.target, newOre.numberOfVeins, newOre.numberOfOres
								/ newOre.numberOfVeins);
		newOres.clear();
	}

	private boolean checkForDefaults(SimpleItem block) {
		if (block.isInOreDict()) {
			String name = block.oreName().toLowerCase();

			if (name.equals("orecopper")) {
				MineGenerator.generateCopper(block);
				return true;
			}

			if (name.equals("oretin")) {
				MineGenerator.generateTin(block);
				return true;
			}

			if (name.equals("oresilver")) {
				MineGenerator.generateSilver(block);
				return true;
			}

			if (name.equals("orelead")) {
				MineGenerator.generateLead(block);
				return true;
			}
		}

		return false;
	}

	public void addNewOre(SimpleItem block, SimpleItem target, int veins, int cluster) {
		String name = block.isInOreDict() ? block.oreName() : block.getName();

		registerMine(new MineCylinder(name, defaultDensityRate * defaultDensityRate, veins * defaultDensityRate, block,
				target, cluster));
		registerTrace(name + "Trace", block, target, veins);
	}

	public NewOre getNewOre(SimpleItem block) {
		for (NewOre other : newOres)
			if (other.equals(block))
				return other;
		return null;
	}

	private class NewOre {
		private final SimpleItem block;
		private final SimpleItem target;
		private int numberOfOres;
		private int numberOfVeins;

		public NewOre(SimpleItem block, SimpleItem target, int numberOfOres) {
			this.block = block;
			this.target = target;
			this.numberOfOres = numberOfOres;
			numberOfVeins = 1;
		}

		public void addData(int numberOfOres) {
			this.numberOfOres += numberOfOres;
			numberOfVeins++;
		}

		@Override
		public boolean equals(Object other) {
			if (other instanceof NewOre)
				return equals((NewOre) other);
			return block.equals(other);
		}

		public boolean equals(NewOre other) {
			return block.equals(other.block);
		}

		@Override
		public int hashCode() {
			return block.hashCode();
		}
	}

	@Override
	public int defaultDensityRate() {
		return defaultDensityRate;
	}

	@Override
	public float volumeModifier() {
		return volumeModifier;
	}

	@Override
	public float densityModifier() {
		return densityModifier;
	}

	@Override
	public boolean useDimensions() {
		return useDimensions;
	}

	@Override
	public boolean useBiomes() {
		return useBiomes;
	}

	@Override
	public boolean registerMine(IMine mine) {
		boolean test = mineAllowed(mine);
		if (test) {
			genHash += mine.getName().hashCode();
			ores.setBoolean(mine.getName(), true);
		}
		return test;
	}

	private boolean mineAllowed(IMine mine) {
		return mines.add(mine);
	}

	@Override
	public boolean registerTrace(String name, int blockId, int blockMeta, int targetId, int targetMeta, int size) {
		return registerTrace(name, new SimpleItem(blockId, blockMeta), new SimpleItem(targetId, targetMeta), size);
	}

	public boolean registerTrace(String name, SimpleItem block, SimpleItem target, int size) {
		registerIgnore(block);

		if (genTrace)
			return registerMine(new MineTrace(name, 1, size, block, target));
		return false;
	}

	@Override
	public boolean registerIgnore(int blockId, int blockMeta) {
		return registerIgnore(new SimpleItem(blockId, blockMeta));
	}

	public boolean registerIgnore(SimpleItem block) {
		return ignore(block, false);
	}

	@Override
	public boolean registerNormalGen(int blockId, int blockMeta) {
		return registerNormalGen(new SimpleItem(blockId, blockMeta));
	}

	public boolean registerNormalGen(SimpleItem block) {
		return ignore(block, true);
	}

	private boolean ignore(SimpleItem block, boolean normal) {
		Boolean value = ignoreOres.put(block, normal);
		return value == null ? true : false;
	}
}
