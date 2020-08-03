package me.alexisevelyn.randomtech.mixin;

import com.google.common.collect.Lists;
import me.alexisevelyn.randomtech.api.items.armor.generic.GenericPoweredArmor;
import me.alexisevelyn.randomtech.api.items.energy.EnergyHelper;
import me.alexisevelyn.randomtech.api.items.tools.generic.GenericPoweredTool;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.*;
import net.minecraft.entity.passive.PandaEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Packet;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;
import java.util.Map;

@SuppressWarnings({"UnusedMixin", "EmptyMethod"}) // The mixin is used, just is loaded by Fabric and not Sponge methods
@Mixin(ExperienceOrbEntity.class)
public abstract class PoweredMendingHelper extends Entity {
	// TODO: Fix this mixin!!!

	@Shadow public int orbAge;
	@Shadow private int health;
	@Shadow private int amount;
	@Shadow public int pickupDelay;

	@Shadow protected abstract int getMendingRepairAmount(int experienceAmount);
	@Shadow protected abstract int getMendingRepairCost(int repairAmount);

	@Shadow public abstract Packet<?> createSpawnPacket();
	@Shadow public abstract void writeCustomDataToTag(CompoundTag tag);
	@Shadow public abstract void readCustomDataFromTag(CompoundTag tag);
	@Shadow protected abstract void initDataTracker();

	public PoweredMendingHelper(EntityType<?> type, World world) {
		super(type, world);
	}

	@Inject(at = @At("HEAD"), method = "onPlayerCollision(Lnet/minecraft/entity/player/PlayerEntity;)V", cancellable = true)
	private void onPlayerCollision(PlayerEntity player, CallbackInfo info) {
		boolean isCancelled = false;

		if (this.world.isClient)
			return;

		if (this.pickupDelay != 0 || player.experiencePickUpDelay != 0)
			return;

		Map.Entry<EquipmentSlot, ItemStack> entry = PoweredMendingHelper.chooseEquipmentWith(player);

		if (entry != null) {
			ItemStack itemStack = entry.getValue();
			if (!itemStack.isEmpty() && itemStack.isDamaged() && itemStack.getItem() instanceof EnergyHelper) {
				info.cancel();
				isCancelled = true;

				EnergyHelper genericPoweredItem = (EnergyHelper) itemStack.getItem();

				int missingEnergy = Math.min(this.getMendingRepairAmount(this.amount), (int) (genericPoweredItem.getMaxStoredPower() - genericPoweredItem.getEnergy(itemStack)));
				this.amount -= this.getMendingRepairCost(missingEnergy);

				genericPoweredItem.setEnergy(itemStack, genericPoweredItem.getEnergy(itemStack) - missingEnergy);
			}
		}

		if (isCancelled) {
			player.experiencePickUpDelay = 2;
			player.sendPickup(this, 1);

			if (this.amount > 0) {
				player.addExperience(this.amount);
			}

			this.remove();
		}
	}

	private static Map.Entry<EquipmentSlot, ItemStack> chooseEquipmentWith(LivingEntity entity) {
		Map<EquipmentSlot, ItemStack> map = Enchantments.MENDING.getEquipment(entity);

		if (map.isEmpty())
			return null;

		List<Map.Entry<EquipmentSlot, ItemStack>> repairableItemStacks = Lists.newArrayList();

		for (Map.Entry<EquipmentSlot, ItemStack> entry : map.entrySet()) {
			ItemStack itemStack = entry.getValue();

			if (!itemStack.isEmpty() && EnchantmentHelper.getLevel(Enchantments.MENDING, itemStack) > 0 && isInNeedOfRepair(itemStack)) {
				repairableItemStacks.add(entry);
			}
		}

		return repairableItemStacks.isEmpty() ? null : repairableItemStacks.get(entity.getRandom().nextInt(repairableItemStacks.size()));
	}

	private static boolean isInNeedOfRepair(ItemStack itemStack) {
		if (!(itemStack.getItem() instanceof EnergyHelper))
			return false;

		EnergyHelper genericPoweredItem = (EnergyHelper) itemStack.getItem();
		return genericPoweredItem.isNotFull(itemStack);
	}
}