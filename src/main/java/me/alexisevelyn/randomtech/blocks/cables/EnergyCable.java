package me.alexisevelyn.randomtech.blocks.cables;

import me.alexisevelyn.randomtech.api.blocks.cables.GenericCable;
import me.alexisevelyn.randomtech.api.blocks.machines.PowerAcceptorBlock;
import me.alexisevelyn.randomtech.api.utilities.GenericBlockHelper;
import me.alexisevelyn.randomtech.utility.Materials;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.InventoryProvider;
import net.minecraft.inventory.Inventory;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.WorldAccess;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import reborncore.common.powerSystem.PowerAcceptorBlockEntity;

public class EnergyCable extends GenericCable {
    // Generic Instantiation of EnergyCable with Default Shape
    public EnergyCable() {
        this(null);
    }

    // I may create more than one cable with this class, so I'm putting extra constructors here
    // Generic Instantiation of EnergyCable with Custom Shape
    public EnergyCable(@Nullable VoxelShape genericShape) {
        this(FabricBlockSettings
                .of(Materials.CABLE_MATERIAL)
                .sounds(BlockSoundGroup.GLASS)
                .nonOpaque() // Fixes xray issue. Also allows light pass through block
                .allowsSpawning(GenericBlockHelper::never) // Allows or denies spawning
                .solidBlock(GenericBlockHelper::never) // ??? - Seems to have no apparent effect
                .suffocates(GenericBlockHelper::never) // Suffocates player
                .blockVision(GenericBlockHelper::never) // Blocks Vision inside of block
                .strength(0.3F, 0.3F)
                .ticksRandomly(), genericShape);
    }

    // For customizing block settings while only supplying one shape
    public EnergyCable(@NotNull Settings settings, @Nullable VoxelShape genericShape) {
        this(settings, genericShape, genericShape, genericShape, null);
    }

    // For full control over cable shapes
    public EnergyCable(@NotNull Settings settings, @Nullable VoxelShape outlinedShape, @Nullable VoxelShape visualShape, @Nullable VoxelShape collisionShape, @Nullable VoxelShape[] cullingShapes) {
        super(settings, outlinedShape, visualShape, collisionShape, cullingShapes);
    }

    @Override
    public boolean isInstanceOfCable(Block block, WorldAccess world, BlockPos blockPos) {
        return block instanceof EnergyCable;
    }

    @Override
    public boolean isInstanceOfInterfaceableBlock(Block block, WorldAccess world, BlockPos blockPos) {
        if (block instanceof PowerAcceptorBlock)
            return true;

        // Checking the instance of also inherently checks if the block entity is null
        return world.getBlockEntity(blockPos) instanceof PowerAcceptorBlockEntity;
    }

    @Override
    public boolean isValidSide(Block block, WorldAccess world, BlockPos blockPos, Direction side) {
        return true;
    }
}
