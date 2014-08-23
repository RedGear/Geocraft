package redgear.geocraft.api;

import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import redgear.geocraft.api.gen.Mine;


public interface IMineRegistry {

	/**
	 * Use this to add your mines.
	 */
	public Mine registerMine(Mine mine);
	
	public Mine registerCylinder(String name, ItemStack block, ItemStack target, float mineRarity, float mineSize,int veinSize, boolean trace);
	
	/**
	 * Use this to add ore to the gen as trace veins. (Calls registerIgnore automatically)
	 */
	public Mine registerTrace(String name, Block block, int blockMeta, Block target, int targetMeta, int size);
	
	/**
	 * Use this if you want Geocraft to ignore this ore and not create defaults, but not allow it to generate normally either. Useful for when you create your own defaults
	**/
	public void registerIgnore(Block block, int blockMeta);
	
	/**
	 * Use this and Geocraft won't interfere with the normal generation of this ore
	 */
	public void registerNormalGen(Block block, int blockMeta);
	
	/**
	 * Use this in your mines to adjust the number of veins based on the global values supplied by users.
	 * Larger numbers mean more ore, smaller means less, 1.0 means no change. 
	 */
	public float volumeModifier();
	
	/**
	 * Use this in your mines to adjust rarity of mines based on the global values supplied by users.  
	 * Larger numbers mean ores mines are further apart, smaller means closer together, 1.0 means no change.
	 */
	public float rarityModifier();
}
