package redgear.geocraft.plugins;

import redgear.core.compat.Mods;
import redgear.core.mod.IPlugin;
import redgear.core.mod.ModUtils;
import redgear.geocraft.generation.MineGenerator;
import redgear.geocraft.generation.MineRegistry;
import redgear.geocraft.mines.MineThaumInfusedStone;

public class ThaumcraftPlugin implements IPlugin {

	@Override
	public void preInit(ModUtils inst) {
		
	}

	@Override
	public void Init(ModUtils inst) {
		if(Mods.Thaum.isIn() && inst.getBoolean("plugins", "Thaumcraft")){
			try{
				Class config = Class.forName("thaumcraft.common.config.Config");
				
				if(config != null){
					int ID = config.getField("blockCustomOreId").getInt(null);
					
					MineRegistry reg = MineGenerator.reg;
					
					reg.registerMine(new MineThaumInfusedStone(ID));
					
					reg.registerIgnore(ID, 1);
					reg.registerIgnore(ID, 2);
					reg.registerIgnore(ID, 3);
					reg.registerIgnore(ID, 4);
					reg.registerIgnore(ID, 5);
					reg.registerIgnore(ID, 6);
					
				}
			}
			catch(Exception e){
				inst.logDebug("Thaumcraft config reflection failed", e);
			}
		}
	}

	@Override
	public void postInit(ModUtils inst) {
		
	}

}
