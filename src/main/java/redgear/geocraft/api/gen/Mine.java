package redgear.geocraft.api.gen;

import java.util.Random;

import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import redgear.geocraft.api.IMineRegistry;
import redgear.geocraft.api.MineManager;

public abstract class Mine {

	public String name;
	public boolean isActive = true;

	public Mine() {
	}

	public Mine(String name) {
		this.name = name;
	}

	public abstract void init();
	
	public abstract void preSave();

	public abstract void generate(World world, Random rand, int chunkX, int chunkZ);

	/**
	 * Return true if the mine can prevent this ore from generating.
	 * IE: if this is a coal mine, it should override vanilla coal.
	 * A copper mine would return true for any block that's ore dicted as copper
	 * ore. ect.
	 *
	 * @param oreBlock The ore block caught by Geocraft's Minable hook.
	 * @return if all mines return false, this ore will be given it's own mine
	 * instance, adding it to the MineRegistry and creating a new config file.
	 * If a single mine returns true, the ore will be added to an ignore list
	 * and never bothered with again.
	 *
	 * Be sure to consider isActive().
	 **/
	public abstract boolean overrides(ItemStack oreBlock);

	public final IMineRegistry getRegistry() {
		return MineManager.oreRegistry;
	}

	@Override
	public final String toString() {
		return name;
	}

	@Override
	public final int hashCode() {
		return name==null ? 0 : name.hashCode();
	}

	@Override
	public final boolean equals(Object obj) {
		if (obj instanceof Mine)
			return name==null ? false : name.equals(((Mine) obj).name);
		else
			return false;
	}
	
	public final Mine setActive(boolean isActive){
		this.isActive = isActive;
		return this;
	}
}
