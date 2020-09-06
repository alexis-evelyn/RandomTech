package me.alexisevelyn.randomtech.blocks;

import me.alexisevelyn.randomtech.api.blocks.machines.FluidMachineBase;
import me.alexisevelyn.randomtech.utility.MaterialsHelper;
import me.alexisevelyn.randomtech.utility.registryhelpers.main.RegistryHelper;
import me.alexisevelyn.randomtech.blockentities.FuserBlockEntity;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.api.tool.attribute.v1.FabricToolTags;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.item.ToolMaterials;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.IntProperty;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import reborncore.api.blockentity.IMachineGuiHandler;

/**
 * The type Fuser block.
 */
public class FuserBlock extends FluidMachineBase {
    public static final IntProperty POWER = IntProperty.of("power", 0, 15);

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
