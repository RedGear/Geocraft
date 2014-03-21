package redgear.geocraft.mines;

import java.util.Random;

import net.minecraft.world.World;
import redgear.core.util.SimpleItem;
import redgear.core.util.StringHelper;
import redgear.core.world.Location;
import redgear.geocraft.api.IEveryChunk;
import redgear.geocraft.api.MineManager;
import redgear.geocraft.core.Geocraft;
import redgear.geocraft.generation.VeinHelper;

public class MineVanilla extends MineSingleOre implements IEveryChunk {
	
	protected final int veinSize;
	
	/**
	 * MineVanilla automatically calls registerIgnore for it's ore. 
	 */
	public MineVanilla(String name, float mineRarity, float mineSize, SimpleItem block, SimpleItem target, int veinSize) {
		super(name, mineRarity, mineSize, block, target);
		this.veinSize = Geocraft.inst.getInt("level2." + StringHelper.camelCase(name), "veinSize", veinSize);
		MineManager.oreRegistry.registerIgnore(block.getBlock(), block.meta);
	}

	@Override
	public void generate(World world, Random rand, int chunkX, int chunkZ) {
		int xMin = chunkX * 16;
		int zMin = chunkZ * 16;
		
		int yMax = world.getHeightValue(chunkX * 16, chunkZ * 16); 
		int size = Math.round(reletiveSize(world, chunkX, chunkZ, mineSize));
		
		for(int i = 0; i < size; ++i)
			VeinHelper.generateSphere(world, block, target, new Location(xMin + rand.nextInt(16),  rand.nextInt(yMax), zMin + rand.nextInt(16)), rand, veinSize);
		
	}

}
