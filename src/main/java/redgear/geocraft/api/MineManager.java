package redgear.geocraft.api;

import java.util.regex.Pattern;

import net.minecraft.init.Blocks;
import net.minecraftforge.oredict.OreDictionary;
import redgear.core.api.item.ISimpleItem;
import redgear.core.util.SimpleItem;
import redgear.core.util.SimpleOre;

public class MineManager {

	public static IMineRegistry oreRegistry;
	
	public static final ISimpleItem stone = new SimpleItem(Blocks.stone);
	public static final ISimpleItem stoneOre = new SimpleOre("stone", stone);
	public static final ISimpleItem netherrack = new SimpleItem(Blocks.netherrack);
	public static final ISimpleItem endStone = new SimpleItem(Blocks.end_stone);

	private static final Pattern orePattern = Pattern.compile("^ore.*", Pattern.CASE_INSENSITIVE);
	
	public static ISimpleItem oreCheck(ISimpleItem item) {
		if (item instanceof SimpleOre)
			return item;

		for (int id : OreDictionary.getOreIDs(item.getStack()))
			if (orePattern.matcher(OreDictionary.getOreName(id)).matches())
				return new SimpleOre(OreDictionary.getOreName(id), item);

		return item;
	}

	public static ISimpleItem stoneCheck(ISimpleItem item) {
		if (stoneOre.equals(item))
			return stoneOre;

		else if (netherrack.equals(item))
			return netherrack;
		else if (endStone.equals(item))
			return endStone;
		else
			return item;
	}
}
