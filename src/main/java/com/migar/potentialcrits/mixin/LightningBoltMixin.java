package com.migar.potentialcrits.mixin;

import net.minecraft.world.entity.LightningBolt;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LightningBolt.class)
public class LightningBoltMixin {

    @Inject(method = "spawnFire", at = @At("HEAD"), cancellable = true)
    private void onSpawnFire(CallbackInfo ci) {
        LightningBolt bolt = (LightningBolt) (Object) this;

        // If it's thunder crits lightning, cancel it.
        if (bolt.getPersistentData().getBoolean("potentialcrits_no_fire")) {
            ci.cancel(); // Cancel fire
        }
    }
}