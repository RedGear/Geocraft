package redgear.geocraft.block;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import net.minecraft.item.ItemStack;
import redgear.core.block.IDifferentDrop;
import redgear.core.block.SubBlock;
import redgear.core.world.WorldLocation;

public class SubBlockGeoOre extends SubBlock implements IDifferentDrop{
	
	private List<BlockDrop> items;
	
	public SubBlockGeoOre(String name) {
		super(name);
		items = new ArrayList<BlockDrop>();
	}

	public SubBlockGeoOre(String name, BlockDrop... items) {
		this(name);
		this.items.addAll(Arrays.asList(items));
	}
	
	public void addItem(BlockDrop item){
		items.add(item);
	}

	@Override
	public ArrayList<ItemStack> getDrops(WorldLocation loc, int meta, int fortune) {
		ArrayList<ItemStack> ans = new ArrayList<ItemStack>(items.size());
		
		
		for(BlockDrop item : items){
			ItemStack stack = item.getStack(fortune, loc.world.rand);
			if(stack != null && stack.stackSize > 0)
				ans.add(stack);
		}
			
		return ans;
	}
	
	


}
