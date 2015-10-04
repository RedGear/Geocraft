package redgear.geocraft.plugins;

import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.LoaderState;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.oredict.ShapelessOreRecipe;
import redgear.core.api.item.ISimpleItem;
import redgear.core.mod.IPlugin;
import redgear.core.mod.ModUtils;
import redgear.core.util.ItemRegUtil;
import redgear.core.util.SimpleItem;
import redgear.core.world.WorldLocation;
import redgear.geocraft.api.mine.MineSpecialStone;
import redgear.geocraft.core.GeocraftConfig;
import redgear.geocraft.generation.MineGenerator;
import redgear.geocraft.generation.MineRegistry;
import redgear.geocraft.generation.VeinHelper;
import redgear.geocraft.mines.MineCylinderComplex;

import java.util.Random;

/**
 * @author Blackhole
 *         Created on 10/23/2014.
 */
public class AE2Plugin implements IPlugin {

    @Override
    public String getName() {
        return "Applied Energistics Compat";
    }

    @Override
    public boolean shouldRun(ModUtils mod, LoaderState.ModState modState) {
        return mod.getBoolean("Plugins", "AE2") && Loader.isModLoaded("appliedenergistics2");
    }

    @Override
    public boolean isRequired() {
        return false;
    }

    @Override
    public void preInit(ModUtils modUtils) {

    }

    @Override
    public void Init(ModUtils modUtils) {
        SimpleItem seedItem = ItemRegUtil.findItem("appliedenergistics2", "item.ItemCrystalSeed");
        SimpleItem certusItem = ItemRegUtil.findItem("appliedenergistics2", "item.ItemMultiMaterial", 2);

        modUtils.logDebug("Seed: ", seedItem.getBlock() == Blocks.air, " Certus: ", certusItem.getBlock() == Blocks.air);

	    ItemStack seed = seedItem.getStack(3);
	    ItemStack certus = certusItem.getStack();

	    //seed.getTagCompound().setInteger("progress", 0);

	    if (modUtils.getBoolean("AE2CertusRecipeNeedsSilver") && ItemRegUtil.isInOreDict("dustSilver"))
		    GameRegistry.addRecipe(new ShapelessOreRecipe(seed, certus, "dustSilver", Blocks.sand, GeocraftConfig.terraQuartz.getStack()));
	    else
		    GameRegistry.addRecipe(new ShapelessOreRecipe(seed, certus, Blocks.sand, GeocraftConfig.terraQuartz.getStack()));

	    SimpleItem certusQuartz = ItemRegUtil.findItem("appliedenergistics2", "tile.OreQuartz");
	    SimpleItem certusQuartzCharged = ItemRegUtil.findItem("appliedenergistics2", "tile.OreQuartzCharged");



	    final ISimpleItem stone = GeocraftConfig.stoneOre;
	    final MineRegistry reg = MineGenerator.reg;

	    if(certusQuartz.getBlock() != Blocks.air) {
		    reg.registerMine(new MineCylinderComplex("CertusQuartzOre", certusQuartz, stone, 6, 4, 8, false));
		    reg.registerTrace("CertusQuartzOreTrace", certusQuartz, stone, 4);
	    }

	    if(certusQuartzCharged.getBlock() != Blocks.air) {
		    reg.registerMine(new MineCylinderComplex("CertusQuartzOreCharged", certusQuartzCharged, stone, 10, 2, 4, false));
		    reg.registerTrace("CertusQuartzOreChargedTrace", certusQuartzCharged, stone, 2);
	    }

    }


    @Override
    public void postInit(ModUtils modUtils) {

    }
}
