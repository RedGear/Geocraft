package redgear.geocraft.block;

import java.util.Random;

import net.minecraft.item.ItemStack;
import redgear.core.api.item.ISimpleItem;

public class SingleDrop implements BlockDrop{
	
	final ISimpleItem item;
	final int amount;
	
	public SingleDrop(ISimpleItem item, int amount) {
		this.item = item;
		this.amount = amount;
	}

	@Override
	public ItemStack getStack(int fortune, Random rand) {
		return item.getStack(amount);
	}

}
