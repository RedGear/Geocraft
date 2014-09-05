package redgear.geocraft.mines;

import java.util.Random;

import net.minecraft.world.World;
import redgear.core.api.item.ISimpleItem;
import redgear.core.world.WorldLocation;
import redgear.geocraft.api.mine.MineSingleOre;
import redgear.geocraft.generation.VeinHelper;

public class MineVanilla extends MineSingleOre {

	protected int veinsPerChunk;
	protected int veinSize;
	
	public MineVanilla(){
		
	}

	public MineVanilla(String name, ISimpleItem block, ISimpleItem target, int veinsPerChunk, int veinSize) {
		super(name, block, target);
		this.veinsPerChunk = veinsPerChunk;
		this.veinSize = veinSize;
	}

	@Override
	public void generate(World world, Random rand, int chunkX, int chunkZ) {
		int xMin = chunkX * 16;
		int zMin = chunkZ * 16;

		int yMax = world.getHeightValue(chunkX * 16, chunkZ * 16);
		int size = Math.round(reletiveSize(world, chunkX, chunkZ, veinsPerChunk));

		for (int i = 0; i < size; ++i)
			VeinHelper.generateSphere(
					new WorldLocation(xMin + rand.nextInt(16), rand.nextInt(yMax), zMin + rand.nextInt(16), world),
					block, target, rand, veinSize);

	}
	
	public static final float reletiveSize(World world, int chunkX, int chunkZ, float size){
		return (((float)world.getHeightValue(chunkX * 16, chunkZ * 16)) / ((float)world.getHeight())) * size;
	}

}
