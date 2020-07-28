package me.alexisevelyn.randomtech.blockentities;

import me.alexisevelyn.randomtech.api.blockentities.GenericComputerBlockEntity;
import me.alexisevelyn.randomtech.utility.BlockEntities;
import me.alexisevelyn.randomtech.utility.registryhelpers.main.RegistryHelper;
import net.minecraft.entity.player.PlayerEntity;
import reborncore.client.screen.builder.BuiltScreenHandler;
import reborncore.client.screen.builder.ScreenHandlerBuilder;
import reborncore.common.util.RebornInventory;

public class BasicComputerBlockEntity extends GenericComputerBlockEntity {
    public BasicComputerBlockEntity() {
        super(RegistryHelper.BASIC_COMPUTER, BlockEntities.BASIC_COMPUTER);
        this.inventory = new RebornInventory<>(1, "BasicComputerBlockEntity", 1, this);
    }

    @Override
    public BuiltScreenHandler createScreenHandler(int syncID, PlayerEntity playerEntity) {
        return new ScreenHandlerBuilder("basic_computer_gui")
                .player(playerEntity.inventory)
                .inventory()
                .hotbar()
                .addInventory()
                .blockEntity(this)
                .syncEnergyValue()
                .addInventory()
                .create(this, syncID);
    }
}
