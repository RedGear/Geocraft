package redgear.geocraft.plugins;

import java.lang.reflect.Field;

import net.minecraft.item.ItemStack;
import redgear.core.mod.IPlugin;
import redgear.core.mod.ModUtils;
import redgear.core.util.SimpleItem;
import redgear.geocraft.core.GeocraftConfig;
import redgear.geocraft.generation.MineGenerator;
import redgear.geocraft.generation.MineRegistry;
import cpw.mods.fml.common.LoaderState.ModState;

public class ThermalExpansionPlugin implements IPlugin {

	@Override
	public String getName() {
		return "Thermal Expansion Compatibility";
	}

	@Override
	public boolean shouldRun(ModUtils mod, ModState state) {
		return false; //Mods.ThermalExpansion.isIn() && inst.getBoolean("plugins", "ThermalExpansion")
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
			Class clazz = Class.forName("thermalexpansion.block.simple.BlockOre");
			if (clazz != null) {
				Field boolArray = clazz.getField("enable");

				//                                copper, tin, silver, lead, nickel
				boolArray.set(null, new boolean[] {false, false, false, false, false });

				SimpleItem copper = new SimpleItem((ItemStack) clazz.getField("oreCopper").get(null));
				SimpleItem tin = new SimpleItem((ItemStack) clazz.getField("oreTin").get(null));
				SimpleItem silver = new SimpleItem((ItemStack) clazz.getField("oreSilver").get(null));
				SimpleItem lead = new SimpleItem((ItemStack) clazz.getField("oreLead").get(null));
				SimpleItem nickel = new SimpleItem((ItemStack) clazz.getField("oreNickel").get(null));

				MineRegistry reg = MineGenerator.reg;
				SimpleItem stone = GeocraftConfig.stone;

				MineGenerator.generateCopper(copper);
				MineGenerator.generateTin(tin);
				MineGenerator.generateSilver(silver);
				MineGenerator.generateLead(lead);
				reg.addNewOre(nickel, stone, 2, 8);
			}

		} catch (Exception e) {
			throw new RuntimeException(e);
			//inst.logDebug("Thermal Expansion config reflection failed", e);
		}

	}

	@Override
	public void postInit(ModUtils inst) {

	}
}
