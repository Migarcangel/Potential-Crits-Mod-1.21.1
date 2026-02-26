package com.migar.potentialcrits.loot;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.neoforged.neoforge.common.loot.IGlobalLootModifier;
import net.neoforged.neoforge.common.loot.LootModifier;

public class AddLootTableModifier extends LootModifier {
    public static final MapCodec<AddLootTableModifier> CODEC = RecordCodecBuilder.mapCodec(inst ->
            LootModifier.codecStart(inst).and(
                    ResourceLocation.CODEC.fieldOf("loot_table").forGetter(e -> e.lootTable)
            ).apply(inst, AddLootTableModifier::new));

    private final ResourceLocation lootTable;
    private static final ThreadLocal<Boolean> IS_PROCESSING = ThreadLocal.withInitial(() -> false);

    public AddLootTableModifier(LootItemCondition[] conditionsIn, ResourceLocation lootTable) {
        super(conditionsIn);
        this.lootTable = lootTable;
    }

    @Override
    protected ObjectArrayList<ItemStack> doApply(ObjectArrayList<ItemStack> generatedLoot, LootContext lootContext) {
        // PREVENIR BUCLE INFINITO
        if (IS_PROCESSING.get()) {
            return generatedLoot;
        }

        // Verificar condiciones
        for (LootItemCondition condition : this.conditions) {
            if (!condition.test(lootContext)) {
                return generatedLoot;
            }
        }

        ResourceKey<LootTable> thisTableKey = ResourceKey.create(
                net.minecraft.core.registries.Registries.LOOT_TABLE,
                lootTable
        );

        // Si es mi propia loot table, salir (evita bucle)
        if (lootContext.getQueriedLootTableId().equals(thisTableKey)) {
            return generatedLoot;
        }

        // OBTENER LA LOOT TABLE
        var registries = lootContext.getLevel().getServer().reloadableRegistries();
        LootTable extraTable = registries.getLootTable(thisTableKey);

        // Si no existe, salir
        if (extraTable == LootTable.EMPTY) {
            return generatedLoot;
        }

        // Generar items con protección
        IS_PROCESSING.set(true);
        try {
            extraTable.getRandomItems(lootContext, generatedLoot::add);
        } finally {
            IS_PROCESSING.set(false);
        }

        return generatedLoot;
    }

    @Override
    public MapCodec<? extends IGlobalLootModifier> codec() {
        return CODEC;
    }
}