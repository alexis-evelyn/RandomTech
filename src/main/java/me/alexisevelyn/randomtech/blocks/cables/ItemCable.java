package me.alexisevelyn.randomtech.blocks.cables;

import me.alexisevelyn.randomtech.api.blocks.cables.GenericCable;
import me.alexisevelyn.randomtech.api.utilities.GenericBlockHelper;
import me.alexisevelyn.randomtech.utility.Materials;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.InventoryProvider;
import net.minecraft.inventory.Inventory;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.WorldAccess;

public class ItemCable extends GenericCable {
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
        );
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
}
