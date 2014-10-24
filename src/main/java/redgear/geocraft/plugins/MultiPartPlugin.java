package redgear.geocraft.plugins;

import codechicken.microblock.BlockMicroMaterial;
import codechicken.microblock.MicroMaterialRegistry;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.LoaderState.ModState;
import redgear.core.api.item.ISimpleItem;
import redgear.core.mod.IPlugin;
import redgear.core.mod.ModUtils;

/**
 * @author Blackhole
 *         Created on 10/22/2014.
 */
public class MultiPartPlugin implements IPlugin {
    @Override
    public String getName() {
        return "Forge Multi Part";
    }

    @Override
    public boolean shouldRun(ModUtils mod, ModState state) {
        return mod.getBoolean("plugins", "ForgeMicroblock") && Loader.isModLoaded("ForgeMicroblock");
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
        StonePlugin stone = StonePlugin.getInst();

        register(stone.limeCobble);
        register(stone.limeClean);
        register(stone.limeCracked);
        register(stone.limeBrick);
        register(stone.limeCarved);
        register(stone.limePaver);

        register(stone.basaltCobble);
        register(stone.basaltClean);
        register(stone.basaltCracked);
        register(stone.basaltBrick);
        register(stone.basaltCarved);
        register(stone.basaltPaver);

        register(stone.marbleCobble);
        register(stone.marbleClean);
        register(stone.marbleCracked);
        register(stone.marbleBrick);
        register(stone.marbleCarved);
        register(stone.marblePaver);
    }

    @Override
    public void postInit(ModUtils modUtils) {

    }

    private void register(ISimpleItem block){
        MicroMaterialRegistry.registerMaterial(new BlockMicroMaterial(block.getBlock(), block.getMeta()), block.getName());
    }
}
