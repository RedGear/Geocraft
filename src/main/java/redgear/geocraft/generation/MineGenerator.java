package redgear.geocraft.generation;

import java.math.BigDecimal;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.world.ChunkDataEvent;
import redgear.core.mod.ModUtils;
import redgear.core.util.SimpleItem;
import redgear.core.world.ChunkCoordinate;
import redgear.geocraft.api.IEveryChunk;
import redgear.geocraft.api.IMine;
import redgear.geocraft.api.MineManager;
import redgear.geocraft.core.Geocraft;
import redgear.geocraft.core.GeocraftConfig;
import redgear.geocraft.mines.MineMetal;

import com.google.common.collect.LinkedListMultimap;
import com.google.common.collect.ListMultimap;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.IWorldGenerator;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent.Phase;
import cpw.mods.fml.common.gameevent.TickEvent.WorldTickEvent;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;

public class MineGenerator implements IWorldGenerator {

	private final long maxTime = 1000000 * 10;
	private boolean canGen = true;
	public static MineGenerator inst;
	public static MineRegistry reg;
	public ListMultimap<Integer, GenData> chunkMap = LinkedListMultimap.<Integer, GenData> create();

	public MineGenerator(ModUtils util) {
		reg = new MineRegistry(util);
		MineManager.oreRegistry = reg;
		GameRegistry.registerWorldGenerator(this, 1);
		MinecraftForge.EVENT_BUS.register(this);
		FMLCommonHandler.instance().bus().register(this);
		inst = this;
	}

	public void addChunk(GenData data, World world) {
		chunkMap.put(world.provider.dimensionId, data);
	}

	@SubscribeEvent
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
			tag.setTag("Ores", reg.ores);

			event.getData().setTag("RedGear.Geocraft", tag);
		}
	}

	@SubscribeEvent
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

	public boolean generate(Random rand, int chunkX, int chunkZ, World world, NBTTagCompound tagData, GenData data,
			long start) {
		if (data.it == null)
			data.it = reg.mines.iterator();

		IMine mine = null;
		while (data.it.hasNext() && System.nanoTime() - start < maxTime) {
			mine = data.it.next();

			if (mine instanceof IEveryChunk)
				checkAndGen(mine, world, rand, chunkX, chunkZ, tagData);
			else if (canGen) {
				int rare = (int) (mine.getMineRarity() * reg.rarityModifier);

				if (rare > 0 && rand.nextInt(rare) == 0) {
					canGen = false;
					checkAndGen(mine, world, rand, chunkX, chunkZ, tagData);
					canGen = true;
				}
			}
		}
		world.getChunkFromChunkCoords(chunkX, chunkZ).setChunkModified();

		return System.nanoTime() - start < maxTime;
	}

	private void checkAndGen(IMine mine, World world, Random rand, int chunkX, int chunkZ, NBTTagCompound tag) {
		if (tag == null || !tag.getBoolean(mine.getName()))
			mine.generate(world, rand, chunkX, chunkZ);	
	}

	@SubscribeEvent
	public void tickStart(WorldTickEvent event) {
		if (event.side == Side.CLIENT || event.phase == Phase.END)
			return;

		World world = event.world;
		int dimID = world.provider.dimensionId;
		List<GenData> list = chunkMap.get(dimID);

		GenData data = null;
		ChunkCoordinate coord;
		long start = System.nanoTime();
		boolean hasTime = true;

		do {
			do {
				if (list.isEmpty())
					return;

				data = list.remove(0);
				coord = data.coord;
			} while (!coord.checkExists(world));// keep removing chunks until you find one that IS loaded. 

			long worldSeed = world.getSeed();
			Random rand = new Random(worldSeed);
			rand.setSeed((rand.nextLong() >> 3) * coord.x + (rand.nextLong() >> 3) * coord.z ^ worldSeed);

			hasTime = generate(rand, coord.x, coord.z, world,
					data.tagData == null ? null : data.tagData.getCompoundTag("Ores"), data, start);
			if(Geocraft.inst.isDebugMode)
			Geocraft.inst.logDebug("Generating chunk X: ", coord.x, " Z: ", coord.z, " Time: ",
					new BigDecimal(System.nanoTime() - start).setScale(4).divide(new BigDecimal(1000000).setScale(4)),
					" Chunks left: ", list.size());
		} while (hasTime);

		if (data != null && data.it.hasNext())
			list.add(0, data);
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
		if (GeocraftConfig.complexMines) {
			reg.registerMine(new MineMetal("Copper", 1, 16, GeocraftConfig.complexOres ? GeocraftConfig.copperOre
					: copperOre, GeocraftConfig.stone, 10));
			reg.registerTrace("CopperTrace", copperOre, GeocraftConfig.stone, 20);
		} else
			reg.addNewOre(copperOre, GeocraftConfig.stone, 16, 10);
	}

	public static void generateTin(SimpleItem tinOre) {
		if (GeocraftConfig.complexMines) {
			reg.registerMine(new MineMetal("Tin", 1, 32, GeocraftConfig.complexOres ? GeocraftConfig.tinOre : tinOre,
					GeocraftConfig.stone, 6));
			reg.registerTrace("TinTrace", tinOre, GeocraftConfig.stone, 20);
		} else
			reg.addNewOre(tinOre, GeocraftConfig.stone, 32, 6);
	}

	public static void generateSilver(SimpleItem silverOre) {
		if (GeocraftConfig.complexMines) {
			if (!GeocraftConfig.hasSilver) {
				if (GeocraftConfig.complexOres) {
					if (!GeocraftConfig.hasLead)
						reg.registerMine(new MineMetal("Galena", 1, 4, GeocraftConfig.galenaOre, GeocraftConfig.stone,
								16));
				} else
					reg.registerMine(new MineMetal("Silver", 1, 3, silverOre, GeocraftConfig.stone, 16));
				reg.registerTrace("SilverTrace", silverOre, GeocraftConfig.stone, 3);
			}
		} else
			reg.addNewOre(silverOre, GeocraftConfig.stone, 3, 16);
		GeocraftConfig.hasSilver = true;
	}

	public static void generateLead(SimpleItem leadOre) {
		if (GeocraftConfig.complexMines) {
			if (!GeocraftConfig.hasLead) {
				if (GeocraftConfig.complexOres) {
					if (!GeocraftConfig.hasSilver)
						reg.registerMine(new MineMetal("Galena", 1, 4, GeocraftConfig.galenaOre, GeocraftConfig.stone,
								16));
				} else
					reg.registerMine(new MineMetal("Lead", 1, 4, leadOre, GeocraftConfig.stone, 16));
				reg.registerTrace("LeadTrace", leadOre, GeocraftConfig.stone, 4);
			}
		} else
			reg.addNewOre(leadOre, GeocraftConfig.stone, 4, 16);
		GeocraftConfig.hasLead = true;
	}
}