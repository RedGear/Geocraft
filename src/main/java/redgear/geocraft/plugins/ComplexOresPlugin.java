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
import redgear.core.util.SimpleItem;
import redgear.geocraft.block.SubBlockGeoOre;
import redgear.geocraft.block.WeightedItem;
import redgear.geocraft.core.Geocraft;
import redgear.geocraft.generation.GeoMode;
import redgear.geocraft.generation.MineGenerator;
import redgear.geocraft.generation.MineRegistry;
import redgear.geocraft.mines.MineCoal;
import redgear.geocraft.mines.MineDiamond;
import redgear.geocraft.mines.MineGold;
import cpw.mods.fml.common.LoaderState.ModState;
import cpw.mods.fml.common.registry.GameRegistry;

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
		drops.setCreativeTab(Geocraft.geoTab);
		SimpleItem diamondNugget = drops.addMetaItem(new SubItem("nuggetDiamond"));
		mod.registerOre("nuggetDiamond", diamondNugget);
		
		SimpleItem coalNugget = drops.addMetaItem(new SubItem("nuggetCoal"));
		mod.registerOre("nuggetCoal", coalNugget);
		
		SimpleItem terraQuartz = drops.addMetaItem(new SubItem("terraQuartz"));
		mod.registerOre("quartz", terraQuartz);
		
		SimpleItem goldLump = drops.addMetaItem(new SubItem("goldLump"));
		mod.registerOre("oreGold", goldLump);
		
		MetaBlock oreBlock = new MetaBlock(Material.rock, "Ore");
		oreBlock.setCreativeTab(Geocraft.geoTab);
		oreBlock.setHardness(3f).setStepSound(Block.soundTypeStone);
		Geocraft.kimberlite = oreBlock.addMetaBlock(new SubBlock("kimberlite"));
		Geocraft.diamondOre = oreBlock.addMetaBlock(new SubBlockGeoOre("kimberliteDiamond", new WeightedItem(diamond, 1, 1, 1), new WeightedItem(diamondNugget, 0, 2, 2)));
		Geocraft.coalOre = oreBlock.addMetaBlock(new SubBlockGeoOre("coalMid", new WeightedItem(coal, 1, 2, 1), new WeightedItem(coalNugget, 0, 4, 2)));
		Geocraft.coalDenseOre = oreBlock.addMetaBlock(new SubBlockGeoOre("coalRich", new WeightedItem(coal, 2, 6, 2), new WeightedItem(coalNugget, 0, 6, 4)));
		Geocraft.terraQuartzOre = oreBlock.addMetaBlock(new SubBlockGeoOre("terraQuartzOre", new WeightedItem(terraQuartz, 2, 4, 1)));
		Geocraft.goldOre = oreBlock.addMetaBlock(new SubBlockGeoOre("goldOre", new WeightedItem(terraQuartz, 1, 4, 1), new WeightedItem(goldLump, 1, 3, 1), new WeightedItem(new SimpleItem(Items.gold_nugget, 0), 0, 1, 1)));
		
		final int density = reg.defaultDensityRate;
		final int density2 = density * density;
		
		reg.registerMine(new MineCoal(2 * density2, 20 * density));//Coal
		reg.registerTrace("CoalTrace", new SimpleItem(Blocks.coal_ore, 0), stone, 60);
		
		reg.registerMine(new MineDiamond(3 * density2, 40 * density)); //Diamond
		reg.registerTrace("DiamondTrace", new SimpleItem(Blocks.diamond_ore, 0), stone, 2);

		reg.registerMine(new MineGold(2 * density2, 30 * density));//Gold
		reg.registerTrace("GoldTrace", new SimpleItem(Blocks.gold_ore, 0), stone, 4);
		
		reg.addNewOre(new SimpleItem(Blocks.iron_ore, 0), stone, 20, 8);
		reg.addNewOre(new SimpleItem(Blocks.redstone_ore, 0), stone, 8, 7);
		reg.addNewOre(new SimpleItem(Blocks.lapis_ore, 0), stone, 1, 6);

		
		
		LeveledRecipe nugs = new LeveledRecipe("XXX", "XXX", "XXX");
		
		nugs.addLevel(true, 'X', "nuggetDiamond");
		nugs.registerShaped(diamond);
		
		nugs.addLevel(true, 'X', "nuggetCoal");
		nugs.registerShaped(coal);
		
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(Items.quartz), terraQuartz.getStack(), Blocks.soul_sand));
	}

	@Override
	public void Init(ModUtils mod) {
		
	}

	@Override
	public void postInit(ModUtils mod) {
		
	}

}
