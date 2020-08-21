package me.alexisevelyn.randomtech.mixin.enchanting;

import com.google.common.collect.Lists;
import me.alexisevelyn.randomtech.api.items.energy.EnergyHelper;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.*;
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

// This mixin makes mending useful for my tools and armor.

/**
 * The type Powered mending helper.
 */
@SuppressWarnings("UnusedMixin") // The mixin is used, just is loaded by Fabric and not Sponge methods
@Mixin(ExperienceOrbEntity.class)
public abstract class PoweredMendingHelper extends Entity {
//	@Shadow public int orbAge;
//	@Shadow private int health;
	@Shadow private int amount;
	@Shadow public int pickupDelay;

	/**
	 * Gets mending repair amount.
	 *
	 * @param experienceAmount the experience amount
	 * @return the mending repair amount
	 */
	@Shadow protected abstract int getMendingRepairAmount(int experienceAmount);

	/**
	 * Gets mending repair cost.
	 *
	 * @param repairAmount the repair amount
	 * @return the mending repair cost
	 */
	@Shadow protected abstract int getMendingRepairCost(int repairAmount);

	/**
	 * Create spawn packet packet.
	 *
	 * @return the packet
	 */
	@Shadow public abstract Packet<?> createSpawnPacket();

	/**
	 * Write custom data to tag.
	 *
	 * @param tag the tag
	 */
	@Shadow public abstract void writeCustomDataToTag(CompoundTag tag);

	/**
	 * Read custom data from tag.
	 *
	 * @param tag the tag
	 */
	@Shadow public abstract void readCustomDataFromTag(CompoundTag tag);

	/**
	 * Init data tracker.
	 */
	@Shadow protected abstract void initDataTracker();

	/**
	 * Instantiates a new Powered mending helper.
	 *
	 * @param type  the type
	 * @param world the world
	 */
	public PoweredMendingHelper(EntityType<?> type, World world) {
		super(type, world);
	}

	/**
	 * On player collision.
	 *
	 * @param player the player
	 * @param info   the info
	 */
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
			if (!itemStack.isEmpty() && itemStack.getItem() instanceof EnergyHelper) {
				isCancelled = true;

				EnergyHelper genericPoweredItem = (EnergyHelper) itemStack.getItem();

				int missingEnergy = Math.min(this.getMendingRepairAmount(this.amount), (int) (genericPoweredItem.getMaxEnergy(itemStack) - genericPoweredItem.getEnergy(itemStack)));
				this.amount -= this.getMendingRepairCost(missingEnergy);

				genericPoweredItem.setEnergy(itemStack, genericPoweredItem.getEnergy(itemStack) + missingEnergy);
			}
		}

		if (isCancelled) {
			player.experiencePickUpDelay = 2;
			player.sendPickup(this, 1);

			if (this.amount > 0) {
				player.addExperience(this.amount);
			}

			this.remove();
			info.cancel();
		}
	}

	/**
	 * Choose equipment with map . entry.
	 *
	 * @param entity the entity
	 * @return the map . entry
	 */
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

	/**
	 * Is in need of repair boolean.
	 *
	 * @param itemStack the item stack
	 * @return the boolean
	 */
	private static boolean isInNeedOfRepair(ItemStack itemStack) {
		if (!(itemStack.getItem() instanceof EnergyHelper))
			return false;

		EnergyHelper genericPoweredItem = (EnergyHelper) itemStack.getItem();
		return genericPoweredItem.isNotFull(itemStack);
	}
}