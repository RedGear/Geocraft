package redgear.geocraft.mines;

import java.util.Random;

import net.minecraft.world.World;
import redgear.core.api.item.ISimpleItem;
import redgear.core.world.Location;

public class MineTrace extends MineSingleOre {

	protected int oresPerChunk;
	
	public MineTrace(){
		
	}

	public MineTrace(String name, ISimpleItem block, ISimpleItem target, int oresPerChunk) {
		super(name, block, target);
		this.oresPerChunk = oresPerChunk;
	}

	@Override
	public void generate(World world, Random rand, int chunkX, int chunkZ) {
		int xMin = chunkX * 16;
		int zMin = chunkZ * 16;
		int yMax = world.getHeightValue(chunkX * 16, chunkZ * 16);
		int size = Math.round(reletiveSize(world, chunkX, chunkZ, oresPerChunk));

		for (int i = 0; i < size; i++)
			new Location(xMin + rand.nextInt(16), rand.nextInt(yMax), zMin + rand.nextInt(16)).placeBlock(world, block,
					target);

	}

	public static final float reletiveSize(World world, int chunkX, int chunkZ, float size) {
		return (float) world.getHeightValue(chunkX * 16, chunkZ * 16) / (float) world.getHeight() * size;
	}

}
