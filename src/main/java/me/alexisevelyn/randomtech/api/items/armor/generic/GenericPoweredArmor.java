package me.alexisevelyn.randomtech.api.items.armor.generic;

import com.google.common.collect.Multimap;
import me.alexisevelyn.randomtech.api.items.energy.EnergyHelper;
import me.alexisevelyn.randomtech.api.utilities.CustomDamageSource;
import me.alexisevelyn.randomtech.api.utilities.ItemManager;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.enchantment.UnbreakingEnchantment;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import reborncore.api.items.ArmorFovHandler;
import reborncore.api.items.ArmorRemoveHandler;
import reborncore.api.items.ArmorTickable;
import reborncore.api.items.ItemStackModifiers;
import reborncore.common.util.ItemDurabilityExtensions;
import reborncore.common.util.ItemUtils;
import team.reborn.energy.Energy;
import team.reborn.energy.EnergyHolder;
import team.reborn.energy.EnergyTier;

import java.util.List;
import java.util.UUID;

public abstract class GenericPoweredArmor extends ArmorItem implements EnergyHelper, ItemDurabilityExtensions, ItemStackModifiers, ArmorTickable, ArmorRemoveHandler, ArmorFovHandler, EnergyHolder, InvulnerabilityHandler {
    private final int maxCharge;
    private final int cost;

    private final EnergyTier tier;
    private final String dischargedTranslationKey;

    // Figure out how these UUIDs were determined. Mojang has hardcoded UUIDs too.
    public static final UUID[] MODIFIERS = new UUID[]{
            UUID.fromString("845DB27C-C624-495F-8C9F-6020A9A58B6B"),
            UUID.fromString("D8499B04-0E66-4726-AB29-64469D734E0D"),
            UUID.fromString("9F3D476D-C118-4544-8365-64846904B48E"),
            UUID.fromString("2AD3F246-FEE1-4E67-B886-69FD380BB150")
    };

    public GenericPoweredArmor(ArmorMaterial material, EquipmentSlot slot, int energyCapacity, EnergyTier tier, int cost, Settings settings, @Nullable String dischargedTranslationKey) {
        super(material, slot, settings.maxDamage(-1).maxCount(1));

        this.maxCharge = energyCapacity;
        this.cost = cost;
        this.tier = tier;
        this.dischargedTranslationKey = dischargedTranslationKey;
    }

    @Override
    @Environment(EnvType.CLIENT)
    public Text getName(ItemStack stack) {
        return new TranslatableText(this.getTranslationKey(stack));
    }


    public @Nullable String getSecondaryArmorTexture(ItemStack itemStack) {
        if (!(itemStack.getItem() instanceof GenericPoweredArmor))
            return null;

        GenericPoweredArmor genericPoweredArmor = (GenericPoweredArmor) itemStack.getItem();

        if (!genericPoweredArmor.isUsable(itemStack))
            return "discharged";

        return null;
    }

    @Override
    public String getTranslationKey(ItemStack stack) {
        if (isUsable(stack) || this.dischargedTranslationKey == null)
            return super.getTranslationKey(stack);

        return this.dischargedTranslationKey;
    }

    @Override
    public boolean isNotFull(ItemStack stack) {
        return getEnergy(stack) != getMaxEnergy(stack);
    }

    @Override
    public boolean isUsable(ItemStack stack) {
        return Energy.of(stack).getEnergy() >= this.cost;
    }

    @Override
    public double getEnergy(ItemStack stack) {
        return Energy.of(stack).getEnergy();
    }

    @Override
    public void setEnergy(ItemStack stack, double energy) {
        Energy.of(stack).set(energy);
    }

    @SuppressWarnings("EmptyMethod")
    @Override
    public boolean isFireproof() {
        return super.isFireproof();
    }

    @Override
    public float changeFov(float oldFOV, ItemStack stack, PlayerEntity playerEntity) {
        return oldFOV; // This is set to old so I can see what I'm doing while testing the armor.
    }

    @Override
    public void onRemoved(PlayerEntity playerEntity) {
        // Actions to perform when armor is removed
    }

    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
        super.inventoryTick(stack, world, entity, slot, selected);
    }

    @Override
    public void tickArmor(ItemStack stack, PlayerEntity playerEntity) {
        // Actions to perform every tick (only when armor is worn?)

//        switch (this.slot) {
//            case HEAD: break;
//            case CHEST: break;
//            case LEGS: break;
//            case FEET: break;
//            case MAINHAND: break;
//            case OFFHAND: break;
//        }
    }

    @Override
    public void getAttributeModifiers(EquipmentSlot equipmentSlot, ItemStack stack, Multimap<EntityAttribute, EntityAttributeModifier> builder) {
        // Modify Armor Attributes Dynamically

        builder.removeAll(EntityAttributes.GENERIC_MOVEMENT_SPEED);
        builder.removeAll(EntityAttributes.GENERIC_ARMOR); // Remove preset armor just so we can dynamically choose to break the protection if the battery dies.
        builder.removeAll(EntityAttributes.GENERIC_ARMOR_TOUGHNESS);

//        if (this.slot == EquipmentSlot.LEGS && equipmentSlot == EquipmentSlot.LEGS) {
//            if (isUsable(stack)) {
//                builder.put(EntityAttributes.GENERIC_MOVEMENT_SPEED, new EntityAttributeModifier(MODIFIERS[equipmentSlot.getEntitySlotId()], "Movement Speed", 0.15, EntityAttributeModifier.Operation.ADDITION));
//            }
//        }

        if (equipmentSlot == this.slot && isUsable(stack)) {
            builder.put(EntityAttributes.GENERIC_ARMOR, new EntityAttributeModifier(MODIFIERS[slot.getEntitySlotId()], "Armor modifier", getMaterial().getProtectionAmount(equipmentSlot), EntityAttributeModifier.Operation.ADDITION));
            builder.put(EntityAttributes.GENERIC_ARMOR_TOUGHNESS, new EntityAttributeModifier(MODIFIERS[slot.getEntitySlotId()], "Armor Toughness modifier", getMaterial().getToughness(), EntityAttributeModifier.Operation.ADDITION));
            builder.put(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE, new EntityAttributeModifier(MODIFIERS[slot.getEntitySlotId()], "Knockback modifier", getMaterial().getKnockbackResistance(), EntityAttributeModifier.Operation.ADDITION));
        }
    }

    @Override
    public double getMaxEnergy(ItemStack itemStack) {
        // RebornCore uses getMaxStoredPower() for their hud.
        // That makes it where the max energy cannot be set per ItemStack.
        // Once I implement dynamic max energy, my code will be able to get the dynamic max energy.

        return getMaxStoredPower();
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
        // Replace this with a dynamic durability bar checker.
        return 1 - ItemUtils.getPowerForDurabilityBar(stack);
    }

    @Override
    public boolean showDurability(ItemStack stack) {
        if (!(stack.getItem() instanceof EnergyHelper))
            return true;

        double currentEnergy = Energy.of(stack).getEnergy();
        double maxEnergy = getMaxEnergy(stack);

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

    // This only applies to the enchantment table, not anvils?
    @Override
    public boolean isEnchantable(ItemStack stack) {
        return true;
    }

    @Override
    public boolean canRepair(ItemStack toolStack, ItemStack repairStack) {
        return false;
    }

    @Environment(EnvType.CLIENT)
    @Override
    public void appendStacks(ItemGroup group, DefaultedList<ItemStack> itemList) {
        // Can be used to add multiple versions of items (e.g. a charged and discharged variant of each armor piece)
        if (!isIn(group)) {
            return;
        }

        ItemManager.initPoweredItems(this, itemList);
    }

    // Armor Protection Level
    @SuppressWarnings("EmptyMethod")
    @Override
    public int getProtection() {
        return super.getProtection();
    }

    // Armor Toughness Level
    @SuppressWarnings("EmptyMethod")
    @Override
    public float method_26353() {
        return super.method_26353();
    }

    public void addDamage(ItemStack stack, LivingEntity livingEntity, DamageSource damageSource, float damage) {
        if (!(stack.getItem() instanceof EnergyHelper))
            return;

        // Adds support for Unbreaking Enchants
        // This seems to occur more rarely than vanilla armor. I'm not sure if that's true though.
        int unbreakingLevel = EnchantmentHelper.getLevel(Enchantments.UNBREAKING, stack);
        boolean shouldPreventDamage = UnbreakingEnchantment.shouldPreventDamage(stack, unbreakingLevel, livingEntity.getRandom());

        if (shouldPreventDamage)
            return;

        ItemManager.useEnergy(livingEntity, stack, cost);
    }

    @Override
    public ItemStack onCraft(ItemStack oldStack, ItemStack newStack, CompoundTag tag) {
        return ItemManager.convertStackToEnergyItemStack(oldStack, newStack, tag);
    }

    @Environment(EnvType.CLIENT)
    @Override
    public void appendTooltip(ItemStack stack, @Nullable World worldIn, List<Text> tooltip, TooltipContext flagIn) {
        if (flagIn.isAdvanced())
            ItemManager.powerLevelTooltip(stack, tooltip);
    }

    @Override
    public boolean denyKillCommand(ItemStack itemStack, LivingEntity livingEntity) {
        return false;
    }

    @Override
    public boolean denyGeneralDamage(ItemStack itemStack, DamageSource damageSource, float amount, LivingEntity livingEntity) {
        return false;
    }
}
