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

/**
 * The type Energy cable.
 */
public class EnergyCable extends GenericCable {
    /**
     * Instantiates a new Energy cable.
     */
// Generic Instantiation of EnergyCable with Default Shape
    public EnergyCable() {
        this(null);
    }

    /**
     * Instantiates a new Energy cable.
     *
     * @param genericShape the generic shape
     */
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

    /**
     * Instantiates a new Energy cable.
     *
     * @param settings     the settings
     * @param genericShape the generic shape
     */
// For customizing block settings while only supplying one shape
    public EnergyCable(@NotNull Settings settings, @Nullable VoxelShape genericShape) {
        this(settings, genericShape, genericShape, genericShape, null);
    }

    /**
     * Instantiates a new Energy cable.
     *
     * @param settings       the settings
     * @param outlinedShape  the outlined shape
     * @param visualShape    the visual shape
     * @param collisionShape the collision shape
     * @param cullingShapes  the culling shapes
     */
// For full control over cable shapes
    public EnergyCable(@NotNull Settings settings, @Nullable VoxelShape outlinedShape, @Nullable VoxelShape visualShape, @Nullable VoxelShape collisionShape, @Nullable VoxelShape[] cullingShapes) {
        super(settings, outlinedShape, visualShape, collisionShape, cullingShapes);
    }

    /**
     * Is instance of cable boolean.
     *
     * @param block    the block
     * @param world    the world
     * @param blockPos the block pos
     * @return the boolean
     */
    @Override
    public boolean isInstanceOfCable(Block block, WorldAccess world, BlockPos blockPos) {
        return block instanceof EnergyCable;
    }

    /**
     * Is instance of interfaceable block boolean.
     *
     * @param block    the block
     * @param world    the world
     * @param blockPos the block pos
     * @return the boolean
     */
    @Override
    public boolean isInstanceOfInterfaceableBlock(Block block, WorldAccess world, BlockPos blockPos) {
        if (block instanceof PowerAcceptorBlock)
            return true;

        // Checking the instance of also inherently checks if the block entity is null
        return world.getBlockEntity(blockPos) instanceof PowerAcceptorBlockEntity;
    }

    /**
     * Is valid side boolean.
     *
     * @param block    the block
     * @param world    the world
     * @param blockPos the block pos
     * @param side     the side
     * @return the boolean
     */
    @Override
    public boolean isValidSide(Block block, WorldAccess world, BlockPos blockPos, Direction side) {
        return true;
    }
}
