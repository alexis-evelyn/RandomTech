package me.alexisevelyn.randomtech.blocks.dusts;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.BlockState;
import net.minecraft.block.Material;
import net.minecraft.block.RedstoneWireBlock;
import net.minecraft.client.util.math.Vector3f;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.BlockRenderView;

import java.util.function.ToIntFunction;

// This seems like a cool idea for a bluestone dust like what Mumbo joked about one time
public class CobaltWire extends RedstoneWireBlock {
    // static Color blue = new Color(0, 0, 255);
    static final Vector3f[] wireColor;

    public CobaltWire() {
        super(FabricBlockSettings
                .of(Material.SUPPORTED)
                .nonOpaque()
                .noCollision()
                .breakInstantly()
                .lightLevel(getLightLevel()));
    }

    public static ToIntFunction<BlockState> getLightLevel() {
        // This works just fine.
        //return (state) -> state.get(RedstoneWireBlock.POWER);
        return (state) -> 0;
    }

    public static int getWireColor(BlockState state, BlockRenderView world, BlockPos pos, int tintIndex) {
        int redstonePower = state.get(RedstoneWireBlock.POWER);

        Vector3f vector3f = wireColor[redstonePower];
        return MathHelper.packRgb(vector3f.getX(), vector3f.getY(), vector3f.getZ());
    }

    static {
        wireColor = new Vector3f[16];

        for(int i = 0; i <= 15; ++i) {
            float dividedPowerLevel = i / 15.0F;
            float red = MathHelper.clamp(dividedPowerLevel * dividedPowerLevel * 0.7F - 0.5F, 0.0F, 1.0F);
            float green = MathHelper.clamp(dividedPowerLevel * dividedPowerLevel * 0.6F - 0.7F, 0.0F, 1.0F);
            float blue = dividedPowerLevel * 0.6F + (dividedPowerLevel > 0.0F ? 0.4F : 0.3F);

            wireColor[i] = new Vector3f(red, green, blue);
        }
    }
}
