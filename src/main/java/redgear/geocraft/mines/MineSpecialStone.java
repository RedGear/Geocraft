package redgear.geocraft.mines;

import java.util.Random;

import redgear.core.util.SimpleItem;
import redgear.core.world.WorldLocation;

public abstract class MineSpecialStone extends Mine{
	
	protected final SimpleItem ore;
	protected final SimpleItem bearer;
	protected final SimpleItem target;

	protected MineSpecialStone(String name, float mineRarity, float mineSize, SimpleItem bearer, SimpleItem ore, SimpleItem target) {
		super(name, mineRarity, mineSize);
		this.ore = ore;
		this.bearer = bearer;
		this.target = target;
	}
	
	protected void gen(WorldLocation loc, Random rand, int ratio) {
		if (rand.nextInt(ratio) == 0)
			loc.placeBlock(ore, target);
		else
			loc.placeBlock(bearer, target);
	}

}
