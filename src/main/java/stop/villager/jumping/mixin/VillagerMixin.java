package stop.villager.jumping.mixin;

import net.minecraft.entity.ai.brain.Brain;
import net.minecraft.entity.passive.VillagerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import stop.villager.jumping.StopVillagerJumpingMod;
import stop.villager.jumping.util.IEntityDataSaver;

@Mixin(VillagerEntity.class)
public abstract class VillagerMixin {

    private VillagerEntity thisVillager = (VillagerEntity) (Object) this;
    private boolean chained;

    @Inject(method = "interactMob", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/player/PlayerEntity;getStackInHand(Lnet/minecraft/util/Hand;)Lnet/minecraft/item/ItemStack;", shift = At.Shift.AFTER), cancellable = true)
    private void interactM(PlayerEntity player, Hand hand, CallbackInfoReturnable<ActionResult> cir) {
        StopVillagerJumpingMod.LOGGER.info("Villager interacted");
        ItemStack stack = player.getStackInHand(hand);
        if(stack.getItem() == StopVillagerJumpingMod.BALL_AND_CHAIN_ITEM && !chained) {
            chained = true;
            player.setStackInHand(hand, ItemStack.EMPTY);
            cir.setReturnValue(ActionResult.SUCCESS);
        } else if (player.getStackInHand(hand) == ItemStack.EMPTY && player.isSneaking() && chained) {
            chained = false;
            Item ballAndChain = StopVillagerJumpingMod.BALL_AND_CHAIN_ITEM;
            player.giveItemStack(ballAndChain.getDefaultStack());
            cir.setReturnValue(ActionResult.SUCCESS);
        }
        NbtCompound nbtData = ((IEntityDataSaver) player).getPersistentData();

        nbtData.putBoolean("isChained", chained);
    }

    @Inject(method = "tick", at=@At(value = "TAIL"))
    private void tickM(CallbackInfo ci) {

    }
}
