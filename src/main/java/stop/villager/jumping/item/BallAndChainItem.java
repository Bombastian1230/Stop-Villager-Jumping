package stop.villager.jumping.item;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

public class BallAndChainItem extends Item {
    public BallAndChainItem(Settings settings) {
        super(settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand) {
        player.playSound(SoundEvents.BLOCK_CHAIN_BREAK, 1.0F, 1.0F);
        return TypedActionResult.success(player.getStackInHand(hand));
    }
}
