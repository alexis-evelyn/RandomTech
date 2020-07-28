package me.alexisevelyn.randomtech.blocks;

import me.alexisevelyn.randomtech.api.blocks.machines.FluidMachineBase;
import me.alexisevelyn.randomtech.blockentities.BasicComputerBlockEntity;
import me.alexisevelyn.randomtech.utility.Materials;
import me.alexisevelyn.randomtech.utility.registryhelpers.main.RegistryHelper;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.api.tool.attribute.v1.FabricToolTags;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.item.ToolMaterials;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.world.BlockView;
import reborncore.api.blockentity.IMachineGuiHandler;

public class BasicComputerBlock extends FluidMachineBase {
    public BasicComputerBlock() {
        super(FabricBlockSettings
                .of(Materials.MACHINE_MATERIAL)
                .breakByHand(false).requiresTool()
                .breakByTool(FabricToolTags.PICKAXES, ToolMaterials.IRON.getMiningLevel())
                .sounds(BlockSoundGroup.NETHERITE)
                .strength(2.0F, 0.2F));
    }

    @Override
    public BlockEntity createBlockEntity(BlockView worldIn) {
        return new BasicComputerBlockEntity();
    }

    @Override
    public IMachineGuiHandler getGui() {
        return RegistryHelper.basicComputerGuiHandler;
    }
}
