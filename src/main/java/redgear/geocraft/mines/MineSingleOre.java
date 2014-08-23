package redgear.geocraft.mines;

import net.minecraft.item.ItemStack;
import redgear.core.api.item.ISimpleItem;
import redgear.core.util.ItemRegUtil;
import redgear.core.util.SimpleItem;
import redgear.core.util.SimpleOre;
import redgear.geocraft.api.gen.Mine;
import redgear.geocraft.core.Geocraft;
import redgear.geocraft.core.GeocraftConfig;
import redgear.geocraft.generation.MineGenerator;

public abstract class MineSingleOre extends Mine {

	protected transient ISimpleItem block;
	protected transient ISimpleItem target;
	protected transient boolean init;

	public String blockOreName;
	public String blockName;
	public String targetOreName;
	public String targetName;

	public MineSingleOre(){
		
	}
	
	public MineSingleOre(String name, ISimpleItem block, ISimpleItem target) {
		super(name);
		init = true;
		this.block = MineGenerator.reg.oreCheck(block);
		this.target = MineGenerator.reg.stoneCheck(target);
	}

	@Override
	public void init() {
		if (!init) {
			if (ItemRegUtil.isInOreDict(blockOreName))
				block = new SimpleOre(blockOreName, ItemRegUtil.findItem(blockName));
			else {
				block = ItemRegUtil.findItem(blockName);
				if (block == null) {
					Geocraft.inst.myLogger.warn("Mine with name " + name
							+ " was unable to find a block with ore dictionary name " + blockOreName
							+ " nor a block of name " + blockName
							+ " this mine must be deactivated until this is fixed.");
					isActive = false;
				}
			}

			if (ItemRegUtil.isInOreDict(targetOreName))
				target = new SimpleOre(targetOreName, ItemRegUtil.findItem(targetName));
			else {
				target = ItemRegUtil.findItem(targetName);
				if (target == null) {
					Geocraft.inst.myLogger.warn("Mine with name " + name
							+ " was unable to find a block with ore dictionary name " + targetOreName
							+ " nor a block of name " + targetName
							+ " this mine will be reset to default target Stone until this is fixed.");
					targetName = GeocraftConfig.stone.getName();
					targetOreName = GeocraftConfig.stoneOre.getName();
					target = GeocraftConfig.stoneOre;
				}
			}
		}
	}
	
	@Override
	public void preSave(){
		Geocraft.inst.logDebug("Saving mine data: " + name);
		
		if (block instanceof SimpleItem){
			Geocraft.inst.logDebug("Block was a SimpleItem");
			blockName = block.getName();
		}
		else if (block instanceof SimpleOre) {
			Geocraft.inst.logDebug("Block was a SimpleOre");
			blockOreName = block.getName();
			ISimpleItem t = ((SimpleOre) block).getTarget();
			if (t != null){
				Geocraft.inst.logDebug("Ore had a block");
				blockName = t.getName();
			}
		}

		if (target instanceof SimpleItem)
			targetName = target.getName();
		else if (target instanceof SimpleOre) {
			targetOreName = target.getName();
			ISimpleItem t = ((SimpleOre) target).getTarget();
			if (t != null)
				targetName = t.getName();
		}
	}

	@Override
	public boolean overrides(ItemStack oreBlock) {
		return block.equals(oreBlock);
	}
}
