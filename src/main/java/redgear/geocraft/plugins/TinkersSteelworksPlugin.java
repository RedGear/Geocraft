package redgear.geocraft.plugins;

import redgear.core.mod.IPlugin;
import redgear.core.mod.ModUtils;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.LoaderState.ModState;

public class TinkersSteelworksPlugin implements IPlugin{

	@Override
	public String getName() {
		return "Tinker's Steelworks Compat";
	}

	@Override
	public boolean shouldRun(ModUtils mod, ModState state) {
		return mod.getBoolean("Plugins", "TSteelworks") && Loader.isModLoaded("TSteelworks");
	}

	@Override
	public boolean isRequired() {
		return false;
	}

	@Override
	public void preInit(ModUtils mod) {
		
	}

	@Override
	public void Init(ModUtils mod) {
		try {
			Class.forName("tsteelworks.common.core.ConfigCore").getField("enableLimestoneWorldgen").setBoolean(null, false);
		} catch(Exception e){
			mod.logDebug("Tinker's Steelworks config reflection failed", e);
		}
	}

	@Override
	public void postInit(ModUtils mod) {
		
	}

}
