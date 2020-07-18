package me.alexisevelyn.randomtech.blocks;

import me.alexisevelyn.randomtech.api.blocks.machines.FluidMachineBase;
import me.alexisevelyn.randomtech.utility.Materials;
import me.alexisevelyn.randomtech.utility.RegistryHelper;
import me.alexisevelyn.randomtech.blockentities.FuserBlockEntity;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.api.tool.attribute.v1.FabricToolTags;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.item.ToolMaterials;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.world.BlockView;
import reborncore.api.blockentity.IMachineGuiHandler;

public class FuserBlock extends FluidMachineBase {
    public FuserBlock() {
        super(FabricBlockSettings
                .of(Materials.MACHINE_MATERIAL)
                .breakByHand(false).requiresTool()
                .breakByTool(FabricToolTags.PICKAXES, ToolMaterials.IRON.getMiningLevel())
                .sounds(BlockSoundGroup.NETHERITE)
                .strength(2.0F, 0.2F));
    }

    @Override
    public BlockEntity createBlockEntity(BlockView worldIn) {
        return new FuserBlockEntity();
    }

    @Override
    public IMachineGuiHandler getGui() {
        return RegistryHelper.fuserGuiHandler;
    }
}
