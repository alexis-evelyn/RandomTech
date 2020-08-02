package me.alexisevelyn.randomtech.blocks.cables;

import me.alexisevelyn.randomtech.api.blocks.cables.GenericCable;
import me.alexisevelyn.randomtech.api.utilities.GenericBlockHelper;
import me.alexisevelyn.randomtech.blockentities.cables.ItemCableBlockEntity;
import me.alexisevelyn.randomtech.utility.Materials;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;
import net.minecraft.world.WorldAccess;

import java.util.Random;

public class ItemCable extends GenericCable implements BlockEntityProvider, Waterloggable {
    // TODO: Figure out if it's possible to only implement a block entity on certain blocks of the same type.

    public ItemCable() {
        super(FabricBlockSettings
                        .of(Materials.CABLE_MATERIAL)
                        .sounds(BlockSoundGroup.GLASS)
                        .nonOpaque() // Fixes xray issue. Also allows light pass through block
                        .allowsSpawning(GenericBlockHelper::never) // Allows or denies spawning
                        .solidBlock(GenericBlockHelper::never) // ??? - Seems to have no apparent effect
                        .suffocates(GenericBlockHelper::never) // Suffocates player
                        .blockVision(GenericBlockHelper::never) // Blocks Vision inside of block
                        .strength(0.3F, 0.3F)
                        .ticksRandomly()
        );
    }

    @Override
    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        super.randomTick(state, world, pos, random);


    }

    @Override
    public boolean isInstanceOfCable(Block block, WorldAccess world, BlockPos blockPos) {
        return block instanceof ItemCable;
    }

    @Override
    public boolean isInstanceOfInterfaceableBlock(Block block, WorldAccess world, BlockPos blockPos) {
        if (block instanceof InventoryProvider)
            return true;

        // Checking the instance of also inherently checks if the block entity is null
        return world.getBlockEntity(blockPos) instanceof Inventory;
    }

    @Override
    public BlockEntity createBlockEntity(BlockView world) {
        return new ItemCableBlockEntity();
    }

//    @Override
//    public boolean hasComparatorOutput(BlockState state) {
//        return true;
//    }
//
//    @Override
//    public int getComparatorOutput(BlockState state, World world, BlockPos pos) {
//        return ScreenHandler.calculateComparatorOutput(getInventory(state, world, pos));
//    }

//    @Override
//    public SidedInventory getInventory(BlockState state, WorldAccess world, BlockPos pos) {
//        BlockEntity blockEntity = world.getBlockEntity(pos);
//
//        if (isInterfacing(state) && blockEntity instanceof SidedInventory) {
//            return (SidedInventory) blockEntity;
//        }
//
//        return null;
//    }
}
