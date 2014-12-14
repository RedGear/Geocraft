package redgear.geocraft.api.mine;

import java.util.Random;

import redgear.core.api.item.ISimpleItem;
import redgear.core.util.ItemRegUtil;
import redgear.core.util.SimpleOre;
import redgear.core.world.WorldLocation;
import redgear.geocraft.api.MineManager;

public abstract class MineSpecialStone extends MineSingleOre {

	protected ISimpleItem bearer;

	protected String rockOreName;
	protected String rockName;
	
	public MineSpecialStone(){
		
	}
	
	public MineSpecialStone(String name, ISimpleItem block, ISimpleItem target, ISimpleItem bearer) {
		super(name, block, target);
		this.bearer = bearer;
		
//		if (bearer instanceof SimpleItem)
//			rockName = bearer.getName();
//		else if (bearer instanceof SimpleOre) {
//			rockOreName = bearer.getName();
//			ISimpleItem t = ((SimpleOre) bearer).getTarget();
//			if (t != null)
//				rockName = t.getName();
//		}
	}

	protected void gen(WorldLocation loc, Random rand, int ratio) {
		if(ratio < 1)
			return;
		
		if (rand.nextInt(ratio) == 0)
			loc.placeBlock(block, target);
		else
			loc.placeBlock(bearer, target);
	}

	@Override
	public void init() {
		if(!init){
		if (bearer != null && ItemRegUtil.isInOreDict(rockOreName))
			bearer = new SimpleOre(rockOreName, ItemRegUtil.findItem(rockName));
		else {
			bearer = ItemRegUtil.findItem(rockName);
			if (bearer == null) {
				/*Geocraft.inst.myLogger.warn("Mine with name " + name
						+ " was unable to find a block with ore dictionary name " + rockOreName
						+ " nor a block of name " + rockName
						+ " this mine will be reset to default bearer to Stone until this is fixed.");*/
				rockName = MineManager.stone.getName();
				rockOreName = MineManager.stoneOre.getName();
				bearer = MineManager.stoneOre;
			}
		}
		}
	}

}
