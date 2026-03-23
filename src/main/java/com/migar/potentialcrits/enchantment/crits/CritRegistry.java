package com.migar.potentialcrits.enchantment.crits;

import net.minecraft.resources.ResourceLocation;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class CritRegistry {
    private static final Map<ResourceLocation, CritEffect> CRIT_EFFECTS = new HashMap<>();

    static {
        // If crits are not in this list, ModEvents won't trigger them.
        register(new SuperCritEffect()); // Improved vanilla crits
        register(new FireCritEffect()); // Flat damage
        register(new ThunderCritEffect());
        register(new UmbralCritEffect());
        register(new ShieldCritEffect());
        register(new BerserkCritEffect());
        register(new SmashCritEffect());
        register(new SunderCritEffect());
        register(new HarvestCritEffect());
        register(new IceCritEffect());
        register(new WaterCritEffect()); // Mixed damage
        register(new VampireCritEffect()); // Percentage damage
        register(new DarkCritEffect());
        register(new LightCritEffect());
        register(new GroundCritEffect());
        register(new TrueCritEffect()); // True damage
        // Add more here.
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
