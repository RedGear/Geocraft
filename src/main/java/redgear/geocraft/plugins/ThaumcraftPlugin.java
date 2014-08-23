package redgear.geocraft.plugins;

import net.minecraft.item.Item;
import redgear.core.api.item.ISimpleItem;
import redgear.core.mod.IPlugin;
import redgear.core.mod.ModUtils;
import redgear.core.mod.Mods;
import redgear.core.util.ItemRegUtil;
import redgear.core.util.SimpleItem;
import redgear.geocraft.core.GeocraftConfig;
import redgear.geocraft.generation.MineGenerator;
import redgear.geocraft.generation.MineRegistry;
import redgear.geocraft.mines.MineCylinder;
import redgear.geocraft.mines.MineThaumInfusedStone;
import cpw.mods.fml.common.LoaderState.ModState;

public class ThaumcraftPlugin implements IPlugin {

	@Override
	public String getName() {
		return "Thaumcraft Compatibility";
	}

	@Override
	public boolean shouldRun(ModUtils mod, ModState state) {
		return Mods.Thaum.isIn() && mod.getBoolean("plugins", "Thaumcraft");
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

		if(Mods.Thaum.isIn() && inst.getBoolean("plugins", "Thaumcraft")){
			try{
				Class<?> blocks = Class.forName("thaumcraft.common.config.Config");

				if(blocks != null){
					Item block = ItemRegUtil.findItem(Mods.Thaum, "blockCustomOre").item;
					
					

					MineRegistry reg = MineGenerator.reg;
					ISimpleItem stone = GeocraftConfig.stone;

					
					SimpleItem cinnibar = new SimpleItem(block, 0);
					SimpleItem air = new SimpleItem(block, 1);
					SimpleItem fire = new SimpleItem(block, 2);
					SimpleItem water = new SimpleItem(block, 3);
					SimpleItem earth = new SimpleItem(block, 4);
					SimpleItem order = new SimpleItem(block, 5);
					SimpleItem entropy = new SimpleItem(block, 6);
					SimpleItem amber = new SimpleItem(block, 7);
					
					reg.registerMine(new MineThaumInfusedStone(cinnibar));


					reg.registerTrace("CinnibarTrace", cinnibar, stone, 4);
					reg.registerTrace("AirInfusedStoneTrace", air, stone, 4);
					reg.registerTrace("FireInfusedStoneTrace", fire, stone, 4);
					reg.registerTrace("WaterInfusedStoneTrace", water, stone, 4);
					reg.registerTrace("EarthInfusedStoneTrace", earth, stone, 4);
					reg.registerTrace("OrderInfusedStoneTrace", order, stone, 4);
					reg.registerTrace("EntropyInfusedStoneTrace", entropy, stone, 4);
					reg.registerTrace("AmberTrace", amber, stone, 4);



					reg.registerMine(new MineCylinder("Amber", amber, stone, 4, 8, 4));
					reg.registerMine(new MineCylinder("Cinnibar", cinnibar, stone, 4, 8, 4));
					
					
				}
				
				
				Class<?> config = Class.forName("thaumcraft.common.config.Config");

				if(config != null){

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
