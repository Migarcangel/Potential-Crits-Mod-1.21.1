package com.migar.potentialcrits.enchantment.crits;

import net.minecraft.resources.ResourceLocation;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class CritRegistry {
    private static final Map<ResourceLocation, CritEffect> CRIT_EFFECTS = new HashMap<>();

    static {
        register(new FireCritEffect());
        register(new ThunderCritEffect());
        register(new DarkCritEffect());
        // Añadir más aquí
    }

    public static void register(CritEffect effect) {
        CRIT_EFFECTS.put(effect.getId(), effect);
    }
    // Este id está raro, creo que no debería estar aquí. En la interfaz CritEffect hay un getid tmbn
    public static CritEffect get(ResourceLocation id) {
        return CRIT_EFFECTS.get(id);
    }

    public static Collection<CritEffect> getAll() {
        return CRIT_EFFECTS.values();
    }

}
