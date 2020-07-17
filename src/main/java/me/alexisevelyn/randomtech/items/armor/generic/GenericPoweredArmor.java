package me.alexisevelyn.randomtech.items.armor.generic;

import com.google.common.collect.Multimap;
import me.alexisevelyn.randomtech.utility.ItemManager;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.collection.DefaultedList;
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

// TODO: Make enchants useless if item is broken.
public class GenericPoweredArmor extends ArmorItem implements ItemDurabilityExtensions, ItemStackModifiers, ArmorTickable, ArmorRemoveHandler, ArmorFovHandler, EnergyHolder {
    private final int maxCharge;
    private final int cost;

    private final EnergyTier tier;
    private final String dischargedTranslationKey;

    public GenericPoweredArmor(ArmorMaterial material, EquipmentSlot slot, int energyCapacity, EnergyTier tier, int cost, Settings settings) {
        this(material, slot, energyCapacity, tier, cost, settings, null);
    }

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

    @Override
    public String getTranslationKey(ItemStack stack) {
        if (isUsable(stack) || this.dischargedTranslationKey == null)
            return super.getTranslationKey(stack);

        return this.dischargedTranslationKey;
    }

    public boolean isUsable(ItemStack stack) {
        return Energy.of(stack).getEnergy() >= this.cost;
    }

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
    public void tickArmor(ItemStack stack, PlayerEntity playerEntity) {
        // Actions to perform every tick

        switch (this.slot) {
            case HEAD: break;
            case CHEST: break;
            case LEGS: break;
            case FEET: break;
            case MAINHAND: break;
            case OFFHAND: break;
        }
    }

    @Override
    public void getAttributeModifiers(EquipmentSlot slot, ItemStack stack, Multimap<EntityAttribute, EntityAttributeModifier> builder) {
        // Modify Armor Attributes Dynamically
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
        if (!(stack.getItem() instanceof GenericPoweredArmor))
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
}
