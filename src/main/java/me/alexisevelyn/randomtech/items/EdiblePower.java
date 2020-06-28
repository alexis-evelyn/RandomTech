package me.alexisevelyn.randomtech.items;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

public class EdiblePower extends Item {

    public EdiblePower(Settings settings) {
        super(settings);
    }

//    @Override
//    public TypedActionResult<ItemStack> use(World world, PlayerEntity playerEntity, Hand hand) {
//
//        return new TypedActionResult<>(ActionResult.SUCCESS, playerEntity.getStackInHand(hand));
//    }
}
