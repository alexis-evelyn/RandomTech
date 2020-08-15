package me.alexisevelyn.randomtech.api.items.tools.generic;

import com.google.common.collect.Multimap;
import me.alexisevelyn.randomtech.api.items.energy.EnergyHelper;
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
import net.minecraft.world.BlockView;
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

/**
 * The type Generic powered tool.
 */
public abstract class GenericPoweredTool extends MiningToolItem implements EnergyHelper, EnergyHolder, ItemDurabilityExtensions, ItemStackModifiers {
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

    /**
     * Instantiates a new Generic powered tool.
     *
     * @param material                 the material
     * @param energyCapacity           the energy capacity
     * @param tier                     the tier
     * @param cost                     the cost
     * @param poweredSpeed             the powered speed
     * @param unpoweredSpeed           the unpowered speed
     * @param attackDamage             the attack damage
     * @param effectiveBlocks          the effective blocks
     * @param settings                 the settings
     * @param dischargedTranslationKey the discharged translation key
     */
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

    /**
     * Gets name.
     *
     * @param stack the stack
     * @return the name
     */
    @Override
    @Environment(EnvType.CLIENT)
    public Text getName(ItemStack stack) {
        return new TranslatableText(this.getTranslationKey(stack));
    }

    /**
     * Gets translation key.
     *
     * @param stack the stack
     * @return the translation key
     */
    @Override
    public String getTranslationKey(ItemStack stack) {
        if (isUsable(stack) || this.dischargedTranslationKey == null)
            return super.getTranslationKey(stack);

        return this.dischargedTranslationKey;
    }

    /**
     * Is effective on boolean.
     *
     * @param state the state
     * @return the boolean
     */
    @Override
    public boolean isEffectiveOn(BlockState state) {
        return effectiveBlocks.contains(state.getBlock());
    }

    /**
     * Can mine boolean.
     *
     * @param state the state
     * @param world the world
     * @param pos   the pos
     * @param miner the miner
     * @return the boolean
     */
    @Override
    public boolean canMine(BlockState state, World world, BlockPos pos, PlayerEntity miner) {
        if (!super.canMine(state, world, pos, miner))
            return false;

        // Allows player to break block in creative mode even when holding a dead tool.
        // Sword still overrides this method, so breaking in creative is denied for sword.
        if (miner.isCreative())
            return true;

        return isUsable(miner.getMainHandStack());
    }

    /**
     * Gets mining speed multiplier.
     *
     * @param stack the stack
     * @param state the state
     * @return the mining speed multiplier
     */
    @Override
    public float getMiningSpeedMultiplier(ItemStack stack, BlockState state) {
        if (isUsable(stack) && isEffectiveOn(state))
            return this.poweredSpeed;

        return super.getMiningSpeedMultiplier(stack, state);
    }

    /**
     * Gets attribute modifiers.
     *
     * @param equipmentSlot the equipment slot
     * @param stack         the stack
     * @param builder       the builder
     */
    @Override
    public void getAttributeModifiers(EquipmentSlot equipmentSlot, ItemStack stack, Multimap<EntityAttribute, EntityAttributeModifier> builder) {
        // Modify Tool Attributes Dynamically

        // The attribute modifiers are automatically added back when they aren't actively being taken away
        if (!isUsable(stack) && equipmentSlot == EquipmentSlot.MAINHAND) {
            builder.removeAll(EntityAttributes.GENERIC_ATTACK_DAMAGE);
            builder.removeAll(EntityAttributes.GENERIC_ATTACK_SPEED);
        }
    }

    /**
     * Is not full boolean.
     *
     * @param stack the stack
     * @return the boolean
     */
    @Override
    public boolean isNotFull(ItemStack stack) {
        return getEnergy(stack) != getMaxEnergy(stack);
    }

    /**
     * Is usable boolean.
     *
     * @param stack the stack
     * @return the boolean
     */
    @Override
    public boolean isUsable(ItemStack stack) {
        return Energy.of(stack).getEnergy() >= this.cost;
    }

    /**
     * Gets energy.
     *
     * @param stack the stack
     * @return the energy
     */
    @Override
    public double getEnergy(ItemStack stack) {
        return Energy.of(stack).getEnergy();
    }

    /**
     * Sets energy.
     *
     * @param stack  the stack
     * @param energy the energy
     */
    @Override
    public void setEnergy(ItemStack stack, double energy) {
        Energy.of(stack).set(energy);
    }

    /**
     * Inventory tick.
     *
     * @param stack    the stack
     * @param world    the world
     * @param entity   the entity
     * @param slot     the slot
     * @param selected the selected
     */
    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
        super.inventoryTick(stack, world, entity, slot, selected);
    }

    /**
     * Gets attack damage.
     *
     * @return the attack damage
     */
// Used by mobs to determine if they prefer a weapon over another one.
    // It does not actually modify the attack damage of an item (for vanilla purposes)?
    @SuppressWarnings("EmptyMethod")
    @Override
    public float getAttackDamage() {
        return super.getAttackDamage();
    }

    /**
     * Gets attack speed.
     *
     * @return the attack speed
     */
    @SuppressWarnings("unused")
    public float getAttackSpeed() {
        return this.unpoweredSpeed;
    }

    /**
     * Post hit boolean.
     *
     * @param stack    the stack
     * @param target   the target
     * @param attacker the attacker
     * @return the boolean
     */
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

    /**
     * Post mine boolean.
     *
     * @param stack the stack
     * @param world the world
     * @param state the state
     * @param pos   the pos
     * @param miner the miner
     * @return the boolean
     */
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

    /**
     * Can repair boolean.
     *
     * @param tool           the tool
     * @param repairMaterial the repair material
     * @return the boolean
     */
// ToolItem
    @Override
    public boolean canRepair(ItemStack tool, ItemStack repairMaterial) {
        return false;
    }

    /**
     * Is damageable boolean.
     *
     * @return the boolean
     */
// Item
    @Override
    public boolean isDamageable() {
        return false;
    }

    /**
     * Is enchantable boolean.
     *
     * @param stack the stack
     * @return the boolean
     */
// This only applies to the enchantment table, not anvils?
    @Override
    public boolean isEnchantable(ItemStack stack) {
        return true;
    }

    /**
     * Use on block action result.
     *
     * @param context the context
     * @return the action result
     */
// For Right Clicking Blocks
    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        if (isUsable(context.getStack()))
            return super.useOnBlock(context);

        return ActionResult.FAIL;
    }

    /**
     * Use on entity action result.
     *
     * @param stack  the stack
     * @param user   the user
     * @param entity the entity
     * @param hand   the hand
     * @return the action result
     */
// For Right Clicking Entities
    @Override
    public ActionResult useOnEntity(ItemStack stack, PlayerEntity user, LivingEntity entity, Hand hand) {
        if (isUsable(stack))
            return super.useOnEntity(stack, user, entity, hand);

        return ActionResult.FAIL;
    }

    /**
     * Is fireproof boolean.
     *
     * @return the boolean
     */
    @SuppressWarnings("EmptyMethod")
    @Override
    public boolean isFireproof() {
        return super.isFireproof();
    }

    /**
     * Gets max energy.
     *
     * @param itemStack the item stack
     * @return the max energy
     */
    @Override
    public double getMaxEnergy(ItemStack itemStack) {
        // RebornCore uses getMaxStoredPower() for their hud.
        // That makes it where the max energy cannot be set per ItemStack.
        // Once I implement dynamic max energy, my code will be able to get the dynamic max energy.

        return getMaxStoredPower();
    }

    /**
     * Gets max stored power.
     *
     * @return the max stored power
     */
    @Override
    public double getMaxStoredPower() {
        return this.maxCharge;
    }

    /**
     * Gets tier.
     *
     * @return the tier
     */
    @Override
    public EnergyTier getTier() {
        return this.tier;
    }

    /**
     * Gets durability.
     *
     * @param stack the stack
     * @return the durability
     */
    @Override
    public double getDurability(ItemStack stack) {
        // TODO: Replace this with a dynamic durability bar checker.
        return 1 - ItemUtils.getPowerForDurabilityBar(stack);
    }

    /**
     * Show durability boolean.
     *
     * @param stack the stack
     * @return the boolean
     */
    @Override
    public boolean showDurability(ItemStack stack) {
        if (!(stack.getItem() instanceof EnergyHelper))
            return true;

        double currentEnergy = Energy.of(stack).getEnergy();
        double maxEnergy = getMaxEnergy(stack);

        return currentEnergy < maxEnergy;
    }

    /**
     * Gets durability color.
     *
     * @param stack the stack
     * @return the durability color
     */
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

    /**
     * Append stacks.
     *
     * @param group    the group
     * @param itemList the item list
     */
    @Environment(EnvType.CLIENT)
    @Override
    public void appendStacks(ItemGroup group, DefaultedList<ItemStack> itemList) {
        // Can be used to add multiple versions of items (e.g. a charged and discharged variant of each tool)
        if (!isIn(group)) {
            return;
        }

        ItemManager.initPoweredItems(this, itemList);
    }

    /**
     * On craft item stack.
     *
     * @param oldStack the old stack
     * @param newStack the new stack
     * @param tag      the tag
     * @return the item stack
     */
    @Override
    public ItemStack onCraft(ItemStack oldStack, ItemStack newStack, CompoundTag tag) {
        return ItemManager.convertStackToEnergyItemStack(oldStack, newStack, tag);
    }

    /**
     * Append tooltip.
     *
     * @param stack   the stack
     * @param worldIn the world in
     * @param tooltip the tooltip
     * @param flagIn  the flag in
     */
    @Environment(EnvType.CLIENT)
    @Override
    public void appendTooltip(ItemStack stack, @Nullable World worldIn, List<Text> tooltip, TooltipContext flagIn) {
        if (flagIn.isAdvanced())
            ItemManager.powerLevelTooltip(stack, tooltip);
    }

    /**
     * Can break unbreakable block boolean.
     *
     * @param state  the state
     * @param player the player
     * @param world  the world
     * @param pos    the pos
     * @return the boolean
     */
    public boolean canBreakUnbreakableBlock(BlockState state, PlayerEntity player, BlockView world, BlockPos pos) {
        return false;
    }

    /**
     * Gets unbreakable block difficulty multiplier.
     *
     * @param state  the state
     * @param player the player
     * @param world  the world
     * @param pos    the pos
     * @return the unbreakable block difficulty multiplier
     */
    public float getUnbreakableBlockDifficultyMultiplier(BlockState state, PlayerEntity player, BlockView world, BlockPos pos) {
        return 1.0F;
    }
}