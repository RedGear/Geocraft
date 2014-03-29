package redgear.geocraft.mines;

import java.util.Random;

import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import redgear.core.util.SimpleItem;
import redgear.core.world.WorldLocation;
import redgear.geocraft.core.Geocraft;
import redgear.geocraft.generation.MineGenerator;
import redgear.geocraft.generation.VeinHelper;
import thaumcraft.api.aspects.Aspect;

public class MineThaumInfusedStone extends Mine{

	private int blockId;
	
	public MineThaumInfusedStone(int blockId) {
		super("ThaumInfusedStone", 1, 8);
		this.blockId = blockId;
	}

	@Override
	public void generate(World world, Random rand, int chunkX, int chunkZ) {
		try{
			int md = rand.nextInt(6) + 1;
	        if (rand.nextInt(3) == 0){
	        	Class biomeHelper = Class.forName("thaumcraft.common.lib.world.biomes.BiomeHandler");
	        	
	        	Aspect tag = (Aspect) biomeHelper.getMethod("getRandomBiomeTag", int.class, Random.class).invoke(null, world.getBiomeGenForCoords(chunkX * 16, chunkZ * 16).biomeID, rand);
			
	        	if (tag == null) {
	                md = 1 + rand.nextInt(6);
	              }
	              else if (tag == Aspect.AIR) md = 1;
	              else if (tag == Aspect.FIRE) md = 2;
	              else if (tag == Aspect.WATER) md = 3;
	              else if (tag == Aspect.EARTH) md = 4;
	              else if (tag == Aspect.ORDER) md = 5;
	              else if (tag == Aspect.ENTROPY) md = 6;
	        }
	        
	        //generateMine(world, rand, chunkX, chunkZ, new SimpleItem(blockId, md));
		}
		catch(Exception e){
			
		}
		
	}
	
	private void generateMine(World world, Random rand, int chunkX, int chunkZ, SimpleItem block){
		SimpleItem target = Geocraft.stone;
		final int desity = 8;
		final int veinSize = 6;
		
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
			
			
			VeinHelper.generateSphere(new WorldLocation(x + centerX, y, z + centerZ, world), block, target, rand, veinSize);
		}
	}

}
