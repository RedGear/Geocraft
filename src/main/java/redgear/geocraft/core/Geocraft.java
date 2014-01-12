package redgear.geocraft.core;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraftforge.oredict.OreDictionary;
import redgear.core.block.MetaBlock;
import redgear.core.block.SubBlock;
import redgear.core.block.SubBlockDifferentDrop;
import redgear.core.mod.ModUtils;
import redgear.core.util.SimpleItem;
import redgear.geocraft.api.MineManager;
import redgear.geocraft.generation.MineGenerator;
import redgear.geocraft.generation.MineRegistry;
import redgear.geocraft.mines.MineDiamond;
import redgear.geocraft.mines.MineFlat;
import redgear.geocraft.mines.MineVanilla;
import redgear.geocraft.plugins.ForestryPlugin;
import redgear.geocraft.plugins.ThaumcraftPlugin;
import redgear.geocraft.plugins.ThermalExpansionPlugin;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.registry.GameRegistry;

@Mod(modid = "RedGear|Geocraft", name= "Geocraft", version = "@ModVersion@", dependencies = "required-after:RedGear|Core;after:ThermalExpansion;")
public class Geocraft extends ModUtils {

	public Geocraft() {
		super(2650, 25500);
	}
	
	@Instance("RedGear|Geocraft")
	public static ModUtils inst;
	
	public static final SimpleItem stone = new SimpleItem(Block.stone);
    
    @Override
    public void PreInit(FMLPreInitializationEvent event){
    	MineGenerator.inst = new MineGenerator(this);
        
        stone();
		registration();
        
		addPlugin(new ThermalExpansionPlugin());
        addPlugin(new ForestryPlugin());
        addPlugin(new ThaumcraftPlugin());
    }

	@Override
	protected void Init(FMLInitializationEvent event) {
		
	}

	@Override
	protected void PostInit(FMLPostInitializationEvent event) {
		
	}
	
	private void registration(){
		final MineRegistry reg = MineGenerator.reg;
        
        reg.registerMine(new MineVanilla("Dirt", 1, 20, new SimpleItem(Block.dirt), stone, 32));//dirt
    	reg.registerMine(new MineVanilla("Gravel", 1, 10, new SimpleItem(Block.gravel), stone, 32));//gravel
        reg.registerMine(new MineVanilla("Sand", 1, 10, new SimpleItem(Block.sand), stone, 32));//sand
        reg.registerMine(new MineVanilla("SandStone", 1, 10, new SimpleItem(Block.sandStone), stone, 32));//sandstone
    	if(getBoolean("RegisterSandstoneAsStone"))
    		OreDictionary.registerOre("stone", Block.sandStone);
    	
    	reg.registerMine(new MineFlat("Coal", 2 * reg.defaultDensityRate * reg.defaultDensityRate, 60 * reg.defaultDensityRate, new SimpleItem(Block.oreCoal), stone));//Coal
    	reg.registerTrace("CoalTrace", new SimpleItem(Block.oreCoal), stone, 60);
    	
    	reg.addNewOre(new SimpleItem(Block.oreIron), stone, 20, 8);
    	reg.addNewOre(new SimpleItem(Block.oreGold), stone, 2, 8);
    	reg.addNewOre(new SimpleItem(Block.oreRedstone), stone, 8, 7);
    	reg.addNewOre(new SimpleItem(Block.oreLapis), stone, 1, 6);
    	
    	reg.registerMine(new MineDiamond(2 * reg.defaultDensityRate * reg.defaultDensityRate, 10 * reg.defaultDensityRate)); //Diamond
    	reg.registerTrace("DiamondTrace", new SimpleItem(Block.oreDiamond), stone, 2);
	}
	
	
	private void stone(){
		MetaBlock limestone = new MetaBlock(getBlockId("Limestone"), Material.rock, "RedGear.Geocraft.Limestone");
		limestone.setHardness(0.5F).setStepSound(Block.soundStoneFootstep);
    	SimpleItem limeCobble 	= limestone.addMetaBlock(new SubBlock("limeCobble", "Limestone Cobble"));
    	SimpleItem limeClean 	= limestone.addMetaBlock(new SubBlockDifferentDrop("limeClean", "Limestone", limeCobble.getStack()));
    	SimpleItem limeCracked 	= limestone.addMetaBlock(new SubBlock("limeCracked", "Cracked Limestone Bricks"));
    	SimpleItem limeBrick 	= limestone.addMetaBlock(new SubBlockDifferentDrop("limeBrick", "Limestone Bricks", limeCracked.getStack()));
    	SimpleItem limeCarved 	= limestone.addMetaBlock(new SubBlock("limeCarved", "Carved Limestone Bricks"));
    	SimpleItem limePaver 	= limestone.addMetaBlock(new SubBlock("limePaver", "Limestone Pavingstone"));
    	
    	GameRegistry.addRecipe(limeBrick.getStack(4), "XX", "XX", 'X', limeClean.getStack());
    	GameRegistry.addRecipe(limeCarved.getStack(4), " X ", "X X", " X ", 'X', limeBrick.getStack());
    	GameRegistry.addRecipe(limePaver.getStack(9), "XXX", "XXX", "XXX", 'X', limeClean.getStack());
    	
    	addSmelting(limeCobble, limeClean);
    	addSmelting(limeCracked, limeClean);
    	addSmelting(limeCarved, limeClean);
    	addSmelting(limePaver, limeClean);
    	
    	MineManager.oreRegistry.registerMine(new MineVanilla("Limestone", 1, 5, limeClean, stone, 32));
    	registerOre("stone", limeClean);
    	registerOre("cobblestone", limeCobble);
    	
    	MetaBlock basalt = new MetaBlock(getBlockId("Basalt"), Material.rock, "RedGear.Geocraft.Basalt");
    	basalt.setHardness(4.0F).setStepSound(Block.soundStoneFootstep);
    	SimpleItem basaltCobble 	= basalt.addMetaBlock(new SubBlock("basaltCobble", "Basalt Cobble"));
    	SimpleItem basaltClean 	= basalt.addMetaBlock(new SubBlockDifferentDrop("basaltClean", "Basalt", basaltCobble.getStack()));
    	SimpleItem basaltCracked = basalt.addMetaBlock(new SubBlock("basaltCracked", "Cracked Basalt Bricks"));
    	SimpleItem basaltBrick 	= basalt.addMetaBlock(new SubBlockDifferentDrop("basaltBrick", "Basalt Bricks", basaltCracked.getStack()));
    	SimpleItem basaltCarved 	= basalt.addMetaBlock(new SubBlock("basaltCarved", "Carved Basalt Bricks"));
    	SimpleItem basaltPaver 	= basalt.addMetaBlock(new SubBlock("basaltPaver", "Basalt Pavingstone"));
    	
    	GameRegistry.addRecipe(basaltBrick.getStack(4), "XX", "XX", 'X', basaltClean.getStack());
    	GameRegistry.addRecipe(basaltCarved.getStack(4), " X ", "X X", " X ", 'X', basaltBrick.getStack());
    	GameRegistry.addRecipe(basaltPaver.getStack(9), "XXX", "XXX", "XXX", 'X', basaltClean.getStack());
    	
    	addSmelting(basaltCobble, basaltClean);
    	addSmelting(basaltCracked, basaltClean);
    	addSmelting(basaltCarved, basaltClean);
    	addSmelting(basaltPaver, basaltClean);
    	
    	MineManager.oreRegistry.registerMine(new MineVanilla("Basalt", 1, 5, basaltClean, stone, 32));
    	registerOre("stone", basaltClean);
    	registerOre("cobblestone", basaltCobble);
    	
    	
    	MetaBlock marble = new MetaBlock(getBlockId("Marble"), Material.rock, "RedGear.Geocraft.Marble");
    	marble.setHardness(4.0F).setStepSound(Block.soundStoneFootstep);
    	SimpleItem marbleCobble 	= marble.addMetaBlock(new SubBlock("marbleCobble", "Marble Cobble"));
    	SimpleItem marbleClean 	= marble.addMetaBlock(new SubBlockDifferentDrop("marbleClean", "Marble", marbleCobble.getStack()));
    	SimpleItem marbleCracked = marble.addMetaBlock(new SubBlock("marbleCracked", "Cracked Marble Bricks"));
    	SimpleItem marbleBrick 	= marble.addMetaBlock(new SubBlockDifferentDrop("marbleBrick", "Marble Bricks", marbleCracked.getStack()));
    	SimpleItem marbleCarved 	= marble.addMetaBlock(new SubBlock("marbleCarved", "Carved Marble Bricks"));
    	SimpleItem marblePaver 	= marble.addMetaBlock(new SubBlock("marblePaver", "Marble Pavingstone"));
    	
    	GameRegistry.addRecipe(marbleBrick.getStack(4), "XX", "XX", 'X', marbleClean.getStack());
    	GameRegistry.addRecipe(marbleCarved.getStack(4), " X ", "X X", " X ", 'X', marbleBrick.getStack());
    	GameRegistry.addRecipe(marblePaver.getStack(9), "XXX", "XXX", "XXX", 'X', marbleClean.getStack());
    	
    	addSmelting(marbleCobble, marbleClean);
    	addSmelting(marbleCracked, marbleClean);
    	addSmelting(marbleCarved, marbleClean);
    	addSmelting(marblePaver, marbleClean);
    	
    	MineManager.oreRegistry.registerMine(new MineVanilla("Marble", 1, 5, marbleClean, stone, 32));
    	registerOre("stone", marbleClean);
    	registerOre("cobblestone", marbleCobble);
    	
    	limestone.setCreativeTab(CreativeTabs.tabDecorations);
    	basalt.setCreativeTab(CreativeTabs.tabDecorations);
    	marble.setCreativeTab(CreativeTabs.tabDecorations);
	}
	
	@EventHandler public void PreInitialization(FMLPreInitializationEvent event){super.PreInitialization(event);}
	@EventHandler public void Initialization(FMLInitializationEvent event){super.Initialization(event);}
	@EventHandler public void PostInitialization(FMLPostInitializationEvent event){super.PostInitialization(event);}
}
