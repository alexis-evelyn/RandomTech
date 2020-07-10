package me.alexisevelyn.randomtech.blocks.glass;

import me.alexisevelyn.randomtech.utility.GenericBlockHelper;
import me.alexisevelyn.randomtech.utility.Materials;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.AbstractGlassBlock;
import net.minecraft.sound.BlockSoundGroup;

// TODO: Add inverted intangible glass which only blocks the player
// TODO: Fix rendering issue on edges of glass - This shows up on every glass block from my mod, but not vanilla (artifacts?)
// TODO: Allow connecting different types of glass together
// TODO: Fix xray ability caused by dark glass
// TODO: Investigate if these suggestions help: https://discordapp.com/channels/507304429255393322/721100785936760876/731144592644374538
public class DarkIntangibleGlass extends AbstractGlassBlock {
    public DarkIntangibleGlass() {
        super(FabricBlockSettings
                .of(Materials.DARK_GLASS_MATERIAL)
                .sounds(BlockSoundGroup.GLASS)
//                .nonOpaque() // Fixes xray issue. Also allows light pass through block
//                .noCollision() // Allows walking through block. Also allows light pass through block
                .allowsSpawning(GenericBlockHelper::never) // Allows or denies spawning
                .solidBlock(GenericBlockHelper::never) // ??? - Seems to have no apparent effect
                .suffocates(GenericBlockHelper::never) // Suffocates player
                .blockVision(GenericBlockHelper::never) // Blocks Vision inside of block
                .strength(0.3F, 0.3F));
    }
}
