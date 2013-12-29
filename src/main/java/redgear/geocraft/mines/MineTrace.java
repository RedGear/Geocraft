package redgear.geocraft.mines;

import java.util.Random;

import net.minecraft.world.World;
import redgear.core.util.SimpleItem;
import redgear.core.world.Location;
import redgear.geocraft.api.IEveryChunk;

public class MineTrace extends MineSingleOre implements IEveryChunk {

	public MineTrace(String name, float mineRarity, float mineSize, SimpleItem block, SimpleItem target) {
		super(name, mineRarity, mineSize, block, target);
	}

	@Override
	public void generate(World world, Random rand, int chunkX, int chunkZ) {
		int xMin = chunkX * 16;
		int zMin = chunkZ * 16;
		int yMax = world.getHeightValue(chunkX * 16, chunkZ * 16);
		int size = Math.round(reletiveSize(world, chunkX, chunkZ, mineSize));
		
		for(int i = 0; i < size; i++)
			new Location(xMin + rand.nextInt(16), rand.nextInt(yMax), zMin + rand.nextInt(16)).placeBlock(world, block, target);
		
	}

}
