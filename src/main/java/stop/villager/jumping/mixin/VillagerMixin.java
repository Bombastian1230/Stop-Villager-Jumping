package stop.villager.jumping.mixin;

import net.minecraft.entity.passive.VillagerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import stop.villager.jumping.StopVillagerJumpingMod;
import stop.villager.jumping.util.IEntityDataSaver;

@Mixin(VillagerEntity.class)
public abstract class VillagerMixin {

    @Unique private final VillagerEntity thisVillager = (VillagerEntity) (Object) this;
    @Unique private boolean chained;

    @Inject(method = "interactMob", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/player/PlayerEntity;getStackInHand(Lnet/minecraft/util/Hand;)Lnet/minecraft/item/ItemStack;", shift = At.Shift.AFTER), cancellable = true)
    private void interactM(PlayerEntity player, Hand hand, CallbackInfoReturnable<ActionResult> cir) {
        ItemStack stack = player.getStackInHand(hand);
        NbtCompound nbtData = ((IEntityDataSaver) thisVillager).getPersistentData();
        if(stack.getItem() == StopVillagerJumpingMod.BALL_AND_CHAIN_ITEM && !nbtData.getBoolean("isChained")) {
            chained = true;
            player.setStackInHand(hand, ItemStack.EMPTY);
            cir.setReturnValue(ActionResult.SUCCESS);
        } else if (player.getStackInHand(hand) == ItemStack.EMPTY && player.isSneaking() && nbtData.getBoolean("isChained")) {
            chained = false;
            Item ballAndChain = StopVillagerJumpingMod.BALL_AND_CHAIN_ITEM;
            player.giveItemStack(ballAndChain.getDefaultStack());
            cir.setReturnValue(ActionResult.SUCCESS);
        }
        nbtData.putBoolean("isChained", chained);
    }
}
