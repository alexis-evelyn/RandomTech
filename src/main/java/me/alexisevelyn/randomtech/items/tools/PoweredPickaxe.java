package me.alexisevelyn.randomtech.items.tools;

import me.alexisevelyn.randomtech.toolmaterials.PoweredToolMaterial;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.BlockState;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class PoweredPickaxe extends PickaxeItem {
    public PoweredPickaxe(ToolMaterial material, int attackDamage, float attackSpeed, Settings settings) {
        super(material, attackDamage, attackSpeed, settings);
    }

    public PoweredPickaxe(ToolMaterial material) {
        super(material, -1, -2.2F, new Item.Settings().group(ItemGroup.TOOLS));
    }

    public PoweredPickaxe(Settings settings) {
        super(new PoweredToolMaterial(), -1, -2.2F, settings);
    }

    @Override
    @Environment(EnvType.CLIENT)
    public Text getName() {
        return new TranslatableText(this.getTranslationKey());
    }

    @Override
    public float getMiningSpeedMultiplier(ItemStack stack, BlockState state) {
        if (isUsable(stack))
            return super.getMiningSpeedMultiplier(stack, state);

        return 1.0F;
    }

    public boolean isUsable(ItemStack stack) {
        return stack.getDamage() < stack.getMaxDamage() - 1;
    }

    // Can be used to allow/prevent player from mining with tool
    @Override
    public boolean canMine(BlockState state, World world, BlockPos pos, PlayerEntity miner) {
        return isUsable(miner.getMainHandStack());
    }

    // For Attacking
    @Override
    public boolean postHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        if (isUsable(stack))
            return super.postHit(stack, target, attacker);

        // For Item Stats
        return false;
    }

    // For Mining
    @Override
    public boolean postMine(ItemStack stack, World world, BlockState state, BlockPos pos, LivingEntity miner) {
        if (isUsable(stack))
            return super.postMine(stack, world, state, pos, miner);

        // For Item Stats
        return false;
    }

    // For Right Clicking Blocks
    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        if (isUsable(context.getStack()))
            return super.useOnBlock(context);

        return ActionResult.FAIL;
    }

    // For Right Clicking Entities
    @Override
    public ActionResult useOnEntity(ItemStack stack, PlayerEntity user, LivingEntity entity, Hand hand) {
        if (isUsable(stack))
            return super.useOnEntity(stack, user, entity, hand);

        return ActionResult.FAIL;
    }

    @Override
    public boolean isFireproof() {
        return super.isFireproof();
    }
}
