package redgear.geocraft.mines;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import redgear.core.util.SimpleItem;
import redgear.core.world.Location;

public class MineDiamond extends MineSingleOre{

	public MineDiamond(float mineRarity, float mineSize) {
		super("Diamond", mineRarity, mineSize, new SimpleItem(Blocks.diamond_ore), new SimpleItem(Blocks.stone));
	}

	@Override
	public void generate(World world, Random rand, int chunkX, int chunkZ) {
		final float angle = 0.25f;
		
		int xMin = chunkX * 16 + rand.nextInt(16);
		int zMin = chunkZ * 16 + rand.nextInt(16);
		int yMax = Math.max(rand.nextInt(world.getActualHeight()), 16);
		
		int size = (int)getMineSize();
		int x = 0;
		int y = 0;
		int z = 0;
		int radius = 0;
		float rotation;
		
		
		for(int i = 0; i <= size; i++){
			rotation = rand.nextFloat() * (float)Math.PI;
			y = rand.nextInt(yMax);
			radius = (int) ((float)y * angle);
					
			x = (int) (MathHelper.sin(rotation) * radius);
			z = (int) (MathHelper.cos(rotation) * radius);
			
			new Location(x + xMin, y, z + zMin).placeBlock(world, block, target);
		}
	}

}
