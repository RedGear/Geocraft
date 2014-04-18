package redgear.geocraft.core;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import cpw.mods.fml.common.LoaderState.ModState;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import redgear.core.mod.IPlugin;
import redgear.core.mod.ModUtils;
import redgear.core.util.SimpleItem;
import redgear.geocraft.block.SubBlockGeoOre;

public class GeocraftConfig implements IPlugin{
	
public static final SimpleItem stone = new SimpleItem(Blocks.stone, 0);
	
	public static SimpleItem kimberlite;
	public static SimpleItem diamondOre;
	
	public static SimpleItem coalOre;
	public static SimpleItem coalDenseOre;

	public static SimpleItem terraQuartzOre;
	public static SimpleItem goldOre;
	
	public static SimpleItem copperLump;
	public static SimpleItem tinLump;
	public static SimpleItem silverLump;
	public static SimpleItem leadLump;
	
	public static SimpleItem copperOre;
	public static SimpleItem tinOre;
	public static SimpleItem galenaOre;
	
	public static SubBlockGeoOre galenaOreBlock;
	
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
	public static boolean hasSilver = false;
	public static boolean hasLead = false;
	

	public static boolean cylinderMode = true; //Forces all unknown ores to spawn in Cylinder shaped mines. If false unknonwn ores will be generated like vanilla only ignoring height.
	public static boolean complexMines = true; //Known ores will get their complex mine types. Overrides CylinderMode for the ores it effects.
	
	public static boolean genTrace = true; //Generates trace veins for cylinder mines and complex ores that allow it. Only applies to these if those mode are active.
	public static boolean complexOres = true; //Known ores will get their complex block and item types activated. False will use vanilla blocks. 
	public static boolean toughMode = false; //Changes the hardness of complex ores to require stronger tools. Defalts to off since it requires other mods to work. 
	
	public static boolean spiderVeins = false; //Uses a new spidery vein type for cyliderMode veins.
	public static boolean spiderMetals = true; //Uses a new spidery vein type for iron, copper, tin, lead and silver. Only valid if complexMines is turned on.
	
	@Override
	public String getName() {
		return "Configuration";
	}
	
	@Override
	public boolean shouldRun(ModUtils mod, ModState state) {
		return true;
	}
	
	@Override
	public boolean isRequired() {
		return true;
	}
	
	@Override
	public void preInit(ModUtils mod) {
		String cat = "GenMode";
		
		cylinderMode = mod.getBoolean(cat, "cylinderMode", "Forces all unknown ores to spawn in Cylinder shaped mines. If false unknonwn ores will be generated like vanilla only ignoring height.", cylinderMode);
		complexMines = mod.getBoolean(cat, "complexMines", "Known ores will get their complex mine types. Overrides CylinderMode for the ores it effects.", complexMines);
		
		genTrace = mod.getBoolean(cat, "genTrace", "Generates trace veins for cylinder mines and complex ores that allow it. Only applies to these if those mode are active.", genTrace);
		complexOres = mod.getBoolean(cat, "complexOres", "Known ores will get their complex block and item types activated. False will use vanilla blocks.", complexOres);
		//toughMode isn't ready yet.
		toughMode = mod.getBoolean(cat, "toughMode", "Changes the hardness of complex ores to require stronger tools. Defalts to off since it requires other mods to work. ", toughMode);
		
		spiderVeins = mod.getBoolean(cat, "spiderVeins", "Uses a new spidery vein type for cyliderMode veins.", spiderVeins);
		spiderMetals = mod.getBoolean(cat, "spiderMetals", "Uses a new spidery vein type for iron, copper, tin, lead and silver. Only valid if complexMines is turned on.", spiderMetals);
	}
	
	@Override
	public void Init(ModUtils mod) {
		
	}
	
	@Override
	public void postInit(ModUtils mod) {
		
	}
	
}
