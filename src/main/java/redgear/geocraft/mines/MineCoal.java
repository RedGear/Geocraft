package redgear.geocraft.mines;

import java.util.Random;

import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import redgear.core.util.SimpleItem;
import redgear.core.world.Location;
import redgear.geocraft.core.GeocraftConfig;
import redgear.geocraft.generation.VeinHelper;

public class MineCoal extends Mine {
	
	private final SimpleItem vanillaCoal = new SimpleItem(Blocks.coal_ore, 0);

	public MineCoal(float mineRarity, float mineSize) {
		super("Coal", mineRarity, mineSize);
	}

	@Override
	public void generate(World world, Random rand, int chunkX, int chunkZ) {
		int veinSize = (int) getMineSize();

		int xMin = chunkX * 16;
		int zMin = chunkZ * 16;
		int yMax = world.getHeightValue(xMin, zMin) + 1;

		int height = 1 + rand.nextInt(3);

		double layer = veinSize / height;//how much ore is in one layer

		int length = (int) (layer / (3 + rand.nextInt(1 + (int) Math.sqrt(layer))));
		int width = (int) (layer / length);

		Location start = new Location(xMin + rand.nextInt(16), rand.nextInt(yMax), zMin + rand.nextInt(16));
		Location end = rand.nextBoolean() ? new Location(length, height, width) : new Location(width, height, length);

		if(GeocraftConfig.complexOres){
		VeinHelper.generateBox(world, GeocraftConfig.coalDenseOre, GeocraftConfig.stone, start, end, rand, veinSize);
		VeinHelper.generateBox(world, GeocraftConfig.coalOre, GeocraftConfig.stone, start.translate(-1, -1, -1),
				end.translate(2, 2, 2), rand, veinSize);

		}
		else{
			VeinHelper.generateBox(world, vanillaCoal, GeocraftConfig.stone, start.translate(-1, -1, -1),
					end.translate(2, 2, 2), rand, veinSize);
		}
	}

}
