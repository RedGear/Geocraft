package redgear.geocraft.plugins;

import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.LoaderState;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.ShapelessOreRecipe;
import redgear.core.mod.IPlugin;
import redgear.core.mod.ModUtils;
import redgear.core.util.ItemRegUtil;
import redgear.core.util.SimpleItem;
import redgear.geocraft.core.GeocraftConfig;

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

        modUtils.logDebug("Seed: ", seedItem == null, " Certus: ", certusItem == null);

        if(seedItem == null || certusItem == null)
            return;

        ItemStack seed = seedItem.getStack(3);
        ItemStack certus = certusItem.getStack();

        //seed.getTagCompound().setInteger("progress", 0);

        if(modUtils.getBoolean("AE2CertusRecipeNeedsSilver") && ItemRegUtil.isInOreDict("dustSilver"))
            GameRegistry.addRecipe(new ShapelessOreRecipe(seed, certus, "dustSilver", Blocks.sand, GeocraftConfig.terraQuartz.getStack()));
        else
            GameRegistry.addRecipe(new ShapelessOreRecipe(seed, certus, Blocks.sand, GeocraftConfig.terraQuartz.getStack()));

    }

    @Override
    public void postInit(ModUtils modUtils) {

    }
}
