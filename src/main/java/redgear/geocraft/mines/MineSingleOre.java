package redgear.geocraft.mines;

import redgear.core.util.SimpleItem;

public abstract class MineSingleOre extends Mine {

	protected final SimpleItem block;
	protected final SimpleItem target;
	
	
	public MineSingleOre(String name, float mineRarity, float mineSize, SimpleItem block, SimpleItem target){
		super(name, mineRarity, mineSize);
		this.block = block;
		this.target = target;
	}
}
