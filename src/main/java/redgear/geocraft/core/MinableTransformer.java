package redgear.geocraft.core;

import static org.objectweb.asm.Opcodes.ALOAD;
import static org.objectweb.asm.Opcodes.GETFIELD;
import static org.objectweb.asm.Opcodes.ICONST_1;
import static org.objectweb.asm.Opcodes.IFNE;
import static org.objectweb.asm.Opcodes.INVOKESTATIC;
import static org.objectweb.asm.Opcodes.IRETURN;

import java.util.Map;

import net.minecraft.block.Block;
import net.minecraft.launchwrapper.IClassTransformer;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.FieldInsnNode;
import org.objectweb.asm.tree.InsnList;
import org.objectweb.asm.tree.InsnNode;
import org.objectweb.asm.tree.JumpInsnNode;
import org.objectweb.asm.tree.LabelNode;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;
import org.objectweb.asm.tree.VarInsnNode;

import redgear.core.asm.CoreLoadingPlugin;
import redgear.geocraft.generation.MineGenerator;
import cpw.mods.fml.common.asm.transformers.deobf.FMLDeobfuscatingRemapper;
import cpw.mods.fml.relauncher.IFMLLoadingPlugin;
import cpw.mods.fml.relauncher.IFMLLoadingPlugin.TransformerExclusions;

@TransformerExclusions({"redgear.geocraft.core"})
public class MinableTransformer implements IClassTransformer, IFMLLoadingPlugin{

	@Override
	public byte[] transform(String name, String transformedName, byte[] bytes) {
		
		if(transformedName.equals("net.minecraft.world.gen.feature.WorldGenMinable") && CoreLoadingPlugin.util.getBoolean("GeocraftMinableHook")){
			ClassReader reader = new ClassReader(bytes);
            ClassNode node = new ClassNode();
            reader.accept(node, 0);
            InsnList generateHook = new InsnList();
            LabelNode skip = new LabelNode();
            
            String minable = "net/minecraft/world/gen/feature/WorldGenMinable";
			
            generateHook.add(new VarInsnNode(ALOAD, 0));
            generateHook.add(new FieldInsnNode(GETFIELD , minable, "field_150519_a", "Lnet/minecraft/block/Block;"));
            generateHook.add(new VarInsnNode(ALOAD, 0));
            generateHook.add(new FieldInsnNode(GETFIELD , minable, "mineableBlockMeta", "I"));
            generateHook.add(new VarInsnNode(ALOAD, 0));
            generateHook.add(new FieldInsnNode(GETFIELD , minable, "field_76541_b", "I"));
            generateHook.add(new VarInsnNode(ALOAD, 0));
            generateHook.add(new FieldInsnNode(GETFIELD , minable, "field_150518_c", "Lnet/minecraft/block/Block;"));
            generateHook.add(new MethodInsnNode(INVOKESTATIC, MinableTransformer.class.getName().replace('.', '/'), "generateHook", "(Lnet/minecraft/block/Block;IILnet/minecraft/block/Block;)Z"));
            generateHook.add(new JumpInsnNode(IFNE, skip));
            generateHook.add(new InsnNode(ICONST_1));
            generateHook.add(new InsnNode(IRETURN));
            generateHook.add(skip);
            
            for(MethodNode method : node.methods)
            	if("func_76484_a".equals(FMLDeobfuscatingRemapper.INSTANCE.mapMethodName(name, method.name, method.desc)))
            		method.instructions.insertBefore(method.instructions.getFirst(), generateHook);

            ClassWriter writer = new ClassWriter(0);
            node.accept(writer);
            return writer.toByteArray();
		}
		
		return bytes;
	}
    
    public static boolean generateHook(Block block, int blockMeta, int numberOfBlocks, Block target){
    	if(MineGenerator.reg != null)
    		return MineGenerator.reg.checkForNew(block, blockMeta, numberOfBlocks, target);
    	else
    		return true;
    }
    
    @Override
	public String[] getASMTransformerClass() {
		return new String[]{MinableTransformer.class.getName()};
	}

	@Override
	public String getModContainerClass() {
		return null;
	}

	@Override
	public String getSetupClass() {
		return null;
	}

	@Override
	public void injectData(Map<String, Object> data) {
		
	}

	@Override
	public String getAccessTransformerClass() {
		return null;
	}
}
