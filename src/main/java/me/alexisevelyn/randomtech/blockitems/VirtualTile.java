package me.alexisevelyn.randomtech.blockitems;

import me.alexisevelyn.randomtech.api.utilities.CalculationHelper;
import me.alexisevelyn.randomtech.api.utilities.GenericBlockHelper;
import me.alexisevelyn.randomtech.blockentities.VirtualTileBlockEntity;
import me.alexisevelyn.randomtech.utility.MaterialsHelper;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.api.tool.attribute.v1.FabricToolTags;
import net.minecraft.block.Block;
import net.minecraft.block.BlockEntityProvider;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolMaterials;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.stat.Stats;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Formatting;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockRenderView;
import net.minecraft.world.BlockView;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.awt.*;
import java.util.List;
import java.util.Optional;
import java.util.function.ToIntFunction;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

// This block was inspired by an accidental texture produced by my clear glass not rendering properly. :P
// I may replace the current recipe system with something similar to ArmorDyeRecipe

/**
 * The type Virtual tile.
 */
public class VirtualTile extends BlockItem {
    public static final Color defaultColor = Color.WHITE;
    public static final Color defaultColor2 = Color.BLACK;

    // public static final Pattern NBT_CRAFTING_REGEX = Pattern.compile("^\\$\\d+#i$");
    public static final Pattern INTEGER_REGEX = Pattern.compile("\\d+");

    /**
     * Instantiates a new Virtual tile.
     *
     * @param block    the block
     * @param settings the settings
     */
    public VirtualTile(Block block, Settings settings) {
        super(block, settings);
    }

    /**
     * Post placement boolean.
     *
     * @param pos    the pos
     * @param world  the world
     * @param player the player
     * @param stack  the stack
     * @param state  the state
     * @return the boolean
     */
    // Used to set the color client side before the server sends the color. No visual difference between this and server color if done correctly.
    @Override
    protected boolean postPlacement(BlockPos pos, World world, @Nullable PlayerEntity player, ItemStack stack, BlockState state) {
        boolean placed = super.postPlacement(pos, world, player, stack, state);

        Color initialColor = parseColorFromItemStack(stack);

        if (initialColor == null)
            initialColor = defaultColor;

        BlockEntity blockEntity = world.getBlockEntity(pos);
        if (!(blockEntity instanceof VirtualTileBlockEntity))
            return placed;

        VirtualTileBlockEntity virtualTileBlockEntity = (VirtualTileBlockEntity) blockEntity;
        virtualTileBlockEntity.setColor(initialColor);

        return placed;
    }

    /**
     * Gets edge color.
     *
     * @param state     the state
     * @param world     the world
     * @param pos       the pos
     * @param tintIndex the tint index
     * @return the edge color
     */
    // For Block Form
    public static int getEdgeColor(BlockState state, BlockRenderView world, BlockPos pos, int tintIndex) {
        BlockEntity blockEntity = world.getBlockEntity(pos);

        if (!(blockEntity instanceof VirtualTileBlockEntity) )
            return determineFallbackColor(world, pos); // Sprint Particles Trigger This Cause Block Entity is Null

        VirtualTileBlockEntity virtualTileBlockEntity = (VirtualTileBlockEntity) blockEntity;

        if (virtualTileBlockEntity.getColor() == null)
            return defaultColor2.getRGB(); // The color is black so the hack of a rerender looks less hacky.

        return virtualTileBlockEntity.getColor().getRGB();
    }

    public static int determineFallbackColor(BlockRenderView world, BlockPos pos) {
        if (!(world instanceof ClientWorld))
            return defaultColor.getRGB();

        // Defaults to no color (same value as if color provider didn't exist) if ran by client
        return -1;
    }

    /**
     * Gets edge color.
     *
     * @param itemStack the item stack
     * @param tintIndex the tint index
     * @return the edge color
     */
    // For Item Form
    public static int getEdgeColor(ItemStack itemStack, int tintIndex) {
        CompoundTag tag = itemStack.getTag();

        if (tag == null)
            return defaultColor.getRGB(); // Hywla Triggers This as NBT Data is Null

        Color color = parseColorFromItemStack(itemStack);

        if (color == null)
            return defaultColor.getRGB();

        return color.getRGB();
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

    /**
     * Gets edge color.
     *
     * @param stack the stack
     * @return the edge color
     */
    private Optional<Color> getEdgeColor(ItemStack stack) {
        if (!stack.hasTag() || stack.getTag() == null)
            return Optional.empty();

        Color color = parseColorFromItemStack(stack);

        if (color == null)
            return Optional.empty();

        return Optional.of(color);
    }

    /**
     * Parse color from item stack color.
     *
     * @param itemStack the item stack
     * @return the color
     */
    @Nullable
    public static Color parseColorFromItemStack(ItemStack itemStack) {
        CompoundTag blockEntityTag = itemStack.getOrCreateTag().getCompound("BlockEntityTag");

        if (blockEntityTag == null || !blockEntityTag.contains("red") || !blockEntityTag.contains("green") || !blockEntityTag.contains("blue"))
            return null;

        int red = blockEntityTag.getInt("red");
        int green = blockEntityTag.getInt("green");
        int blue = blockEntityTag.getInt("blue");

        Matcher redMatcher = INTEGER_REGEX.matcher(blockEntityTag.getString("red"));
        Matcher greenMatcher = INTEGER_REGEX.matcher(blockEntityTag.getString("green"));
        Matcher blueMatcher = INTEGER_REGEX.matcher(blockEntityTag.getString("blue"));

        // This is to parse out the integer from the NBT Crafting Recipes
        if (redMatcher.find())
            red = Integer.parseInt(redMatcher.group());

        if (greenMatcher.find())
            green = Integer.parseInt(greenMatcher.group());

        if (blueMatcher.find())
            blue = Integer.parseInt(blueMatcher.group());

        return new Color(red, green, blue);
    }

    /**
     * The type Virtual tile block.
     */
    public static class VirtualTileBlock extends Block implements BlockEntityProvider {
        private static BooleanProperty DUMMY = BooleanProperty.of("dummy"); // Minecraft updates the rendering of a block when the blockstate is changed. This forces re-rendering of the block.

        // Take a look at `Lnet/minecraft/entity/Entity;spawnSprintingParticles()V` for fixing sprinting particles

        /**
         * Instantiates a new Virtual tile block.
         */
        public VirtualTileBlock() {
            super(FabricBlockSettings
                    .of(MaterialsHelper.TILE_MATERIAL)
                    .sounds(BlockSoundGroup.STONE)
                    .breakByTool(FabricToolTags.PICKAXES, ToolMaterials.WOOD.getMiningLevel())
                    .requiresTool()
                    .allowsSpawning(GenericBlockHelper::never)
                    .solidBlock(GenericBlockHelper::always)
                    .suffocates(GenericBlockHelper::always)
                    .blockVision(GenericBlockHelper::always)
                    .strength(1.8F, 1.8F)
                    .lightLevel(getLightLevel()));

            this.setDefaultState(this.getStateManager().getDefaultState().with(DUMMY, false));
        }

//        @Override
//        public void onBroken(WorldAccess world, BlockPos pos, BlockState state) {
//            super.onBroken(world, pos, state);
//        }

        /**
         * After break.
         *
         * @param world       the world
         * @param player      the player
         * @param pos         the pos
         * @param state       the state
         * @param blockEntity the block entity
         * @param stack       the stack
         */
        @Override
        public void afterBreak(World world, PlayerEntity player, BlockPos pos, BlockState state, @Nullable BlockEntity blockEntity, ItemStack stack) {
            player.incrementStat(Stats.MINED.getOrCreateStat(this));
            player.addExhaustion(0.005F);
            dropNBTStacks(state, world, pos, blockEntity, player, stack);
        }

        /**
         * Drop nbt stacks.
         *
         * @param state       the state
         * @param world       the world
         * @param pos         the pos
         * @param blockEntity the block entity
         * @param entity      the entity
         * @param stack       the stack
         */
        public static void dropNBTStacks(BlockState state, World world, BlockPos pos, @Nullable BlockEntity blockEntity, Entity entity, ItemStack stack) {
            if (world instanceof ServerWorld) {
                getDroppedStacks(state, (ServerWorld) world, pos, blockEntity, entity, stack).forEach((itemStack) -> dropNBTStack(world, pos, blockEntity, itemStack));
                state.onStacksDropped((ServerWorld) world, pos, stack);
            }
        }

        /**
         * Drop nbt stack.
         *
         * @param world       the world
         * @param pos         the pos
         * @param blockEntity the block entity
         * @param stack       the stack
         */
        // This allows for dropping custom nbt tagged items when Virtual Tile is mined.
        public static void dropNBTStack(World world, BlockPos pos, BlockEntity blockEntity, ItemStack stack) {
            if (!world.isClient && !stack.isEmpty() && blockEntity instanceof VirtualTileBlockEntity && world.getGameRules().getBoolean(GameRules.DO_TILE_DROPS)) {
                float generalOffset = 0.5F;
                double xOffset = (world.random.nextFloat() * generalOffset) + 0.25D;
                double yOffset = (world.random.nextFloat() * generalOffset) + 0.25D;
                double zOffset = (world.random.nextFloat() * generalOffset) + 0.25D;

                VirtualTileBlockEntity virtualTileBlockEntity = (VirtualTileBlockEntity) blockEntity;
                CompoundTag rootStackTag = stack.getOrCreateTag();
                Color blockEntityColor = virtualTileBlockEntity.getColor();
                ItemEntity itemEntity;

                if (rootStackTag != null && blockEntityColor != null) {
                    CompoundTag blockEntityTag = rootStackTag.getCompound("BlockEntityTag");

                    blockEntityTag.putInt("red", blockEntityColor.getRed());
                    blockEntityTag.putInt("green", blockEntityColor.getGreen());
                    blockEntityTag.putInt("blue", blockEntityColor.getBlue());

                    // rootStackTag.put("BlockEntityTag", blockEntityTag);
                    stack.putSubTag("BlockEntityTag", blockEntityTag);
                }

                itemEntity = new ItemEntity(world, pos.getX() + xOffset, pos.getY() + yOffset, pos.getZ() + zOffset, stack);
                itemEntity.setToDefaultPickupDelay();

                world.spawnEntity(itemEntity);
            }
        }

//        @Deprecated
//        @Override
//        public void onStacksDropped(BlockState state, World world, BlockPos pos, ItemStack stack) {
//            // Do Nothing For Now!!!
//            super.onStacksDropped(state, world, pos, stack);
//        }

        /**
         * Update block for render.
         *
         * @param world the world
         * @param pos   the pos
         */
        // This is a hack and I'm not happy with it.
        public void updateBlockForRender(World world, BlockPos pos) {
            boolean dummy = world.getBlockState(pos).get(DUMMY);
            BlockState state = world.getBlockState(pos).with(DUMMY, !dummy);
            world.setBlockState(pos, state, 3);
        }

        /**
         * Gets light level.
         *
         * @return the light level
         */
        public static ToIntFunction<BlockState> getLightLevel() {
            // TODO: Adjust light level based on color brightness.
            return (state) -> {
                // I'm not sure how to grab the block entity from only the blockstate.
                // If it's even possible to track down the block entity without the world and position.

                // Light Gray - #AAAAAA - (170, 170, 170)
                // Calculated to Produce Light Level 10 At Source.

                // Lighter Gray - #BEBEBE - (190, 190, 190)
                // Calculated to Produce Light Level 11 At Source.

                // Until we can grab the color of the block, we are just hardcoding the light level
                // Once we gain the ability to grab the color, we just output the rgb values into these three variables and profit!!!!
                float red = 190;
                float green = 190;
                float blue = 190;

                // Formula pulled from https://stackoverflow.com/a/596241/6828099
                // Minimum 0; Maximum 255
                double brightness = (red + red + blue + green + green + green)/6;

                int maxLightLevel = 15;
                int minLightLevel = 0;
                int maxBrightness = 255;

                return (int) Math.floor(CalculationHelper.proportionCalculator(brightness, 0, maxBrightness, minLightLevel, maxLightLevel)); // (brightness * maxLightLevel) / maxBrightness) + minLightLevel;
            };
        }

        /**
         * Create block entity block entity.
         *
         * @param worldIn the world in
         * @return the block entity
         */
        @Override
        public BlockEntity createBlockEntity(BlockView worldIn) {
            return new VirtualTileBlockEntity();
        }

        /**
         * Append properties.
         *
         * @param builder the builder
         */
        @Override
        protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
            DUMMY = BooleanProperty.of("dummy");
            builder.add(DUMMY);
        }
    }
}
