package redgear.geocraft.core;

import cpw.mods.fml.common.LoaderState.ModState;
import redgear.core.mod.IPlugin;
import redgear.core.mod.ModUtils;

public class GeocraftConfig implements IPlugin{

	public static boolean cylinderMode = true; //Forces all unknown ores to spawn in Cylinder shaped mines. If false unknonwn ores will be generated like vanilla only ignoring height.
	public static boolean complexMines = true; //Known ores will get their complex mine types. Overrides CylinderMode for the ores it effects.
	
	public static boolean genTrace = true; //Generates trace veins for cylinder mines and complex ores that allow it. Only applies to these if those mode are active.
	public static boolean complexOres = true; //Known ores will get their complex block and item types activated. False will use vanilla blocks. 
	public static boolean toughMode = false; //Changes the hardness of complex ores to require stronger tools. Defalts to off since it requires other mods to work. 
	
	public static boolean spiderVeins = false; //Uses a new spidery vein type for cyliderMode veins.
	public static boolean spiderMetals = true; //Uses a new spidery vein type for iron, copper, tin, lead and silver. Only valid if complexMines is turned on.
	
	public static boolean forceCopper = false; //forces copper to generate even if it's not found.
	public static boolean forceTin = false; //forces tin to generate even if it's not found.
	public static boolean forceSilver = false; //forces silver to generate even if it's not found.
	public static boolean forceLead = false; //forces lead to generate even if it's not found.
	
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
		//toughMode = mod.getBoolean(cat, "toughMode", "Changes the hardness of complex ores to require stronger tools. Defalts to off since it requires other mods to work. ", toughMode);
		
		spiderVeins = mod.getBoolean(cat, "spiderVeins", "Uses a new spidery vein type for cyliderMode veins.", spiderVeins);
		spiderMetals = mod.getBoolean(cat, "spiderMetals", "Uses a new spidery vein type for iron, copper, tin, lead and silver. Only valid if complexMines is turned on.", spiderMetals);
		
		forceCopper = mod.getBoolean(cat, "forceCopper", "forces copper to generate even if it's not found.", forceCopper);
		forceTin = mod.getBoolean(cat, "forceTin", "forces tin to generate even if it's not found.", forceTin);
		forceSilver = mod.getBoolean(cat, "forceSilver", "forces silver to generate even if it's not found.", forceSilver);
		forceLead = mod.getBoolean(cat, "forceLead", "forces lead to generate even if it's not found.", forceLead);
	}
	
	@Override
	public void Init(ModUtils mod) {
		
	}
	
	@Override
	public void postInit(ModUtils mod) {
		
	}
	
}
