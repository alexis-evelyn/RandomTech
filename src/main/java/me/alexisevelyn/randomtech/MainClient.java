package me.alexisevelyn.randomtech;

import me.alexisevelyn.randomtech.modmenu.screens.MainScreen;
import me.alexisevelyn.randomtech.utility.RegistryHelper;
import me.sargunvohra.mcmods.autoconfig1u.AutoConfig;
import me.sargunvohra.mcmods.autoconfig1u.serializer.GsonConfigSerializer;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.render.fluid.v1.FluidRenderHandler;
import net.fabricmc.fabric.api.client.render.fluid.v1.FluidRenderHandlerRegistry;
import net.fabricmc.fabric.api.event.client.ClientSpriteRegistryCallback;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.fabricmc.fabric.api.resource.SimpleSynchronousResourceReloadListener;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.texture.Sprite;
import net.minecraft.client.texture.SpriteAtlasTexture;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.FluidState;
import net.minecraft.resource.ResourceManager;
import net.minecraft.resource.ResourceType;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.BlockRenderView;

import java.util.function.Function;

public class MainClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		// Client Side Only!!!

		// Register Configuration Screen for Mod Menu
		AutoConfig.register(MainScreen.class, GsonConfigSerializer::new);

		// Color is the HTML Color Code Formatted as 0xHTML-Color-Code. For example, #4CC248 becomes 0x4CC248
		setupFluidRendering(RegistryHelper.REDSTONE_FLUID, RegistryHelper.REDSTONE_FLUID_FLOWING, new Identifier("randomtech", "redstone"), 0xFFFFFF);
		setupFluidRendering(RegistryHelper.MAGIC_FLUID, RegistryHelper.MAGIC_FLUID_FLOWING, new Identifier("randomtech", "magic"), 0xFFFFFF);
		setupFluidRendering(RegistryHelper.EXPERIENCE_FLUID, RegistryHelper.EXPERIENCE_FLUID_FLOWING, new Identifier("randomtech", "experience"), 0xFFFFFF);

		// Can use RenderLayer.getTranslucent()
		BlockRenderLayerMap.INSTANCE.putFluids(RenderLayer.getSolid(), RegistryHelper.REDSTONE_FLUID, RegistryHelper.REDSTONE_FLUID_FLOWING);
		BlockRenderLayerMap.INSTANCE.putFluids(RenderLayer.getSolid(), RegistryHelper.MAGIC_FLUID, RegistryHelper.MAGIC_FLUID_FLOWING);
		BlockRenderLayerMap.INSTANCE.putFluids(RenderLayer.getSolid(), RegistryHelper.EXPERIENCE_FLUID, RegistryHelper.EXPERIENCE_FLUID_FLOWING);
	}

	// From Tutorial at https://fabricmc.net/wiki/tutorial:fluids
	public static void setupFluidRendering(final Fluid still, final Fluid flowing, final Identifier textureFluidId, final int color) {
		final Identifier stillSpriteId = new Identifier(textureFluidId.getNamespace(), "block/liquids/" + textureFluidId.getPath() + "/" + textureFluidId.getPath() + "_still");
		final Identifier flowingSpriteId = new Identifier(textureFluidId.getNamespace(), "block/liquids/" + textureFluidId.getPath() + "/" + textureFluidId.getPath() + "_flow");

		// If they're not already present, add the sprites to the block atlas
		ClientSpriteRegistryCallback.event(SpriteAtlasTexture.BLOCK_ATLAS_TEX).register((atlasTexture, registry) -> {
			registry.register(stillSpriteId);
			registry.register(flowingSpriteId);
		});

		final Identifier fluidId = Registry.FLUID.getId(still);
		final Identifier listenerId = new Identifier(fluidId.getNamespace(), fluidId.getPath() + "_reload_listener");

		final Sprite[] fluidSprites = { null, null };

		ResourceManagerHelper.get(ResourceType.CLIENT_RESOURCES).registerReloadListener(new SimpleSynchronousResourceReloadListener() {
			@Override
			public Identifier getFabricId() {
				return listenerId;
			}

			/**
			 * Get the sprites from the block atlas when resources are reloaded
			 */
			@Override
			public void apply(ResourceManager resourceManager) {
				final Function<Identifier, Sprite> atlas = MinecraftClient.getInstance().getSpriteAtlas(SpriteAtlasTexture.BLOCK_ATLAS_TEX);
				fluidSprites[0] = atlas.apply(stillSpriteId);
				fluidSprites[1] = atlas.apply(flowingSpriteId);
			}
		});

		// The FluidRenderer gets the sprites and color from a FluidRenderHandler during rendering
		final FluidRenderHandler renderHandler = new FluidRenderHandler() {
			@Override
			public Sprite[] getFluidSprites(BlockRenderView view, BlockPos pos, FluidState state) {
				return fluidSprites;
			}

			@Override
			public int getFluidColor(BlockRenderView view, BlockPos pos, FluidState state) {
				return color;
			}
		};

		FluidRenderHandlerRegistry.INSTANCE.register(still, renderHandler);
		FluidRenderHandlerRegistry.INSTANCE.register(flowing, renderHandler);
	}
}
