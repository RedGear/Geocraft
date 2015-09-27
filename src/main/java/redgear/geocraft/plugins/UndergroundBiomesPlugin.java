package redgear.geocraft.plugins;

import cpw.mods.fml.common.LoaderState.ModState;
import net.minecraft.block.Block;
import net.minecraft.world.World;
import redgear.core.mod.IPlugin;
import redgear.core.mod.ModUtils;
import redgear.core.mod.Mods;
import redgear.geocraft.core.Geocraft;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by LordBlackHole on 9/27/2015.
 */
public class UndergroundBiomesPlugin implements IPlugin {

	private boolean active = false;
	private List<BlockSetupData> blocks = new ArrayList<BlockSetupData>();
	private Object ubOreTexturizer;
	private Method redoOres;

	@Override
	public String getName() {
		return "Underground Biomes Compatibility";
	}

	@Override
	public boolean shouldRun(ModUtils mod, ModState state) {
		Geocraft.inst.logDebug("Is in: ", Mods.UndergroundBiomes.isIn(), ", and turned on: ", mod.getBoolean("plugins", "UndergroundBiomes"));
		active = Mods.UndergroundBiomes.isIn() && mod.getBoolean("plugins", "UndergroundBiomes");
		return active;
	}

	@Override
	public boolean isRequired() {
		return false;
	}

	@Override
	public void preInit(ModUtils modUtils) {
		try {
			Class<?> apiClass = Class.forName("exterminatorJeff.undergroundBiomes.api.UBAPIHook");

			Object api = apiClass.getField("ubAPIHook").get(null);

			this.ubOreTexturizer = apiClass.getField("ubOreTexturizer").get(api);

			Method requestUBOreSetup = ubOreTexturizer.getClass().getMethod("requestUBOreSetup", Block.class, int.class, String.class, String.class);

			redoOres = ubOreTexturizer.getClass().getMethod("redoOres", int.class, int.class, World.class);

			for (BlockSetupData block : blocks) {
//				UBAPIHook.ubAPIHook.ubOreTexturizer.requestUBOreSetup(block.oreBlock, block.metadata, block.overlayName, block.minecraftName);

				//The following line is the reflection-based equivalent to the above line
				requestUBOreSetup.invoke(ubOreTexturizer, block.oreBlock, block.metadata, block.overlayName, block.minecraftName);
			}

		} catch (ReflectiveOperationException e) {
			active = false;
			throw new RuntimeException("Reflection to access UndergroundBiomes API failed! Has the API Changed?", e);
		} finally {
			blocks = null; // Garbage collect/ensure that further attempts to add with fail.
		}


	}

	@Override
	public void Init(ModUtils modUtils) {

	}

	@Override
	public void postInit(ModUtils modUtils) {

	}

	private static class BlockSetupData {

		private final Block oreBlock;
		private final int metadata;
		private final String overlayName;
		private final String minecraftName;

		private BlockSetupData(Block oreBlock, int metadata, String overlayName, String minecraftName) {
			this.oreBlock = oreBlock;
			this.metadata = metadata;
			this.overlayName = overlayName;
			this.minecraftName = minecraftName;
		}

	}


	public void setup(Block oreBlock, int metadata, String overlayName, String minecraftName) {
		blocks.add(new BlockSetupData(oreBlock, metadata, overlayName, minecraftName));
	}


	public void replace(int xInBlockCoordinates, int zInBlockCoordinates, World serverSideWorld) {
		if (active) {
			try {
//				UBAPIHook.ubAPIHook.ubOreTexturizer.redoOres(xInBlockCoordinates, zInBlockCoordinates, serverSideWorld);

				//The following line is the reflection-based equivalent to the above line
				redoOres.invoke(ubOreTexturizer, xInBlockCoordinates, zInBlockCoordinates, serverSideWorld);


			} catch (Exception e) {
				active = false;
				Geocraft.inst.logDebug("Problem during UndergroundBiomesPlugin replace! Has the API changed?", e);
			}
		}

	}
}
