package redgear.geocraft.plugins;

import java.io.File;

import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;
import redgear.core.api.item.ISimpleItem;
import redgear.core.mod.IPlugin;
import redgear.core.mod.ModUtils;
import redgear.core.util.SimpleItem;
import redgear.geocraft.core.GeocraftConfig;
import redgear.geocraft.generation.MineGenerator;
import cpw.mods.fml.common.LoaderState.ModState;
import redgear.geocraft.generation.MineRegistry;
import redgear.geocraft.mines.MineCylinderComplex;

public class ThermalFoundationPlugin implements IPlugin {

	@Override
	public String getName() {
		return "Thermal Foundation Compatibility";
	}

	@Override
	public boolean shouldRun(ModUtils mod, ModState state) {
		return mod.getBoolean("plugins", "ThermalFoundation") && Loader.isModLoaded("ThermalFoundation");
	}

	@Override
	public boolean isRequired() {
		return false;
	}

	@Override
	public void preInit(ModUtils inst) {
	}

	@Override
	public void Init(ModUtils inst) {
		try {
			Object config = Class.forName("cofh.thermalfoundation.ThermalFoundation").getField("config").get(null);

			config.getClass().getMethod("set", String.class, String.class, boolean.class).invoke(config, "World", "GenerateDefaultFiles", false);

			File configDir = (File) Class.forName("cofh.core.CoFHProps").getField("configDir").get(null);
			File configFile = new File(configDir, "/cofh/world/ThermalFoundation-Ores.json");

			if (configFile.exists())
				configFile.delete();

			Block block = GameRegistry.findBlock("ThermalFoundation", "Ore");

			MineRegistry reg = MineGenerator.reg;
			ISimpleItem stone = GeocraftConfig.stone;

			SimpleItem copper = new SimpleItem(block, 0);
			SimpleItem tin = new SimpleItem(block, 1);
			SimpleItem silver = new SimpleItem(block, 2);
			SimpleItem lead = new SimpleItem(block, 3);
			SimpleItem nickel = new SimpleItem(block, 4);
			SimpleItem platinum = new SimpleItem(block, 5);
			//SimpleItem mithril = new SimpleItem(block, 6);

			reg.registerTrace("CopperTrace", copper, stone, 4);
			reg.registerTrace("TinTrace", tin, stone, 4);
			reg.registerTrace("SilverTrace", silver, stone, 4);
			reg.registerTrace("LeadTrace", lead, stone, 4);
			reg.registerTrace("NickelTrace", nickel, stone, 4);
			reg.registerTrace("PlatinumTrace", platinum, stone, 4);
			//reg.registerTrace("MithrilTrace", mithril, stone, 4);

			reg.registerMine(new MineCylinderComplex("Copper", copper, stone, 4, 16, 6, false).setActive(!GeocraftConfig.complexMines));
			reg.registerMine(new MineCylinderComplex("Tin", tin, stone, 4, 16, 6, false).setActive(!GeocraftConfig.complexMines));
			reg.registerMine(new MineCylinderComplex("Silver", silver, stone, 4, 10, 6, false).setActive(!GeocraftConfig.complexMines));
			reg.registerMine(new MineCylinderComplex("Lead", lead, stone, 4, 8, 4, false).setActive(!GeocraftConfig.complexMines));
			reg.registerMine(new MineCylinderComplex("Nickel", nickel, stone, 4, 8, 4, false));
			reg.registerMine(new MineCylinderComplex("Platinum", platinum, stone, 4, 6, 4, false));
			//reg.registerMine(new MineCylinderComplex("Mithril", mithril, stone, 4, 16, 6, true));
		} catch (Throwable t) {
			inst.logDebug("Thermal Foundation reflection failed!", t);
		}
	}

	@Override
	public void postInit(ModUtils inst) {

	}
}
