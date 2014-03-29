package redgear.geocraft.mines;

import java.util.Random;

import redgear.core.util.SimpleItem;
import redgear.core.world.WorldLocation;
import redgear.geocraft.core.GeocraftConfig;
import redgear.geocraft.generation.VeinHelper;

public class MineMetal extends MineCylinder{

	public MineMetal(String name, float mineRarity, float mineSize, SimpleItem block, SimpleItem target, int veinSize) {
		super(name, mineRarity, mineSize, block, target, veinSize);
	}

	@Override
	protected void generateVein(WorldLocation start, SimpleItem block, SimpleItem target, Random rand, int size){
		if(GeocraftConfig.spiderMetals)
			VeinHelper.generateMetal(start, block, target, rand, size);
		else
			VeinHelper.generateSphere(start, block, target, rand, size);
	}

}
