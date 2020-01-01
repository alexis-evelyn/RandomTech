package me.alexisevelyn.randomtech.items.tools;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import com.google.common.collect.MultimapBuilder;
import me.alexisevelyn.randomtech.toolmaterials.PoweredToolMaterial;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class PoweredSword extends SwordItem {
    private boolean isUsable = true;
    private final ImmutableMultimap.Builder<EntityAttribute, EntityAttributeModifier> builder = ImmutableMultimap.builder();
    private final Multimap<EntityAttribute, EntityAttributeModifier> brokenToolAttributes;

    public PoweredSword(ToolMaterial material, int attackDamage, float attackSpeed, Settings settings) {
        super(material, attackDamage, attackSpeed, settings);

        // This is to cut down on processing cost. This is performed once per item instead of every time an ImmutableMultimap is called
        builder.put(EntityAttributes.GENERIC_ATTACK_DAMAGE, new EntityAttributeModifier(ATTACK_DAMAGE_MODIFIER_ID, "Weapon modifier", (double) 0, EntityAttributeModifier.Operation.ADDITION));
        builder.put(EntityAttributes.GENERIC_ATTACK_SPEED, new EntityAttributeModifier(ATTACK_SPEED_MODIFIER_ID, "Weapon modifier", (double) 0, EntityAttributeModifier.Operation.ADDITION));
        brokenToolAttributes = builder.build();
    }

    public PoweredSword(ToolMaterial material) {
        super(material, -1, -2.2F, new Settings().group(ItemGroup.TOOLS));

        // This is to cut down on processing cost. This is performed once per item instead of every time an ImmutableMultimap is called
        builder.put(EntityAttributes.GENERIC_ATTACK_DAMAGE, new EntityAttributeModifier(ATTACK_DAMAGE_MODIFIER_ID, "Weapon modifier", (double) 0, EntityAttributeModifier.Operation.ADDITION));
        builder.put(EntityAttributes.GENERIC_ATTACK_SPEED, new EntityAttributeModifier(ATTACK_SPEED_MODIFIER_ID, "Weapon modifier", (double) 0, EntityAttributeModifier.Operation.ADDITION));
        brokenToolAttributes = builder.build();
    }

    public PoweredSword(Settings settings) {
        super(new PoweredToolMaterial(), -1, -2.2F, settings);

        // This is to cut down on processing cost. This is performed once per item instead of every time an ImmutableMultimap is called
        builder.put(EntityAttributes.GENERIC_ATTACK_DAMAGE, new EntityAttributeModifier(ATTACK_DAMAGE_MODIFIER_ID, "Weapon modifier", (double) 0, EntityAttributeModifier.Operation.ADDITION));
        builder.put(EntityAttributes.GENERIC_ATTACK_SPEED, new EntityAttributeModifier(ATTACK_SPEED_MODIFIER_ID, "Weapon modifier", (double) 0, EntityAttributeModifier.Operation.ADDITION));
        brokenToolAttributes = builder.build();
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
        if (stack.getDamage() < stack.getMaxDamage() - 1) {
            this.isUsable = true;
            return true;
        }

        this.isUsable = false;
        return false;
    }

    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
        super.inventoryTick(stack, world, entity, slot, selected);

        // TODO: This doesn't take effect until the player switches to a different item and back. Figure out why.
        isUsable(stack); // Force update if this item is usable
    }

    // Used by mobs to determine if they prefer a weapon over another one.
    // It does not actually modify the attack damage of an item.
    @Override
    public float getAttackDamage() {
        return super.getAttackDamage();
    }

    @Override
    public Multimap<EntityAttribute, EntityAttributeModifier> getAttributeModifiers(EquipmentSlot slot) {
        if (this.isUsable)
            return super.getAttributeModifiers(slot);

        // The slot check is so that the modifiers only work if the sword is in the main hand.
        // TODO: Make sure this works
        return slot == EquipmentSlot.MAINHAND ? this.brokenToolAttributes : super.getAttributeModifiers(slot);
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
