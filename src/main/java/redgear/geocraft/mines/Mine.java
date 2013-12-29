package redgear.geocraft.mines;

import net.minecraft.world.World;
import redgear.core.mod.ModUtils;
import redgear.core.util.StringHelper;
import redgear.geocraft.api.IMine;
import redgear.geocraft.api.MineManager;
import redgear.geocraft.core.Geocraft;

public abstract class Mine implements IMine {
	
	protected final String name;
	protected final float mineRarity;
	protected final float mineSize;
	
	@Override
	public final String getName(){
		return name;
	}
	
	@Override
	public final float getMineRarity(){
		return mineRarity;
	}
	
	@Override
	public final float getMineSize(){
		return mineSize * MineManager.oreRegistry.densityModifier();
	}
	
	public final float reletiveSize(World world, int chunkX, int chunkZ){
		return reletiveSize(world, chunkX, chunkZ, getMineSize());
	}
	
	public static final float reletiveSize(World world, int chunkX, int chunkZ, float size){
		return (((float)world.getHeightValue(chunkX * 16, chunkZ * 16)) / ((float)world.getHeight())) * size;
	}

	protected Mine(String name, float mineRarity, float mineSize){
		this.name = name;
		
		String cat = "level2." + StringHelper.camelCase(name);
		final ModUtils util = Geocraft.inst;
		
		this.mineRarity = (float)util.getDouble(cat, "mineRarity", mineRarity);
		this.mineSize = (float)util.getDouble(cat, "mineSize", mineSize);
		util.saveConfig();
	}
	
	@Override
	public final String toString(){
		return getName();
	}

	@Override
	public final int hashCode() {
		return name.hashCode();
	}

	@Override
	public final boolean equals(Object obj) {
		if(obj instanceof IMine)
			return this.getName().equals(((IMine) obj).getName());
		else
			return false;
	}
}
