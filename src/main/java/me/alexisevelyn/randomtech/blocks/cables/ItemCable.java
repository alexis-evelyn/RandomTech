package me.alexisevelyn.randomtech.blocks.cables;

import me.alexisevelyn.randomtech.api.blocks.cables.GenericCable;
import me.alexisevelyn.randomtech.api.utilities.GenericBlockHelper;
import me.alexisevelyn.randomtech.blockentities.cables.ItemCableBlockEntity;
import me.alexisevelyn.randomtech.utility.Materials;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SidedInventory;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;

import java.util.Random;

public class ItemCable extends GenericCable implements BlockEntityProvider {
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
                        .ticksRandomly()//,

                // TODO: Remove me
                // Block.createCuboidShape(4.0D, 0.0D, 4.0D, 12.0D, 9.0D, 12.0D)
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

    @SuppressWarnings("deprecation")
    @Override
    public boolean hasComparatorOutput(BlockState state) {
        return true;
    }

    @Override
    @SuppressWarnings("deprecation") // https://discordapp.com/channels/507304429255393322/523633816078647296/740720404800209041
    public int getComparatorOutput(BlockState state, World world, BlockPos pos) {
        return ScreenHandler.calculateComparatorOutput(getInventory(state, world, pos));
    }

    public SidedInventory getInventory(BlockState state, WorldAccess world, BlockPos pos) {
        BlockEntity blockEntity = world.getBlockEntity(pos);

        if (blockEntity instanceof InventoryProvider)
            return ((InventoryProvider) blockEntity).getInventory(state, world, pos);

        return null;
    }
}
