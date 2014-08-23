package redgear.geocraft.plugins;

import java.io.File;

import redgear.core.mod.IPlugin;
import redgear.core.mod.ModUtils;
import redgear.core.mod.Mods;
import redgear.core.util.SimpleOre;
import redgear.geocraft.core.Geocraft;
import redgear.geocraft.core.GeocraftConfig;
import redgear.geocraft.generation.MineGenerator;
import cpw.mods.fml.common.LoaderState.ModState;

public class ThermalExpansionPlugin implements IPlugin {

	@Override
	public String getName() {
		return "Thermal Expansion Compatibility";
	}

	@Override
	public boolean shouldRun(ModUtils mod, ModState state) {
		return Mods.ThermalExpansion.isIn() && mod.getBoolean("plugins", "ThermalExpansion");
	}

	@Override
	public boolean isRequired() {
		return false;
	}

	@Override
	public void preInit(ModUtils inst) {
		try {
			Object config = Class.forName("thermalexpansion.ThermalExpansion").getField("config").get(null);

			config.getClass().getMethod("set", String.class, String.class, boolean.class).invoke(config, "world", "GenerateDefaultFiles", false);

			File configDir = (File) Class.forName("cofh.core.CoFHProps").getField("configDir").get(null);
			File configFile = new File(configDir, "/cofh/world/ThermalExpansion-Ores.json");

			if (configFile.exists())
				configFile.delete();

		} catch (Throwable t) {
			Geocraft.inst.myLogger.warn("Thermal Expansion reflection failed!");
			Geocraft.inst.logDebug("Thermal Expansion reflection failed!", t);
		}
	}

	@Override
	public void Init(ModUtils inst) {
		MineGenerator.reg.addNewOre(new SimpleOre("oreNickel"), GeocraftConfig.stone, 2, 8);
	}

	@Override
	public void postInit(ModUtils inst) {

	}
}
