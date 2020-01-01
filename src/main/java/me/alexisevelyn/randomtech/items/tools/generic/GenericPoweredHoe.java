package me.alexisevelyn.randomtech.items.tools.generic;

import me.alexisevelyn.randomtech.toolmaterials.PoweredToolMaterial;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class GenericPoweredHoe extends HoeItem {
    private final EntityAttributeModifier brokenAttackAttribute = new EntityAttributeModifier(ATTACK_SPEED_MODIFIER_ID, "Broken Weapon Modifier", 0, EntityAttributeModifier.Operation.MULTIPLY_TOTAL);

    public GenericPoweredHoe(ToolMaterial material, int attackDamage, float attackSpeed, Settings settings) {
        super(material, attackDamage, attackSpeed, settings);
    }

    public GenericPoweredHoe(ToolMaterial material) {
        super(material, -1, -2.2F, new Settings().group(ItemGroup.TOOLS));
    }

    public GenericPoweredHoe(Settings settings) {
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

    @SuppressWarnings("DuplicatedCode") // I know. I've had to purposely duplicate the code to allow retrieving attributes of the correct type of tool.
    private void changeModifiers(ItemStack stack) {
        EquipmentSlot equipmentSlot = EquipmentSlot.MAINHAND;

        if (isUsable(stack) && hasBrokenAttribute(stack, equipmentSlot)) {
            removeBrokenAttribute(stack, equipmentSlot);

            return;
        }

        if (!isUsable(stack) && !hasBrokenAttribute(stack, equipmentSlot)) {
            stack.addAttributeModifier(
                    EntityAttributes.GENERIC_ATTACK_DAMAGE,
                    brokenAttackAttribute,
                    equipmentSlot
            );
        }
    }

    private void removeBrokenAttribute(ItemStack stack, EquipmentSlot equipmentSlot) {
        CompoundTag nbtTags = stack.getOrCreateTag();

        if (!nbtTags.contains("AttributeModifiers", 9))
            return;

        // This works, but removes all AttributeModifiers.
        // I would prefer restoring the old attribute modifiers if it exists.
        nbtTags.remove("AttributeModifiers");
    }

    private boolean hasBrokenAttribute(ItemStack stack, EquipmentSlot equipmentSlot) {
        return stack.getAttributeModifiers(equipmentSlot).containsEntry(EntityAttributes.GENERIC_ATTACK_DAMAGE, brokenAttackAttribute);
    }

    public boolean isUsable(ItemStack stack) {
        return stack.getDamage() < stack.getMaxDamage() - 1;
    }

    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
        super.inventoryTick(stack, world, entity, slot, selected);

        // Updates if this item is usable
        changeModifiers(stack);
    }

    // Used by mobs to determine if they prefer a weapon over another one.
    // It does not actually modify the attack damage of an item.
    @Override
    public float getAttackDamage() {
        return super.getAttackDamage();
    }

    // Can be used to allow/prevent player from mining with tool
    @Override
    public boolean canMine(BlockState state, World world, BlockPos pos, PlayerEntity miner) {
        if (isUsable(miner.getMainHandStack()))
            return super.canMine(state, world, pos, miner);

        return false;
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

    // For Right Clicking Blocks (e.g. Tilling Dirt/Grass)
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

        return ActionResult.PASS;
    }

    @Override
    public boolean isFireproof() {
        return super.isFireproof();
    }
}
