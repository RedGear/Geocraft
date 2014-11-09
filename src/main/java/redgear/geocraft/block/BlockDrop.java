package redgear.geocraft.block;

import java.util.Random;

import net.minecraft.item.ItemStack;

public interface BlockDrop {

	ItemStack getStack(int fortune, Random rand);

}