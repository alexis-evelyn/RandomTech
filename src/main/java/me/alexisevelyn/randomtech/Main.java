package me.alexisevelyn.randomtech;

import me.alexisevelyn.randomtech.utility.PostRegistryHelper;
import me.alexisevelyn.randomtech.utility.RegistryHelper;
import net.fabricmc.api.ModInitializer;

/* TODO: Ordered by importance and logical steps
 *
 * Fix texture for Cobalt Wire
 *
 * Make it possible to not output item in some recipes in Fuser
 * Fix Cobalt Recipes (to be balanced)
 * Fix output recipes for lava production
 *
 * Fix REI Recipes to show output item if exists
 * Work on Fuser Gui
 * Work on entities
 *
 * Fix cobalt wire to not connect to Redstone wire
 * Consider how to obtain Death Ingot
 *
 * Create mixin for dynamic armor texture based on ItemStack properties
 * - net.minecraft.client.render.entity.feature.ArmorFeatureRenderer#getArmorTexture(ArmorItem, boolean, @Nullable String)
 * - net.minecraft.client.render.entity.feature.ArmorFeatureRenderer#renderArmor(MatrixStack, VertexConsumerProvider, T livingEntity, EquipmentSlot, int, A bipedEntityModel)
 * * Most likely, we'll have to inject at head of renderArmor and check if it's an instance of GenericPoweredArmor.
 * *  That's the last method that has the ability to get an item stack for the armor.
 */

// This runs on both the server and the client
public class Main implements ModInitializer {
	public static final String MODID = "randomtech";

	protected PostRegistryHelper postRegistryHelper = new PostRegistryHelper();
	protected RegistryHelper registryHelper = new RegistryHelper();

	@Override
	public void onInitialize() {
		// Client and Server Side!!!

		registryHelper.register();
		postRegistryHelper.postRegister();
	}
}
