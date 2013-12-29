package redgear.geocraft.api;

import java.util.Random;

import net.minecraft.world.World;

public interface IMine {
	public String getName();
	
	public float getMineRarity();
	public float getMineSize();
	
	/**
	 * @param world
	 * @param rand
	 * @param chunkX
	 * @param chunkZ
	 * @param creator
	 */
	public void generate(World world, Random rand, int chunkX, int chunkZ);
}
