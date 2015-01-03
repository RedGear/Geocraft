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
import redgear.core.block.SubBlockDifferentDrop;
import redgear.core.item.MetaItem;
import redgear.core.item.SubItem;
import redgear.core.mod.IPlugin;
import redgear.core.mod.ModUtils;
import redgear.core.recipes.LeveledRecipe;
import redgear.core.util.ItemRegUtil;
import redgear.core.util.ItemStackUtil;
import redgear.core.util.SimpleItem;
import redgear.core.util.SimpleOre;
import redgear.geocraft.block.RareDrop;
import redgear.geocraft.block.SingleDrop;
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
		final SimpleItem lapis = new SimpleItem(Items.dye, 4);
		final SimpleItem emerald = new SimpleItem(Items.emerald);

		MetaItem<SubItem> drops = new MetaItem<SubItem>("Drops");
		drops.setCreativeTab(GeocraftConfig.geoTab);
		SimpleItem diamondNugget = drops.addMetaItem(new SubItem("nuggetDiamond"));
		mod.registerOre("nuggetDiamond", diamondNugget);

		SimpleItem coalNugget = drops.addMetaItem(new SubItem("nuggetCoal"));
		mod.registerOre("nuggetCoal", coalNugget);

        GeocraftConfig.terraQuartz = drops.addMetaItem(new SubItem("terraQuartz"));
		mod.registerOre("quartz", GeocraftConfig.terraQuartz);

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
		GeocraftConfig.lapisLump = drops.addMetaItem(new SubItem("lapisLump"));
		mod.registerOre("oreLapis", GeocraftConfig.lapisLump);
		
		GeocraftConfig.lapisNugget = drops.addMetaItem(new SubItem("nuggetLapis"));
		mod.registerOre("nuggetLapis", GeocraftConfig.lapisNugget);
		
		GeocraftConfig.redstoneLump = drops.addMetaItem(new SubItem("redstoneLump"));
		mod.registerOre("oreRedstone", GeocraftConfig.redstoneLump);
		
		GeocraftConfig.emeraldLump = drops.addMetaItem(new SubItem("emeraldLump"));
		mod.registerOre("oreEmerald", GeocraftConfig.emeraldLump);
		
		GeocraftConfig.emeraldNugget = drops.addMetaItem(new SubItem("nuggetEmerald"));
		mod.registerOre("nuggetEmerald", GeocraftConfig.emeraldNugget);
		

		MetaBlock<SubBlock> oreBlock = new MetaBlock<SubBlock>(Material.rock, "Ore");
		oreBlock.setCreativeTab(GeocraftConfig.geoTab);
		oreBlock.setHardness(3f).setStepSound(Block.soundTypeStone);
		GeocraftConfig.kimberlite = oreBlock.addMetaBlock(new SubBlockDifferentDrop("kimberlite", new SimpleItem(Blocks.gravel).getStack()));
		GeocraftConfig.diamondOre = oreBlock.addMetaBlock(new SubBlockGeoOre("kimberliteDiamond", new SingleDrop(new SimpleItem(Blocks.gravel), 1), new WeightedItem(
				diamond, 1, 1, 1), new RareDrop(diamondNugget, 3, 6)));
		mod.registerOre("denseoreDiamond", GeocraftConfig.diamondOre);

		GeocraftConfig.coalOre = oreBlock.addMetaBlock(new SubBlockGeoOre("coalMid", new WeightedItem(coal, 1, 2, 1),
				new RareDrop(coalNugget, 4, 3)));
		mod.registerOre("denseoreCoal", GeocraftConfig.coalOre);

		GeocraftConfig.coalDenseOre = oreBlock.addMetaBlock(new SubBlockGeoOre("coalRich", new WeightedItem(coal, 2, 6,
				2), new RareDrop(coalNugget, 6, 4)));
		mod.registerOre("denseoreCoal", GeocraftConfig.coalDenseOre);

		GeocraftConfig.terraQuartzOre = oreBlock.addMetaBlock(new SubBlockGeoOre("terraQuartzOre", new WeightedItem(
                GeocraftConfig.terraQuartz, 2, 4, 1), new RareDrop(goldLump, 1, 10)));
		mod.registerOre("denseoreQuartz", GeocraftConfig.terraQuartz);
		
		GeocraftConfig.goldOre = oreBlock.addMetaBlock(new SubBlockGeoOre("goldOre", new WeightedItem(GeocraftConfig.terraQuartz, 1,
				4, 1), new WeightedItem(goldLump, 1, 3, 1), new RareDrop(new SimpleItem(Items.gold_nugget),
				2, 9), new RareDrop(GeocraftConfig.silverLump, 1, 9)));
		mod.registerOre("denseoreGold", GeocraftConfig.goldOre);

		ironOreBlock = new SubBlockGeoOre("ironOre", new WeightedItem(ironLump, 1, 2, 1));
		ironOre = oreBlock.addMetaBlock(ironOreBlock);
		mod.registerOre("denseoreIron", ironOre);

		copperOreBlock = new SubBlockGeoOre("copperOre", new WeightedItem(GeocraftConfig.copperLump, 1, 2, 1), new RareDrop(new SimpleItem(Items.gold_nugget), 1, 30));
		GeocraftConfig.copperOre = oreBlock.addMetaBlock(copperOreBlock);
		mod.registerOre("denseoreCopper", GeocraftConfig.copperOre);

		tinOreBlock = new SubBlockGeoOre("tinOre", new WeightedItem(GeocraftConfig.tinLump, 1, 2, 1), new RareDrop(ironLump, 1, 15));
		GeocraftConfig.tinOre = oreBlock.addMetaBlock(tinOreBlock);
		mod.registerOre("denseoreTin", GeocraftConfig.tinOre);

		GeocraftConfig.galenaOreBlock = new SubBlockGeoOre("galenaOre");
		GeocraftConfig.galenaOre = oreBlock.addMetaBlock(GeocraftConfig.galenaOreBlock);
		mod.registerOre("oreGalena", GeocraftConfig.galenaOre);
		
		SubBlockGeoOre lapisOreBlock = new SubBlockGeoOre("lapisOre", new WeightedItem(GeocraftConfig.lapisLump, 1, 2, 1), new RareDrop(lapis, 1, 8), new RareDrop(lapis, 2, 20), new RareDrop(GeocraftConfig.lapisNugget, 1, 20));
		GeocraftConfig.lapisOre = oreBlock.addMetaBlock(lapisOreBlock);
		mod.registerOre("denseoreLapis", GeocraftConfig.lapisOre);
		
		SubBlockGeoOre redstoneOreBlock = new SubBlockGeoOre("redstoneOre", new WeightedItem(GeocraftConfig.redstoneLump, 1, 1, 1), new RareDrop(new SimpleItem(Items.redstone), 1, 4), new RareDrop(new SimpleItem(Items.redstone), 2, 20));
		GeocraftConfig.redstoneOre = oreBlock.addMetaBlock(redstoneOreBlock);
		mod.registerOre("denseoreRedstone", GeocraftConfig.redstoneOre);
		
		SubBlockGeoOre emeraldOreBlock = new SubBlockGeoOre("emeraldOre", new WeightedItem(GeocraftConfig.emeraldLump, 1, 1, 1), new RareDrop(GeocraftConfig.emeraldNugget, 1, 8), new RareDrop(emerald, 1, 24));
		GeocraftConfig.emeraldOre = oreBlock.addMetaBlock(emeraldOreBlock);
		mod.registerOre("denseoreEmerald", GeocraftConfig.emeraldOre);
		
		
		final String pick = "pickaxe";
		final String cat = "HarvestLevel";

		oreBlock.setHarvestLevel(pick, mod.getInt(cat, "Kimberlite", 2), GeocraftConfig.kimberlite.meta);
		oreBlock.setHarvestLevel(pick, mod.getInt(cat, "DiamondOre", 2), GeocraftConfig.diamondOre.meta);

		oreBlock.setHarvestLevel(pick, mod.getInt(cat, "CoalOre", 1), GeocraftConfig.coalOre.meta);
		oreBlock.setHarvestLevel(pick, mod.getInt(cat, "CoalOreDense", 1), GeocraftConfig.coalDenseOre.meta);

		oreBlock.setHarvestLevel(pick, mod.getInt(cat, "TerraQuartz", 2), GeocraftConfig.terraQuartzOre.meta);
		oreBlock.setHarvestLevel(pick, mod.getInt(cat, "GoldOre", 2), GeocraftConfig.goldOre.meta);

		oreBlock.setHarvestLevel(pick, mod.getInt(cat, "IronOre", 1), ironOre.meta);
		
		oreBlock.setHarvestLevel(pick, mod.getInt(cat, "RedstoneOre", 2), GeocraftConfig.redstoneOre.meta);
		oreBlock.setHarvestLevel(pick, mod.getInt(cat, "LapisOre", 2), GeocraftConfig.lapisOre.meta);
		oreBlock.setHarvestLevel(pick, mod.getInt(cat, "EmeraldOre", 2), GeocraftConfig.emeraldOre.meta);

		oreBlock.setHarvestLevel(pick, mod.getInt(cat, "CopperOre", 1), GeocraftConfig.copperOre.meta);
		oreBlock.setHarvestLevel(pick, mod.getInt(cat, "TinOre", 1), GeocraftConfig.tinOre.meta);
		oreBlock.setHarvestLevel(pick, mod.getInt(cat, "GalenaOre", 2), GeocraftConfig.galenaOre.meta);

		LeveledRecipe nugs = new LeveledRecipe("XXX", "XXX", "XXX");

		nugs.addLevel(true, 'X', "nuggetDiamond");
		nugs.registerShaped(diamond);

		nugs.addLevel(true, 'X', "nuggetCoal");
		nugs.registerShaped(coal);
		
		nugs.addLevel(true, 'X', "nuggetLapis");
		nugs.registerShaped(lapis);
		
		nugs.addLevel(true, 'X', "nuggetEmerald");
		nugs.registerShaped(emerald);

		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(Items.quartz), GeocraftConfig.terraQuartz.getStack(),
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
		final ISimpleItem vanillaLapis = new SimpleOre("oreLapis", new SimpleItem(Blocks.lapis_ore));
		final ISimpleItem vanillaRedstone = new SimpleOre("oreRedstone", new SimpleItem(Blocks.redstone_ore));
		final ISimpleItem vanillaEmerald = new SimpleOre("oreEmerald", new SimpleItem(Blocks.emerald_ore));
		final ISimpleItem copperOre = new SimpleOre("oreCopper");
		final ISimpleItem tinOre = new SimpleOre("oreTin");
		final ISimpleItem silverOre = new SimpleOre("oreSilver");
		final ISimpleItem leadOre = new SimpleOre("oreLead");

		reg.registerMine(new MineCoal());//Coal
		reg.registerMine(new MineCylinderComplex("CoalCylinder", vanillaCoal, stone, 2, 20, 16, false).setActive(!GeocraftConfig.complexMines));
		reg.registerTrace("CoalTrace", GeocraftConfig.complexOres ? GeocraftConfig.coalOre : vanillaCoal, stone, 60);

		reg.registerMine(new MineDiamond()); //Diamond
		reg.registerMine(new MineCylinderComplex("CylinderDiamonds", vanillaDiamond, stone, 8, 2, 7, false).setActive(!GeocraftConfig.complexMines));
		reg.registerTrace("DiamondTrace", vanillaDiamond, stone, 2);

		reg.registerMine(new MineGold());//Gold
		reg.registerMine(new MineCylinderComplex("GoldCylinder", vanillaGold, stone, 6, 4, 8, false).setActive(!GeocraftConfig.complexMines));
		reg.registerTrace("GoldTrace", vanillaGold, stone, 4);

		reg.registerMine(new MineCylinderComplex("IronComplex", ironOre, stone, 4, 20, 8, true) .setActive(GeocraftConfig.complexMines));
		reg.registerMine(new MineCylinderComplex("IronCylinder", vanillaIron, stone, 1, 20, 8, false) .setActive(!GeocraftConfig.complexMines));
		reg.registerMine(new MineTrace("IronComplexTrace", ironOre, stone, 20).setActive(GeocraftConfig.complexMines));
		reg.registerMine(new MineTrace("IronTrace", vanillaIron, stone, 20).setActive(!GeocraftConfig.complexMines));

		
		reg.registerMine(new MineCylinderComplex("RedstoneComplex", GeocraftConfig.redstoneOre, stone, 4, 8, 7, true).setActive(GeocraftConfig.complexMines));
		reg.registerMine(new MineCylinderComplex("RedstoneCylinder", vanillaRedstone, stone, 1, 8, 7, false).setActive(!GeocraftConfig.complexMines));
		reg.registerMine(new MineTrace("RedstoneComplexTrace", GeocraftConfig.redstoneOre, stone, 4).setActive(GeocraftConfig.complexMines));
		reg.registerMine(new MineTrace("RedstoneTrace", vanillaRedstone, stone, 8).setActive(!GeocraftConfig.complexMines));
		
		reg.registerMine(new MineCylinderComplex("LapisComplex", GeocraftConfig.lapisOre, stone, 8, 2, 6, true).setActive(GeocraftConfig.complexMines));
		reg.registerMine(new MineCylinderComplex("LapisCylinder", vanillaLapis, stone, 4, 2, 6, false).setActive(!GeocraftConfig.complexMines));
		reg.registerMine(new MineTrace("LapisComplexTrace", GeocraftConfig.lapisOre, stone, 2).setActive(GeocraftConfig.complexMines));
		reg.registerMine(new MineTrace("LapisTrace", vanillaLapis, stone, 4).setActive(!GeocraftConfig.complexMines));
		
		reg.registerMine(new MineCylinderComplex("EmeraldComplex", GeocraftConfig.emeraldOre, stone, 8, 3, 6, true).setActive(GeocraftConfig.complexMines));
		reg.registerMine(new MineCylinderComplex("EmeraldCylinder", vanillaEmerald, stone, 4, 3, 6, false).setActive(!GeocraftConfig.complexMines));
		reg.registerMine(new MineTrace("EmeraldComplexTrace", GeocraftConfig.emeraldOre, stone, 1).setActive(GeocraftConfig.complexMines));
		reg.registerMine(new MineTrace("EmeraldTrace", vanillaEmerald, stone, 2).setActive(!GeocraftConfig.complexMines));

		if (GeocraftConfig.complexMines) {

			if (ItemRegUtil.isInOreDict("ingotCopper")) {
				reg.registerMine(new MineCylinderComplex("CopperComplex", GeocraftConfig.copperOre, GeocraftConfig.stone, 4, 16, 10, true));
				reg.registerMine(new MineTrace("CopperComplexTrace", GeocraftConfig.copperOre, GeocraftConfig.stone, 20));
			}

			if (ItemRegUtil.isInOreDict("ingotTin")) {
				reg.registerMine(new MineCylinderComplex("TinComplex", GeocraftConfig.tinOre, GeocraftConfig.stone, 4, 32, 6, true).setActive(GeocraftConfig.complexMines));
				reg.registerMine(new MineTrace("TinTrace", GeocraftConfig.tinOre, GeocraftConfig.stone, 20) .setActive(GeocraftConfig.complexMines));
			}

			if (ItemRegUtil.isInOreDict("ingotLead") || ItemRegUtil.isInOreDict("ingotSilver")) {
				reg.registerMine(new MineCylinderComplex("Galena", GeocraftConfig.galenaOre, GeocraftConfig.stone, 4, 4, 16, true).setActive(GeocraftConfig.complexMines));
				reg.registerMine(new MineTrace("GalenaTrace", GeocraftConfig.galenaOre, GeocraftConfig.stone, 3) .setActive(GeocraftConfig.complexMines));
			}
		}

		reg.registerIgnore(vanillaCoal);
		reg.registerIgnore(vanillaDiamond);
		reg.registerIgnore(vanillaGold);
		reg.registerIgnore(vanillaIron);
		reg.registerIgnore(vanillaLapis);
		reg.registerIgnore(vanillaRedstone);
		reg.registerIgnore(vanillaEmerald);
		reg.registerIgnore(copperOre);
		reg.registerIgnore(tinOre);
		reg.registerIgnore(silverOre);
		reg.registerIgnore(leadOre);
	}

	@Override
	public void postInit(ModUtils mod) {

		ItemStack ironNugget = ItemStackUtil.getOreWithName("nuggetIron", 1);
		if (ironNugget != null)
			ironOreBlock.addItem(new RareDrop(new SimpleItem(ironNugget), 1, 20));

		ItemStack copperIngot = ItemStackUtil.getOreWithName("ingotCopper", 1);
		if (copperIngot != null)
			mod.addSmelting(GeocraftConfig.copperLump.getStack(), copperIngot);

		ItemStack copperNugget = ItemStackUtil.getOreWithName("nuggetCopper", 1);
		if (copperNugget != null)
			copperOreBlock.addItem(new RareDrop(new SimpleItem(copperNugget), 1, 10));

		ItemStack tinIngot = ItemStackUtil.getOreWithName("ingotTin", 1);
		if (tinIngot != null)
			mod.addSmelting(GeocraftConfig.tinLump.getStack(), tinIngot);

		ItemStack tinNugget = ItemStackUtil.getOreWithName("nuggetTin", 1);
		if (tinNugget != null)
			tinOreBlock.addItem(new RareDrop(new SimpleItem(tinNugget), 1, 10));

		ItemStack ingotSilver = ItemStackUtil.getOreWithName("ingotSilver", 1);
		if (ingotSilver != null) {
			GeocraftConfig.galenaOreBlock.addItem(new WeightedItem(GeocraftConfig.silverLump, 1, 1, 1));
			mod.addSmelting(GeocraftConfig.silverLump.getStack(), ingotSilver);
		}
		ItemStack nuggetSilver = ItemStackUtil.getOreWithName("nuggetSilver", 1);
		if (nuggetSilver != null)
			GeocraftConfig.galenaOreBlock.addItem(new RareDrop(new SimpleItem(nuggetSilver), 1, 8));

		ItemStack ingotLead = ItemStackUtil.getOreWithName("ingotLead", 1);
		if (ingotLead != null) {
			GeocraftConfig.galenaOreBlock.addItem(new WeightedItem(GeocraftConfig.leadLump, 1, 2, 1));
			mod.addSmelting(GeocraftConfig.leadLump.getStack(), ingotLead);
		}
		ItemStack nuggetLead = ItemStackUtil.getOreWithName("nuggetLead", 1);
		if (nuggetLead != null)
			GeocraftConfig.galenaOreBlock.addItem(new RareDrop(new SimpleItem(nuggetLead), 1, 6));
	}

}
