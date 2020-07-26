package me.alexisevelyn.randomtech.blockitems;

import me.alexisevelyn.randomtech.api.utilities.GenericBlockHelper;
import me.alexisevelyn.randomtech.fluids.ExperienceFluid;
import me.alexisevelyn.randomtech.utility.Materials;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.api.tool.attribute.v1.FabricToolTags;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolMaterials;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.text.Text;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.function.ToIntFunction;

public class BottledDemon extends BlockItem {
    public BottledDemon(Block block, Settings settings) {
        super(block, settings);
    }

    @Environment(EnvType.CLIENT)
    @Override
    public void appendTooltip(ItemStack stack, @Nullable World worldIn, List<Text> tooltip, TooltipContext flagIn) {
        // TODO: Add info about captured cloud demon
    }

    public static class BottledDemonBlock extends Block {
        public BottledDemonBlock() {
            super(FabricBlockSettings
                    .of(Materials.TILE_MATERIAL)
                    .sounds(BlockSoundGroup.STONE)
//                    .breakByTool(FabricToolTags.PICKAXES, ToolMaterials.WOOD.getMiningLevel())
//                    .requiresTool()
                    .allowsSpawning(GenericBlockHelper::never)
                    .solidBlock(GenericBlockHelper::always)
                    .suffocates(GenericBlockHelper::always)
                    .blockVision(GenericBlockHelper::always)
                    .strength(1.8F, 1.8F)
                    .lightLevel(getLightLevel()));
        }

        public static ToIntFunction<BlockState> getLightLevel() {
            return (state) -> 0; // 7?
        }
    }
}