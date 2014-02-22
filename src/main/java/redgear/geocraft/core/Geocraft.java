package redgear.geocraft.core;

import net.minecraft.init.Blocks;
import redgear.core.mod.ModUtils;
import redgear.core.util.SimpleItem;
import redgear.geocraft.generation.MineGenerator;
import redgear.geocraft.plugins.ForestryPlugin;
import redgear.geocraft.plugins.StonePlugin;
import redgear.geocraft.plugins.ThaumcraftPlugin;
import redgear.geocraft.plugins.ThermalExpansionPlugin;
import redgear.geocraft.plugins.VanillaOresPlugin;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = "redgear_geocraft", name = "Geocraft", version = "@ModVersion@", dependencies = "required-after:redgear_core;after:ThermalExpansion;")
public class Geocraft extends ModUtils {

	@Instance("redgear_geocraft")
	public static ModUtils inst;

	public static final SimpleItem stone = new SimpleItem(Blocks.stone);
	
	public Geocraft(){
		addPlugin(new StonePlugin());
		addPlugin(new VanillaOresPlugin());
		
		addPlugin(new ThermalExpansionPlugin());
		addPlugin(new ForestryPlugin());
		addPlugin(new ThaumcraftPlugin());
	}

	@Override
	public void PreInit(FMLPreInitializationEvent event) {
		MineGenerator.inst = new MineGenerator(this);
	}

	@Override
	protected void Init(FMLInitializationEvent event) {

	}

	@Override
	protected void PostInit(FMLPostInitializationEvent event) {

	}

	@Override
	@EventHandler
	public void PreInitialization(FMLPreInitializationEvent event) {
		super.PreInitialization(event);
	}

	@Override
	@EventHandler
	public void Initialization(FMLInitializationEvent event) {
		super.Initialization(event);
	}

	@Override
	@EventHandler
	public void PostInitialization(FMLPostInitializationEvent event) {
		super.PostInitialization(event);
	}
}
