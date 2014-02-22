package redgear.geocraft.plugins;

import net.minecraft.init.Blocks;
import net.minecraftforge.oredict.OreDictionary;
import cpw.mods.fml.common.LoaderState.ModState;
import redgear.core.mod.IPlugin;
import redgear.core.mod.ModUtils;
import redgear.core.util.SimpleItem;
import redgear.geocraft.core.Geocraft;
import redgear.geocraft.generation.MineGenerator;
import redgear.geocraft.generation.MineRegistry;
import redgear.geocraft.mines.MineDiamond;
import redgear.geocraft.mines.MineFlat;
import redgear.geocraft.mines.MineVanilla;

public class VanillaOresPlugin implements IPlugin{

	@Override
	public String getName() {
		return "Vanilla Ores";
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
		final SimpleItem stone = Geocraft.stone;
		final MineRegistry reg = MineGenerator.reg;

		reg.registerMine(new MineVanilla("Dirt", 1, 20, new SimpleItem(Blocks.dirt), stone, 32));//dirt
		reg.registerMine(new MineVanilla("Gravel", 1, 10, new SimpleItem(Blocks.gravel), stone, 32));//gravel
		reg.registerMine(new MineVanilla("Sand", 1, 10, new SimpleItem(Blocks.sand), stone, 32));//sand
		reg.registerMine(new MineVanilla("SandStone", 1, 10, new SimpleItem(Blocks.sandstone), stone, 32));//sandstone
		if (mod.getBoolean("RegisterSandstoneAsStone"))
			OreDictionary.registerOre("stone", Blocks.sandstone);

		reg.registerMine(new MineFlat("Coal", 2 * reg.defaultDensityRate * reg.defaultDensityRate,
				60 * reg.defaultDensityRate, new SimpleItem(Blocks.coal_ore), stone));//Coal
		reg.registerTrace("CoalTrace", new SimpleItem(Blocks.coal_ore), stone, 60);

		reg.addNewOre(new SimpleItem(Blocks.iron_ore), stone, 20, 8);
		reg.addNewOre(new SimpleItem(Blocks.gold_ore), stone, 2, 8);
		reg.addNewOre(new SimpleItem(Blocks.redstone_ore), stone, 8, 7);
		reg.addNewOre(new SimpleItem(Blocks.lapis_ore), stone, 1, 6);

		reg.registerMine(new MineDiamond(2 * reg.defaultDensityRate * reg.defaultDensityRate,
				10 * reg.defaultDensityRate)); //Diamond
		reg.registerTrace("DiamondTrace", new SimpleItem(Blocks.diamond_ore), stone, 2);
		
	}

	@Override
	public void Init(ModUtils mod) {
		
	}

	@Override
	public void postInit(ModUtils mod) {
		
	}

}
