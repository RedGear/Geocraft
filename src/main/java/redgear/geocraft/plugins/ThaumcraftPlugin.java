package redgear.geocraft.plugins;

import redgear.core.mod.IPlugin;
import redgear.core.mod.ModUtils;
import redgear.geocraft.generation.MineGenerator;
import redgear.geocraft.generation.MineRegistry;
import redgear.geocraft.mines.MineThaumInfusedStone;
import cpw.mods.fml.common.LoaderState.ModState;

public class ThaumcraftPlugin implements IPlugin {

	@Override
	public String getName() {
		return "Thaumcraft Compatibility";
	}

	@Override
	public boolean shouldRun(ModUtils mod, ModState state) {
		return false;//Mods.Thaum.isIn() && inst.getBoolean("plugins", "Thaumcraft")
	}

	@Override
	public boolean isRequired() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void preInit(ModUtils inst) {

	}

	@Override
	public void Init(ModUtils inst) {
		try {
			Class config = Class.forName("thaumcraft.common.config.Config");

			if (config != null) {
				int ID = config.getField("blockCustomOreId").getInt(null);

				MineRegistry reg = MineGenerator.reg;

				reg.registerMine(new MineThaumInfusedStone(ID));
				/*
				 * reg.registerIgnore(ID, 1);
				 * reg.registerIgnore(ID, 2);
				 * reg.registerIgnore(ID, 3);
				 * reg.registerIgnore(ID, 4);
				 * reg.registerIgnore(ID, 5);
				 * reg.registerIgnore(ID, 6);
				 */
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
			//inst.logDebug("Thaumcraft config reflection failed", e);
		}

	}

	@Override
	public void postInit(ModUtils inst) {

	}
}
