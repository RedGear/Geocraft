package redgear.geocraft.core;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import redgear.core.mod.ModUtils;
import redgear.core.util.SimpleItem;
import redgear.geocraft.generation.MineGenerator;
import redgear.geocraft.generation.MineRegistry;
import redgear.geocraft.mines.MineVanilla;
import redgear.geocraft.plugins.ComplexOresPlugin;
import redgear.geocraft.plugins.ForestryPlugin;
import redgear.geocraft.plugins.StonePlugin;
import redgear.geocraft.plugins.ThaumcraftPlugin;
import redgear.geocraft.plugins.ThermalExpansionPlugin;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@Mod(modid = "redgear_geocraft", name = "Geocraft", version = "@ModVersion@", dependencies = "required-after:redgear_core;after:ThermalExpansion;")
public class Geocraft extends ModUtils {

	@Instance("redgear_geocraft")
	public static ModUtils inst;

	public static final SimpleItem stone = new SimpleItem(Blocks.stone, 0);
	
	public static SimpleItem kimberlite;
	public static SimpleItem diamondOre;
	
	public static SimpleItem coalOre;
	public static SimpleItem coalDenseOre;

	public static SimpleItem terraQuartzOre;
	public static SimpleItem goldOre;
	
	public static SimpleItem marble;
	
	public static CreativeTabs geoTab = new CreativeTabs("Geocraft"){

		@SideOnly(Side.CLIENT)
	    public int func_151243_f(){
			return marble.meta;
		}
		
		@Override
		public Item getTabIconItem() {
			return marble.getItem();
		}
		
	};
	
	public Geocraft(){
		addPlugin(new StonePlugin());
		addPlugin(new ComplexOresPlugin());
		
		addPlugin(new ThermalExpansionPlugin());
		addPlugin(new ForestryPlugin());
		addPlugin(new ThaumcraftPlugin());
	}

	@Override
	public void PreInit(FMLPreInitializationEvent event) {
		new MineGenerator(this);
		final MineRegistry reg = MineGenerator.reg;
		reg.registerMine(new MineVanilla("Dirt", 1, 20, new SimpleItem(Blocks.dirt, 0), stone, 32));//dirt
		reg.registerMine(new MineVanilla("Gravel", 1, 10, new SimpleItem(Blocks.gravel, 0), stone, 32));//gravel
		reg.registerMine(new MineVanilla("Sand", 1, 10, new SimpleItem(Blocks.sand, 0), stone, 32));//sand
		SimpleItem sandStone = new SimpleItem(Blocks.sandstone, 0);
		
		reg.registerMine(new MineVanilla("SandStone", 1, 10, sandStone, stone, 32));//sandstone
		if (getBoolean("RegisterSandstoneAsStone"))
			registerOre("stone", sandStone);
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
