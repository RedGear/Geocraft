package redgear.geocraft.mines;

import java.util.Random;

import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import redgear.core.api.item.ISimpleItem;
import redgear.core.util.SimpleItem;
import redgear.core.util.SimpleOre;
import redgear.core.world.WorldLocation;
import redgear.geocraft.api.mine.MineSpecialStone;
import redgear.geocraft.core.GeocraftConfig;

// Thars gold in them thar hills!
public class MineGold extends MineSpecialStone {

	private transient final static ISimpleItem vanillaGold = new SimpleOre("oreGold", new SimpleItem(Blocks.gold_ore));
	protected transient int intRarity;
	protected transient int intMineSize;
	protected transient int intVeinSize;

	float mineRarity;
	float mineSize;
	float veinSize;
	public boolean complexOre;
	private final int ratio;

	public MineGold() {
		super("GoldComplex", GeocraftConfig.complexOres ? GeocraftConfig.goldOre : vanillaGold,
				GeocraftConfig.stoneOre, GeocraftConfig.complexOres ? GeocraftConfig.terraQuartzOre
						: GeocraftConfig.stone);

		isActive = GeocraftConfig.complexMines;
		complexOre = GeocraftConfig.complexOres;

		mineRarity = 2;
		mineSize = 4;
		veinSize = 20;
		ratio = 5;// times more quartz than gold.
	}

	@Override
	public void generate(World world, Random rand, int chunkX, int chunkZ) {
		if (rand.nextInt(intRarity) == 0) {
			final int veinCount = 4 + rand.nextInt(ratio);

			final int minX = chunkX * 16;
			final int minZ = chunkZ * 16;
			final int y = 2;
			WorldLocation loc;

			for (int i = 0; i < veinCount; i++) {
				loc = new WorldLocation(minX + rand.nextInt(32), y, minZ + rand.nextInt(32), world);
				genTendril(loc, rand, intVeinSize);
			}
		}

	}

	private void genTendril(WorldLocation start, Random rand, int remaining) {
		gen(start, rand, ratio);

		if (remaining > 1) {
			if (rand.nextInt(5) == 0)
				start = start.translate(2 + rand.nextInt(5), 1);

			genTendril(start.translate(ForgeDirection.UP, 1), rand, --remaining);
		}

	}

	@Override
	public void init() {
		super.init();

		intRarity = (int) (mineRarity * getRegistry().rarityModifier());
		if (intRarity <= 0)
			intRarity = 1;
		intMineSize = (int) (mineSize * getRegistry().volumeModifier() / 2);
		intVeinSize = (int) (veinSize * getRegistry().volumeModifier() / 2);

	}

	@Override
	public boolean overrides(ItemStack oreBlock) {
		return vanillaGold.equals(oreBlock);
	}
}
