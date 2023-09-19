package stop.villager.jumping.mixin;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.passive.VillagerEntity;
import net.minecraft.nbt.NbtCompound;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import stop.villager.jumping.util.IEntityDataSaver;

@Mixin(LivingEntity.class)
public class LivingEntityMixin {
    @Unique
    LivingEntity thisLivingEntity = (LivingEntity) (Object) this;

    @Inject(method = "getJumpVelocity", at = @At(value = "HEAD"), cancellable = true)
    private void canJump(CallbackInfoReturnable<Float> cir) {
        if(thisLivingEntity instanceof VillagerEntity villager) {
            NbtCompound villagerData = ((IEntityDataSaver) villager).getPersistentData();
            if(villagerData.getBoolean("isChained")) {
                cir.setReturnValue(0f);
            }
        }
    }
}
