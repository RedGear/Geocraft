package redgear.geocraft.plugins;

import redgear.core.compat.Mods;
import redgear.core.mod.IPlugin;
import redgear.core.mod.ModUtils;
import redgear.core.util.SimpleItem;
import redgear.geocraft.core.Geocraft;
import redgear.geocraft.generation.MineGenerator;
import redgear.geocraft.generation.MineRegistry;
import redgear.geocraft.mines.MineCylinder;
import redgear.geocraft.mines.MineThaumInfusedStone;

public class ThaumcraftPlugin implements IPlugin {

	@Override
	public void preInit(ModUtils inst) {
		
	}

	@Override
	public void Init(ModUtils inst) {
		if(Mods.Thaum.isIn() && inst.getBoolean("plugins", "Thaumcraft")){
			try{
				Class<?> config = Class.forName("thaumcraft.common.config.Config");
				
				if(config != null){
					int ID = config.getField("blockCustomOreId").getInt(null);
					
					MineRegistry reg = MineGenerator.reg;
					SimpleItem stone = Geocraft.stone;
					
					reg.registerMine(new MineThaumInfusedStone(ID));
					SimpleItem cinnibar = new SimpleItem(ID, 0);
					SimpleItem air = new SimpleItem(ID, 1);
					SimpleItem fire = new SimpleItem(ID, 2);
					SimpleItem water = new SimpleItem(ID, 3);
					SimpleItem earth = new SimpleItem(ID, 4);
					SimpleItem order = new SimpleItem(ID, 5);
					SimpleItem entropy = new SimpleItem(ID, 6);
					SimpleItem amber = new SimpleItem(ID, 7);
					
					
					
					reg.registerTrace("CinnibarTrace", cinnibar, stone, 4);
					reg.registerTrace("AirInfusedStoneTrace", air, stone, 4);
					reg.registerTrace("FireInfusedStoneTrace", fire, stone, 4);
					reg.registerTrace("WaterInfusedStoneTrace", water, stone, 4);
					reg.registerTrace("EarthInfusedStoneTrace", earth, stone, 4);
					reg.registerTrace("OrderInfusedStoneTrace", order, stone, 4);
					reg.registerTrace("EntropyInfusedStoneTrace", entropy, stone, 4);
					reg.registerTrace("AmberTrace", amber, stone, 4);
					
					
					final int size = MineGenerator.reg.defaultDensityRate / 4;
					final int rare = size * size;
					
					
					reg.registerMine(new MineCylinder("Amber", rare, size, amber, stone, 4));
					reg.registerMine(new MineCylinder("Cinnibar", rare, size, cinnibar, stone, 4));
					
					config.getField("genCinnibar").setBoolean(null, false);
					config.getField("genAmber").setBoolean(null, false);
					config.getField("genInfusedStone").setBoolean(null, false);
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
