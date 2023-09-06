package stop.villager.jumping.mixin;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.passive.VillagerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public class LivingEntityMixin {
    LivingEntity thisLivingEntity = (LivingEntity) (Object) this;

    @Inject(method = "getJumpVelocity", at = @At(value = "HEAD"), cancellable = true)
    private void getJumpVelocityM(CallbackInfoReturnable<Float> cir) {
        if(thisLivingEntity instanceof VillagerEntity villager) {
            if(villager.canImmediatelyDespawn(1d)) {
                cir.setReturnValue(0f);
            }
        }
    }
}
