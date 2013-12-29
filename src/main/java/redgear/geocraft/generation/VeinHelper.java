package redgear.geocraft.generation;

import java.util.Random;

import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import redgear.core.util.SimpleItem;
import redgear.core.world.Location;

public class VeinHelper  {
	
	private static boolean isInAir(World world, Location test){
		return test.y >= world.getHeightValue(test.x, test.z);
	}
	
	public static void generateSphere(World world, SimpleItem block, SimpleItem target, Location start, Random rand, int size){
		if(isInAir(world, start))
			return;
		
		float f = rand.nextFloat() * (float)Math.PI;
        double d0 = (double)((float)(start.x + 8) + MathHelper.sin(f) * (float)size / 8.0F);
        double d1 = (double)((float)(start.x + 8) - MathHelper.sin(f) * (float)size / 8.0F);
        double d2 = (double)((float)(start.z + 8) + MathHelper.cos(f) * (float)size / 8.0F);
        double d3 = (double)((float)(start.z + 8) - MathHelper.cos(f) * (float)size / 8.0F);
        double d4 = (double)(start.y + rand.nextInt(3) - 2);
        double d5 = (double)(start.y + rand.nextInt(3) - 2);

        for (int l = 0; l <= size; ++l){
            double d6 = d0 + (d1 - d0) * (double)l / (double)size;
            double d7 = d4 + (d5 - d4) * (double)l / (double)size;
            double d8 = d2 + (d3 - d2) * (double)l / (double)size;
            double d9 = rand.nextDouble() * (double)size / 16.0D;
            double d10 = (double)(MathHelper.sin((float)l * (float)Math.PI / (float)size) + 1.0F) * d9 + 1.0D;
            double d11 = (double)(MathHelper.sin((float)l * (float)Math.PI / (float)size) + 1.0F) * d9 + 1.0D;
            int i1 = MathHelper.floor_double(d6 - d10 / 2.0D);
            int j1 = MathHelper.floor_double(d7 - d11 / 2.0D);
            int k1 = MathHelper.floor_double(d8 - d10 / 2.0D);
            int l1 = MathHelper.floor_double(d6 + d10 / 2.0D);
            int i2 = MathHelper.floor_double(d7 + d11 / 2.0D);
            int j2 = MathHelper.floor_double(d8 + d10 / 2.0D);

            for (int k2 = i1; k2 <= l1; ++k2){
                double d12 = ((double)k2 + 0.5D - d6) / (d10 / 2.0D);

                if (d12 * d12 < 1.0D)
                    for (int l2 = j1; l2 <= i2; ++l2){
                        double d13 = ((double)l2 + 0.5D - d7) / (d11 / 2.0D);

                        if (d12 * d12 + d13 * d13 < 1.0D)
                            for (int i3 = k1; i3 <= j2; ++i3){
                                double d14 = ((double)i3 + 0.5D - d8) / (d10 / 2.0D);
                                
                                if (d12 * d12 + d13 * d13 + d14 * d14 < 1.0D)
                                	new Location(k2, l2, i3).placeBlock(world, block, target);
                            }
                    }
            }
        }
    }
	
	public static void generateFlat(World world, SimpleItem block, SimpleItem target, Location start, Random rand, int size) {
		if(isInAir(world, start))
			return;
		
		int height = 1 + rand.nextInt(3);
		
		double layer = size / height;//how much ore is in one layer
		
		int length = (int) (layer / (1 + rand.nextInt((int) Math.sqrt(layer))));
		int width = (int) (layer / length);
		
		boolean direct = rand.nextBoolean();
		
		for(int h = 0; h < height; h++)
			for(int l = 0; l < length; l++)
				for(int w = 0; w < width; w++)
					if(direct)
						new Location(l, h, w).placeBlock(world, block, target, start);
					else
						new Location(w, h, l).placeBlock(world, block, target, start);
	}
}
