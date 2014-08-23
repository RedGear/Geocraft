package redgear.geocraft.generation;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Pattern;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.oredict.OreDictionary;
import redgear.core.api.item.ISimpleItem;
import redgear.core.mod.ModUtils;
import redgear.core.util.SimpleItem;
import redgear.core.util.SimpleOre;
import redgear.core.util.StringHelper;
import redgear.geocraft.api.IMineRegistry;
import redgear.geocraft.api.gen.Mine;
import redgear.geocraft.config.ConfigHandler;
import redgear.geocraft.core.Geocraft;
import redgear.geocraft.core.GeocraftConfig;
import redgear.geocraft.mines.MineCylinder;
import redgear.geocraft.mines.MineTrace;
import redgear.geocraft.mines.MineVanilla;

public class MineRegistry implements IMineRegistry {
	public Set<Mine> mines = Collections.newSetFromMap(new ConcurrentHashMap<Mine, Boolean>());
	public Set<NewOre> newOres = new HashSet<NewOre>();
	public List<ISimpleItem> ignoreOres = new LinkedList<ISimpleItem>();
	public List<ISimpleItem> normalOres = new LinkedList<ISimpleItem>();

	public final float volumeModifier; //more or less ore in a mine
	public final float rarityModifier; //rarity of mines

	public long genHash = 0;
	public NBTTagCompound ores = new NBTTagCompound();

	private final ISimpleItem netherrack = new SimpleItem(Blocks.netherrack);
	private final ISimpleItem endStone = new SimpleItem(Blocks.end_stone);

	private final Pattern orePattern = Pattern.compile("^ore.*", Pattern.CASE_INSENSITIVE);

	public MineRegistry(ModUtils util) {
		final int defaultDensityRate = 4; //used for creating default values

		final String l1 = "Level1";
		volumeModifier = (float) util.getDouble(l1, "volumeModifier",
				"Changes the number of veins in each mine for ALL ores. It is a multiplier. "
						+ "Larger numbers mean more ore, smaller means less.", defaultDensityRate);
		rarityModifier = (float) util.getDouble(l1, "rarityModifier", "Changes the rarities of ALL ores. "
				+ "It is a multiplier. Larger numbers mean further apart , smaller means closer together.",
				defaultDensityRate * defaultDensityRate);
	}

	/**
	 * Checks for new ores, adds up new ores, generates trace and normal veins
	 * if needed
	 *
	 * @param blockId
	 * @param blockMeta
	 * @param numberOfBlocks
	 * @param target
	 */
	public boolean checkForNew(Block block, int blockMeta, int numberOfBlocks, Block target) {
		ISimpleItem item = new SimpleItem(block, blockMeta);

		if (listCheck(item, ignoreOres))
			return false;
		else if (listCheck(item, normalOres))
			return true;
		else {
			ItemStack stack = item.getStack();

			for (Mine m : mines)
				if (m.overrides(stack)) {
					ignoreOres.add(item);
					return false;
				}

			NewOre newOre = getNewOre(item);

			if (newOre == null) {
				newOre = new NewOre(item, new SimpleItem(target), numberOfBlocks);
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
					addNewOre(newOre.block, newOre.target, newOre.numberOfVeins, newOre.numberOfOres
							/ newOre.numberOfVeins);
		newOres.clear();
	}

	public void addNewOre(ISimpleItem block, ISimpleItem target, int veins, int cluster) {
		String name = StringHelper.camelCase(block.getDisplayName());

		if (GeocraftConfig.cylinderMode)
			registerCylinder(name, block, target, 4, veins, cluster, true);
		else
			registerMine(new MineVanilla(name, block, target, veins, cluster));
	}

	public NewOre getNewOre(ISimpleItem block) {
		for (NewOre other : newOres)
			if (other.equals(block))
				return other;
		return null;
	}

	private class NewOre {
		private final ISimpleItem block;
		private final ISimpleItem target;
		private int numberOfOres;
		private int numberOfVeins;

		public NewOre(ISimpleItem block, ISimpleItem target, int numberOfOres) {
			this.block = oreCheck(block);
			this.target = stoneCheck(target);
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
	public float volumeModifier() {
		return volumeModifier;
	}

	@Override
	public float rarityModifier() {
		return rarityModifier;
	}

	public ISimpleItem oreCheck(ISimpleItem item) {
		if (item instanceof SimpleOre)
			return item;

		for (int id : OreDictionary.getOreIDs(item.getStack()))
			if (orePattern.matcher(OreDictionary.getOreName(id)).matches())
				return new SimpleOre(OreDictionary.getOreName(id), item);

		return item;
	}

	public ISimpleItem stoneCheck(ISimpleItem item) {
		if (GeocraftConfig.stoneOre.equals(item))
			return GeocraftConfig.stoneOre;

		else if (netherrack.equals(item))
			return netherrack;
		else if (endStone.equals(item))
			return endStone;
		else
			return item;
	}
	
	public boolean listCheck(ISimpleItem item, Collection<ISimpleItem> list){
		for(ISimpleItem check : list)
			if(check.isItemEqual(item, false))
				return true;
		
		return false;
	}

	public void load() {
		ConfigHandler.inst.loadAll(this);
	}

	public void save() {
		for (Mine m : mines) {
			m.init();
			ConfigHandler.inst.saveJson(m);

		}
	}

	@Override
	public Mine registerMine(Mine mine) {
		if (mineAllowed(mine)) {
			if (Geocraft.init) {
				mine.init();
				ConfigHandler.inst.saveJson(mine);
			}
			genHash += mine.hashCode();
			ores.setBoolean(mine.name, true);
		}
		return mine;
	}

	private boolean mineAllowed(Mine mine) {
		if(mine.name == null || mine.name.length() <= 0)
			return false;
		
		boolean value = mines.add(mine);
		
		Geocraft.inst.logDebug("Registering mine: ", mine.name, " : ", value);
		
		return value;
	}

	@Override
	public Mine registerCylinder(String name, ItemStack block, ItemStack target, float mineRarity, float mineSize,
			int veinSize, boolean trace) {
		return registerCylinder(name, new SimpleItem(block), new SimpleItem(target), mineRarity, mineSize, veinSize,
				trace);
	}

	public Mine registerCylinder(String name, ISimpleItem block, ISimpleItem target, float mineRarity, float mineSize,
			int veinSize, boolean trace) {
		Mine ans = registerMine(new MineCylinder(name, block, target, mineRarity, mineSize, veinSize));
		if (trace)
			registerTrace(name + "Trace", block, target, (int) mineSize);
		return ans;
	}

	@Override
	public Mine registerTrace(String name, Block block, int blockMeta, Block target, int targetMeta, int size) {
		return registerTrace(name, new SimpleItem(block, blockMeta), new SimpleItem(target, targetMeta), size);
	}

	public Mine registerTrace(String name, ISimpleItem block, ISimpleItem target, int size) {
		registerIgnore(block);

		Mine mine = new MineTrace(name, block, target, size);
		mine.isActive = GeocraftConfig.genTrace;
		registerMine(mine);

		return mine;
	}

	@Override
	public void registerIgnore(Block blockId, int blockMeta) {
		registerIgnore(new SimpleItem(blockId, blockMeta));
	}

	public void registerIgnore(ISimpleItem block) {
		ignoreOres.add(block);
	}

	@Override
	public void registerNormalGen(Block blockId, int blockMeta) {
		registerNormalGen(new SimpleItem(blockId, blockMeta));
	}

	public void registerNormalGen(SimpleItem block) {
		normalOres.add(block);
	}
}
