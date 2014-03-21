package redgear.geocraft.mines;

import java.util.Random;

import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import redgear.core.util.SimpleItem;
import redgear.core.util.StringHelper;
import redgear.core.world.Location;
import redgear.geocraft.core.Geocraft;
import redgear.geocraft.generation.VeinHelper;

/**
 * Defines a vertical standing cylinder
 * @author BlackHole
 *
 */
public class MineCylinder extends MineSingleOre {

	private final int veinSize;
	private static final int desity = 8;
	
	public MineCylinder(String name, float mineRarity, float mineSize, SimpleItem block, SimpleItem target, int veinSize){
		super(name, mineRarity, mineSize, block, target);
		this.veinSize = Geocraft.inst.getInt("level2." + StringHelper.camelCase(name), "veinSize", veinSize);
	}

	@Override
	public void generate(World world, Random rand, int chunkX, int chunkZ) {
		int centerX = chunkX * 16 + rand.nextInt(16); //defines the center of the vertical standing cylinder
		int centerZ = chunkZ * 16 + rand.nextInt(16);
		int top = Math.max(rand.nextInt(world.getActualHeight()), 16);
		int bottom = Math.max(rand.nextInt(top), top - 16);
		
		if(bottom >= world.getHeightValue(chunkX * 16, chunkZ * 16))
			return;
		
		float size = getMineSize();
		
		int maxRadius =  (int)Math.max(Math.sqrt((veinSize * (int)size * desity) / (top - bottom) / Math.PI), 4);
		
		int x = 0;
		int y = 0;
		int z = 0;
		
		float angle = 0;
		float radius = 0;
		
		
		
		for(int i = 0; i <= size; i++){
			y = 4 + rand.nextInt(top);
			angle = rand.nextFloat() * (float)Math.PI;
			radius = rand.nextInt(maxRadius);
			
			x = (int) (MathHelper.sin(angle) * radius);
			z = (int) (MathHelper.cos(angle) * radius);
			
			
			VeinHelper.generateSphere(world, block, target, new Location(x + centerX, y, z + centerZ), rand, veinSize);
		}
		
		
	}

}
