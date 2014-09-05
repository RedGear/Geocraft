package redgear.geocraft.api.mine;

import java.util.Random;

import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import redgear.core.api.item.ISimpleItem;
import redgear.core.world.WorldLocation;

/**
 * Defines a vertical standing cylinder
 *
 * @author BlackHole
 *
 */
public abstract class MineCylinder extends MineSingleOre {

	protected transient int intRarity;
	protected transient int intMineSize;
	protected transient int intVeinSize;

	protected float mineSize;
	protected float mineRarity;
	protected float veinSize;

	protected int minBottom;
	protected int maxTop;
	
	public MineCylinder(){
		
	}

	public MineCylinder(String name, ISimpleItem block, ISimpleItem target, float mineRarity, float mineSize, int veinSize) {
		super(name, block, target);
		this.mineSize = mineSize;
		this.mineRarity = mineRarity;
		this.veinSize = veinSize;

		maxTop = 256;
		minBottom = 0;
		
	}

	@Override
	public void generate(World world, Random rand, int chunkX, int chunkZ) {
		if (rand.nextInt(intRarity) == 0)
			for (int j = 0; j < intMineSize; j++) {
				int centerX = chunkX * 16 + rand.nextInt(16); //defines the center of the vertical standing cylinder
				int centerZ = chunkZ * 16 + rand.nextInt(16);
				
				int top = Math.max(rand.nextInt(maxTop), 16);
				int bottom = Math.max(Math.max(rand.nextInt(top), top - 16), minBottom);

				if (bottom >= world.getHeightValue(chunkX * 16, chunkZ * 16))
					return;

				int x = 0;
				int y = 0;
				int z = 0;
				
				int maxRadius = (int) Math.max(Math.sqrt(intVeinSize * intMineSize * 8 / (top - bottom) / Math.PI), 4);

				float angle = 0;
				float radius = 0;

				for (int i = 0; i <= intMineSize; i++) {
					y = 4 + rand.nextInt(top);
					angle = rand.nextFloat() * (float) Math.PI;
					radius = rand.nextInt(maxRadius);

					x = (int) (MathHelper.sin(angle) * radius);
					z = (int) (MathHelper.cos(angle) * radius);

					generateVein(new WorldLocation(x + centerX, y, z + centerZ, world), block, target, rand,
							intVeinSize);
				}
			}

	}

	protected abstract void generateVein(WorldLocation start, ISimpleItem block, ISimpleItem target, Random rand, int size);

	@Override
	public void init() {
		super.init();
		intRarity = (int) (mineRarity * getRegistry().rarityModifier());
		if (intRarity <= 0)
			intRarity = 1;
		intMineSize = (int) (mineSize * getRegistry().volumeModifier() / 2);
		intVeinSize = (int) (veinSize * getRegistry().volumeModifier() / 2);
	}
}
