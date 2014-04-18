package redgear.geocraft.plugins;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.ShapelessOreRecipe;
import redgear.core.block.MetaBlock;
import redgear.core.block.SubBlock;
import redgear.core.item.MetaItem;
import redgear.core.item.SubItem;
import redgear.core.mod.IPlugin;
import redgear.core.mod.ModUtils;
import redgear.core.recipes.LeveledRecipe;
import redgear.core.util.ItemStackUtil;
import redgear.core.util.SimpleItem;
import redgear.geocraft.block.SubBlockGeoOre;
import redgear.geocraft.block.WeightedItem;
import redgear.geocraft.core.GeocraftConfig;
import redgear.geocraft.generation.MineGenerator;
import redgear.geocraft.generation.MineRegistry;
import redgear.geocraft.mines.MineCoal;
import redgear.geocraft.mines.MineDiamond;
import redgear.geocraft.mines.MineGold;
import redgear.geocraft.mines.MineMetal;
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

	@Override
	public void preInit(ModUtils mod) {
		final SimpleItem stone = GeocraftConfig.stone;
		final MineRegistry reg = MineGenerator.reg;
		final SimpleItem diamond = new SimpleItem(Items.diamond, 0);
		final SimpleItem coal = new SimpleItem(Items.coal, 0);

		MetaItem drops = new MetaItem("Drops");
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

		MetaBlock oreBlock = new MetaBlock(Material.rock, "Ore");
		oreBlock.setCreativeTab(GeocraftConfig.geoTab);
		oreBlock.setHardness(3f).setStepSound(Block.soundTypeStone);
		GeocraftConfig.kimberlite = oreBlock.addMetaBlock(new SubBlock("kimberlite"));
		GeocraftConfig.diamondOre = oreBlock.addMetaBlock(new SubBlockGeoOre("kimberliteDiamond", new WeightedItem(
				diamond, 1, 1, 1), new WeightedItem(diamondNugget, 0, 2, 2)));
		GeocraftConfig.coalOre = oreBlock.addMetaBlock(new SubBlockGeoOre("coalMid", new WeightedItem(coal, 1, 2, 1),
				new WeightedItem(coalNugget, 0, 4, 2)));
		GeocraftConfig.coalDenseOre = oreBlock.addMetaBlock(new SubBlockGeoOre("coalRich", new WeightedItem(coal, 2, 6,
				2), new WeightedItem(coalNugget, 0, 6, 4)));
		GeocraftConfig.terraQuartzOre = oreBlock.addMetaBlock(new SubBlockGeoOre("terraQuartzOre", new WeightedItem(
				terraQuartz, 2, 4, 1)));
		GeocraftConfig.goldOre = oreBlock.addMetaBlock(new SubBlockGeoOre("goldOre", new WeightedItem(terraQuartz, 1,
				4, 1), new WeightedItem(goldLump, 1, 3, 1), new WeightedItem(new SimpleItem(Items.gold_nugget, 0), 0,
				1, 1)));
		

		ironOreBlock = new SubBlockGeoOre("ironOre", new WeightedItem(ironLump, 1, 2, 1));
		SimpleItem ironOre = oreBlock.addMetaBlock(ironOreBlock);

		copperOreBlock = new SubBlockGeoOre("copperOre", new WeightedItem(GeocraftConfig.copperLump, 1, 2, 1));
		GeocraftConfig.copperOre = oreBlock.addMetaBlock(copperOreBlock);

		tinOreBlock = new SubBlockGeoOre("tinOre", new WeightedItem(GeocraftConfig.tinLump, 1, 2, 1));
		GeocraftConfig.tinOre = oreBlock.addMetaBlock(tinOreBlock);

		GeocraftConfig.galenaOreBlock = new SubBlockGeoOre("galenaOre");
		GeocraftConfig.galenaOre = oreBlock.addMetaBlock(GeocraftConfig.galenaOreBlock);
		
		
		
		
		final String pick = "pickaxe";
		
		
		if(GeocraftConfig.toughMode){
			/* wood: 0
			 * stone: 1
			 * copper: 2
			 * bronze: 3
			 * iron: 4
			 * steel: 5
			 * ?
			 */
			
			
			oreBlock.setHarvestLevel(pick, 5, GeocraftConfig.kimberlite.meta);
			oreBlock.setHarvestLevel(pick, 5, GeocraftConfig.diamondOre.meta);
			
			oreBlock.setHarvestLevel(pick, 1, GeocraftConfig.coalOre.meta);
			oreBlock.setHarvestLevel(pick, 1, GeocraftConfig.coalDenseOre.meta);
			
			oreBlock.setHarvestLevel(pick, 5, GeocraftConfig.terraQuartzOre.meta);
			oreBlock.setHarvestLevel(pick, 5, GeocraftConfig.goldOre.meta);
			
			oreBlock.setHarvestLevel(pick, 3, ironOre.meta);
			
			oreBlock.setHarvestLevel(pick, 1, GeocraftConfig.copperOre.meta);
			oreBlock.setHarvestLevel(pick, 2, GeocraftConfig.tinOre.meta);
			oreBlock.setHarvestLevel(pick, 4, GeocraftConfig.galenaOre.meta);
		}
		else{
			oreBlock.setHarvestLevel(pick, 2, GeocraftConfig.kimberlite.meta);
			oreBlock.setHarvestLevel(pick, 2, GeocraftConfig.diamondOre.meta);
			
			oreBlock.setHarvestLevel(pick, 1, GeocraftConfig.coalOre.meta);
			oreBlock.setHarvestLevel(pick, 1, GeocraftConfig.coalDenseOre.meta);
			
			oreBlock.setHarvestLevel(pick, 2, GeocraftConfig.terraQuartzOre.meta);
			oreBlock.setHarvestLevel(pick, 2, GeocraftConfig.goldOre.meta);
			
			oreBlock.setHarvestLevel(pick, 1, ironOre.meta);
			
			oreBlock.setHarvestLevel(pick, 1, GeocraftConfig.copperOre.meta);
			oreBlock.setHarvestLevel(pick, 1, GeocraftConfig.tinOre.meta);
			oreBlock.setHarvestLevel(pick, 2, GeocraftConfig.galenaOre.meta);
		}

		LeveledRecipe nugs = new LeveledRecipe("XXX", "XXX", "XXX");

		nugs.addLevel(true, 'X', "nuggetDiamond");
		nugs.registerShaped(diamond);

		nugs.addLevel(true, 'X', "nuggetCoal");
		nugs.registerShaped(coal);

		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(Items.quartz), terraQuartz.getStack(),
				Blocks.soul_sand));

		if (GeocraftConfig.complexMines) {
			//in order to prevent problems, we need to always add the blocks, and only make the mines configurable.
			reg.registerMine(new MineCoal(2, 20));//Coal
			reg.registerTrace("CoalTrace", new SimpleItem(Blocks.coal_ore, 0), stone, 60);

			reg.registerMine(new MineDiamond(3, 40)); //Diamond
			reg.registerTrace("DiamondTrace", new SimpleItem(Blocks.diamond_ore, 0), stone, 2);

			reg.registerMine(new MineGold(2, 20));//Gold
			reg.registerTrace("GoldTrace", new SimpleItem(Blocks.gold_ore, 0), stone, 4);

			reg.registerMine(new MineMetal("Iron", 1, 20, ironOre, stone, 8));
			reg.registerTrace("IronTrace", new SimpleItem(Blocks.iron_ore, 0), stone, 20);

			reg.addNewOre(new SimpleItem(Blocks.redstone_ore, 0), stone, 8, 7);
			reg.addNewOre(new SimpleItem(Blocks.lapis_ore, 0), stone, 1, 6);
		}

	}

	@Override
	public void Init(ModUtils mod) {

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
