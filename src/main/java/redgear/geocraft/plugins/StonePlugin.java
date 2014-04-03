package redgear.geocraft.plugins;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import redgear.core.block.MetaBlock;
import redgear.core.block.SubBlock;
import redgear.core.block.SubBlockDifferentDrop;
import redgear.core.mod.IPlugin;
import redgear.core.mod.ModUtils;
import redgear.core.util.SimpleItem;
import redgear.geocraft.api.MineManager;
import redgear.geocraft.core.Geocraft;
import redgear.geocraft.core.GeocraftConfig;
import redgear.geocraft.mines.MineVanilla;
import cpw.mods.fml.common.LoaderState.ModState;
import cpw.mods.fml.common.registry.GameRegistry;

public class StonePlugin implements IPlugin{

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
		MetaBlock limestone = new MetaBlock(Material.rock, "Limestone");
		limestone.setHardness(0.5F).setStepSound(Block.soundTypeStone).setCreativeTab(GeocraftConfig.geoTab);
		SimpleItem limeCobble = limestone.addMetaBlock(new SubBlock("limeCobble"));
		SimpleItem limeClean = limestone.addMetaBlock(new SubBlockDifferentDrop("limeClean", limeCobble.getStack()));
		SimpleItem limeCracked = limestone.addMetaBlock(new SubBlock("limeCracked"));
		SimpleItem limeBrick = limestone.addMetaBlock(new SubBlockDifferentDrop("limeBrick", limeCracked.getStack()));
		SimpleItem limeCarved = limestone.addMetaBlock(new SubBlock("limeCarved"));
		SimpleItem limePaver = limestone.addMetaBlock(new SubBlock("limePaver"));

		GameRegistry.addRecipe(limeBrick.getStack(4), "XX", "XX", 'X', limeClean.getStack());
		GameRegistry.addRecipe(limeCarved.getStack(4), " X ", "X X", " X ", 'X', limeBrick.getStack());
		GameRegistry.addRecipe(limePaver.getStack(9), "XXX", "XXX", "XXX", 'X', limeClean.getStack());

		mod.addSmelting(limeCobble, limeClean);
		mod.addSmelting(limeCracked, limeClean);
		mod.addSmelting(limeCarved, limeClean);
		mod.addSmelting(limePaver, limeClean);

		
		mod.registerOre("stone", limeClean);
		mod.registerOre("cobblestone", limeCobble);

		MetaBlock basalt = new MetaBlock(Material.rock, "Basalt");
		basalt.setHardness(4.0F).setStepSound(Block.soundTypeStone).setCreativeTab(GeocraftConfig.geoTab);
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

		mod.addSmelting(basaltCobble, basaltClean);
		mod.addSmelting(basaltCracked, basaltClean);
		mod.addSmelting(basaltCarved, basaltClean);
		mod.addSmelting(basaltPaver, basaltClean);

		
		mod.registerOre("stone", basaltClean);
		mod.registerOre("cobblestone", basaltCobble);

		MetaBlock marble = new MetaBlock(Material.rock, "Marble");
		marble.setHardness(4.0F).setStepSound(Block.soundTypeStone).setCreativeTab(GeocraftConfig.geoTab);
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

		mod.addSmelting(marbleCobble, marbleClean);
		mod.addSmelting(marbleCracked, marbleClean);
		mod.addSmelting(marbleCarved, marbleClean);
		mod.addSmelting(marblePaver, marbleClean);

		
		mod.registerOre("stone", marbleClean);
		mod.registerOre("cobblestone", marbleCobble);

		GeocraftConfig.marble = marbleClean;
		

		
		if(mod.getBoolean("GenerateNewStoneTypes")){
			//in order to prevent problems, we need to always add the blocks, and only make the mines configurable.
			MineManager.oreRegistry.registerMine(new MineVanilla("Limestone", 1, 5, limeClean, GeocraftConfig.stone, 32));
			MineManager.oreRegistry.registerMine(new MineVanilla("Basalt", 1, 5, basaltClean, GeocraftConfig.stone, 32));
			MineManager.oreRegistry.registerMine(new MineVanilla("Marble", 1, 5, marbleClean, GeocraftConfig.stone, 32));
		}
		
	}

	@Override
	public void Init(ModUtils mod) {
		
	}

	@Override
	public void postInit(ModUtils mod) {
		
	}

}
