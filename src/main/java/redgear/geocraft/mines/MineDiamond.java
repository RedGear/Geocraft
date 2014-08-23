package redgear.geocraft.mines;

import java.util.Random;

import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import redgear.core.api.item.ISimpleItem;
import redgear.core.util.SimpleItem;
import redgear.core.util.SimpleOre;
import redgear.core.world.WorldLocation;
import redgear.geocraft.core.GeocraftConfig;

public class MineDiamond extends MineSpecialStone {

	private transient final static ISimpleItem vanillaDiamond = new SimpleOre("oreDiamond", new SimpleItem(
			Blocks.diamond_ore));

	protected transient int intRarity;
	protected transient int intMineSize;
	protected transient int intVeinSize;

	float mineRarity;
	float mineSize;

	public MineDiamond() {
		super("DiamondComplex", GeocraftConfig.complexOres ? GeocraftConfig.diamondOre : new SimpleItem(
				Blocks.diamond_ore, 0), GeocraftConfig.stone, GeocraftConfig.complexOres ? GeocraftConfig.kimberlite
				: GeocraftConfig.stone);

		isActive = GeocraftConfig.complexMines;

		mineRarity = 3;
		mineSize = 40;
	}

	@Override
	public void generate(World world, Random rand, int chunkX, int chunkZ) {
		if (rand.nextInt(intRarity) == 0) {
			final float angle = 0.25f;

			int xMin = chunkX * 16 + rand.nextInt(16);
			int zMin = chunkZ * 16 + rand.nextInt(16);
			int yMax = rand.nextInt(30) + 20;

			int radius = 0;

			// volume of a cone: 1/3PIr^2h divided by ores in volume
			final float fMax = yMax;
			final int ratio = (int) (1f / 3f * Math.PI * fMax * angle * fMax * angle * fMax / intMineSize);

			for (int y = 1; y <= yMax; y++) {
				radius = (int) (y * angle);

				for (int x = 0; x < radius; x++)
					for (int z = 0; z < radius; z++)
						if (x * x + z * z < radius * radius)
							for (int r = 0; r < 4; r++)
								gen(new WorldLocation(x, 0, z, world).rotate(ForgeDirection.UP, r).translate(xMin, y,
										zMin), rand, ratio);
			}
		}
	}

	@Override
	public void init() {
		super.init();

		intRarity = (int) (mineRarity * getRegistry().rarityModifier());
		if (intRarity <= 0)
			intRarity = 1;
		intMineSize = (int) (mineSize * getRegistry().volumeModifier());

	}

}
