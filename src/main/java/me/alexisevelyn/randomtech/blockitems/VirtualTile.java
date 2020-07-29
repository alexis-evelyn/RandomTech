package me.alexisevelyn.randomtech.blockitems;

import me.alexisevelyn.randomtech.api.utilities.GenericBlockHelper;
import me.alexisevelyn.randomtech.blockentities.FuserBlockEntity;
import me.alexisevelyn.randomtech.blockentities.VirtualTileBlockEntity;
import me.alexisevelyn.randomtech.utility.Materials;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.api.tool.attribute.v1.FabricToolTags;
import net.minecraft.block.Block;
import net.minecraft.block.BlockEntityProvider;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.datafixer.NbtOps;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolMaterials;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Formatting;
import net.minecraft.util.dynamic.GlobalPos;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockRenderView;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.awt.*;
import java.util.List;
import java.util.Optional;
import java.util.function.ToIntFunction;

// This block was inspired by an accidental texture produced by my clear glass not rendering properly. :P

public class VirtualTile extends BlockItem {
    // TODO: Figure out how to dynamically choose the color based on the crafting recipe ingredients
    // TODO: Grab Block NBT Data when block mined and store in ItemStack

    public VirtualTile(Block block, Settings settings) {
        super(block, settings);
    }

    // For Block Form
    public static int getEdgeColor(BlockState state, BlockRenderView world, BlockPos pos, int tintIndex) {
        // TODO: Ensure data is synced between server and client.
        BlockEntity blockEntity = world.getBlockEntity(pos);

        if (!(blockEntity instanceof VirtualTileBlockEntity))
            return new Color(255, 255, 255).getRGB();

        // VirtualTileBlockEntity virtualTileBlockEntity = (VirtualTileBlockEntity) blockEntity;
        // return virtualTileBlockEntity.getColor().getRGB(); // Temporarily disabled until data syncing is added
        return new Color(57, 148, 25).getRGB();
    }

    // For Item Form
    public static int getEdgeColor(ItemStack itemStack, int tintIndex) {
        CompoundTag tag = itemStack.getTag();

        if (tag == null)
            return new Color(255, 255, 255).getRGB();

        CompoundTag blockEntityTag = tag.getCompound("BlockEntityTag");

        if (blockEntityTag == null)
            return new Color(255, 255, 255).getRGB();

        int red = blockEntityTag.getInt("red");
        int green = blockEntityTag.getInt("green");
        int blue = blockEntityTag.getInt("blue");

        return new Color(red, green, blue).getRGB();
    }

    @Environment(EnvType.CLIENT)
    @Override
    public void appendTooltip(ItemStack stack, @Nullable World worldIn, List<Text> tooltip, TooltipContext flagIn) {
        getEdgeColor(stack).ifPresent(color -> {
            Text colorText = new TranslatableText("message.randomtech.virtual_tile_color",
                    new LiteralText(String.valueOf(color.getRed()))
                            .formatted(Formatting.DARK_GREEN, Formatting.BOLD),
                    new LiteralText(String.valueOf(color.getGreen()))
                            .formatted(Formatting.DARK_GREEN, Formatting.BOLD),
                    new LiteralText(String.valueOf(color.getBlue()))
                            .formatted(Formatting.DARK_GREEN, Formatting.BOLD))
                    .formatted(Formatting.GOLD, Formatting.BOLD);

            tooltip.add(colorText);
        });
    }

    private Optional<Color> getEdgeColor(ItemStack stack) {
        if (!stack.hasTag() || stack.getTag() == null)
            return Optional.empty();

        CompoundTag blockEntityTag = stack.getTag().getCompound("BlockEntityTag");

        if (blockEntityTag == null || !blockEntityTag.contains("red") || !blockEntityTag.contains("green") || !blockEntityTag.contains("blue"))
            return Optional.empty();

        return Optional.of(new Color(blockEntityTag.getInt("red"), blockEntityTag.getInt("green"), blockEntityTag.getInt("blue")));
    }

    public static class VirtualTileBlock extends Block implements BlockEntityProvider {
        public VirtualTileBlock() {
            super(FabricBlockSettings
                    .of(Materials.TILE_MATERIAL)
                    .sounds(BlockSoundGroup.STONE)
                    .breakByTool(FabricToolTags.PICKAXES, ToolMaterials.WOOD.getMiningLevel())
                    .requiresTool()
                    .allowsSpawning(GenericBlockHelper::never)
                    .solidBlock(GenericBlockHelper::always)
                    .suffocates(GenericBlockHelper::always)
                    .blockVision(GenericBlockHelper::always)
                    .strength(1.8F, 1.8F)
                    .lightLevel(getLightLevel()));
        }

        public static ToIntFunction<BlockState> getLightLevel() {
            return (state) -> 10; // 7?
        }

        @Override
        public BlockEntity createBlockEntity(BlockView worldIn) {
            // TODO: Retrieve color from ItemStack
            return new VirtualTileBlockEntity(new Color(255, 0, 0));
        }
    }
}
