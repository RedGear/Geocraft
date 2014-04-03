package redgear.geocraft.block;

import java.util.Random;

import net.minecraft.item.ItemStack;
import redgear.core.api.item.ISimpleItem;

public class WeightedItem {

	final ISimpleItem item;
	final int min;
	final int max;
	final float weight;

	public WeightedItem(ISimpleItem item, int min, int max, float weight) {
		this.item = item;
		this.min = min;
		this.max = max;
		this.weight = Math.abs(weight);
	}

	public ItemStack getStack(int fortune, Random rand) {
		return item.getStack(min + rand.nextInt((int) (fortune * weight + max)));
	}

}
