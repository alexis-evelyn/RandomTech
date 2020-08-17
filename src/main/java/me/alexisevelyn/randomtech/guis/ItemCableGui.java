package me.alexisevelyn.randomtech.guis;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.Blocks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.Generic3x3ContainerScreenHandler;
import net.minecraft.screen.slot.SlotActionType;

@Environment(EnvType.CLIENT)
public class ItemCableGui extends Generic3x3ContainerScreenHandler {
    public ItemCableGui(int syncId, PlayerInventory playerInventory, Inventory inventory) {
        super(syncId, playerInventory, inventory);
    }

    @Override
    public ItemStack onSlotClick(int slotId, int clickData, SlotActionType actionType, PlayerEntity playerEntity) {
        if (slotId >= 0) { // slotId < 0 are used for networking internals
            ItemStack stack = getSlot(slotId).getStack();

            if (stack.getItem().equals(Blocks.BEDROCK.asItem())) {
                // Prevent moving bedrock around
                return stack;
            }
        }

        return super.onSlotClick(slotId, clickData, actionType, playerEntity);
    }
}