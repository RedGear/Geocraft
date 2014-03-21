package redgear.geocraft.plugins;

import redgear.core.mod.IPlugin;
import redgear.core.mod.ModUtils;
import redgear.core.mod.Mods;
import cpw.mods.fml.common.LoaderState.ModState;

public class ForestryPlugin implements IPlugin{
	
	@Override
	public String getName() {
		return "Forestry Compatibility";
	}

	@Override
	public boolean shouldRun(ModUtils inst, ModState state) {
		return false; //inst.getBoolean("plugins", "Forestry") && Mods.Forestry.isIn();
	}

	@Override
	public boolean isRequired() {
		return false;
	}

	@Override
	public void preInit(ModUtils inst) {
			try{
				Class clazz = Class.forName("forestry.core.config.Config");
				if(clazz != null){
					clazz.getField("generateCopperOre").setBoolean(null, false);
					clazz.getField("generateTinOre").setBoolean(null, false);
					
					
					
				}
				/*
				clazz = Class.forName("forestry.core.config.ForestryBlock");
				
				if(clazz != null){
					int ID = ((Block) clazz.getField("resources").get(null)).blockID;
					
					MineGenerator.generateCopper(new SimpleItem(ID, 1));
					MineGenerator.generateTin(new SimpleItem(ID, 2));
				}*/
				
			}
			catch(Exception e){
				throw new RuntimeException(e);
				//inst.logDebug("Forestry config reflection failed", e);
			}
			
			
			
		
	}

	@Override
	public void Init(ModUtils inst) {
		
	}

	@Override
	public void postInit(ModUtils inst) {
		
	}
}
