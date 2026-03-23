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

    public static final DeferredItem<Item> CRIT_COOKIE = ITEMS.register("crit_cookie",
            () -> new Item(new Item.Properties().food(ModFoodProperties.CRIT_COOKIE)));

    public static final DeferredItem<Item> CRIT_HEART = ITEMS.register("crit_heart",
            () -> new CritHeartItem(new Item.Properties()));

    public static final DeferredItem<Item> CRIT_PIE = ITEMS.register("crit_pie",
            () -> new Item(new Item.Properties().food(ModFoodProperties.CRIT_PIE)));

    public static void register(IEventBus bus) {
        ITEMS.register(bus);
    }
}
