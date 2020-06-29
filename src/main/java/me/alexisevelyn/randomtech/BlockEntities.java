package me.alexisevelyn.randomtech;

import me.alexisevelyn.randomtech.blockentities.FirstBlockEntity;
import me.alexisevelyn.randomtech.blocks.FirstBlock;
import net.minecraft.block.Block;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.item.ItemConvertible;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import org.apache.commons.lang3.Validate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;

public class BlockEntities {
    private static List<BlockEntityType<?>> TYPES = new ArrayList<>();

    public static final BlockEntityType<FirstBlockEntity> FIRST_BLOCK = register(FirstBlockEntity::new, "first_block", Main.FIRST_BLOCK);

    public static <T extends BlockEntity> BlockEntityType<T> register(Supplier<T> supplier, String name, Block... blocks) {
        Validate.isTrue(blocks.length > 0, "no blocks for blockEntity entity type!");
        return register(new Identifier(Main.MODID, name).toString(), BlockEntityType.Builder.create(supplier, blocks));
    }

    public static <T extends BlockEntity> BlockEntityType<T> register(String id, BlockEntityType.Builder<T> builder) {
        BlockEntityType<T> blockEntityType = builder.build(null);
        Registry.register(Registry.BLOCK_ENTITY_TYPE, new Identifier(id), blockEntityType);

        BlockEntities.TYPES.add(blockEntityType);
        return blockEntityType;
    }
}
