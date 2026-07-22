import org.objectweb.asm.*;
import org.objectweb.asm.tree.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

public class MixinGenerator {
    public static void main(String[] args) throws Exception {
        ClassNode cn = new ClassNode();
        cn.version = Opcodes.V17;
        cn.access = Opcodes.ACC_PUBLIC | Opcodes.ACC_SUPER;
        cn.name = "com/codex/rongolemhealerintegration/mixin/GuardIllagerRenderMixin";
        cn.superName = "java/lang/Object";

        // @Mixin(value = GuardIllagerRender.class, remap = false)
        AnnotationNode mixinAnn = new AnnotationNode("Lorg/spongepowered/asm/mixin/Mixin;");
        mixinAnn.values = new ArrayList<>();
        mixinAnn.values.add("value");
        java.util.List<Type> mixinClasses = new ArrayList<>();
        mixinClasses.add(Type.getType("Lcom/min01/guardillagers/client/render/GuardIllagerRender;"));
        mixinAnn.values.add(mixinClasses);
        mixinAnn.values.add("remap");
        mixinAnn.values.add(Boolean.FALSE);
        
        cn.invisibleAnnotations = new ArrayList<>();
        cn.invisibleAnnotations.add(mixinAnn);

        // Dummy constructor
        MethodNode init = new MethodNode(Opcodes.ACC_PUBLIC, "<init>", "()V", null, null);
        init.instructions.add(new VarInsnNode(Opcodes.ALOAD, 0));
        init.instructions.add(new MethodInsnNode(Opcodes.INVOKESPECIAL, "java/lang/Object", "<init>", "()V", false));
        init.instructions.add(new InsnNode(Opcodes.RETURN));
        init.maxStack = 1;
        init.maxLocals = 1;
        cn.methods.add(init);

        // private void onInit(EntityRendererProvider.Context context, CallbackInfo ci)
        MethodNode onInit = new MethodNode(Opcodes.ACC_PRIVATE, "onInit", "(Lnet/minecraft/client/renderer/entity/EntityRendererProvider$Context;Lorg/spongepowered/asm/mixin/injection/callback/CallbackInfo;)V", null, null);
        
        // @Inject(method = "<init>", at = @At("TAIL"), remap = false)
        AnnotationNode injectAnn = new AnnotationNode("Lorg/spongepowered/asm/mixin/injection/Inject;");
        injectAnn.values = new ArrayList<>();
        injectAnn.values.add("method");
        java.util.List<String> methods = new ArrayList<>();
        methods.add("<init>");
        injectAnn.values.add(methods);
        injectAnn.values.add("at");
        AnnotationNode atAnn = new AnnotationNode("Lorg/spongepowered/asm/mixin/injection/At;");
        atAnn.values = new ArrayList<>();
        atAnn.values.add("value");
        atAnn.values.add("TAIL");
        injectAnn.values.add(atAnn);
        injectAnn.values.add("remap");
        injectAnn.values.add(Boolean.FALSE);

        onInit.visibleAnnotations = new ArrayList<>();
        onInit.visibleAnnotations.add(injectAnn);

        InsnList insns = new InsnList();
        
        insns.add(new FieldInsnNode(Opcodes.GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;"));
        insns.add(new LdcInsnNode("[RoN Mixin Log] GuardIllagerRenderMixin onInit called!"));
        insns.add(new MethodInsnNode(Opcodes.INVOKEVIRTUAL, "java/io/PrintStream", "println", "(Ljava/lang/String;)V", false));
        
        insns.add(new VarInsnNode(Opcodes.ALOAD, 0));
        insns.add(new TypeInsnNode(Opcodes.NEW, "com/solegendary/reignofnether/unit/modelling/layers/VillagerUnitArmorLayer"));
        insns.add(new InsnNode(Opcodes.DUP));
        
        insns.add(new VarInsnNode(Opcodes.ALOAD, 0));
        insns.add(new TypeInsnNode(Opcodes.CHECKCAST, "net/minecraft/client/renderer/entity/RenderLayerParent"));
        
        insns.add(new TypeInsnNode(Opcodes.NEW, "com/solegendary/reignofnether/unit/modelling/models/IllagerArmorModel"));
        insns.add(new InsnNode(Opcodes.DUP));
        insns.add(new VarInsnNode(Opcodes.ALOAD, 1));
        insns.add(new FieldInsnNode(Opcodes.GETSTATIC, "com/solegendary/reignofnether/unit/modelling/renderers/AbstractVillagerUnitRenderer", "VILLAGER_ARMOR_INNER_LAYER", "Lnet/minecraft/client/model/geom/ModelLayerLocation;"));
        insns.add(new MethodInsnNode(Opcodes.INVOKEVIRTUAL, "net/minecraft/client/renderer/entity/EntityRendererProvider$Context", "m_174023_", "(Lnet/minecraft/client/model/geom/ModelLayerLocation;)Lnet/minecraft/client/model/geom/ModelPart;", false));
        insns.add(new MethodInsnNode(Opcodes.INVOKESPECIAL, "com/solegendary/reignofnether/unit/modelling/models/IllagerArmorModel", "<init>", "(Lnet/minecraft/client/model/geom/ModelPart;)V", false));
        
        insns.add(new TypeInsnNode(Opcodes.NEW, "com/solegendary/reignofnether/unit/modelling/models/IllagerArmorModel"));
        insns.add(new InsnNode(Opcodes.DUP));
        insns.add(new VarInsnNode(Opcodes.ALOAD, 1));
        insns.add(new FieldInsnNode(Opcodes.GETSTATIC, "com/solegendary/reignofnether/unit/modelling/renderers/AbstractVillagerUnitRenderer", "VILLAGER_ARMOR_OUTER_LAYER", "Lnet/minecraft/client/model/geom/ModelLayerLocation;"));
        insns.add(new MethodInsnNode(Opcodes.INVOKEVIRTUAL, "net/minecraft/client/renderer/entity/EntityRendererProvider$Context", "m_174023_", "(Lnet/minecraft/client/model/geom/ModelLayerLocation;)Lnet/minecraft/client/model/geom/ModelPart;", false));
        insns.add(new MethodInsnNode(Opcodes.INVOKESPECIAL, "com/solegendary/reignofnether/unit/modelling/models/IllagerArmorModel", "<init>", "(Lnet/minecraft/client/model/geom/ModelPart;)V", false));
        
        insns.add(new MethodInsnNode(Opcodes.INVOKESPECIAL, "com/solegendary/reignofnether/unit/modelling/layers/VillagerUnitArmorLayer", "<init>", "(Lnet/minecraft/client/renderer/entity/RenderLayerParent;Lnet/minecraft/client/model/HumanoidModel;Lnet/minecraft/client/model/HumanoidModel;)V", false));
        
        insns.add(new MethodInsnNode(Opcodes.INVOKEVIRTUAL, "com/min01/guardillagers/client/render/GuardIllagerRender", "m_115326_", "(Lnet/minecraft/client/renderer/entity/layers/RenderLayer;)Z", false));
        insns.add(new InsnNode(Opcodes.POP));
        insns.add(new InsnNode(Opcodes.RETURN));
        
        onInit.instructions.add(insns);
        cn.methods.add(onInit);

        ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_FRAMES | ClassWriter.COMPUTE_MAXS);
        ClassWriter cwCustom = new ClassWriter(ClassWriter.COMPUTE_FRAMES | ClassWriter.COMPUTE_MAXS) {
            @Override
            protected String getCommonSuperClass(String type1, String type2) {
                return "java/lang/Object"; 
            }
        };
        cn.accept(cwCustom);
        
        Files.createDirectories(Paths.get("com/codex/rongolemhealerintegration/mixin"));
        Files.write(Paths.get("com/codex/rongolemhealerintegration/mixin/GuardIllagerRenderMixin.class"), cwCustom.toByteArray());
        System.out.println("Generated GuardIllagerRenderMixin.class");
    }
}
