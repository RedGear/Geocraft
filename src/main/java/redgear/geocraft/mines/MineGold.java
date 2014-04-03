package redgear.geocraft.mines;

import java.util.Random;

import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import redgear.core.util.SimpleItem;
import redgear.core.world.WorldLocation;
import redgear.geocraft.core.GeocraftConfig;

//Thars gold in them thar hills!
public class MineGold extends MineSpecialStone{
	
	private static final int ratio = 5;// times more quartz than gold. 

	public MineGold(float mineRarity, float mineSize) {
		super("Gold", mineRarity, mineSize, GeocraftConfig.complexOres ? GeocraftConfig.terraQuartzOre : GeocraftConfig.stone, 
				GeocraftConfig.complexOres ? GeocraftConfig.goldOre : new SimpleItem(Blocks.gold_ore, 0), GeocraftConfig.stone);
	}

	@Override
	public void generate(World world, Random rand, int chunkX, int chunkZ) {
		final int veinCount = 4 + rand.nextInt(ratio);
		
		final int minX = chunkX * 16;
		final int minZ = chunkZ * 16;
		final int y = 2;
		WorldLocation loc;
		
		final int venSize = (int) (getMineSize() * (ratio + 1) / veinCount);
		
		for(int i = 0; i < veinCount; i++) {
			loc = new WorldLocation(minX + rand.nextInt(32), y, minZ + rand.nextInt(32), world);
			genTendril(loc, rand, venSize);
		}
		
		
	}
	
	private void genTendril(WorldLocation start, Random rand, int remaining){ 
		gen(start, rand, ratio);
		
		if(remaining  > 1){
			if(rand.nextInt(5) == 0)
				start = start.translate(2 + rand.nextInt(5), 1);
				
			genTendril(start.translate(ForgeDirection.UP, 1), rand, --remaining);
		}
		
	}

}
