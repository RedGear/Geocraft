package redgear.geocraft.api;

import net.minecraft.block.Block;


public interface IMineRegistry {

	/**
	 * Use this to add your mines.
	 */
	public boolean registerMine(IMine mine);
	
	/**
	 * Use this to add ore to the gen as trace veins. (Calls registerIgnore automatically)
	 */
	public boolean registerTrace(String name, Block block, int blockMeta, Block target, int targetMeta, int size);
	
	/**
	 * Use this if you want Geocraft to ignore this ore and not create defaults, but not allow it to generate normally either. Useful for when you create your own defaults
	**/
	public boolean registerIgnore(Block block, int blockMeta);
	
	/**
	 * Use this and Geocraft won't interfere with the normal generation of this ore
	 */
	public boolean registerNormalGen(Block block, int blockMeta);
	
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
	
	
	//These are used internally. You can use them if you'd like, but not needed. CURRENTLY NOT USED AT ALL
	public boolean useDimensions();
	public boolean useBiomes();
}
