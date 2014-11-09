package redgear.geocraft.block;

import java.util.Random;

import redgear.core.api.item.ISimpleItem;
import net.minecraft.item.ItemStack;

public class RareDrop implements BlockDrop{
	
	final ISimpleItem item;
	final int max;
	final int weight;

	public RareDrop(ISimpleItem item, int max, int weight) {
		this.item = item;
		this.max = max;
		this.weight = weight;
	}

	@Override
	public ItemStack getStack(int fortune, Random rand) {
		final int rarity = weight - fortune;
		
		if(rarity <= 0 || rand.nextInt(rarity) == 0)
			return item.getStack(rand.nextInt(max) + 1);
		else
			return null;
	}

}
