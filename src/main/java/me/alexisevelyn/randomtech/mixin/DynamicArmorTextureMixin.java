package me.alexisevelyn.randomtech.mixin;

import me.alexisevelyn.randomtech.api.items.armor.generic.GenericPoweredArmor;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.feature.ArmorFeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * The type Dynamic armor texture mixin.
 *
 * @param <T> the type parameter
 * @param <M> the type parameter
 * @param <A> the type parameter
 */
@SuppressWarnings("UnusedMixin") // The mixin is used, just is loaded by Fabric and not Sponge methods
@Mixin(ArmorFeatureRenderer.class)
public abstract class DynamicArmorTextureMixin<T extends LivingEntity, M extends BipedEntityModel<T>, A extends BipedEntityModel<T>> extends FeatureRenderer<T, M> {
	/**
	 * Instantiates a new Dynamic armor texture mixin.
	 *
	 * @param context the context
	 */
	@SuppressWarnings("unused")
	public DynamicArmorTextureMixin(FeatureRendererContext<T, M> context) {
		super(context);
	}

	/**
	 * Uses second layer boolean.
	 *
	 * @param equipmentSlot the equipment slot
	 * @return the boolean
	 */
	@Shadow protected abstract boolean usesSecondLayer(EquipmentSlot equipmentSlot);

	/**
	 * Sets visible.
	 *
	 * @param bipedModel the biped model
	 * @param slot       the slot
	 */
	@Shadow protected abstract void setVisible(BipedEntityModel<?> bipedModel, EquipmentSlot slot);

	/**
	 * Render armor parts.
	 *
	 * @param matrixStack            the matrix stack
	 * @param vertexConsumerProvider the vertex consumer provider
	 * @param i                      the
	 * @param armorItem              the armor item
	 * @param bl                     the bl
	 * @param bipedEntityModel       the biped entity model
	 * @param bl2                    the bl 2
	 * @param f                      the f
	 * @param g                      the g
	 * @param h                      the h
	 * @param string                 the string
	 */
	@SuppressWarnings("SameParameterValue") @Shadow protected abstract void renderArmorParts(MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i, ArmorItem armorItem, boolean bl, BipedEntityModel<?> bipedEntityModel, boolean bl2, float f, float g, float h, @Nullable String string);
	// @Shadow public abstract M getContextModel();

	/**
	 * Render armor.
	 *
	 * @param matrices         the matrices
	 * @param vertexConsumers  the vertex consumers
	 * @param livingEntity     the living entity
	 * @param equipmentSlot    the equipment slot
	 * @param i                the
	 * @param bipedEntityModel the biped entity model
	 * @param info             the info
	 */
	@Inject(at = @At("INVOKE"), method = "renderArmor(Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;Lnet/minecraft/entity/LivingEntity;Lnet/minecraft/entity/EquipmentSlot;ILnet/minecraft/client/render/entity/model/BipedEntityModel;)V", cancellable = true)
	private void renderArmor(MatrixStack matrices, VertexConsumerProvider vertexConsumers, T livingEntity, EquipmentSlot equipmentSlot, int i, A bipedEntityModel, CallbackInfo info) {
		ItemStack itemStack = livingEntity.getEquippedStack(equipmentSlot);
		Item item = itemStack.getItem();

		if (item instanceof GenericPoweredArmor) {
			GenericPoweredArmor genericPoweredArmor = (GenericPoweredArmor) item;

			if (genericPoweredArmor.getSlotType() != equipmentSlot)
				return;

			this.getContextModel().setAttributes(bipedEntityModel);
			this.setVisible(bipedEntityModel, equipmentSlot);
			boolean usesSecondLayer = this.usesSecondLayer(equipmentSlot);
			boolean hasGlint = itemStack.hasGlint();

			this.renderArmorParts(matrices, vertexConsumers, i, (ArmorItem) item, hasGlint, bipedEntityModel, usesSecondLayer, 1.0F, 1.0F, 1.0F, genericPoweredArmor.getSecondaryArmorTexture(itemStack));

			info.cancel();
		}
	}
}
