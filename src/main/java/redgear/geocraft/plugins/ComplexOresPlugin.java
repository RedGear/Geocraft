package redgear.geocraft.plugins;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.ShapelessOreRecipe;
import redgear.core.api.item.ISimpleItem;
import redgear.core.block.MetaBlock;
import redgear.core.block.SubBlock;
import redgear.core.item.MetaItem;
import redgear.core.item.SubItem;
import redgear.core.mod.IPlugin;
import redgear.core.mod.ModUtils;
import redgear.core.recipes.LeveledRecipe;
import redgear.core.util.ItemRegUtil;
import redgear.core.util.ItemStackUtil;
import redgear.core.util.SimpleItem;
import redgear.core.util.SimpleOre;
import redgear.geocraft.api.mine.MineCylinder;
import redgear.geocraft.block.SubBlockGeoOre;
import redgear.geocraft.block.WeightedItem;
import redgear.geocraft.core.GeocraftConfig;
import redgear.geocraft.generation.MineGenerator;
import redgear.geocraft.generation.MineRegistry;
import redgear.geocraft.mines.MineCoal;
import redgear.geocraft.mines.MineCylinderComplex;
import redgear.geocraft.mines.MineDiamond;
import redgear.geocraft.mines.MineGold;
import redgear.geocraft.mines.MineTrace;
import cpw.mods.fml.common.LoaderState.ModState;
import cpw.mods.fml.common.registry.GameRegistry;

public class ComplexOresPlugin implements IPlugin {

	SubBlockGeoOre ironOreBlock;

	SubBlockGeoOre copperOreBlock;
	SubBlockGeoOre tinOreBlock;

	@Override
	public String getName() {
		return "Complex Ores";
	}

	@Override
	public boolean shouldRun(ModUtils mod, ModState state) {
		return true;
	}

	@Override
	public boolean isRequired() {
		return true;
	}

	SimpleItem ironOre;

	@Override
	public void preInit(ModUtils mod) {
		final SimpleItem diamond = new SimpleItem(Items.diamond);
		final SimpleItem coal = new SimpleItem(Items.coal);

		MetaItem<SubItem> drops = new MetaItem<SubItem>("Drops");
		drops.setCreativeTab(GeocraftConfig.geoTab);
		SimpleItem diamondNugget = drops.addMetaItem(new SubItem("nuggetDiamond"));
		mod.registerOre("nuggetDiamond", diamondNugget);

		SimpleItem coalNugget = drops.addMetaItem(new SubItem("nuggetCoal"));
		mod.registerOre("nuggetCoal", coalNugget);

		SimpleItem terraQuartz = drops.addMetaItem(new SubItem("terraQuartz"));
		mod.registerOre("quartz", terraQuartz);

		SimpleItem goldLump = drops.addMetaItem(new SubItem("goldLump"));
		mod.registerOre("oreGold", goldLump);
		mod.addSmelting(goldLump, new SimpleItem(Items.gold_ingot, 0));

		SimpleItem ironLump = drops.addMetaItem(new SubItem("ironLump"));
		mod.registerOre("oreIron", ironLump);
		mod.addSmelting(ironLump, new SimpleItem(Items.iron_ingot, 0));

		GeocraftConfig.copperLump = drops.addMetaItem(new SubItem("copperLump"));
		mod.registerOre("oreCopper", GeocraftConfig.copperLump);
		GeocraftConfig.tinLump = drops.addMetaItem(new SubItem("tinLump"));
		mod.registerOre("oreTin", GeocraftConfig.tinLump);
		GeocraftConfig.silverLump = drops.addMetaItem(new SubItem("silverLump"));
		mod.registerOre("oreSilver", GeocraftConfig.silverLump);
		GeocraftConfig.leadLump = drops.addMetaItem(new SubItem("leadLump"));
		mod.registerOre("oreLead", GeocraftConfig.leadLump);

		MetaBlock<SubBlock> oreBlock = new MetaBlock<SubBlock>(Material.rock, "Ore");
		oreBlock.setCreativeTab(GeocraftConfig.geoTab);
		oreBlock.setHardness(3f).setStepSound(Block.soundTypeStone);
		GeocraftConfig.kimberlite = oreBlock.addMetaBlock(new SubBlock("kimberlite"));
		GeocraftConfig.diamondOre = oreBlock.addMetaBlock(new SubBlockGeoOre("kimberliteDiamond", new WeightedItem(
				diamond, 1, 1, 1), new WeightedItem(diamondNugget, 0, 2, 2)));
		//mod.registerOre("oreDiamond", GeocraftConfig.diamondOre);

		GeocraftConfig.coalOre = oreBlock.addMetaBlock(new SubBlockGeoOre("coalMid", new WeightedItem(coal, 1, 2, 1),
				new WeightedItem(coalNugget, 0, 4, 2)));
		mod.registerOre("oreCoal", GeocraftConfig.coalOre);

		GeocraftConfig.coalDenseOre = oreBlock.addMetaBlock(new SubBlockGeoOre("coalRich", new WeightedItem(coal, 2, 6,
				2), new WeightedItem(coalNugget, 0, 6, 4)));
		//mod.registerOre("oreCoal", GeocraftConfig.coalDenseOre);

		GeocraftConfig.terraQuartzOre = oreBlock.addMetaBlock(new SubBlockGeoOre("terraQuartzOre", new WeightedItem(
				terraQuartz, 2, 4, 1)));
		GeocraftConfig.goldOre = oreBlock.addMetaBlock(new SubBlockGeoOre("goldOre", new WeightedItem(terraQuartz, 1,
				4, 1), new WeightedItem(goldLump, 1, 3, 1), new WeightedItem(new SimpleItem(Items.gold_nugget, 0), 0,
				1, 1)));
		//mod.registerOre("oreGold", GeocraftConfig.goldOre);

		ironOreBlock = new SubBlockGeoOre("ironOre", new WeightedItem(ironLump, 1, 2, 1));
		ironOre = oreBlock.addMetaBlock(ironOreBlock);
		//mod.registerOre("oreIron", ironOre);

		copperOreBlock = new SubBlockGeoOre("copperOre", new WeightedItem(GeocraftConfig.copperLump, 1, 2, 1));
		GeocraftConfig.copperOre = oreBlock.addMetaBlock(copperOreBlock);
		//mod.registerOre("oreCopper", GeocraftConfig.copperOre);

		tinOreBlock = new SubBlockGeoOre("tinOre", new WeightedItem(GeocraftConfig.tinLump, 1, 2, 1));
		GeocraftConfig.tinOre = oreBlock.addMetaBlock(tinOreBlock);
		//mod.registerOre("oreTin", GeocraftConfig.tinOre);

		GeocraftConfig.galenaOreBlock = new SubBlockGeoOre("galenaOre");
		GeocraftConfig.galenaOre = oreBlock.addMetaBlock(GeocraftConfig.galenaOreBlock);
		//mod.registerOre("oreSilver", GeocraftConfig.galenaOre);
		//mod.registerOre("oreLead", GeocraftConfig.galenaOre);

		final String pick = "pickaxe";
		final String cat = "HarvestLevel";

		oreBlock.setHarvestLevel(pick, mod.getInt(cat, "Kimberlite", 2), GeocraftConfig.kimberlite.meta);
		oreBlock.setHarvestLevel(pick, mod.getInt(cat, "DiamondOre", 2), GeocraftConfig.diamondOre.meta);

		oreBlock.setHarvestLevel(pick, mod.getInt(cat, "CoalOre", 1), GeocraftConfig.coalOre.meta);
		oreBlock.setHarvestLevel(pick, mod.getInt(cat, "CoalOreDense", 1), GeocraftConfig.coalDenseOre.meta);

		oreBlock.setHarvestLevel(pick, mod.getInt(cat, "TerraQuartz", 2), GeocraftConfig.terraQuartzOre.meta);
		oreBlock.setHarvestLevel(pick, mod.getInt(cat, "GoldOre", 2), GeocraftConfig.goldOre.meta);

		oreBlock.setHarvestLevel(pick, mod.getInt(cat, "IronOre", 1), ironOre.meta);

		oreBlock.setHarvestLevel(pick, mod.getInt(cat, "CopperOre", 1), GeocraftConfig.copperOre.meta);
		oreBlock.setHarvestLevel(pick, mod.getInt(cat, "TinOre", 1), GeocraftConfig.tinOre.meta);
		oreBlock.setHarvestLevel(pick, mod.getInt(cat, "GalenaOre", 2), GeocraftConfig.galenaOre.meta);

		LeveledRecipe nugs = new LeveledRecipe("XXX", "XXX", "XXX");

		nugs.addLevel(true, 'X', "nuggetDiamond");
		nugs.registerShaped(diamond);

		nugs.addLevel(true, 'X', "nuggetCoal");
		nugs.registerShaped(coal);

		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(Items.quartz), terraQuartz.getStack(),
				Blocks.soul_sand));

	}

	@Override
	public void Init(ModUtils mod) {
		final ISimpleItem stone = GeocraftConfig.stoneOre;
		final MineRegistry reg = MineGenerator.reg;

		final ISimpleItem vanillaCoal = new SimpleOre("oreCoal", new SimpleItem(Blocks.coal_ore));
		final ISimpleItem vanillaDiamond = new SimpleOre("oreDiamond", new SimpleItem(Blocks.diamond_ore));
		final ISimpleItem vanillaGold = new SimpleOre("oreGold", new SimpleItem(Blocks.gold_ore));
		final ISimpleItem vanillaIron = new SimpleOre("oreIron", new SimpleItem(Blocks.iron_ore));
		final ISimpleItem copperOre = new SimpleOre("oreCopper");
		final ISimpleItem tinOre = new SimpleOre("oreTin");
		final ISimpleItem silverOre = new SimpleOre("oreSilver");
		final ISimpleItem leadOre = new SimpleOre("oreLead");

		reg.registerMine(new MineCoal());//Coal
		reg.registerMine(new MineCylinderComplex("CoalCylinder", vanillaCoal, stone, 2, 20, 16, false)
				.setActive(!GeocraftConfig.complexMines));
		reg.registerTrace("CoalTrace", GeocraftConfig.complexOres ? GeocraftConfig.coalOre : vanillaCoal, stone, 60);

		reg.registerMine(new MineDiamond()); //Diamond
		reg.registerMine(new MineCylinderComplex("CylinderDiamonds", vanillaDiamond, stone, 8, 2, 7, false)
				.setActive(!GeocraftConfig.complexMines));
		reg.registerTrace("DiamondTrace", vanillaDiamond, stone, 2);

		reg.registerMine(new MineGold());//Gold
		reg.registerMine(new MineCylinderComplex("GoldCylinder", vanillaGold, stone, 6, 4, 8, false)
		.setActive(!GeocraftConfig.complexMines));
		reg.registerTrace("GoldTrace", vanillaGold, stone, 4);

		reg.registerMine(new MineCylinderComplex("IronComplex", ironOre, stone, 4, 20, 8, true)
				.setActive(GeocraftConfig.complexMines));
		reg.registerMine(new MineCylinderComplex("IronCylinder", vanillaIron, stone, 1, 20, 8, false)
				.setActive(!GeocraftConfig.complexMines));
		reg.registerMine(new MineTrace("IronComplexTrace", ironOre, stone, 20).setActive(GeocraftConfig.complexMines));
		reg.registerMine(new MineTrace("IronTrace", vanillaIron, stone, 20).setActive(!GeocraftConfig.complexMines));

		reg.addNewOre(new SimpleOre("oreRedstone", new SimpleItem(Blocks.redstone_ore)), stone, 8, 7);
		reg.addNewOre(new SimpleOre("oreLapis", new SimpleItem(Blocks.lapis_ore)), stone, 1, 6);

		if (GeocraftConfig.complexMines) {

			if (ItemRegUtil.isInOreDict("ingotCopper")) {
				reg.registerMine(new MineCylinderComplex("CopperComplex", GeocraftConfig.copperOre,
						GeocraftConfig.stone, 4, 16, 10, true));
				reg.registerMine(new MineTrace("CopperComplexTrace", GeocraftConfig.copperOre, GeocraftConfig.stone, 20));
			}

			if (ItemRegUtil.isInOreDict("ingotTin")) {
				reg.registerMine(new MineCylinderComplex("TinComplex", GeocraftConfig.tinOre, GeocraftConfig.stone, 4,
						32, 6, true).setActive(GeocraftConfig.complexMines));
				reg.registerMine(new MineTrace("TinTrace", GeocraftConfig.tinOre, GeocraftConfig.stone, 20)
						.setActive(GeocraftConfig.complexMines));
			}

			if (ItemRegUtil.isInOreDict("ingotLead") || ItemRegUtil.isInOreDict("ingotSilver")) {
				reg.registerMine(new MineCylinderComplex("Galena", GeocraftConfig.galenaOre, GeocraftConfig.stone, 4,
						4, 16, true).setActive(GeocraftConfig.complexMines));
				reg.registerMine(new MineTrace("GalenaTrace", GeocraftConfig.galenaOre, GeocraftConfig.stone, 3)
						.setActive(GeocraftConfig.complexMines));
			}
		}

		reg.registerIgnore(vanillaCoal);
		reg.registerIgnore(vanillaDiamond);
		reg.registerIgnore(vanillaGold);
		reg.registerIgnore(vanillaIron);
		reg.registerIgnore(copperOre);
		reg.registerIgnore(tinOre);
		reg.registerIgnore(silverOre);
		reg.registerIgnore(leadOre);
	}

	@Override
	public void postInit(ModUtils mod) {

		ItemStack ironNugget = ItemStackUtil.getOreWithName("nuggetIron", 1);
		if (ironNugget != null)
			ironOreBlock.addItem(new WeightedItem(new SimpleItem(ironNugget), 0, 1, 1));

		ItemStack copperIngot = ItemStackUtil.getOreWithName("ingotCopper", 1);
		if (copperIngot != null)
			mod.addSmelting(GeocraftConfig.copperLump.getStack(), copperIngot);

		ItemStack copperNugget = ItemStackUtil.getOreWithName("nuggetCopper", 1);
		if (copperNugget != null)
			copperOreBlock.addItem(new WeightedItem(new SimpleItem(copperNugget), 0, 1, 1));

		ItemStack tinIngot = ItemStackUtil.getOreWithName("ingotTin", 1);
		if (tinIngot != null)
			mod.addSmelting(GeocraftConfig.tinLump.getStack(), tinIngot);

		ItemStack tinNugget = ItemStackUtil.getOreWithName("nuggetTin", 1);
		if (tinNugget != null)
			tinOreBlock.addItem(new WeightedItem(new SimpleItem(tinNugget), 0, 1, 1));

		ItemStack ingotSilver = ItemStackUtil.getOreWithName("ingotSilver", 1);
		if (ingotSilver != null) {
			GeocraftConfig.galenaOreBlock.addItem(new WeightedItem(GeocraftConfig.silverLump, 1, 1, 1));
			mod.addSmelting(GeocraftConfig.silverLump.getStack(), ingotSilver);
		}
		ItemStack nuggetSilver = ItemStackUtil.getOreWithName("nuggetSilver", 1);
		if (nuggetSilver != null)
			GeocraftConfig.galenaOreBlock.addItem(new WeightedItem(new SimpleItem(nuggetSilver), 0, 1, 1));

		ItemStack ingotLead = ItemStackUtil.getOreWithName("ingotLead", 1);
		if (ingotLead != null) {
			GeocraftConfig.galenaOreBlock.addItem(new WeightedItem(GeocraftConfig.leadLump, 1, 2, 1));
			mod.addSmelting(GeocraftConfig.leadLump.getStack(), ingotLead);
		}
		ItemStack nuggetLead = ItemStackUtil.getOreWithName("nuggetLead", 1);
		if (nuggetLead != null)
			GeocraftConfig.galenaOreBlock.addItem(new WeightedItem(new SimpleItem(nuggetLead), 0, 1, 1));
	}

}
