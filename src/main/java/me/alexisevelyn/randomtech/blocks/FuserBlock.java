package me.alexisevelyn.randomtech.blocks;

import me.alexisevelyn.randomtech.api.blocks.machines.FluidMachineBase;
import me.alexisevelyn.randomtech.utility.MaterialsHelper;
import me.alexisevelyn.randomtech.utility.registryhelpers.main.RegistryHelper;
import me.alexisevelyn.randomtech.blockentities.FuserBlockEntity;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.api.tool.attribute.v1.FabricToolTags;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.item.ToolMaterials;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.IntProperty;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import reborncore.api.blockentity.IMachineGuiHandler;

/**
 * The type Fuser block.
 */
public class FuserBlock extends FluidMachineBase {
    public static final IntProperty POWER = IntProperty.of("power", 0, 15);

    private static final VoxelShape BASE_SHAPE = Block.createCuboidShape(1, 1, 1, 15, 15, 15);

    // These numbers were ripped straight from the model file produced by Blockbench
    // Different Border Shapes Per Side
    private static final VoxelShape WEST_BORDER_SHAPE = VoxelShapes.union(Block.createCuboidShape(15, 0, 1, 16, 1, 15), Block.createCuboidShape(15, 15, 1, 16, 16, 15)); // Left
    private static final VoxelShape EAST_BORDER_SHAPE = VoxelShapes.union(Block.createCuboidShape(0, 0, 1, 1, 1, 15), Block.createCuboidShape(0, 15, 1, 1, 16, 15)); // Right
    private static final VoxelShape NORTH_BORDER_SHAPE = VoxelShapes.union(Block.createCuboidShape(0, 0, 0, 1, 16, 1), Block.createCuboidShape(15, 0, 0, 16, 16, 1), Block.createCuboidShape(1, 0, 0, 15, 1, 1), Block.createCuboidShape(1, 15, 0, 15, 16, 1)); // Front
    private static final VoxelShape SOUTH_BORDER_SHAPE = VoxelShapes.union(Block.createCuboidShape(0, 0, 15, 1, 16, 16), Block.createCuboidShape(15, 0, 15, 16, 16, 16), Block.createCuboidShape(1, 0, 15, 15, 1, 16), Block.createCuboidShape(1, 15, 15, 15, 16, 16)); // Back

    // Full Combined Border Shape
    private static final VoxelShape BORDER_SHAPE = VoxelShapes.union(WEST_BORDER_SHAPE, EAST_BORDER_SHAPE, NORTH_BORDER_SHAPE, SOUTH_BORDER_SHAPE);

    // Drawer Handle
//    private static final VoxelShape NORTH_HANDLE_SHAPE = Block.createCuboidShape(4, 10, 0, 12, 11, 1);
//    private static final VoxelShape SOUTH_HANDLE_SHAPE = Block.createCuboidShape(12, 10, 16, 4, 11, 15);
//    private static final VoxelShape EAST_HANDLE_SHAPE = Block.createCuboidShape(0, 10, 4, 1, 11, 12);
//    private static final VoxelShape WEST_HANDLE_SHAPE = Block.createCuboidShape(16, 10, 12, 15, 11, 4);

    // Full Combined Shape
    private static final VoxelShape NORTH_SHAPE = VoxelShapes.union(BASE_SHAPE, BORDER_SHAPE); // NORTH_HANDLE_SHAPE
    private static final VoxelShape SOUTH_SHAPE = VoxelShapes.union(BASE_SHAPE, BORDER_SHAPE); // SOUTH_HANDLE_SHAPE
    private static final VoxelShape EAST_SHAPE = VoxelShapes.union(BASE_SHAPE, BORDER_SHAPE); // EAST_HANDLE_SHAPE
    private static final VoxelShape WEST_SHAPE = VoxelShapes.union(BASE_SHAPE, BORDER_SHAPE); // WEST_HANDLE_SHAPE

    /**
     * Instantiates a new Fuser block.
     */
    public FuserBlock() {
        super(FabricBlockSettings
                .of(MaterialsHelper.MACHINE_MATERIAL)
                .breakByHand(false).requiresTool()
                .breakByTool(FabricToolTags.PICKAXES, ToolMaterials.IRON.getMiningLevel())
                .sounds(BlockSoundGroup.NETHERITE)
                .strength(2.0F, 0.2F), true);

        setDefaultState(getStateManager()
                .getDefaultState()
                .with(POWER, 0)
        );
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        Direction direction = state.get(FACING);

        switch (direction) {
            case NORTH:
                return NORTH_SHAPE;
            case SOUTH:
                return SOUTH_SHAPE;
            case EAST:
                return EAST_SHAPE;
            case WEST:
            default:
                return WEST_SHAPE;
        }
    }

    @Override
    public VoxelShape getCullingShape(BlockState state, BlockView world, BlockPos pos) {
        return this.getOutlineShape(state, world, pos, ShapeContext.absent());
    }

    @Override
    public VoxelShape getCollisionShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return this.getOutlineShape(state, world, pos, context);
    }

    /**
     * Append properties.
     *
     * @param builder the builder
     */
    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        super.appendProperties(builder);

        builder.add(POWER);
    }

    /**
     * Create block entity block entity.
     *
     * @param worldIn the world in
     * @return the block entity
     */
    @Override
    public BlockEntity createBlockEntity(BlockView worldIn) {
        return new FuserBlockEntity();
    }

    /**
     * Gets gui.
     *
     * @return the gui
     */
    @Override
    public IMachineGuiHandler getGui() {
        return RegistryHelper.fuserGuiHandler;
    }

    /**
     * RebornCore's Recipe Handler Calls This
     *
     * @param active
     * @param world
     * @param pos
     */
    @Override
    public void setActive(Boolean active, World world, BlockPos pos) {
        Direction facing = world.getBlockState(pos).get(FACING);
        Integer power = world.getBlockState(pos).get(POWER);

        BlockState state = world.getBlockState(pos).with(ACTIVE, active).with(FACING, facing).with(POWER, power);

        world.setBlockState(pos, state, 3);
    }
}
