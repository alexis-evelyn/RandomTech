package me.alexisevelyn.randomtech.blocks;

import me.alexisevelyn.randomtech.api.blocks.machines.PowerAcceptorBlock;
import me.alexisevelyn.randomtech.blockentities.TeleporterBlockEntity;
import me.alexisevelyn.randomtech.utility.Materials;
import me.alexisevelyn.randomtech.utility.registryhelpers.main.RegistryHelper;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.api.tool.attribute.v1.FabricToolTags;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.item.ToolMaterials;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.IntProperty;
import net.minecraft.util.math.Direction;
import net.minecraft.world.BlockView;
import reborncore.api.blockentity.IMachineGuiHandler;

import java.util.function.ToIntFunction;

/**
 * The type Teleporter block.
 */
public class TeleporterBlock extends PowerAcceptorBlock {
    public static final BooleanProperty ACTIVE = BooleanProperty.of("active");
    public static final IntProperty ENERGY = IntProperty.of("energy", 0, 15);

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
                .lightLevel(getLightLevel()), true);

        setDefaultState(getStateManager()
                .getDefaultState()
                .with(FACING, Direction.NORTH)
                .with(ACTIVE, false)
                .with(ENERGY, 0));
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
}
