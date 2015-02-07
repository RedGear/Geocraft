package redgear.geocraft.plugins;

import redgear.core.api.item.ISimpleItem;
import redgear.core.mod.IPlugin;
import redgear.core.mod.ModUtils;
import redgear.geocraft.core.Geocraft;
import codechicken.microblock.BlockMicroMaterial;
import codechicken.microblock.MicroMaterialRegistry;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.LoaderState.ModState;

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
        StonePlugin stone = Geocraft.inst.stonePlugin;
        
        Test t = new Test();

        t.register(stone.limeCobble);
        t.register(stone.limeClean);
        t.register(stone.limeCracked);
        t.register(stone.limeBrick);
        t.register(stone.limeCarved);
        t.register(stone.limePaver);

        t.register(stone.basaltCobble);
        t.register(stone.basaltClean);
        t.register(stone.basaltCracked);
        t.register(stone.basaltBrick);
        t.register(stone.basaltCarved);
        t.register(stone.basaltPaver);

        t.register(stone.marbleCobble);
        t.register(stone.marbleClean);
        t.register(stone.marbleCracked);
        t.register(stone.marbleBrick);
        t.register(stone.marbleCarved);
        t.register(stone.marblePaver);
        
        OnePointEightPlugin oneEight = Geocraft.inst.oneEightPlugin;
        
        
        t.register(oneEight.graniteRough);
        t.register(oneEight.granitePolished);
        t.register(oneEight.doriteRough);
        t.register(oneEight.doritePolished);
        t.register(oneEight.andesiteRough);
        t.register(oneEight.andesitePolished);
    }

    @Override
    public void postInit(ModUtils modUtils) {

    }

    //Private class keeps the Multipart stuff separate. 
    
    private class Test{
    	private void register(ISimpleItem block){
    		MicroMaterialRegistry.registerMaterial(new BlockMicroMaterial(block.getBlock(), block.getMeta()), block.getName());
    	}
    }
}
