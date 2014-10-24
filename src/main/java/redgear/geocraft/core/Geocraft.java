package redgear.geocraft.core;

import net.minecraft.init.Blocks;
import redgear.core.mod.ModUtils;
import redgear.core.util.SimpleItem;
import redgear.geocraft.generation.MineGenerator;
import redgear.geocraft.generation.MineRegistry;
import redgear.geocraft.mines.MineVanilla;
import redgear.geocraft.plugins.*;
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

	public static boolean init = false;

	public Geocraft() {
		addPlugin(new GeocraftConfig());
		addPlugin(new StonePlugin());
		addPlugin(new ComplexOresPlugin());
        addPlugin(new MultiPartPlugin());
        addPlugin(new AE2Plugin());

		addPlugin(new ThermalExpansionPlugin());
		addPlugin(new ForestryPlugin());
		addPlugin(new ThaumcraftPlugin());
		addPlugin(new NetherOresPlugin());
	}

	@Override
	public void PreInit(FMLPreInitializationEvent event) {
		new MineGenerator(this);
		MineGenerator.reg.load();
	}

	@Override
	protected void Init(FMLInitializationEvent event) {
		final MineRegistry reg = MineGenerator.reg;
		reg.registerMine(new MineVanilla("Dirt", new SimpleItem(Blocks.dirt), GeocraftConfig.stone, 20, 32));//dirt
		reg.registerMine(new MineVanilla("Gravel", new SimpleItem(Blocks.gravel), GeocraftConfig.stone, 10, 32));//gravel
		reg.registerMine(new MineVanilla("Sand", new SimpleItem(Blocks.sand), GeocraftConfig.stone, 10, 32));//sand
		reg.registerMine(new MineVanilla("SandStone", new SimpleItem(Blocks.sandstone), GeocraftConfig.stone, 10, 32));//sandstone
	}

	@Override
	protected void PostInit(FMLPostInitializationEvent event) {
		init = true;
		MineGenerator.reg.save();
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
