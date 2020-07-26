package me.alexisevelyn.randomtech.utility.registryhelpers.client;

import ladysnake.satin.api.event.ShaderEffectRenderCallback;
import ladysnake.satin.api.managed.ManagedShaderEffect;
import ladysnake.satin.api.managed.ShaderEffectManager;
import me.alexisevelyn.randomtech.Main;
import me.alexisevelyn.randomtech.blocks.dusts.CobaltWire;
import me.alexisevelyn.randomtech.entities.renderers.CloudDemonRenderer;
import me.alexisevelyn.randomtech.entities.renderers.WizardRenderer;
import me.alexisevelyn.randomtech.modmenu.screens.MainScreen;
import me.alexisevelyn.randomtech.utility.registryhelpers.main.RegistryHelper;
import me.sargunvohra.mcmods.autoconfig1u.AutoConfig;
import me.sargunvohra.mcmods.autoconfig1u.serializer.GsonConfigSerializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.render.fluid.v1.FluidRenderHandler;
import net.fabricmc.fabric.api.client.render.fluid.v1.FluidRenderHandlerRegistry;
import net.fabricmc.fabric.api.client.rendereregistry.v1.EntityRendererRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import net.fabricmc.fabric.api.event.client.ClientSpriteRegistryCallback;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.fabricmc.fabric.api.resource.SimpleSynchronousResourceReloadListener;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.options.KeyBinding;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.texture.Sprite;
import net.minecraft.client.texture.SpriteAtlasTexture;
import net.minecraft.client.util.InputUtil;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.FluidState;
import net.minecraft.resource.ResourceManager;
import net.minecraft.resource.ResourceType;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.BlockRenderView;
import org.lwjgl.glfw.GLFW;

import java.util.function.Function;

public class ClientRegistryHelper {
    private static final ManagedShaderEffect GRAYSCALE_SHADER = ShaderEffectManager.getInstance()
            .manage(new Identifier(Main.MODID, "shaders/post/grayscale.json"));

    private static final boolean grayscaleEnabled = false;  // can be disabled whenever you want

    // To provide zoom capability when wearing powered helmet.
    public static final KeyBinding poweredHelmetZoom = new KeyBinding(Main.MODID + ".keybinds.powered_helmet_zoom", // The translation key of the keybinding's name
            InputUtil.Type.KEYSYM, // The type of the keybinding, KEYSYM for keyboard, MOUSE for mouse.
            GLFW.GLFW_KEY_C, // The keycode of the key
            Main.MODID + ".keybinds" // The translation key of the keybinding's category.
    );

    public void register() {
        // Register Configuration Screen for Mod Menu
        AutoConfig.register(MainScreen.class, GsonConfigSerializer::new);

        // Blocks
        blockSetup();

        // Fluids
        fluidSetup();

        // Entities
        entitySetup();

        // Keybinds
        keybindSetup();

        // Shader Setup
        shaderSetup();
    }

    private void entitySetup() {
        EntityRendererRegistry.INSTANCE.register(RegistryHelper.WIZARD, (dispatcher, context) -> new WizardRenderer(dispatcher));

        EntityRendererRegistry.INSTANCE.register(RegistryHelper.CLOUD_DEMON, (dispatcher, context) -> new CloudDemonRenderer(dispatcher));
    }

    private void blockSetup() {
        // Makes Glass Translucent
        BlockRenderLayerMap.INSTANCE.putBlock(RegistryHelper.CLEAR_GLASS, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(RegistryHelper.DARK_GLASS, RenderLayer.getTranslucent());
        BlockRenderLayerMap.INSTANCE.putBlock(RegistryHelper.INTANGIBLE_GLASS, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(RegistryHelper.DARK_INTANGIBLE_GLASS, RenderLayer.getTranslucent());
        BlockRenderLayerMap.INSTANCE.putBlock(RegistryHelper.INVERSE_DARK_INTANGIBLE_GLASS, RenderLayer.getTranslucent());
        BlockRenderLayerMap.INSTANCE.putBlock(RegistryHelper.INVERSE_INTANGIBLE_GLASS, RenderLayer.getCutout());

        BlockRenderLayerMap.INSTANCE.putBlock(RegistryHelper.POWERED_GLASS, RenderLayer.getTranslucent());

        // Cobalt Wiring
        BlockRenderLayerMap.INSTANCE.putBlock(RegistryHelper.COBALT_WIRE, RenderLayer.getTranslucent());

        ColorProviderRegistry.BLOCK.register(CobaltWire::getWireColor, RegistryHelper.COBALT_WIRE);
    }

    private void fluidSetup() {
        // Color is the HTML Color Code Formatted as 0xHTML-Color-Code. For example, #4CC248 becomes 0x4CC248
        setupFluidRendering(RegistryHelper.REDSTONE_FLUID, RegistryHelper.REDSTONE_FLUID_FLOWING, new Identifier(Main.MODID, "redstone"));
        setupFluidRendering(RegistryHelper.MAGIC_FLUID, RegistryHelper.MAGIC_FLUID_FLOWING, new Identifier(Main.MODID, "magic"));
        setupFluidRendering(RegistryHelper.EXPERIENCE_FLUID, RegistryHelper.EXPERIENCE_FLUID_FLOWING, new Identifier(Main.MODID, "experience"));
        setupFluidRendering(RegistryHelper.HONEY_FLUID, RegistryHelper.HONEY_FLUID_FLOWING, new Identifier(Main.MODID, "honey"));
        setupFluidRendering(RegistryHelper.COBALT_FLUID, RegistryHelper.COBALT_FLUID_FLOWING, new Identifier(Main.MODID, "cobalt"));

        // Can use RenderLayer.getTranslucent()
        BlockRenderLayerMap.INSTANCE.putFluids(RenderLayer.getSolid(), RegistryHelper.REDSTONE_FLUID, RegistryHelper.REDSTONE_FLUID_FLOWING);
        BlockRenderLayerMap.INSTANCE.putFluids(RenderLayer.getSolid(), RegistryHelper.MAGIC_FLUID, RegistryHelper.MAGIC_FLUID_FLOWING);
        BlockRenderLayerMap.INSTANCE.putFluids(RenderLayer.getSolid(), RegistryHelper.EXPERIENCE_FLUID, RegistryHelper.EXPERIENCE_FLUID_FLOWING);
        BlockRenderLayerMap.INSTANCE.putFluids(RenderLayer.getSolid(), RegistryHelper.HONEY_FLUID, RegistryHelper.HONEY_FLUID_FLOWING);
        BlockRenderLayerMap.INSTANCE.putFluids(RenderLayer.getSolid(), RegistryHelper.COBALT_FLUID, RegistryHelper.HONEY_FLUID_FLOWING);
    }

    // From Tutorial at https://fabricmc.net/wiki/tutorial:fluids
    private static void setupFluidRendering(final Fluid still, final Fluid flowing, final Identifier textureFluidId) {
        setupFluidRendering(still, flowing, textureFluidId, 0xFFFFFF);
    }

    // From Tutorial at https://fabricmc.net/wiki/tutorial:fluids
    private static void setupFluidRendering(final Fluid still, final Fluid flowing, final Identifier textureFluidId, @SuppressWarnings("SameParameterValue") final int color) {
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

    private void keybindSetup() {
        KeyBindingHelper.registerKeyBinding(poweredHelmetZoom);
    }

    private void shaderSetup() {
        ShaderEffectRenderCallback.EVENT.register(tickDelta -> {
            if (grayscaleEnabled) {
                GRAYSCALE_SHADER.render(tickDelta);
            }
        });
    }
}
