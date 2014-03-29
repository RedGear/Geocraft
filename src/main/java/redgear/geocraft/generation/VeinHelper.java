package redgear.geocraft.generation;

import java.util.Random;

import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import redgear.core.util.SimpleItem;
import redgear.core.world.Location;
import redgear.core.world.WorldLocation;

public final class VeinHelper {

	private VeinHelper() {
	}

	public static boolean isInAir(World world, Location test) {
		return test.getY() >= world.getHeightValue(test.getX(), test.getZ());
	}

	public static boolean isInAir(WorldLocation test) {
		return isInAir(test.world, test);
	}

	public static void generateSphere(WorldLocation start, SimpleItem block, SimpleItem target, Random rand, int size) {
		if (isInAir(start))
			return;

		float f = rand.nextFloat() * (float) Math.PI;
		double d0 = start.getX() + 8 + MathHelper.sin(f) * size / 8.0F;
		double d1 = start.getX() + 8 - MathHelper.sin(f) * size / 8.0F;
		double d2 = start.getZ() + 8 + MathHelper.cos(f) * size / 8.0F;
		double d3 = start.getZ() + 8 - MathHelper.cos(f) * size / 8.0F;
		double d4 = start.getY() + rand.nextInt(3) - 2;
		double d5 = start.getY() + rand.nextInt(3) - 2;

		for (int l = 0; l <= size; ++l) {
			double d6 = d0 + (d1 - d0) * l / size;
			double d7 = d4 + (d5 - d4) * l / size;
			double d8 = d2 + (d3 - d2) * l / size;
			double d9 = rand.nextDouble() * size / 16.0D;
			double d10 = (MathHelper.sin(l * (float) Math.PI / size) + 1.0F) * d9 + 1.0D;
			double d11 = (MathHelper.sin(l * (float) Math.PI / size) + 1.0F) * d9 + 1.0D;
			int i1 = MathHelper.floor_double(d6 - d10 / 2.0D);
			int j1 = MathHelper.floor_double(d7 - d11 / 2.0D);
			int k1 = MathHelper.floor_double(d8 - d10 / 2.0D);
			int l1 = MathHelper.floor_double(d6 + d10 / 2.0D);
			int i2 = MathHelper.floor_double(d7 + d11 / 2.0D);
			int j2 = MathHelper.floor_double(d8 + d10 / 2.0D);

			for (int k2 = i1; k2 <= l1; ++k2) {
				double d12 = (k2 + 0.5D - d6) / (d10 / 2.0D);

				if (d12 * d12 < 1.0D)
					for (int l2 = j1; l2 <= i2; ++l2) {
						double d13 = (l2 + 0.5D - d7) / (d11 / 2.0D);

						if (d12 * d12 + d13 * d13 < 1.0D)
							for (int i3 = k1; i3 <= j2; ++i3) {
								double d14 = (i3 + 0.5D - d8) / (d10 / 2.0D);

								if (d12 * d12 + d13 * d13 + d14 * d14 < 1.0D)
									new Location(k2, l2, i3).placeBlock(start.world, block, target);
							}
					}
			}
		}
	}

	public static void generateBox(World world, SimpleItem block, SimpleItem target, Location start, Location relative,
			Random rand, int size) {
		if (isInAir(world, start))
			return;

		for (int h = 0; h < relative.getY(); h++)
			for (int l = 0; l < relative.getX(); l++)
				for (int w = 0; w < relative.getZ(); w++)
					new Location(l, h, w).placeBlock(world, block, target, start);
	}

	public static void generateMetal(WorldLocation start, SimpleItem block, SimpleItem target, Random rand, int size) {
		if (isInAir(start))
			return;

		WorldLocation previous = start;
		WorldLocation next;

		for (int i = 0; i < size; i++) {
			do
				next = start.translate(rand.nextInt(), 1);
			while (next.equals(previous));

			next.placeBlock(block, target);
			previous = start;
			start = next;
		}
	}
}
