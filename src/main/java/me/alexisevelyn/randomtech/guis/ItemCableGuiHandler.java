package me.alexisevelyn.randomtech.guis;

import me.alexisevelyn.randomtech.inventories.ItemCableInventory;
import me.alexisevelyn.randomtech.utility.registryhelpers.main.RegistryHelper;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.Slot;

public class ItemCableGuiHandler extends ScreenHandler {
    private final ItemCableInventory inventory;

    /**
     * Called Client Side - Blank Inventory is Synced With Server
     *
     * @param syncID
     * @param playerInventory
     * @param packetByteBuf
     */
    public ItemCableGuiHandler(int syncID, PlayerInventory playerInventory, PacketByteBuf packetByteBuf) {
        super(RegistryHelper.itemCableScreenHandlerType, syncID);

        this.inventory = new ItemCableInventory();
    }

    /**
     * Called Server Side
     *
     * @param syncId
     * @param playerInventory
     * @param inventory
     */
    public ItemCableGuiHandler(int syncId, PlayerInventory playerInventory, Inventory inventory) {
        super(RegistryHelper.itemCableScreenHandlerType, syncId);

        this.inventory = (ItemCableInventory) inventory;

        // Incase I run custom logic in the future
        this.inventory.onOpen(playerInventory.player);
    }

    @Override
    public boolean canUse(PlayerEntity player) {
        return this.inventory.canPlayerUse(player);
    }

    /**
     * When the player shift clicks
     *
     * @param player
     * @param invSlot
     * @return
     */
    @Override
    public ItemStack transferSlot(PlayerEntity player, int invSlot) {
        ItemStack newStack = ItemStack.EMPTY;
        Slot slot = this.slots.get(invSlot);

        // Don't Allow Shift Clicking into Filter Slots (May change this for UX)
        if (!isRealSlot(player, invSlot))
            return ItemStack.EMPTY;

        if (slot != null && slot.hasStack()) {
            ItemStack originalStack = slot.getStack();
            newStack = originalStack.copy();

            if (invSlot < this.inventory.size())
                if (!this.insertItem(originalStack, this.inventory.size(), this.slots.size(), true))
                    return ItemStack.EMPTY;
            else if (!this.insertItem(originalStack, 0, this.inventory.size(), false))
                return ItemStack.EMPTY;

            if (originalStack.isEmpty())
                slot.setStack(ItemStack.EMPTY);
            else
                slot.markDirty();
        }

        return newStack;
    }

    private boolean isRealSlot(PlayerEntity playerEntity, int slot) {
        int[] slots = this.inventory.getRealSlots(null); // You can check the player direction facing the block. I don't need to as all real slots are accessible everywhere

        for (int currentSlot : slots)
            if (currentSlot == slot)
                return true;

        return false;
    }
}
