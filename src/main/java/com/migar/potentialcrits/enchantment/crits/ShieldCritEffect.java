package com.migar.potentialcrits.enchantment.crits;

import com.migar.potentialcrits.PotentialCrits;
import com.migar.potentialcrits.effect.ModEffects;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;

import static com.migar.potentialcrits.event.EventUtils.getEnchantmentLevel;

public class ShieldCritEffect implements CritEffect {
    private static final ResourceLocation ID =
            ResourceLocation.fromNamespaceAndPath(PotentialCrits.MODID, "shield_crit");

    @Override
    public boolean applyEffect(Player player, LivingIncomingDamageEvent event, int level) {

        float chance = level * 0.2f;

        if (player.level().random.nextFloat() < chance) {
            float damage = event.getAmount() + 3;
            int amplifier = 0;

            ItemStack shield = player.getOffhandItem();

            if (shield.getItem() instanceof net.minecraft.world.item.ShieldItem) {
                damage += 1.5f;
                if (getEnchantmentLevel(shield, player, ID) > 0) {
                    chance = 0.5f;
                    if (player.level().random.nextFloat() < chance) {
                        amplifier = 1;
                    }
                }
            }

            event.setAmount(damage);

            player.addEffect(new MobEffectInstance(ModEffects.SHIELD_EFFECT,200,amplifier));

            return true;
        }
        return false;
    }

    @Override
    public void playSpecialEffects(Player player, LivingEntity target) {

        if (target.level() instanceof ServerLevel serverLevel) {
            BlockState shieldBlockState = Blocks.IRON_BLOCK.defaultBlockState();
            serverLevel.sendParticles(
                    new BlockParticleOption(ParticleTypes.BLOCK, shieldBlockState),
                    target.getX(),
                    target.getY() + 0.9,
                    target.getZ(),
                    36,
                    0.8,
                    1,
                    0.8,
                    0.05
            );
        }
        player.level().playSound(
                null,
                target.getX(), target.getY(), target.getZ(),
                SoundEvents.ANVIL_PLACE,
                SoundSource.PLAYERS,
                0.8f,
                1.0f + player.level().random.nextFloat() * 0.2f
        );
    }

    @Override
    public ResourceLocation getId() {
        return ID;
    }
}

