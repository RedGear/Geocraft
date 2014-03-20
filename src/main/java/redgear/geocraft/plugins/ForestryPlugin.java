package redgear.geocraft.plugins;

import net.minecraft.block.Block;
import redgear.core.compat.Mods;
import redgear.core.mod.IPlugin;
import redgear.core.mod.ModUtils;
import redgear.core.util.SimpleItem;
import redgear.geocraft.generation.MineGenerator;

public class ForestryPlugin implements IPlugin{

	@Override
	public void preInit(ModUtils inst) {
		
	}

	@Override
	public void Init(ModUtils inst) {
		if(Mods.Forestry.isIn() && inst.getBoolean("plugins", "Forestry")){
			try{
				Class<?> clazz = Class.forName("forestry.core.config.Config");
				if(clazz != null){
					clazz.getField("generateCopperOre").setBoolean(null, false);
					clazz.getField("generateTinOre").setBoolean(null, false);
					
					
					
				}
				
				clazz = Class.forName("forestry.core.config.ForestryBlock");
				
				if(clazz != null){
					int ID = ((Block) clazz.getField("resources").get(null)).blockID;
					
					MineGenerator.generateCopper(new SimpleItem(ID, 1));
					MineGenerator.generateTin(new SimpleItem(ID, 2));
				}
				
			}
			catch(Exception e){
				inst.logDebug("Forestry config reflection failed", e);
			}
			
			
			
		}
	}

	@Override
	public void postInit(ModUtils inst) {
		
	}

}
