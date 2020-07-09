package me.alexisevelyn.randomtech.utility;

import me.alexisevelyn.randomtech.Main;
import me.alexisevelyn.randomtech.blockentities.FuserBlockEntity;
import me.alexisevelyn.randomtech.blockentities.TeleporterBlockEntity;
import net.minecraft.block.Block;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import org.apache.commons.lang3.Validate;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class BlockEntities {
    private static List<BlockEntityType<?>> TYPES = new ArrayList<>();

    public static final BlockEntityType<TeleporterBlockEntity> TELEPORTER = register(TeleporterBlockEntity::new, "teleporter_block_entity", RegistryHelper.TELEPORTER);
    public static final BlockEntityType<FuserBlockEntity> FUSER = register(FuserBlockEntity::new, "fuser_block_entity", RegistryHelper.FUSER);

    public static <T extends BlockEntity> BlockEntityType<T> register(Supplier<T> supplier, String name, Block... blocks) {
        Validate.isTrue(blocks.length > 0, "Add a block to your Block Entity: " + supplier.get().getClass().getName());

        return register(new Identifier(Main.MODID, name).toString(), BlockEntityType.Builder.create(supplier, blocks));
    }

    public static <T extends BlockEntity> BlockEntityType<T> register(String id, BlockEntityType.Builder<T> builder) {
        BlockEntityType<T> blockEntityType = builder.build(null);
        Registry.register(Registry.BLOCK_ENTITY_TYPE, new Identifier(id), blockEntityType);

        BlockEntities.TYPES.add(blockEntityType);
        return blockEntityType;
    }
}
