package me.alexisevelyn.randomtech.api.items.tools.generic;

import com.google.common.collect.Multimap;
import me.alexisevelyn.randomtech.api.utilities.ItemManager;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import reborncore.api.items.ItemStackModifiers;
import reborncore.common.util.ItemDurabilityExtensions;
import reborncore.common.util.ItemUtils;
import team.reborn.energy.Energy;
import team.reborn.energy.EnergyHolder;
import team.reborn.energy.EnergyTier;

import java.util.List;
import java.util.Random;
import java.util.Set;

public abstract class GenericPoweredTool extends MiningToolItem implements EnergyHolder, ItemDurabilityExtensions, ItemStackModifiers {
    public final int maxCharge;
    public final int cost;
    public final float poweredSpeed;
    public final float unpoweredSpeed;
    public final EnergyTier tier;
    public final Set<Block> effectiveBlocks;
    private final String dischargedTranslationKey;

    // Pulled From: net.minecraft.item.Item
//    public static final UUID ATTACK_DAMAGE_UUID = UUID.fromString("CB3F55D3-645C-4F38-A497-9C13A33DB5CF");
//    public static final UUID ATTACK_SPEED_UUID = UUID.fromString("FA233E1C-4180-4865-B01B-BCCE9785ACA3");

    public GenericPoweredTool(ToolMaterial material, int energyCapacity, EnergyTier tier, int cost, float poweredSpeed, float unpoweredSpeed, float attackDamage, Set<Block> effectiveBlocks, Settings settings, @Nullable String dischargedTranslationKey) {
        super(attackDamage, unpoweredSpeed, material, effectiveBlocks, settings.maxCount(1).maxDamage(material.getDurability()));

        this.maxCharge = energyCapacity;
        this.tier = tier;

        this.cost = cost;
        this.unpoweredSpeed = unpoweredSpeed;
        this.poweredSpeed = poweredSpeed;
        this.effectiveBlocks = effectiveBlocks;
        this.dischargedTranslationKey = dischargedTranslationKey;
    }

    @Override
    @Environment(EnvType.CLIENT)
    public Text getName(ItemStack stack) {
        return new TranslatableText(this.getTranslationKey(stack));
    }

    @Override
    public String getTranslationKey(ItemStack stack) {
        if (isUsable(stack) || this.dischargedTranslationKey == null)
            return super.getTranslationKey(stack);

        return this.dischargedTranslationKey;
    }

    @Override
    public boolean isEffectiveOn(BlockState state) {
        return effectiveBlocks.contains(state.getBlock());
    }

    @Override
    public boolean canMine(BlockState state, World world, BlockPos pos, PlayerEntity miner) {
        if (!super.canMine(state, world, pos, miner))
            return false;

        return isUsable(miner.getMainHandStack());
    }

    @Override
    public float getMiningSpeedMultiplier(ItemStack stack, BlockState state) {
        if (isUsable(stack) && isEffectiveOn(state))
            return this.poweredSpeed;

        return super.getMiningSpeedMultiplier(stack, state);
    }

    @Override
    public void getAttributeModifiers(EquipmentSlot equipmentSlot, ItemStack stack, Multimap<EntityAttribute, EntityAttributeModifier> builder) {
        // Modify Tool Attributes Dynamically

        // The attribute modifiers are automatically added back when they aren't actively being taken away
        if (!isUsable(stack) && equipmentSlot == EquipmentSlot.MAINHAND) {
            builder.removeAll(EntityAttributes.GENERIC_ATTACK_DAMAGE);
            builder.removeAll(EntityAttributes.GENERIC_ATTACK_SPEED);
        }
    }

    public boolean isUsable(ItemStack stack) {
        return Energy.of(stack).getEnergy() >= this.cost;
    }

    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
        super.inventoryTick(stack, world, entity, slot, selected);
    }

    // Used by mobs to determine if they prefer a weapon over another one.
    // It does not actually modify the attack damage of an item (for vanilla purposes)?
    @SuppressWarnings("EmptyMethod")
    @Override
    public float getAttackDamage() {
        return super.getAttackDamage();
    }

    @SuppressWarnings("unused")
    public float getAttackSpeed() {
        return this.unpoweredSpeed;
    }

    // For Attacking
    @Override
    public boolean postHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        Random rand = new Random();
        if (isUsable(stack) && rand.nextInt(EnchantmentHelper.getLevel(Enchantments.UNBREAKING, stack) + 1) == 0) {
            ItemManager.useEnergy(attacker, stack, cost);
            return super.postHit(stack, target, attacker);
        }

        // For Item Stats
        return false;
    }

    // For Mining
    @Override
    public boolean postMine(ItemStack stack, World world, BlockState state, BlockPos pos, LivingEntity miner) {
        Random rand = new Random();
        if (isUsable(stack) && (state.getHardness(world, pos) != 0.0F) && rand.nextInt(EnchantmentHelper.getLevel(Enchantments.UNBREAKING, stack) + 1) == 0) {
            if (isEffectiveOn(state))
                ItemManager.useEnergy(miner, stack, cost);
            else
                ItemManager.useEnergy(miner, stack, cost+1);

            return super.postMine(stack, world, state, pos, miner);
        }

        // For Item Stats
        return false;
    }

    // ToolItem
    @Override
    public boolean canRepair(ItemStack tool, ItemStack repairMaterial) {
        return false;
    }

    // Item
    @Override
    public boolean isDamageable() {
        return false;
    }

    // This only applies to the enchantment table, not anvils?
    @Override
    public boolean isEnchantable(ItemStack stack) {
        return true;
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

    @SuppressWarnings("EmptyMethod")
    @Override
    public boolean isFireproof() {
        return super.isFireproof();
    }

    @Override
    public double getMaxStoredPower() {
        return this.maxCharge;
    }

    @Override
    public EnergyTier getTier() {
        return this.tier;
    }

    @Override
    public double getDurability(ItemStack stack) {
        return 1 - ItemUtils.getPowerForDurabilityBar(stack);
    }

    @Override
    public boolean showDurability(ItemStack stack) {
        if (!(stack.getItem() instanceof GenericPoweredTool))
            return true;

        double currentEnergy = Energy.of(stack).getEnergy();
        double maxEnergy = Energy.of(stack).getMaxStored();

        return currentEnergy < maxEnergy;
    }

    @Override
    public int getDurabilityColor(ItemStack stack) {
        // Red - PowerSystem.getDisplayPower().colour;
        // Darker Red - PowerSystem.getDisplayPower().altColour;
        // Blue - 0xFF0014A2
        // Green - 0xFF006700

        return 0xFF0014A2;
    }

    // I may or may not use this in the future, so I'm just marking it to not be used by 3rd parties for now.
//    @Deprecated
//    public boolean allowActionResult(ActionResult actionResult) {
//        return actionResult.isAccepted() || actionResult == ActionResult.PASS;
//    }

    @Environment(EnvType.CLIENT)
    @Override
    public void appendStacks(ItemGroup group, DefaultedList<ItemStack> itemList) {
        // Can be used to add multiple versions of items (e.g. a charged and discharged variant of each tool)
        if (!isIn(group)) {
            return;
        }

        ItemManager.initPoweredItems(this, itemList);
    }

    public ItemStack onCraft(ItemStack oldStack, ItemStack newStack, CompoundTag tag) {
        return ItemManager.convertStackToEnergyItemStack(oldStack, newStack, tag);
    }

    @Environment(EnvType.CLIENT)
    @Override
    public void appendTooltip(ItemStack stack, @Nullable World worldIn, List<Text> tooltip, TooltipContext flagIn) {
        if (flagIn.isAdvanced())
            ItemManager.powerLevelTooltip(stack, tooltip);
    }
}