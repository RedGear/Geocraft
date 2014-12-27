package redgear.geocraft.plugins;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.init.Items;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;
import redgear.core.api.item.ISimpleItem;
import redgear.core.block.MetaBlock;
import redgear.core.block.SubBlock;
import redgear.core.mod.IPlugin;
import redgear.core.mod.ModUtils;
import redgear.geocraft.api.MineManager;
import redgear.geocraft.core.GeocraftConfig;
import redgear.geocraft.mines.MineVanilla;
import cpw.mods.fml.common.LoaderState.ModState;
import cpw.mods.fml.common.registry.GameRegistry;

public class OnePointEightPlugin implements IPlugin{
	
	ISimpleItem graniteRough;
	ISimpleItem granitePolished;
	ISimpleItem doriteRough;
	ISimpleItem doritePolished;
	ISimpleItem andesiteRough;
	ISimpleItem andesitePolished;

	@Override
	public String getName() {
		return "Minecraft 1.8 backporting";
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
		MetaBlock<SubBlock> stone = new MetaBlock<SubBlock>(Material.rock, "OnePointEightStone");
		stone.setHardness(1.5F).setResistance(10.0F).setStepSound(Block.soundTypePiston).setCreativeTab(GeocraftConfig.geoTab);
		
		
		 graniteRough = stone.addMetaBlock(new SubBlock("graniteRough"));
		 granitePolished = stone.addMetaBlock(new SubBlock("granitePolished"));
		 doriteRough = stone.addMetaBlock(new SubBlock("doriteRough"));
		 doritePolished =stone.addMetaBlock(new SubBlock("doritePolished"));
		 andesiteRough =stone.addMetaBlock(new SubBlock("andesiteRough"));
		 andesitePolished =stone.addMetaBlock(new SubBlock("andesitePolished"));
		
		
		GameRegistry.addRecipe(new ShapedOreRecipe(doriteRough.getStack(2), "CQ", "QC", 'Q', Items.quartz, 'C', "cobblestone"));
		GameRegistry.addRecipe(new ShapelessOreRecipe(andesiteRough.getStack(2), doriteRough.getStack(), "cobblestone"));
		GameRegistry.addRecipe(new ShapelessOreRecipe(graniteRough.getStack(2), doriteRough.getStack(), Items.quartz));
		
		
		GameRegistry.addShapedRecipe(granitePolished.getStack(4), "BB", "BB", 'B', graniteRough.getStack());
		GameRegistry.addShapedRecipe(doritePolished.getStack(4), "BB", "BB", 'B', doriteRough.getStack());
		GameRegistry.addShapedRecipe(andesitePolished.getStack(4), "BB", "BB", 'B', andesiteRough.getStack());
		
		mod.addSmelting(granitePolished, graniteRough);
		mod.addSmelting(doritePolished, doriteRough);
		mod.addSmelting(andesitePolished, andesiteRough);
		
		mod.registerOre("stone", granitePolished);
		mod.registerOre("stone", doritePolished);
		mod.registerOre("stone", andesitePolished);
		
		mod.registerOre("cobblestone", graniteRough);
		mod.registerOre("cobblestone", doriteRough);
		mod.registerOre("cobblestone", andesiteRough);
		
	}

	@Override
	public void Init(ModUtils mod) {
		boolean genStone = mod.getBoolean("Plugins", "MinecraftOnePointEight");

		//in order to prevent problems, we need to always add the blocks, and only make the mines configurable.
		MineManager.oreRegistry.registerMine(new MineVanilla("Granite", graniteRough, GeocraftConfig.stone, 5, 32).setActive(genStone));
		MineManager.oreRegistry.registerMine(new MineVanilla("Dorite", doriteRough, GeocraftConfig.stone, 5, 32).setActive(genStone));
		MineManager.oreRegistry.registerMine(new MineVanilla("Andesite", andesiteRough, GeocraftConfig.stone, 5, 32).setActive(genStone));
	}

	@Override
	public void postInit(ModUtils mod) {
		
	}

}
