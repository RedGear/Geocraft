package redgear.geocraft.mines;

import java.util.Random;

import redgear.core.api.item.ISimpleItem;
import redgear.core.util.SimpleItem;
import redgear.core.world.WorldLocation;
import redgear.geocraft.core.GeocraftConfig;
import redgear.geocraft.generation.VeinHelper;
import thaumcraft.api.aspects.Aspect;

public class MineThaumInfusedStone extends MineCylinder {
	
	public MineThaumInfusedStone(){
		
	}

	public MineThaumInfusedStone(ISimpleItem block) {
		super("ThaumInfusedStone", block,GeocraftConfig.stoneOre, 4, 16, 6);
	}

	private int nextMeta(Aspect tag, Random rand) {
		int md = rand.nextInt(6) + 1;
		if (rand.nextInt(3) == 0)
			if (tag == null) {
			} else if (tag == Aspect.AIR)
				md = 1;
			else if (tag == Aspect.FIRE)
				md = 2;
			else if (tag == Aspect.WATER)
				md = 3;
			else if (tag == Aspect.EARTH)
				md = 4;
			else if (tag == Aspect.ORDER)
				md = 5;
			else if (tag == Aspect.ENTROPY)
				md = 6;

		return md;
	}

	private Aspect biomeTag(WorldLocation start, Random rand) {

		Aspect tag = null;
		try {
			Class<?> biomeHelper = Class.forName("thaumcraft.common.lib.world.biomes.BiomeHandler");

			tag = (Aspect) biomeHelper.getMethod("getRandomBiomeTag", int.class, Random.class).invoke(null,
					start.world.getBiomeGenForCoords(start.getX(), start.getZ()).biomeID, rand);
		} catch (Exception e) {
		}

		return tag;
	}

	@Override
	protected void generateVein(WorldLocation start, ISimpleItem block, ISimpleItem target, Random rand, int size) {
		VeinHelper.generateSphere(start, new SimpleItem(block.getItem(), nextMeta(biomeTag(start, rand), rand)), target,
				rand, size);

	}

}