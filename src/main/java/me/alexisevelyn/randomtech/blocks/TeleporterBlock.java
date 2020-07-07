package me.alexisevelyn.randomtech.blocks;

import me.alexisevelyn.randomtech.Main;
import me.alexisevelyn.randomtech.Materials;
import me.alexisevelyn.randomtech.RegistryHelper;
import me.alexisevelyn.randomtech.blockentities.TeleporterBlockEntity;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.api.tool.attribute.v1.FabricToolTags;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.item.ToolMaterials;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.IntProperty;
import net.minecraft.state.property.Property;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import reborncore.api.blockentity.IMachineGuiHandler;
import reborncore.common.blocks.BlockMachineBase;
import reborncore.common.powerSystem.PowerAcceptorBlockEntity;
import reborncore.common.util.ItemHandlerUtils;

import java.util.function.ToIntFunction;

public class TeleporterBlock extends BlockMachineBase {
    public static final BooleanProperty ACTIVE = BooleanProperty.of("active");
    public static final IntProperty ENERGY = IntProperty.of("energy", 0, 15);

    public TeleporterBlock() {
        super(FabricBlockSettings
                .of(Materials.FirstMaterial)
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

    public static ToIntFunction<BlockState> getLightLevel() {
        return (state) -> {

            return state.get(ACTIVE) ? state.get(ENERGY) : 0;
        };
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(FACING, ACTIVE, ENERGY);
    }

    @Override
    public BlockEntity createBlockEntity(BlockView worldIn) {
        return new TeleporterBlockEntity();
    }

    @Override
    public IMachineGuiHandler getGui() {
        return RegistryHelper.teleporterGuiHandler;
    }

    @Override
    public boolean hasComparatorOutput(BlockState state) {
        return true;
    }

    @Override
    public int getComparatorOutput(BlockState state, World world, BlockPos pos) {
        return PowerAcceptorBlockEntity.calculateComparatorOutputFromEnergy(world.getBlockEntity(pos));
    }

    public double getPower(BlockState state, World world, BlockPos pos) {
        TeleporterBlockEntity teleporterBlockEntity = (TeleporterBlockEntity) world.getBlockEntity(pos);

        if (teleporterBlockEntity == null) {
            return -1.0;
        }

        return teleporterBlockEntity.getEnergy();
    }

    public double getMaxPower(BlockState state, World world, BlockPos pos) {
        TeleporterBlockEntity teleporterBlockEntity = (TeleporterBlockEntity) world.getBlockEntity(pos);

        if (teleporterBlockEntity == null) {
            return -1.0;
        }

        return teleporterBlockEntity.getBaseMaxPower();
    }
}
