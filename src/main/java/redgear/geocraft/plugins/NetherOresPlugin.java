package redgear.geocraft.plugins;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import net.minecraft.item.ItemStack;
import net.minecraftforge.common.config.Property;
import redgear.core.mod.IPlugin;
import redgear.core.mod.ModUtils;
import redgear.core.mod.Mods;
import redgear.core.util.SimpleItem;
import redgear.geocraft.api.MineManager;
import redgear.geocraft.api.mine.Mine;
import redgear.geocraft.generation.MineGenerator;
import redgear.geocraft.generation.MineRegistry;
import cpw.mods.fml.common.LoaderState.ModState;

public class NetherOresPlugin implements IPlugin {

	@Override
	public String getName() {
		return "Nether Ores Compatability";
	}

	@Override
	public boolean shouldRun(ModUtils mod, ModState state) {
		return mod.getBoolean("plugins", "NetherOres") && Mods.NetherOres.isIn();
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
			Class<?> clazz = Class.forName("powercrystals.netherores.NetherOresCore");
			if (clazz != null) {
				Field field = clazz.getField("enableWorldGen");
				Property enableWorldGen = (Property) field.get(null);
				enableWorldGen.set(false);
				field.set(null, enableWorldGen);
			}
			clazz = Class.forName("powercrystals.netherores.ores.Ores");
			if (clazz != null) {

				MineRegistry reg = MineGenerator.reg;
				Object[] ores = clazz.getEnumConstants();

				Method getForced = clazz.getMethod("getForced");
				Method isRegisteredSmelting = clazz.getMethod("isRegisteredSmelting");
				Method isRegisteredMacerator = clazz.getMethod("isRegisteredMacerator");

				Method getGroupsPerChunk = clazz.getMethod("getGroupsPerChunk");
				Method getBlocksPerGroup = clazz.getMethod("getBlocksPerGroup");

				Method getItemStack = clazz.getMethod("getItemStack", int.class);

				int index = 0;
				for (Object ore : ores) {
					int veins = (Integer) getGroupsPerChunk.invoke(ore);
					int cluster = (Integer) getBlocksPerGroup.invoke(ore);

					ItemStack stack = (ItemStack) getItemStack.invoke(ore, index++);

					Mine mine = reg.addNewOre(new SimpleItem(stack), MineManager.netherrack, veins, cluster);

					mine.isActive = (Boolean) getForced.invoke(ore) || (Boolean) isRegisteredSmelting.invoke(ore)
							|| (Boolean) isRegisteredMacerator.invoke(ore);
				}
			}

		} catch (Exception e) {
			mod.logDebug("Nether Ores config reflection failed", e);
		}
	}

	@Override
	public void postInit(ModUtils mod) {
		
	}

}
