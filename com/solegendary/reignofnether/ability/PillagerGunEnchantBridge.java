package com.solegendary.reignofnether.ability;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public final class PillagerGunEnchantBridge {
    private static final String MULTISHOT_CLASS = "com.solegendary.reignofnether.ability.abilities.EnchantMultishot";
    private static final String QUICK_CHARGE_CLASS = "com.solegendary.reignofnether.ability.abilities.EnchantQuickCharge";
    private static final String EXPLOSIVE_MAG = "tacz:ammo_mod_he";
    private static final String FIRE_MAG = "tacz:ammo_mod_i";

    private PillagerGunEnchantBridge() {
    }

    public static boolean isSpringfieldPillager(Object object) {
        try {
            if (object == null) {
                System.out.println("[RoN Debug Bridge] isSpringfieldPillager: object is null");
                return false;
            }
            String className = object.getClass().getName();
            System.out.println("[RoN Debug Bridge] isSpringfieldPillager checking object class: " + className);
            Class<?> clazz = object.getClass();
            while (clazz != null) {
                if (clazz.getName().endsWith("PillagerUnit") || clazz.getSimpleName().equals("PillagerUnit")) {
                    System.out.println("[RoN Debug Bridge] isSpringfieldPillager: matched PillagerUnit for " + className);
                    return true;
                }
                clazz = clazz.getSuperclass();
            }
            System.out.println("[RoN Debug Bridge] isSpringfieldPillager: did not match PillagerUnit for " + className);
            return false;
        }
        catch (Throwable throwable) {
            System.out.println("[RoN Debug Bridge] isSpringfieldPillager error: " + throwable);
            throwable.printStackTrace();
            return false;
        }
    }

    private static boolean isActualSpringfield(Object object) {
        try {
            Object object2 = PillagerGunEnchantBridge.callNoArg(object, "m_21205_");
            Object object3 = PillagerGunEnchantBridge.getGun(object2);
            if (object3 == null) {
                return false;
            }
            Object object4 = PillagerGunEnchantBridge.callInterface("com.tacz.guns.api.item.IGun", "getGunId", new String[]{"net.minecraft.world.item.ItemStack"}, object3, object2);
            return "tacz:springfield1873".equals(String.valueOf(object4));
        }
        catch (Throwable throwable) {
            return false;
        }
    }

    public static boolean hasSame(Object object, Object object2) {
        try {
            System.out.println("[RoN Debug Bridge] hasSame called for enchant: " + (object != null ? object.getClass().getName() : "null") + " on unit: " + (object2 != null ? object2.getClass().getName() : "null"));
            String string = PillagerGunEnchantBridge.magazineFor(object, object2);
            if (string != null) {
                boolean result = string.equals(PillagerGunEnchantBridge.getInstalledExtendedMagId(object2));
                System.out.println("[RoN Debug Bridge] hasSame magazine check result: " + result);
                return result;
            }
            Object object3 = PillagerGunEnchantBridge.getField(object, "equipmentSlot");
            Object object4 = PillagerGunEnchantBridge.call(object2, "m_6844_", new Class[]{object3.getClass()}, object3);
            Object object5 = PillagerGunEnchantBridge.callNoArg(object, "getEnchantment");
            if (object5 == null) {
                System.out.println("[RoN Debug Bridge] hasSame: getEnchantment is null");
                return false;
            }
            Map map = (Map)PillagerGunEnchantBridge.callNoArg(object4, "getAllEnchantments");
            boolean result = map.containsKey(object5);
            System.out.println("[RoN Debug Bridge] hasSame vanilla enchant check result: " + result);
            return result;
        }
        catch (Throwable throwable) {
            System.out.println("[RoN Debug Bridge] hasSame error: " + throwable);
            throwable.printStackTrace();
            return false;
        }
    }

    public static void apply(Object object, Object object2) {
        try {
            System.out.println("[RoN Debug Bridge] apply called for enchant: " + (object != null ? object.getClass().getName() : "null") + " on unit: " + (object2 != null ? object2.getClass().getName() : "null"));
            String string = PillagerGunEnchantBridge.magazineFor(object, object2);
            System.out.println("[RoN Debug Bridge] magazineFor returned: " + string);
            if (string != null) {
                PillagerGunEnchantBridge.installExtendedMag(object2, string);
                System.out.println("[RoN Debug Bridge] Successfully installed magazine: " + string);
                return;
            }
            PillagerGunEnchantBridge.applyVanillaEnchant(object, object2);
            System.out.println("[RoN Debug Bridge] Applied vanilla enchant");
        }
        catch (Throwable throwable) {
            System.out.println("[RoN Debug Bridge] apply error: " + throwable);
            throwable.printStackTrace();
        }
    }

    private static String magazineFor(Object object, Object object2) {
        if (object == null || object2 == null) {
            return null;
        }
        if (!isActualSpringfield(object2)) {
            return null;
        }
        String string = object.getClass().getName();
        if (MULTISHOT_CLASS.equals(string)) {
            return EXPLOSIVE_MAG;
        }
        if (QUICK_CHARGE_CLASS.equals(string)) {
            return FIRE_MAG;
        }
        return null;
    }

    private static void installExtendedMag(Object object, String string) throws Exception {
        Object object2 = PillagerGunEnchantBridge.callNoArg(object, "m_21205_");
        Object object3 = PillagerGunEnchantBridge.getGun(object2);
        if (object3 == null) {
            return;
        }
        Object object4 = PillagerGunEnchantBridge.buildAttachment(string);
        Object object5 = PillagerGunEnchantBridge.callNoArg(object2, "m_41784_");
        Object object6 = PillagerGunEnchantBridge.newCompoundTag();
        PillagerGunEnchantBridge.call(object4, "m_41739_", new Class[]{object6.getClass()}, object6);
        PillagerGunEnchantBridge.call(object5, "m_128365_", new Class[]{String.class, Class.forName("net.minecraft.nbt.Tag")}, "AttachmentEXTENDED_MAG", object6);
    }

    private static String getInstalledExtendedMagId(Object object) throws Exception {
        Object object2 = PillagerGunEnchantBridge.callNoArg(object, "m_21205_");
        Object object3 = PillagerGunEnchantBridge.getGun(object2);
        if (object3 == null) {
            return "";
        }
        Class<?> clazz = Class.forName("com.tacz.guns.api.item.attachment.AttachmentType");
        Enum enum_ = Enum.valueOf(clazz.asSubclass(Enum.class), "EXTENDED_MAG");
        Object object4 = PillagerGunEnchantBridge.callInterface("com.tacz.guns.api.item.IGun", "getAttachmentId", new String[]{"net.minecraft.world.item.ItemStack", "com.tacz.guns.api.item.attachment.AttachmentType"}, object3, object2, enum_);
        return String.valueOf(object4);
    }

    private static Object buildAttachment(String string) throws Exception {
        Class<?> clazz = Class.forName("com.tacz.guns.api.item.builder.AttachmentItemBuilder");
        Object object = clazz.getMethod("create", new Class[0]).invoke(null, new Object[0]);
        Object object2 = PillagerGunEnchantBridge.newResourceLocation(string);
        object = clazz.getMethod("setId", object2.getClass()).invoke(object, object2);
        return clazz.getMethod("build", new Class[0]).invoke(object, new Object[0]);
    }

    private static Object getGun(Object object) throws Exception {
        Class<?> clazz = Class.forName("net.minecraft.world.item.ItemStack");
        Class<?> clazz2 = Class.forName("com.tacz.guns.api.item.IGun");
        Method method = clazz2.getMethod("getIGunOrNull", clazz);
        return method.invoke(null, object);
    }

    private static void applyVanillaEnchant(Object object, Object object2) throws Exception {
        Class<?> clazz;
        Object object3;
        Object object4 = PillagerGunEnchantBridge.getField(object, "equipmentSlot");
        Object object5 = PillagerGunEnchantBridge.call(object2, "m_6844_", new Class[]{object4.getClass()}, object4);
        Class<?> clazz2 = Class.forName("net.minecraft.world.entity.LivingEntity");
        Object object6 = PillagerGunEnchantBridge.call(object, "getMutuallyExclusiveEnchant", new Class[]{clazz2}, object2);
        if (object6 != null) {
            object3 = (Map)PillagerGunEnchantBridge.callNoArg(object5, "getAllEnchantments");
            HashMap hashMap = new HashMap();
            for (Map.Entry object72 : ((Map<?, ?>)object3).entrySet()) {
                hashMap.put(object72.getKey(), object72.getValue());
            }
            hashMap.remove(object6);
            clazz = Class.forName("net.minecraft.world.item.enchantment.EnchantmentHelper");
            Class<?> clazz3 = Class.forName("net.minecraft.world.item.ItemStack");
            clazz.getMethod("m_44865_", Map.class, clazz3).invoke(null, hashMap, object5);
        }
        object3 = PillagerGunEnchantBridge.callNoArg(object, "getEnchantment");
        int n = ((Number)PillagerGunEnchantBridge.getField(object, "enchantmentLevel")).intValue();
        clazz = Class.forName("net.minecraft.world.item.enchantment.Enchantment");
        PillagerGunEnchantBridge.call(object5, "m_41663_", new Class[]{clazz, Integer.TYPE}, object3, n);
    }

    private static Object callInterface(String string, String string2, String[] stringArray, Object object, Object ... objectArray) throws Exception {
        Class<?> clazz = Class.forName(string);
        Class[] classArray = new Class[stringArray.length];
        for (int i = 0; i < stringArray.length; ++i) {
            classArray[i] = Class.forName(stringArray[i]);
        }
        return clazz.getMethod(string2, classArray).invoke(object, objectArray);
    }

    private static Object callNoArg(Object object, String string) throws Exception {
        Method method = PillagerGunEnchantBridge.findMethod(object.getClass(), string, new Class[0]);
        return method.invoke(object, new Object[0]);
    }

    private static Object call(Object object, String string, Class<?>[] classArray, Object ... objectArray) throws Exception {
        Method method = PillagerGunEnchantBridge.findMethod(object.getClass(), string, classArray);
        return method.invoke(object, objectArray);
    }

    private static Method findMethod(Class<?> clazz, String string, Class<?> ... classArray) throws Exception {
        for (Class<?> clazz2 = clazz; clazz2 != null; clazz2 = clazz2.getSuperclass()) {
            try {
                Method method = clazz2.getDeclaredMethod(string, classArray);
                method.setAccessible(true);
                return method;
            }
            catch (NoSuchMethodException noSuchMethodException) {
                continue;
            }
        }
        Method method = clazz.getMethod(string, classArray);
        method.setAccessible(true);
        return method;
    }

    private static Object getField(Object object, String string) throws Exception {
        Field field = PillagerGunEnchantBridge.findField(object.getClass(), string);
        return field.get(object);
    }

    private static Field findField(Class<?> clazz, String string) throws Exception {
        for (Class<?> clazz2 = clazz; clazz2 != null; clazz2 = clazz2.getSuperclass()) {
            try {
                Field field = clazz2.getDeclaredField(string);
                field.setAccessible(true);
                return field;
            }
            catch (NoSuchFieldException noSuchFieldException) {
                continue;
            }
        }
        Field field = clazz.getField(string);
        field.setAccessible(true);
        return field;
    }

    private static Object newResourceLocation(String string) throws Exception {
        Class<?> clazz = Class.forName("net.minecraft.resources.ResourceLocation");
        Constructor<?> constructor = clazz.getConstructor(String.class);
        return constructor.newInstance(string);
    }

    private static Object newCompoundTag() throws Exception {
        return Class.forName("net.minecraft.nbt.CompoundTag").getConstructor(new Class[0]).newInstance(new Object[0]);
    }
}
