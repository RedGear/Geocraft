package redgear.geocraft.plugins;

import java.lang.reflect.Field;

import net.minecraft.item.ItemStack;
import redgear.core.compat.Mods;
import redgear.core.mod.IPlugin;
import redgear.core.mod.ModUtils;
import redgear.core.util.SimpleItem;
import redgear.geocraft.core.Geocraft;
import redgear.geocraft.generation.MineGenerator;
import redgear.geocraft.generation.MineRegistry;

public class ThermalExpansionPlugin implements IPlugin{

	@Override
	public void preInit(ModUtils inst) {
		
	}

	@Override
	public void Init(ModUtils inst) {
		if(Mods.ThermalExpansion.isIn() && inst.getBoolean("plugins", "ThermalExpansion")){
			try{
				Class clazz = Class.forName("thermalexpansion.block.simple.BlockOre");
				if(clazz != null){
					Field boolArray = clazz.getField("enable");
					
					//                                copper, tin, silver, lead, nickel
					boolArray.set(null, new boolean[]{false, false, false, false, false});
					
					SimpleItem copper = new SimpleItem((ItemStack) clazz.getField("oreCopper").get(null));
					SimpleItem tin = new SimpleItem((ItemStack) clazz.getField("oreTin").get(null));
					SimpleItem silver = new SimpleItem((ItemStack) clazz.getField("oreSilver").get(null));
					SimpleItem lead = new SimpleItem((ItemStack) clazz.getField("oreLead").get(null));
					SimpleItem nickel = new SimpleItem((ItemStack) clazz.getField("oreNickel").get(null));
					
					MineRegistry reg = MineGenerator.reg;
					SimpleItem stone = Geocraft.stone;
					
					MineGenerator.generateCopper(copper);
					MineGenerator.generateTin(tin);
					reg.addNewOre(silver, 	stone, 3, 16);
					reg.addNewOre(lead, 	stone, 4, 16);
					reg.addNewOre(nickel, 	stone, 2, 8);
				}
				
			}
			catch(Exception e){
				inst.logDebug("Thermal Expansion config reflection failed", e);
			}
			
		}
		
	}

	@Override
	public void postInit(ModUtils inst) {
		
	}

}
