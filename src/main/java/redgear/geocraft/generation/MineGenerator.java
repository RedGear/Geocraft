package redgear.geocraft.generation;

import java.util.EnumSet;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraftforge.common.Configuration;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.ForgeSubscribe;
import net.minecraftforge.event.world.ChunkDataEvent;
import redgear.core.mod.ModUtils;
import redgear.core.util.SimpleItem;
import redgear.core.world.ChunkCoordinate;
import redgear.geocraft.api.IEveryChunk;
import redgear.geocraft.api.IMine;
import redgear.geocraft.api.MineManager;
import redgear.geocraft.core.Geocraft;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.ListMultimap;

import cpw.mods.fml.common.ITickHandler;
import cpw.mods.fml.common.IWorldGenerator;
import cpw.mods.fml.common.TickType;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.TickRegistry;
import cpw.mods.fml.relauncher.Side;

public class MineGenerator implements IWorldGenerator, ITickHandler {

	private int crashTimer = 0;
	private final boolean crash;
	private final double genRate;
	private boolean canGen = true;
	public static MineGenerator inst;
	public static MineRegistry reg;
	public ListMultimap<Integer, GenData> chunkMap = ArrayListMultimap.<Integer, GenData> create();

	public MineGenerator(ModUtils util) {
		reg = new MineRegistry(util);
		MineManager.oreRegistry = reg;
		crash = util.getBoolean("crash", false);
		double temp = util.getDouble(Configuration.CATEGORY_GENERAL, "generationRate",
				"The rate at which Geocraft generates ores. "
						+ "Larger numbers will mean the generation happens faster, but this will cause more lag. "
						+ "Smaller numbers mean that generation will take longer "
						+ "IE: it will take longer for ores to pop into the world; "
						+ "but it will cause less lag and be easier on weaker computers. "
						+ "Adjust to suit your system and preference. " + "Can be any decimal number larger than 0. "
						+ "Default is 1, that is, one chunk per world tick.", 1);
		genRate = temp <= 0 ? 1.0 : temp; //if temp is 0 or negative, set genRate to 1, otherwise set it to temp.
		GameRegistry.registerWorldGenerator(this);
		TickRegistry.registerTickHandler(this, Side.SERVER);
		MinecraftForge.EVENT_BUS.register(this);
	}

	public void addChunk(GenData data, World world) {
		chunkMap.put(world.provider.dimensionId, data);
	}

	@ForgeSubscribe
	public void chunkSave(ChunkDataEvent.Save event) {
		int dimId = event.world.provider.dimensionId;
		List<GenData> chunks = chunkMap.get(dimId);
		int chunkIndex = chunks.indexOf(new ChunkCoordinate(event.getChunk()));

		if (chunkIndex != -1) {
			GenData dat = chunks.remove(chunkIndex);
			event.getData().setTag("RedGear.Geocraft", dat.tagData);
		} else {
			NBTTagCompound tag = new NBTTagCompound();

			tag.setLong("GenHas", reg.genHash);
			tag.setCompoundTag("Ores", MineRegistry.ores);

			event.getData().setTag("RedGear.Geocraft", tag);
		}
	}

	@ForgeSubscribe
	public void chunkLoad(ChunkDataEvent.Load event) {
		NBTTagCompound tag = (NBTTagCompound) event.getData().getTag("RedGear.Geocraft");

		if (tag == null || tag.getLong("GenHas") != reg.genHash)
			addChunk(new GenData(new ChunkCoordinate(event.getChunk()), tag), event.world);
	}

	@Override
	public void generate(Random rand, int chunkX, int chunkZ, World world, IChunkProvider chunkGenerator,
			IChunkProvider chunkProvider) {
		if (canGen)
			reg.addNewOres();
		addChunk(new GenData(new ChunkCoordinate(chunkX, chunkZ), null), world);
	}

	public int generate(Random rand, int chunkX, int chunkZ, World world, NBTTagCompound tagData, GenData data,
			int countDown) {
		if (crash && crashTimer++ > 10)
			throw new RuntimeException("Stop running!");

		//Geocraft.util.logDebug("Generating chunk X: " + chunkX + " Z: " + chunkZ);
		//long start = System.nanoTime();

		if (data.it == null)
			data.it = reg.mines.iterator();

		IMine mine;
		while (data.it.hasNext() && countDown-- > 0) {
			mine = data.it.next();

			if (mine instanceof IEveryChunk)
				checkAndGen(mine, world, rand, chunkX, chunkZ, tagData);
			else if (canGen) {
				int rare = (int) (mine.getMineRarity() * reg.densityModifier);

				if (rare > 0 && rand.nextInt(rare) == 0) {
					canGen = false;
					checkAndGen(mine, world, rand, chunkX, chunkZ, tagData);
					canGen = true;
				}
			}
		}
		world.getChunkFromChunkCoords(chunkX, chunkZ).setChunkModified();
		//Geocraft.inst.logDebug("Retro: ", retro, " Time: ", new BigDecimal(System.nanoTime() - start).setScale(4).divide(new BigDecimal(1000000000).setScale(4)));

		return countDown;
	}

	private void checkAndGen(IMine mine, World world, Random rand, int chunkX, int chunkZ, NBTTagCompound tag) {
		if (tag == null || !tag.getBoolean(mine.getName()))
			mine.generate(world, rand, chunkX, chunkZ);
	}

	@Override
	public void tickStart(EnumSet<TickType> type, Object... tickData) {

	}

	@Override
	public void tickEnd(EnumSet<TickType> type, Object... tickData) {
		World world = (World) tickData[0];
		int dimID = world.provider.dimensionId;
		List<GenData> list = chunkMap.get(dimID);
		int countDown = (int) (reg.mines.size() * genRate) + 1;

		GenData data = null;
		ChunkCoordinate coord;

		 do {
			do {
				if (list.size() == 0)
					return;

				data = list.remove(0);
				coord = data.coord;
			} while (!coord.checkExists(world));// keep removing chunks until you find one that IS loaded. 

			long worldSeed = world.getSeed();
			Random rand = new Random(worldSeed);
			rand.setSeed((rand.nextLong() >> 3) * coord.x + (rand.nextLong() >> 3) * coord.z ^ worldSeed);

			countDown = generate(rand, coord.x, coord.z, world,
					data.tagData == null ? null : data.tagData.getCompoundTag("Ores"), data, countDown);
		} while (countDown > 0);
		
		if(data != null && data.it.hasNext())
			list.add(0, data);
	}

	@Override
	public EnumSet<TickType> ticks() {
		return EnumSet.of(TickType.WORLD);
	}

	@Override
	public String getLabel() {
		return "RedGear.Geocraft";
	}

	private class GenData {
		public final ChunkCoordinate coord;
		public final NBTTagCompound tagData;
		public Iterator<IMine> it;

		public GenData(ChunkCoordinate coord, NBTTagCompound tagData) {
			this.coord = coord;
			this.tagData = tagData;
			it = null;
		}

		@Override
		public int hashCode() {
			return coord.hashCode();
		}

		@Override
		public boolean equals(Object obj) {
			return coord.equals(obj);
		}
	}

	public static void generateCopper(SimpleItem copperOre) {
		reg.addNewOre(copperOre, Geocraft.stone, 16, 10);
	}

	public static void generateTin(SimpleItem tinOre) {
		reg.addNewOre(tinOre, Geocraft.stone, 32, 6);
	}

	public static void generateSilver(SimpleItem silverOre) {
		reg.addNewOre(silverOre, Geocraft.stone, 3, 16);
	}

	public static void generateLead(SimpleItem leadOre) {
		reg.addNewOre(leadOre, Geocraft.stone, 4, 16);
	}
}