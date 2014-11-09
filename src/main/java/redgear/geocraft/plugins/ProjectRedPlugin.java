package redgear.geocraft.plugins;

import net.minecraft.block.Block;
import redgear.core.mod.IPlugin;
import redgear.core.mod.ModUtils;
import redgear.core.util.SimpleItem;
import redgear.geocraft.core.Geocraft;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.LoaderState.ModState;
import cpw.mods.fml.common.registry.GameRegistry;

public class ProjectRedPlugin implements IPlugin {

	@Override
	public String getName() {
		return "Project Red Compatability";
	}

	@Override
	public boolean shouldRun(ModUtils mod, ModState state) {
		return Loader.isModLoaded("ProjRed|Exploration") && mod.getBoolean("Plugins", "ProjectRed");
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

	}

	@Override
	public void postInit(ModUtils mod) {
		Block block = GameRegistry.findBlock("ProjRed|Exploration", "projectred.exploration.stone");

		Geocraft.inst.logDebug("ProjectRed Stone: ", block == null ? "null" : block);

		if (block != null) {
			mod.registerOre("blockMarble", new SimpleItem(block, 0));
			mod.registerOre("blockMarble", new SimpleItem(block, 1));

			mod.registerOre("blockBasalt", new SimpleItem(block, 2));
			mod.registerOre("blockBasalt", new SimpleItem(block, 3));
			mod.registerOre("blockBasalt", new SimpleItem(block, 4));
		}
	}

}
