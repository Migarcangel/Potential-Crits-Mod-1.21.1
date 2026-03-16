package com.migar.potentialcrits.item;

import com.migar.potentialcrits.PotentialCrits;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModItems {

    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(PotentialCrits.MODID);

    public static final DeferredItem<Item> CRIT_GEM = ITEMS.register("crit_gem",
            () -> new Item(new Item.Properties()));

    public static void register(IEventBus bus) {
        ITEMS.register(bus);
    }
}
