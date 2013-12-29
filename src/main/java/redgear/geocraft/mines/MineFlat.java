package redgear.geocraft.mines;

import java.util.Random;

import net.minecraft.world.World;
import redgear.core.util.SimpleItem;
import redgear.core.world.Location;
import redgear.geocraft.generation.VeinHelper;

public class MineFlat extends MineSingleOre {

	public MineFlat(String name, float mineRarity, float mineSize, SimpleItem block, SimpleItem target) {
		super(name, mineRarity, mineSize, block, target);
	}

	@Override
	public void generate(World world, Random rand, int chunkX, int chunkZ) {
		int size = 1 + rand.nextInt(4);
		int veinSize = (int) (getMineSize() / size);
		
		int xMin = chunkX * 16;
		int zMin = chunkZ * 16;
		int yMax = world.getHeightValue(xMin, zMin) + 1;
		
		
		for(int i = 0; i < size; i++)
			VeinHelper.generateFlat(world, block, target, new Location(xMin + rand.nextInt(32), rand.nextInt(yMax), zMin + rand.nextInt(32)), rand, veinSize);
	}

}
