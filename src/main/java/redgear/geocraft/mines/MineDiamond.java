package redgear.geocraft.mines;

import java.util.Random;

import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import redgear.core.world.Location;
import redgear.core.world.WorldLocation;
import redgear.geocraft.core.Geocraft;

public class MineDiamond extends Mine {

	public MineDiamond(float mineRarity, float mineSize) {
		super("Diamond", mineRarity, mineSize);
	}

	@Override
	public void generate(World world, Random rand, int chunkX, int chunkZ) {
		final float angle = 0.25f;

		int xMin = chunkX * 16 + rand.nextInt(16);
		int zMin = chunkZ * 16 + rand.nextInt(16);
		int yMax = rand.nextInt(30) + 20;

		int radius = 0;
		
		// volume of a cone: 1/3PIr^2h divided by ores in volume
		final float fMax = yMax;
		final int ratio = (int) (1f / 3f * Math.PI * fMax * angle * fMax * angle * fMax / getMineSize()); 
		
		for (int y = 1; y <= yMax; y++) {
			radius = (int) (y * angle);

			for (int x = 0; x < radius; x++)
				for (int z = 0; z < radius; z++)
					if (x * x + z * z < radius * radius) {
						for (int r = 0; r < 4; r++)
							gen(new WorldLocation(x, 0, z, world).rotate(ForgeDirection.UP, r), new Location(xMin, y, zMin), rand, ratio);
					}
		}
	}

	private void gen(WorldLocation loc, Location relative, Random rand, int ratio) {
		if (rand.nextInt(ratio) == 0)
			loc.placeBlock(Geocraft.diamondOre, Geocraft.stone, relative);
		else
			loc.placeBlock(Geocraft.kimberlite, Geocraft.stone, relative);
	}
}
