package redgear.geocraft.plugins;

import java.lang.reflect.Method;

import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;
import redgear.core.mod.IPlugin;
import redgear.core.mod.ModUtils;
import redgear.core.util.StringHelper;
import redgear.geocraft.core.Geocraft;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.LoaderState.ModState;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.GameRegistry.UniqueIdentifier;

public class ChiselPlugin implements IPlugin {

	private Method addSupport;

	@Override
	public String getName() {
		return "Chisel Compatability";
	}

	@Override
	public boolean shouldRun(ModUtils mod, ModState state) {
		return Loader.isModLoaded("chisel") && mod.getBoolean("Plugins", "Chisel");
	}

	@Override
	public boolean isRequired() {
		return false;
	}

	@Override
	public void preInit(ModUtils mod) {
		try {
			Class<?> compat = Class.forName("com.cricketcraft.chisel.compat.Compatibility");

			//public static void addSupport(String modname, String blockname, String name, int metadata, int order)
			addSupport = compat.getMethod("addSupport", String.class, String.class, String.class, int.class, int.class);
		} catch (Exception e) {
			Geocraft.inst.logWarning("Chisel Compatiblity failed!", e);
		}
	}

	@Override
	public void Init(ModUtils mod) {

	}

	@Override
	public void postInit(ModUtils mod) {

		if (addSupport == null)
			return;
		
		fromOreName("blockLimestone", "limestone");
		fromOreName("blockBasalt", "basalt");
		fromOreName("blockMarble", "marble");
	}

	private void fromOreName(String oreName, String chiselName) {
		for (ItemStack ore : OreDictionary.getOres(oreName)) {
			UniqueIdentifier id = GameRegistry.findUniqueIdentifierFor(ore.getItem());

			if (id != null)
				addSupport(id.modId, id.name, chiselName, ore.getItemDamage());
		}
	}

	private void addSupport(String modname, String blockname, String name, int metadata) {
		try {
			addSupport.invoke(null, modname, blockname, name, metadata, 99);
		} catch (Exception e) {
			Geocraft.inst.logWarning(StringHelper.concat("Chisel reflection failed for ", modname, ":", blockname), e);
		}
	}

}
