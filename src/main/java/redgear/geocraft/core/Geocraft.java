package redgear.geocraft.core;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
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
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.registry.GameRegistry;

@Mod(modid = "redgear_geocraft", name = "Geocraft", version = "@ModVersion@", dependencies = "required-after:redgear_core;after:ThermalExpansion;")
public class Geocraft extends ModUtils {

	@Instance("redgear_geocraft")
	public static ModUtils inst;

	public static final SimpleItem stone = new SimpleItem(Blocks.stone);

	@Override
	public void PreInit(FMLPreInitializationEvent event) {
		MineGenerator.inst = new MineGenerator(this);

		stone();
		registration();

		//addPlugin(new ThermalExpansionPlugin());
		//addPlugin(new ForestryPlugin());
		//addPlugin(new ThaumcraftPlugin());
	}

	@Override
	protected void Init(FMLInitializationEvent event) {

	}

	@Override
	protected void PostInit(FMLPostInitializationEvent event) {

	}

	private void registration() {
		final MineRegistry reg = MineGenerator.reg;

		reg.registerMine(new MineVanilla("Dirt", 1, 20, new SimpleItem(Blocks.dirt), stone, 32));//dirt
		reg.registerMine(new MineVanilla("Gravel", 1, 10, new SimpleItem(Blocks.gravel), stone, 32));//gravel
		reg.registerMine(new MineVanilla("Sand", 1, 10, new SimpleItem(Blocks.sand), stone, 32));//sand
		reg.registerMine(new MineVanilla("SandStone", 1, 10, new SimpleItem(Blocks.sandstone), stone, 32));//sandstone
		if (getBoolean("RegisterSandstoneAsStone"))
			OreDictionary.registerOre("stone", Blocks.sandstone);

		reg.registerMine(new MineFlat("Coal", 2 * reg.defaultDensityRate * reg.defaultDensityRate,
				60 * reg.defaultDensityRate, new SimpleItem(Blocks.coal_ore), stone));//Coal
		reg.registerTrace("CoalTrace", new SimpleItem(Blocks.coal_ore), stone, 60);

		reg.addNewOre(new SimpleItem(Blocks.iron_ore), stone, 20, 8);
		reg.addNewOre(new SimpleItem(Blocks.gold_ore), stone, 2, 8);
		reg.addNewOre(new SimpleItem(Blocks.redstone_ore), stone, 8, 7);
		reg.addNewOre(new SimpleItem(Blocks.lapis_ore), stone, 1, 6);

		reg.registerMine(new MineDiamond(2 * reg.defaultDensityRate * reg.defaultDensityRate,
				10 * reg.defaultDensityRate)); //Diamond
		reg.registerTrace("DiamondTrace", new SimpleItem(Blocks.diamond_ore), stone, 2);
	}

	private void stone() {
		MetaBlock limestone = new MetaBlock(Material.rock, "RedGear.Geocraft.Limestone");
		limestone.setHardness(0.5F).setStepSound(Block.soundTypeStone);
		SimpleItem limeCobble = limestone.addMetaBlock(new SubBlock("limeCobble"));
		SimpleItem limeClean = limestone.addMetaBlock(new SubBlockDifferentDrop("limeClean", limeCobble.getStack()));
		SimpleItem limeCracked = limestone.addMetaBlock(new SubBlock("limeCracked"));
		SimpleItem limeBrick = limestone.addMetaBlock(new SubBlockDifferentDrop("limeBrick", limeCracked.getStack()));
		SimpleItem limeCarved = limestone.addMetaBlock(new SubBlock("limeCarved"));
		SimpleItem limePaver = limestone.addMetaBlock(new SubBlock("limePaver"));

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

		MetaBlock basalt = new MetaBlock(Material.rock, "RedGear.Geocraft.Basalt");
		basalt.setHardness(4.0F).setStepSound(Block.soundTypeStone);
		SimpleItem basaltCobble = basalt.addMetaBlock(new SubBlock("basaltCobble"));
		SimpleItem basaltClean = basalt.addMetaBlock(new SubBlockDifferentDrop("basaltClean", basaltCobble.getStack()));
		SimpleItem basaltCracked = basalt.addMetaBlock(new SubBlock("basaltCracked"));
		SimpleItem basaltBrick = basalt
				.addMetaBlock(new SubBlockDifferentDrop("basaltBrick", basaltCracked.getStack()));
		SimpleItem basaltCarved = basalt.addMetaBlock(new SubBlock("basaltCarved"));
		SimpleItem basaltPaver = basalt.addMetaBlock(new SubBlock("basaltPaver"));

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

		MetaBlock marble = new MetaBlock(Material.rock, "RedGear.Geocraft.Marble");
		marble.setHardness(4.0F).setStepSound(Block.soundTypeStone);
		SimpleItem marbleCobble = marble.addMetaBlock(new SubBlock("marbleCobble"));
		SimpleItem marbleClean = marble.addMetaBlock(new SubBlockDifferentDrop("marbleClean", marbleCobble.getStack()));
		SimpleItem marbleCracked = marble.addMetaBlock(new SubBlock("marbleCracked"));
		SimpleItem marbleBrick = marble
				.addMetaBlock(new SubBlockDifferentDrop("marbleBrick", marbleCracked.getStack()));
		SimpleItem marbleCarved = marble.addMetaBlock(new SubBlock("marbleCarved"));
		SimpleItem marblePaver = marble.addMetaBlock(new SubBlock("marblePaver"));

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

	private void addSmelting(SimpleItem input, SimpleItem result) {
		this.addSmelting(input.getStack(), result.getStack());
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
