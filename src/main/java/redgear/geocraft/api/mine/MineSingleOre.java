package redgear.geocraft.api.mine;

import net.minecraft.item.ItemStack;
import redgear.core.api.item.ISimpleItem;
import redgear.core.util.ItemRegUtil;
import redgear.core.util.SimpleOre;
import redgear.geocraft.api.MineManager;

public abstract class MineSingleOre extends Mine {

	protected ISimpleItem block;
	protected ISimpleItem target;
	protected transient boolean init;

	public String blockOreName;
	public String blockName;
	public String targetOreName;
	public String targetName;

	public MineSingleOre() {

	}

	public MineSingleOre(String name, ISimpleItem block, ISimpleItem target) {
		super(name);
		init = true;
		this.block = MineManager.oreCheck(block);
		this.target = MineManager.stoneCheck(target);
	}

	@Override
	public void init() {
		if (!init) {
			if (block == null && ItemRegUtil.isInOreDict(blockOreName))
				block = new SimpleOre(blockOreName, ItemRegUtil.findItem(blockName));
			else {
				block = ItemRegUtil.findItem(blockName);
				if (block == null) {
					/*Geocraft.inst.myLogger.warn("Mine with name " + name
							+ " was unable to find a block with ore dictionary name " + blockOreName
							+ " nor a block of name " + blockName
							+ " this mine must be deactivated until this is fixed.");*/
					isActive = false;
				}
			}

			if (target == null && ItemRegUtil.isInOreDict(targetOreName))
				target = new SimpleOre(targetOreName, ItemRegUtil.findItem(targetName));
			else {
				target = ItemRegUtil.findItem(targetName);
				if (target == null) {
					/*Geocraft.inst.myLogger.warn("Mine with name " + name
							+ " was unable to find a block with ore dictionary name " + targetOreName
							+ " nor a block of name " + targetName
							+ " this mine will be reset to default target Stone until this is fixed.");*/
					targetName = MineManager.stone.getName();
					targetOreName = MineManager.stoneOre.getName();
					target = MineManager.stoneOre;
				}
			}
		}
	}

	@Override
	public void preSave() {
		/*if (block instanceof SimpleItem)
			blockName = block.getName();
		else if (block instanceof SimpleOre) {
			blockOreName = block.getName();
			ISimpleItem t = ((SimpleOre) block).getTarget();
			if (t != null)
				blockName = t.getName();
		}

		if (target instanceof SimpleItem)
			targetName = target.getName();
		else if (target instanceof SimpleOre) {
			targetOreName = target.getName();
			ISimpleItem t = ((SimpleOre) target).getTarget();
			if (t != null)
				targetName = t.getName();
		}*/
		
		
		
		blockOreName = null;
		blockName = null;
		targetOreName = null;
		targetName = null;
	}

	@Override
	public boolean overrides(ItemStack oreBlock) {
		return block.equals(oreBlock);
	}
}
