package me.alexisevelyn.randomtech.blockitems;

import me.alexisevelyn.randomtech.api.utilities.GenericBlockHelper;
import me.alexisevelyn.randomtech.utility.MaterialsHelper;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.text.Text;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.function.ToIntFunction;

/**
 * The type Bottled demon.
 */
public class BottledDemon extends BlockItem {
    /**
     * Instantiates a new Bottled demon.
     *
     * @param block    the block
     * @param settings the settings
     */
    public BottledDemon(Block block, Settings settings) {
        super(block, settings);
    }

    /**
     * Append tooltip.
     *
     * @param stack   the stack
     * @param worldIn the world in
     * @param tooltip the tooltip
     * @param flagIn  the flag in
     */
    @Environment(EnvType.CLIENT)
    @Override
    public void appendTooltip(ItemStack stack, @Nullable World worldIn, List<Text> tooltip, TooltipContext flagIn) {
        // Intentionally Left Empty For Now
    }

    /**
     * The type Bottled demon block.
     */
    public static class BottledDemonBlock extends Block {
        /**
         * Instantiates a new Bottled demon block.
         */
        public BottledDemonBlock() {
            super(FabricBlockSettings
                    .of(MaterialsHelper.TILE_MATERIAL)
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

        /**
         * Gets light level.
         *
         * @return the light level
         */
        public static ToIntFunction<BlockState> getLightLevel() {
            return (state) -> 0; // 7?
        }
    }
}