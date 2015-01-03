package redgear.geocraft.plugins;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.LinkedList;
import java.util.List;

import net.minecraft.item.ItemStack;
import net.minecraftforge.common.config.Property;
import redgear.core.api.item.ISimpleItem;
import redgear.core.mod.IPlugin;
import redgear.core.mod.ModUtils;
import redgear.core.mod.Mods;
import redgear.core.util.SimpleItem;
import redgear.geocraft.api.MineManager;
import redgear.geocraft.api.mine.Mine;
import redgear.geocraft.config.ConfigHandler;
import redgear.geocraft.core.GeocraftConfig;
import redgear.geocraft.generation.MineGenerator;
import redgear.geocraft.mines.MineCylinderComplex;
import redgear.geocraft.mines.MineVanilla;
import cpw.mods.fml.common.LoaderState.ModState;

public class NetherOresPlugin implements IPlugin {

	private List<NetherOre> oreList;
	private Method getForced;
	private Method isRegisteredSmelting;
	private Method isRegisteredMacerator;

	private Method getGroupsPerChunk;
	private Method getBlocksPerGroup;
	private Method getItemStack;
	private Method getName;

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
				Object[] ores = clazz.getEnumConstants();

				getForced = clazz.getMethod("getForced");
				isRegisteredSmelting = clazz.getMethod("isRegisteredSmelting");
				isRegisteredMacerator = clazz.getMethod("isRegisteredMacerator");

				getGroupsPerChunk = clazz.getMethod("getGroupsPerChunk");
				getBlocksPerGroup = clazz.getMethod("getBlocksPerGroup");

				getItemStack = clazz.getMethod("getItemStack", int.class);
				getName = clazz.getMethod("name");

				oreList = new LinkedList<NetherOre>();

				for (Object ore : ores)
					oreList.add(new NetherOre(ore));
			}

		} catch (Exception e) {
			mod.logDebug("Nether Ores config reflection failed", e);
		}
	}

	@Override
	public void postInit(ModUtils mod) {
		try {
			for (NetherOre ore : oreList)
				ore.register();
		} catch (Exception e) {
			mod.logDebug("Nether Ores config reflection failed", e);
		}
		
		oreList.clear();
	}

	private class NetherOre {
		Object ore;
		Mine main;
		Mine trace;

		private NetherOre(Object ore) throws IllegalAccessException,
				IllegalArgumentException, InvocationTargetException {
			int veins = (Integer) getGroupsPerChunk.invoke(ore);
			int cluster = (Integer) getBlocksPerGroup.invoke(ore);

			ItemStack stack = (ItemStack) getItemStack.invoke(ore, 1);

			String name = "NetherOre" + (String) getName.invoke(ore);

			this.ore = ore;

			ISimpleItem block = new SimpleItem(stack);

			if (GeocraftConfig.cylinderMode) {
				main = MineGenerator.reg.registerMine(new MineCylinderComplex(name, block, MineManager.netherrack, 4,
						veins, cluster, true));
				trace = MineGenerator.reg.registerTrace(name + "Trace", block, MineManager.netherrack, veins);
			} else
				main = MineGenerator.reg.registerMine(new MineVanilla(name, new SimpleItem(stack),
						MineManager.netherrack, veins, cluster));

		}

		private void register() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
			boolean test = (Boolean) getForced.invoke(ore) || (Boolean) isRegisteredSmelting.invoke(ore)
					|| (Boolean) isRegisteredMacerator.invoke(ore);

			main.isActive = test;
			ConfigHandler.inst.saveJson(main);

			if (trace != null) {
				trace.isActive = test;
				ConfigHandler.inst.saveJson(trace);
			}

		}

	}

}
