package me.alexisevelyn.randomtech.utility;

import me.alexisevelyn.randomtech.Main;
import me.alexisevelyn.randomtech.blockentities.*;
import me.alexisevelyn.randomtech.blockentities.cables.EnergyCableBlockEntity;
import me.alexisevelyn.randomtech.blockentities.cables.FluidCableBlockEntity;
import me.alexisevelyn.randomtech.blockentities.cables.ItemCableBlockEntity;
import me.alexisevelyn.randomtech.utility.registryhelpers.main.RegistryHelper;
import net.minecraft.block.Block;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import org.apache.commons.lang3.Validate;

import java.util.function.Supplier;

/**
 * The type Block entities.
 */
public class BlockEntitiesHelper {
    // Machines
    public static final BlockEntityType<TeleporterBlockEntity> TELEPORTER = register(TeleporterBlockEntity::new, "teleporter_block_entity", RegistryHelper.TELEPORTER);
    public static final BlockEntityType<FuserBlockEntity> FUSER = register(FuserBlockEntity::new, "fuser_block_entity", RegistryHelper.FUSER);
    public static final BlockEntityType<BasicComputerBlockEntity> BASIC_COMPUTER = register(BasicComputerBlockEntity::new, "basic_computer_block_entity", RegistryHelper.BASIC_COMPUTER);

    // Intangible Glass
    public static final BlockEntityType<IntangibleDarkGlassBlockEntity> INTANGIBLE_DARK_GLASS = register(IntangibleDarkGlassBlockEntity::new, "intangible_dark_glass_block_entity", RegistryHelper.DARK_INTANGIBLE_GLASS);
    public static final BlockEntityType<IntangibleGlassBlockEntity> INTANGIBLE_GLASS = register(IntangibleGlassBlockEntity::new, "intangible_glass_block_entity", RegistryHelper.INTANGIBLE_GLASS);
    public static final BlockEntityType<InverseIntangibleDarkGlassBlockEntity> INVERSE_INTANGIBLE_DARK_GLASS = register(InverseIntangibleDarkGlassBlockEntity::new, "inverse_intangible_dark_glass_block_entity", RegistryHelper.INVERSE_DARK_INTANGIBLE_GLASS);
    public static final BlockEntityType<InverseIntangibleGlassBlockEntity> INVERSE_INTANGIBLE_GLASS = register(InverseIntangibleGlassBlockEntity::new, "inverse_intangible_glass_block_entity", RegistryHelper.INVERSE_INTANGIBLE_GLASS);

    // Virtual Tile
    public static final BlockEntityType<VirtualTileBlockEntity> VIRTUAL_TILE = register(VirtualTileBlockEntity::new, "virtual_tile_block_entity", RegistryHelper.VIRTUAL_TILE_BLOCK);

    // Cables
    public static final BlockEntityType<ItemCableBlockEntity> ITEM_CABLE = register(ItemCableBlockEntity::new, "item_cable_block_entity", RegistryHelper.ITEM_CABLE_BLOCK, RegistryHelper.CHORUS_ITEM_CABLE_BLOCK);
    public static final BlockEntityType<FluidCableBlockEntity> FLUID_CABLE = register(FluidCableBlockEntity::new, "fluid_cable_block_entity", RegistryHelper.FLUID_CABLE_BLOCK, RegistryHelper.CHORUS_FLUID_CABLE_BLOCK);
    public static final BlockEntityType<EnergyCableBlockEntity> ENERGY_CABLE = register(EnergyCableBlockEntity::new, "energy_cable_block_entity", RegistryHelper.ENERGY_CABLE_BLOCK, RegistryHelper.CHORUS_ENERGY_CABLE_BLOCK);

    /**
     * Register block entity type.
     *
     * @param <T>      the type parameter
     * @param supplier the supplier
     * @param name     the name
     * @param blocks   the blocks
     * @return the block entity type
     */
    public static <T extends BlockEntity> BlockEntityType<T> register(Supplier<T> supplier, String name, Block... blocks) {
        Validate.isTrue(blocks.length > 0, "Add a block to your Block Entity: " + supplier.get().getClass().getName());

        return register(new Identifier(Main.MODID, name).toString(), BlockEntityType.Builder.create(supplier, blocks));
    }

    /**
     * Register block entity type.
     *
     * @param <T>     the type parameter
     * @param id      the id
     * @param builder the builder
     * @return the block entity type
     */
    public static <T extends BlockEntity> BlockEntityType<T> register(String id, BlockEntityType.Builder<T> builder) {
        BlockEntityType<T> blockEntityType = builder.build(null);
        Registry.register(Registry.BLOCK_ENTITY_TYPE, new Identifier(id), blockEntityType);

        return blockEntityType;
    }
}