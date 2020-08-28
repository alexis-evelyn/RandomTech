package me.alexisevelyn.randomtech.blocks;

import me.alexisevelyn.randomtech.api.blocks.machines.PowerAcceptorBlock;
import me.alexisevelyn.randomtech.blockentities.TeleporterBlockEntity;
import me.alexisevelyn.randomtech.utility.Materials;
import me.alexisevelyn.randomtech.utility.registryhelpers.main.RegistryHelper;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.api.tool.attribute.v1.FabricToolTags;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.enums.SlabType;
import net.minecraft.item.ToolMaterials;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.IntProperty;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import reborncore.api.blockentity.IMachineGuiHandler;

import java.util.function.ToIntFunction;

/**
 * The type Teleporter block.
 */
public class TeleporterBlock extends PowerAcceptorBlock {
    public static final BooleanProperty ACTIVE = BooleanProperty.of("active");
    public static final IntProperty ENERGY = IntProperty.of("energy", 0, 15);

    private final VoxelShape OUTLINED_SHAPE;
    private final VoxelShape VISUAL_SHAPE;
    private final VoxelShape COLLISION_SHAPE;
    private final VoxelShape[] CULLING_SHAPES;

    /**
     * Instantiates a new Teleporter block.
     */
    public TeleporterBlock() {
        super(FabricBlockSettings
                .of(Materials.MACHINE_MATERIAL)
                .breakByHand(false).requiresTool()
                .breakByTool(FabricToolTags.PICKAXES, ToolMaterials.IRON.getMiningLevel())
                .sounds(BlockSoundGroup.NETHERITE)
                .strength(2.0F, 0.2F)
                .nonOpaque()
                .lightLevel(getLightLevel()), true);

        setDefaultState(getStateManager()
                .getDefaultState()
                .with(FACING, Direction.NORTH)
                .with(ACTIVE, false)
                .with(ENERGY, 0));

        // Custom Shapes
        OUTLINED_SHAPE = VoxelShapes.cuboid(0, 0, 0, 1, 5.0/16, 1);
        VISUAL_SHAPE = VoxelShapes.cuboid(0, 0, 0, 1, 5.0/16, 1);
        COLLISION_SHAPE = VoxelShapes.cuboid(0, 0, 0, 1, 5.0/16, 1);

        // CULLING_SHAPES = VoxelShapes.fullCube();
        CULLING_SHAPES = null;
    }

    /**
     * Gets light level.
     *
     * @return the light level
     */
    public static ToIntFunction<BlockState> getLightLevel() {
        return (state) -> state.get(ACTIVE) ? state.get(ENERGY) : 0;
    }

    /**
     * Append properties.
     *
     * @param builder the builder
     */
    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(FACING, ACTIVE, ENERGY);
    }

    /**
     * Create block entity block entity.
     *
     * @param worldIn the world in
     * @return the block entity
     */
    @Override
    public BlockEntity createBlockEntity(BlockView worldIn) {
        return new TeleporterBlockEntity();
    }

    /**
     * Gets gui.
     *
     * @return the gui
     */
    @Override
    public IMachineGuiHandler getGui() {
        return RegistryHelper.teleporterGuiHandler;
    }

    /**
     *
     * @param state
     * @return
     */
    public boolean hasSidedTransparency(BlockState state) {
        return true;
    }

    /**
     * Gets outline shape.
     *
     * @param state   the state
     * @param world   the world
     * @param pos     the pos
     * @param context the context
     * @return the outline shape
     */
    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        if (this.OUTLINED_SHAPE == null)
            return super.getOutlineShape(state, world, pos, context);

        return this.OUTLINED_SHAPE;
    }

    /**
     * Gets culling shape.
     *
     * @param state the state
     * @param world the world
     * @param pos   the pos
     * @return the culling shape
     */
    @Override
    public VoxelShape getCullingShape(BlockState state, BlockView world, BlockPos pos) {
        if (this.CULLING_SHAPES == null)
            return super.getOutlineShape(state, world, pos, ShapeContext.absent());

        return this.CULLING_SHAPES[this.getShapeIndex(state)];
    }

    /**
     * Gets visual shape.
     *
     * @param state   the state
     * @param world   the world
     * @param pos     the pos
     * @param context the context
     * @return the visual shape
     */
    @Override
    public VoxelShape getVisualShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        if (this.VISUAL_SHAPE == null)
            return super.getVisualShape(state, world, pos, context);

        return this.VISUAL_SHAPE;
    }

    /**
     * Gets collision shape.
     *
     * @param state   the state
     * @param world   the world
     * @param pos     the pos
     * @param context the context
     * @return the collision shape
     */
    @Override
    public VoxelShape getCollisionShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        if (this.COLLISION_SHAPE == null)
            return super.getCollisionShape(state, world, pos, context);

        return this.COLLISION_SHAPE;
    }

    /**
     * Gets shape index.
     *
     * @param state the state
     * @return the shape index
     */
    public int getShapeIndex(BlockState state) {
        return 0;
    }
}
