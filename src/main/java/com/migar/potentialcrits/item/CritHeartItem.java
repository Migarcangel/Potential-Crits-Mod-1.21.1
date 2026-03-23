package com.migar.potentialcrits.item;

import com.migar.potentialcrits.effect.ModEffects;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class CritHeartItem extends Item {
    public CritHeartItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand usedHand) {
        ItemStack itemstack = player.getItemInHand(usedHand);
        player.addEffect(new MobEffectInstance(ModEffects.ACCURACY_EFFECT,18000,0));
        player.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED,18000,0));
        itemstack.consume(1, player);
        if(!level.isClientSide){
            level.playSound(null,player.blockPosition(), SoundEvents.RESPAWN_ANCHOR_SET_SPAWN, SoundSource.PLAYERS, 0.7f,1.2f);
        }
        return InteractionResultHolder.success(itemstack);
    }
}
