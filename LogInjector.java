import org.objectweb.asm.*;
import org.objectweb.asm.tree.*;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;

public class LogInjector {
    public static void main(String[] args) throws Exception {
        byte[] bytes = Files.readAllBytes(Paths.get("com/solegendary/reignofnether/unit/modelling/layers/VillagerUnitArmorLayer.class"));
        ClassReader cr = new ClassReader(bytes);
        ClassNode cn = new ClassNode();
        cr.accept(cn, 0);

        for (MethodNode mn : cn.methods) {
            if (mn.name.equals("renderArmorPiece")) {
                System.out.println("Found renderArmorPiece, injecting logs...");
                InsnList list = new InsnList();
                
                // System.out
                list.add(new FieldInsnNode(Opcodes.GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;"));
                
                // new StringBuilder()
                list.add(new TypeInsnNode(Opcodes.NEW, "java/lang/StringBuilder"));
                list.add(new InsnNode(Opcodes.DUP));
                list.add(new MethodInsnNode(Opcodes.INVOKESPECIAL, "java/lang/StringBuilder", "<init>", "()V", false));
                
                // append("[RoN Armor Log] Entity: ")
                list.add(new LdcInsnNode("[RoN Armor Log] Entity: "));
                list.add(new MethodInsnNode(Opcodes.INVOKEVIRTUAL, "java/lang/StringBuilder", "append", "(Ljava/lang/String;)Ljava/lang/StringBuilder;", false));
                
                // append(entity.getClass().getName())
                list.add(new VarInsnNode(Opcodes.ALOAD, 3)); // entity
                list.add(new MethodInsnNode(Opcodes.INVOKEVIRTUAL, "java/lang/Object", "getClass", "()Ljava/lang/Class;", false));
                list.add(new MethodInsnNode(Opcodes.INVOKEVIRTUAL, "java/lang/Class", "getName", "()Ljava/lang/String;", false));
                list.add(new MethodInsnNode(Opcodes.INVOKEVIRTUAL, "java/lang/StringBuilder", "append", "(Ljava/lang/String;)Ljava/lang/StringBuilder;", false));
                
                // append(", slot: ")
                list.add(new LdcInsnNode(", slot: "));
                list.add(new MethodInsnNode(Opcodes.INVOKEVIRTUAL, "java/lang/StringBuilder", "append", "(Ljava/lang/String;)Ljava/lang/StringBuilder;", false));
                
                // append(slot)
                list.add(new VarInsnNode(Opcodes.ALOAD, 4)); // slot
                list.add(new MethodInsnNode(Opcodes.INVOKEVIRTUAL, "java/lang/StringBuilder", "append", "(Ljava/lang/Object;)Ljava/lang/StringBuilder;", false));
                
                // append(", item: ")
                list.add(new LdcInsnNode(", item: "));
                list.add(new MethodInsnNode(Opcodes.INVOKEVIRTUAL, "java/lang/StringBuilder", "append", "(Ljava/lang/String;)Ljava/lang/StringBuilder;", false));
                
                // append(entity.getItemBySlot(slot))
                list.add(new VarInsnNode(Opcodes.ALOAD, 3)); // entity
                list.add(new VarInsnNode(Opcodes.ALOAD, 4)); // slot
                list.add(new MethodInsnNode(Opcodes.INVOKEVIRTUAL, "net/minecraft/world/entity/LivingEntity", "m_6844_", "(Lnet/minecraft/world/entity/EquipmentSlot;)Lnet/minecraft/world/item/ItemStack;", false));
                list.add(new MethodInsnNode(Opcodes.INVOKEVIRTUAL, "java/lang/StringBuilder", "append", "(Ljava/lang/Object;)Ljava/lang/StringBuilder;", false));
                
                // toString()
                list.add(new MethodInsnNode(Opcodes.INVOKEVIRTUAL, "java/lang/StringBuilder", "toString", "()Ljava/lang/String;", false));
                
                // PrintStream.println(String)
                list.add(new MethodInsnNode(Opcodes.INVOKEVIRTUAL, "java/io/PrintStream", "println", "(Ljava/lang/String;)V", false));

                mn.instructions.insert(list);
            }
        }

        ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_FRAMES | ClassWriter.COMPUTE_MAXS);
        cn.accept(cw);
        Files.write(Paths.get("com/solegendary/reignofnether/unit/modelling/layers/VillagerUnitArmorLayer.class"), cw.toByteArray());
        System.out.println("Successfully injected logs into VillagerUnitArmorLayer.class");
    }
}
