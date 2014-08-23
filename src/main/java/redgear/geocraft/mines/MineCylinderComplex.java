package redgear.geocraft.mines;

import java.util.Random;

import redgear.core.api.item.ISimpleItem;
import redgear.core.world.WorldLocation;
import redgear.geocraft.generation.VeinHelper;

public class MineCylinderComplex extends MineCylinder {

	protected boolean spiderVeins;
	
	public MineCylinderComplex(){
		
	}

	public MineCylinderComplex(String name, ISimpleItem block, ISimpleItem target, float mineRarity, float mineSize,
			int veinSize, boolean spiderVeins) {
		super(name, block, target, mineRarity, mineSize, veinSize);
		this.spiderVeins = spiderVeins;
	}

	@Override
	protected void generateVein(WorldLocation start, ISimpleItem block, ISimpleItem target, Random rand, int size) {
		if (spiderVeins)
			VeinHelper.generateMetal(start, block, target, rand, size);
		else
			VeinHelper.generateSphere(start, block, target, rand, size);
	}
}
