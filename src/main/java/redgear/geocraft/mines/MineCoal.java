package redgear.geocraft.mines;

import java.util.Random;

import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import redgear.core.api.item.ISimpleItem;
import redgear.core.util.SimpleItem;
import redgear.core.util.SimpleOre;
import redgear.core.world.Location;
import redgear.geocraft.api.gen.Mine;
import redgear.geocraft.core.GeocraftConfig;
import redgear.geocraft.generation.VeinHelper;

public class MineCoal extends Mine {

	private transient final ISimpleItem vanillaCoal = new SimpleOre("oreCoal", new SimpleItem(Blocks.coal_ore));

	float rarity;
	float mineSize;
	float veinSize;
	boolean complexOre;

	transient int intRarity;
	transient int intMineSize;
	transient int intVeinSize;

	public MineCoal() {
		super("CoalComplex");
		isActive = GeocraftConfig.complexMines;
		complexOre = GeocraftConfig.complexOres;
		rarity = 2f;
		mineSize = 1f;
		veinSize = 20f;
	}

	@Override
	public void generate(World world, Random rand, int chunkX, int chunkZ) {
		if (rand.nextInt(intRarity) == 0)
			for (int i = 0; i < intMineSize; i++) {
				int xMin = chunkX * 16;
				int zMin = chunkZ * 16;
				int yMax = world.getHeightValue(xMin, zMin) + 1;

				int height = 1 + rand.nextInt(3);

				double layer = intVeinSize / height;//how much ore is in one layer

				int length = (int) (layer / (3 + rand.nextInt(1 + (int) Math.sqrt(layer))));
				int width = (int) (layer / length);

				Location start = new Location(xMin + rand.nextInt(16), rand.nextInt(yMax), zMin + rand.nextInt(16));
				Location end = rand.nextBoolean() ? new Location(length, height, width) : new Location(width, height,
						length);

				if (complexOre) {
					VeinHelper.generateBox(world, GeocraftConfig.coalDenseOre, GeocraftConfig.stoneOre, start, end,
							rand, intVeinSize);
					VeinHelper.generateBox(world, GeocraftConfig.coalOre, GeocraftConfig.stoneOre,
							start.translate(-1, -1, -1), end.translate(2, 2, 2), rand, intVeinSize);

				} else
					VeinHelper.generateBox(world, vanillaCoal, GeocraftConfig.stoneOre, start.translate(-1, -1, -1),
							end.translate(2, 2, 2), rand, intVeinSize);
			}
	}

	@Override
	public void init() {
		intRarity = (int) (rarity * getRegistry().rarityModifier());
		if (intRarity <= 0)
			intRarity = 1;
		intMineSize = (int) (mineSize * getRegistry().volumeModifier() / 2);
		intVeinSize = (int) (veinSize * getRegistry().volumeModifier() / 2);
	}

	@Override
	public boolean overrides(ItemStack oreBlock) {
		return vanillaCoal.equals(oreBlock);
	}

	@Override
	public void preSave() {
		
	}

}
