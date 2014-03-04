package redgear.geocraft.plugins;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import redgear.core.block.MetaBlock;
import redgear.core.block.SubBlock;
import redgear.core.item.MetaItem;
import redgear.core.item.SubItem;
import redgear.core.mod.IPlugin;
import redgear.core.mod.ModUtils;
import redgear.core.recipes.LeveledRecipe;
import redgear.core.util.SimpleItem;
import redgear.geocraft.block.SubBlockGeoOre;
import redgear.geocraft.block.WeightedItem;
import redgear.geocraft.core.Geocraft;
import redgear.geocraft.generation.GeoMode;
import redgear.geocraft.generation.MineGenerator;
import redgear.geocraft.generation.MineRegistry;
import redgear.geocraft.mines.MineCoal;
import redgear.geocraft.mines.MineDiamond;
import cpw.mods.fml.common.LoaderState.ModState;

public class ComplexOresPlugin implements IPlugin{

	@Override
	public String getName() {
		return "Complex Ores";
	}

	@Override
	public boolean shouldRun(ModUtils mod, ModState state) {
		return MineGenerator.reg.mode == GeoMode.Complex;
	}

	@Override
	public boolean isRequired() {
		return true;
	}

	@Override
	public void preInit(ModUtils mod) {
		final SimpleItem stone = Geocraft.stone;
		final MineRegistry reg = MineGenerator.reg;
		final SimpleItem diamond = new SimpleItem(Items.diamond, 0);
		final SimpleItem coal = new SimpleItem(Items.coal, 0);
		
		MetaItem drops = new MetaItem("Drops");
		SimpleItem diamondNugget = drops.addMetaItem(new SubItem("nuggetDiamond"));
		mod.registerOre("nuggetDiamond", diamondNugget);
		
		SimpleItem coalNugget = drops.addMetaItem(new SubItem("nuggetCoal"));
		mod.registerOre("nuggetCoal", coalNugget);
		
		MetaBlock oreBlock = new MetaBlock(Material.rock, "Ore");
		oreBlock.setHardness(3f).setStepSound(Block.soundTypeStone);
		Geocraft.kimberlite = oreBlock.addMetaBlock(new SubBlock("kimberlite"));
		Geocraft.diamondOre = oreBlock.addMetaBlock(new SubBlockGeoOre("kimberliteDiamond", new WeightedItem(diamond, 1, 1, 1), new WeightedItem(diamondNugget, 0, 2, 2)));
		Geocraft.coalOre = oreBlock.addMetaBlock(new SubBlockGeoOre("coalMid", new WeightedItem(coal, 1, 2, 1), new WeightedItem(coalNugget, 0, 4, 2)));
		Geocraft.coalDenseOre = oreBlock.addMetaBlock(new SubBlockGeoOre("coalRich", new WeightedItem(coal, 2, 6, 2), new WeightedItem(coalNugget, 0, 6, 4)));

		reg.registerMine(new MineCoal(2 * reg.defaultDensityRate * reg.defaultDensityRate,
				20 * reg.defaultDensityRate));//Coal
		reg.registerTrace("CoalTrace", new SimpleItem(Blocks.coal_ore, 0), stone, 60);

		reg.addNewOre(new SimpleItem(Blocks.iron_ore, 0), stone, 20, 8);
		reg.addNewOre(new SimpleItem(Blocks.gold_ore, 0), stone, 2, 8);
		reg.addNewOre(new SimpleItem(Blocks.redstone_ore, 0), stone, 8, 7);
		reg.addNewOre(new SimpleItem(Blocks.lapis_ore, 0), stone, 1, 6);

		reg.registerMine(new MineDiamond(5 * reg.defaultDensityRate * reg.defaultDensityRate,
				40 * reg.defaultDensityRate)); //Diamond
		reg.registerTrace("DiamondTrace", new SimpleItem(Blocks.diamond_ore, 0), stone, 2);
		
		LeveledRecipe nugs = new LeveledRecipe("XXX", "XXX", "XXX");
		
		nugs.addLevel(true, 'X', "nuggetDiamond");
		nugs.registerShaped(diamond);
		
		nugs.addLevel(true, 'X', "nuggetCoal");
		nugs.registerShaped(coal);
	}

	@Override
	public void Init(ModUtils mod) {
		
	}

	@Override
	public void postInit(ModUtils mod) {
		
	}

}
