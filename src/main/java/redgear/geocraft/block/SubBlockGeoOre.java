package redgear.geocraft.block;

import java.util.ArrayList;

import net.minecraft.item.ItemStack;
import redgear.core.block.IDifferentDrop;
import redgear.core.block.SubBlock;
import redgear.core.world.WorldLocation;

public class SubBlockGeoOre extends SubBlock implements IDifferentDrop{
	
	private final WeightedItem[] items;

	public SubBlockGeoOre(String name, WeightedItem... items) {
		super(name);
		this.items = items;
	}

	@Override
	public ArrayList<ItemStack> getDrops(WorldLocation loc, int meta, int fortune) {
		ArrayList<ItemStack> ans = new ArrayList<ItemStack>(items.length);
		
		
		for(WeightedItem item : items){
			ItemStack stack = item.getStack(fortune, loc.world.rand);
			if(stack != null && stack.stackSize > 0)
				ans.add(stack);
		}
			
		return ans;
	}
	
	


}
