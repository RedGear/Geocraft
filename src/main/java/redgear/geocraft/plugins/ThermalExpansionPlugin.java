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

public class ThermalExpansionPlugin implements IPlugin {

	@Override
	public void preInit(ModUtils inst) {
		inst.logDebug("TE is in: ", Mods.ThermalExpansion.isIn());

		if (Mods.ThermalExpansion.isIn() && inst.getBoolean("plugins", "ThermalExpansion"))
			try {
				Class<?> clazz = Class.forName("thermalexpansion.block.simple.BlockOre");
				if (clazz != null) {
					Field boolArray = clazz.getField("enable");
					inst.logDebug("Got boolArray");

					//                                copper, tin, silver, lead, nickel
					boolArray.set(null, new boolean[] {false, false, false, false, false });
					inst.logDebug("Set array to false");
				} else
					inst.logDebug("Class in null");

			} catch (Exception e) {
				inst.logDebug("Thermal Expansion config reflection failed", e);
			}
	}

	@Override
	public void Init(ModUtils inst) {
		inst.logDebug("TE is in: ", Mods.ThermalExpansion.isIn());
		if (Mods.ThermalExpansion.isIn() && inst.getBoolean("plugins", "ThermalExpansion"))
			try {
				Class<?> clazz = Class.forName("thermalexpansion.block.simple.BlockOre");
				if (clazz != null) {
					SimpleItem copper = new SimpleItem((ItemStack) clazz.getField("oreCopper").get(null));
					SimpleItem tin = new SimpleItem((ItemStack) clazz.getField("oreTin").get(null));
					SimpleItem silver = new SimpleItem((ItemStack) clazz.getField("oreSilver").get(null));
					SimpleItem lead = new SimpleItem((ItemStack) clazz.getField("oreLead").get(null));
					SimpleItem nickel = new SimpleItem((ItemStack) clazz.getField("oreNickel").get(null));

					inst.logDebug(copper, ", ", tin, ", ", silver, ", ", lead, ", ", nickel);

					MineRegistry reg = MineGenerator.reg;
					SimpleItem stone = Geocraft.stone;

					MineGenerator.generateCopper(copper);
					MineGenerator.generateTin(tin);
					MineGenerator.generateSilver(silver);
					MineGenerator.generateLead(lead);
					reg.addNewOre(nickel, stone, 2, 8);
					inst.logDebug("Finished");
				} else
					inst.logDebug("Class in null");

			} catch (Exception e) {
				inst.logDebug("Thermal Expansion config reflection failed", e);
			}
	}

	@Override
	public void postInit(ModUtils inst) {

	}

}
