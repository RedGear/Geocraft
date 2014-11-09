package redgear.geocraft.plugins;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import redgear.core.api.item.ISimpleItem;
import redgear.core.block.MetaBlock;
import redgear.core.block.SubBlock;
import redgear.core.block.SubBlockAntiConnected;
import redgear.core.block.SubBlockConnected;
import redgear.core.block.SubBlockDifferentDrop;
import redgear.core.mod.IPlugin;
import redgear.core.mod.ModUtils;
import redgear.geocraft.api.MineManager;
import redgear.geocraft.core.GeocraftConfig;
import redgear.geocraft.mines.MineVanilla;
import cpw.mods.fml.common.LoaderState.ModState;
import cpw.mods.fml.common.registry.GameRegistry;

public class StonePlugin implements IPlugin {

    public ISimpleItem limeCobble;
    public ISimpleItem limeClean;
    public ISimpleItem limeCracked;
    public ISimpleItem limeBrick;
    public ISimpleItem limeCarved;
    public ISimpleItem limePaver;
    public ISimpleItem limeAntiPaver;

    public ISimpleItem basaltCobble;
    public ISimpleItem basaltClean;
    public ISimpleItem basaltCracked;
    public ISimpleItem basaltBrick;
    public ISimpleItem basaltCarved;
    public ISimpleItem basaltPaver;
    public ISimpleItem basaltAntiPaver;

    public ISimpleItem marbleCobble;
    public ISimpleItem marbleClean;
    public ISimpleItem marbleCracked;
    public ISimpleItem marbleBrick;
    public ISimpleItem marbleCarved ;
    public ISimpleItem marblePaver;
    public ISimpleItem marbleAntiPaver;

	@Override
	public String getName() {
		return "Stone Types Creator";
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
		MetaBlock<SubBlock> limestone = new MetaBlock<SubBlock>(Material.rock, "Limestone");
		limestone.setHardness(0.5F).setStepSound(Block.soundTypeStone).setCreativeTab(GeocraftConfig.geoTab);
		GeocraftConfig.limestoneBlock = limestone;
        limeCobble = limestone.addMetaBlock(new SubBlock("limeCobble"));
        limeClean = limestone.addMetaBlock(new SubBlockDifferentDrop("limeClean", limeCobble.getStack()));
        limeCracked = limestone.addMetaBlock(new SubBlock("limeCracked"));
        limeBrick = limestone.addMetaBlock(new SubBlockDifferentDrop("limeBrick", limeCracked.getStack()));
        limeCarved = limestone.addMetaBlock(new SubBlock("limeCarved"));
        limePaver = limestone.addMetaBlock(new SubBlockConnected("limePaver"));
        limeAntiPaver = limestone.addMetaBlock(new SubBlockAntiConnected("limeAntiPaver", "limePaver"));

		GameRegistry.addRecipe(limeBrick.getStack(4), "XX", "XX", 'X', limeClean.getStack());
		GameRegistry.addRecipe(limeCarved.getStack(4), " X ", "X X", " X ", 'X', limeBrick.getStack());
		GameRegistry.addRecipe(limePaver.getStack(9), "XXX", "XXX", "XXX", 'X', limeClean.getStack());
		GameRegistry.addShapelessRecipe(limeAntiPaver.getStack(), limePaver.getStack());
		GameRegistry.addShapelessRecipe(limePaver.getStack(), limeAntiPaver.getStack());

		mod.addSmelting(limeCobble, limeClean);
		mod.addSmelting(limeCracked, limeClean);
		mod.addSmelting(limeCarved, limeClean);
		mod.addSmelting(limePaver, limeClean);
		mod.addSmelting(limeAntiPaver, limeClean);

		mod.registerOre("stone", limeClean);
		mod.registerOre("cobblestone", limeCobble);

        mod.registerOre("blockLimestone", limeCobble);
        mod.registerOre("blockLimestone", limeClean);
        mod.registerOre("blockLimestone", limeCracked);
        mod.registerOre("blockLimestone", limeBrick);
        mod.registerOre("blockLimestone", limeCarved);
        mod.registerOre("blockLimestone", limePaver);
        mod.registerOre("blockLimestone", limeAntiPaver);

		MetaBlock<SubBlock> basalt = new MetaBlock<SubBlock>(Material.rock, "Basalt");
		basalt.setHardness(4.0F).setStepSound(Block.soundTypeStone).setCreativeTab(GeocraftConfig.geoTab);
		GeocraftConfig.basaltBlock = basalt;
	    basaltCobble = basalt.addMetaBlock(new SubBlock("basaltCobble"));
		basaltClean = basalt.addMetaBlock(new SubBlockDifferentDrop("basaltClean", basaltCobble.getStack()));
		basaltCracked = basalt.addMetaBlock(new SubBlock("basaltCracked"));
		basaltBrick = basalt.addMetaBlock(new SubBlockDifferentDrop("basaltBrick", basaltCracked.getStack()));
		basaltCarved = basalt.addMetaBlock(new SubBlock("basaltCarved"));
		basaltPaver = basalt.addMetaBlock(new SubBlockConnected("basaltPaver"));
		basaltAntiPaver = basalt.addMetaBlock(new SubBlockAntiConnected("basaltAntiPaver", "basaltPaver"));

		GameRegistry.addRecipe(basaltBrick.getStack(4), "XX", "XX", 'X', basaltClean.getStack());
		GameRegistry.addRecipe(basaltCarved.getStack(4), " X ", "X X", " X ", 'X', basaltBrick.getStack());
		GameRegistry.addRecipe(basaltPaver.getStack(9), "XXX", "XXX", "XXX", 'X', basaltClean.getStack());
		GameRegistry.addShapelessRecipe(basaltAntiPaver.getStack(), basaltPaver.getStack());
		GameRegistry.addShapelessRecipe(basaltPaver.getStack(), basaltAntiPaver.getStack());

		mod.addSmelting(basaltCobble, basaltClean);
		mod.addSmelting(basaltCracked, basaltClean);
		mod.addSmelting(basaltCarved, basaltClean);
		mod.addSmelting(basaltPaver, basaltClean);
		mod.addSmelting(basaltAntiPaver, basaltClean);

		mod.registerOre("stone", basaltClean);
		mod.registerOre("cobblestone", basaltCobble);
		
		mod.registerOre("blockBasalt", basaltCobble);
        mod.registerOre("blockBasalt", basaltClean);
        mod.registerOre("blockBasalt", basaltCracked);
        mod.registerOre("blockBasalt", basaltBrick);
        mod.registerOre("blockBasalt", basaltCarved);
        mod.registerOre("blockBasalt", basaltPaver);
        mod.registerOre("blockBasalt", basaltAntiPaver);

		MetaBlock<SubBlock> marble = new MetaBlock<SubBlock>(Material.rock, "Marble");
		marble.setHardness(4.0F).setStepSound(Block.soundTypeStone).setCreativeTab(GeocraftConfig.geoTab);
		GeocraftConfig.marbleBlock = marble;
		marbleCobble = marble.addMetaBlock(new SubBlock("marbleCobble"));
		marbleClean = marble.addMetaBlock(new SubBlockDifferentDrop("marbleClean", marbleCobble.getStack()));
		marbleCracked = marble.addMetaBlock(new SubBlock("marbleCracked"));
		marbleBrick = marble.addMetaBlock(new SubBlockDifferentDrop("marbleBrick", marbleCracked.getStack()));
		marbleCarved = marble.addMetaBlock(new SubBlock("marbleCarved"));
		marblePaver = marble.addMetaBlock(new SubBlockConnected("marblePaver"));
		marbleAntiPaver = marble.addMetaBlock(new SubBlockAntiConnected("marbleAntiPaver", "marblePaver"));

		GameRegistry.addRecipe(marbleBrick.getStack(4), "XX", "XX", 'X', marbleClean.getStack());
		GameRegistry.addRecipe(marbleCarved.getStack(4), " X ", "X X", " X ", 'X', marbleBrick.getStack());
		GameRegistry.addRecipe(marblePaver.getStack(9), "XXX", "XXX", "XXX", 'X', marbleClean.getStack());
		GameRegistry.addShapelessRecipe(marbleAntiPaver.getStack(), marblePaver.getStack());
		GameRegistry.addShapelessRecipe(marblePaver.getStack(), marbleAntiPaver.getStack());

		mod.addSmelting(marbleCobble, marbleClean);
		mod.addSmelting(marbleCracked, marbleClean);
		mod.addSmelting(marbleCarved, marbleClean);
		mod.addSmelting(marblePaver, marbleClean);
		mod.addSmelting(marbleAntiPaver, marbleClean);

		mod.registerOre("stone", marbleClean);
		mod.registerOre("cobblestone", marbleCobble);
		
		mod.registerOre("blockMarble", marbleCobble);
        mod.registerOre("blockMarble", marbleClean);
        mod.registerOre("blockMarble", marbleCracked);
        mod.registerOre("blockMarble", marbleBrick);
        mod.registerOre("blockMarble", marbleCarved);
        mod.registerOre("blockMarble", marblePaver);
        mod.registerOre("blockMarble", marbleAntiPaver);

		GeocraftConfig.marble = marbleClean;

		boolean genStone = mod.getBoolean("GenerateNewStoneTypes");

		//in order to prevent problems, we need to always add the blocks, and only make the mines configurable.
		MineManager.oreRegistry.registerMine(new MineVanilla("Limestone", limeClean, GeocraftConfig.stone, 5, 32).setActive(genStone));
		MineManager.oreRegistry.registerMine(new MineVanilla("Basalt", basaltClean, GeocraftConfig.stone, 5, 32).setActive(genStone));
		MineManager.oreRegistry.registerMine(new MineVanilla("Marble", marbleClean, GeocraftConfig.stone, 5, 32).setActive(genStone));

	}

	@Override
	public void Init(ModUtils mod) {

	}

	@Override
	public void postInit(ModUtils mod) {

	}
}
