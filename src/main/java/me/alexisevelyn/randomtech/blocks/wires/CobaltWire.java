package me.alexisevelyn.randomtech.blocks.wires;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.BlockState;
import net.minecraft.block.Material;
import net.minecraft.block.RedstoneWireBlock;
import net.minecraft.block.enums.WireConnection;
import net.minecraft.client.util.math.Vector3f;
import net.minecraft.particle.DustParticleEffect;
import net.minecraft.state.property.Property;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.BlockRenderView;
import net.minecraft.world.World;

import java.util.Random;
import java.util.function.ToIntFunction;

// This seems like a cool idea for a bluestone dust like what Mumbo joked about one time
// Video for reference: https://www.youtube.com/watch?v=9qO7325uDl4&ab_channel=MumboJumbo
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
        int redstonePower = getPower(state);

        Vector3f vector3f = wireColor[redstonePower];
        return MathHelper.packRgb(vector3f.getX(), vector3f.getY(), vector3f.getZ());
    }

    public static int getPower(BlockState state) {
        return state.get(RedstoneWireBlock.POWER);
    }

    // Responsible for setting particle properties including color of powered line
    @Override
    @Environment(EnvType.CLIENT)
    public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random random) {
        int i = state.get(POWER);
        if (i != 0) {

            for (Direction direction : Direction.Type.HORIZONTAL) {
                WireConnection wireConnection = state.get(DIRECTION_TO_WIRE_CONNECTION_PROPERTY.get(direction));
                switch (wireConnection) {
                    case UP:
                        this.createParticle(world, random, pos, wireColor[i], direction, Direction.UP, -0.5F, 0.5F);
                    case SIDE:
                        this.createParticle(world, random, pos, wireColor[i], Direction.DOWN, direction, 0.0F, 0.5F);
                        break;
                    case NONE:
                    default:
                        this.createParticle(world, random, pos, wireColor[i], Direction.DOWN, direction, 0.0F, 0.3F);
                }
            }
        }
    }

    public void createParticle(World world, Random random, BlockPos blockPos, Vector3f vector3f, Direction direction, Direction direction2, float f, float g) {
        float h = g - f;

        if (random.nextFloat() < 0.2F * h) {
            float j = f + h * random.nextFloat();
            double randomOffsetX = 0.5D + (0.4375F * direction.getOffsetX()) + (j * direction2.getOffsetX());
            double randomOffsetY = 0.5D + (0.4375F * direction.getOffsetY()) + (j * direction2.getOffsetY());
            double randomOffsetZ = 0.5D + (0.4375F * direction.getOffsetZ()) + (j * direction2.getOffsetZ());

            world.addParticle(new DustParticleEffect(vector3f.getX(), vector3f.getY(), vector3f.getZ(), 1.0F), blockPos.getX() + randomOffsetX, blockPos.getY() + randomOffsetY, blockPos.getZ() + randomOffsetZ, 0.0D, 0.0D, 0.0D);
        }
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
