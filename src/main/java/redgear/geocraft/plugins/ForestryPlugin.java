package redgear.geocraft.plugins;

import net.minecraft.block.Block;
import redgear.core.mod.IPlugin;
import redgear.core.mod.ModUtils;
import redgear.core.mod.Mods;
import redgear.core.util.SimpleItem;
import redgear.geocraft.generation.MineGenerator;
import cpw.mods.fml.common.LoaderState.ModState;
import cpw.mods.fml.common.registry.GameRegistry;

public class ForestryPlugin implements IPlugin {

	@Override
	public String getName() {
		return "Forestry Compatibility";
	}

	@Override
	public boolean shouldRun(ModUtils inst, ModState state) {
		return inst.getBoolean("plugins", "Forestry") && Mods.Forestry.isIn();
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

	}

	@Override
	public void postInit(ModUtils inst) {
		try {
			Class<?> clazz = Class.forName("forestry.core.config.Config");
			if (clazz != null) {
				clazz.getField("generateCopperOre").setBoolean(null, false);
				clazz.getField("generateTinOre").setBoolean(null, false);

			}

			Block ore = GameRegistry.findBlock("Forestry", "tile.resources");

			if (ore != null) {
				MineGenerator.generateCopper(new SimpleItem(ore, 1));
				MineGenerator.generateTin(new SimpleItem(ore, 2));

			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
