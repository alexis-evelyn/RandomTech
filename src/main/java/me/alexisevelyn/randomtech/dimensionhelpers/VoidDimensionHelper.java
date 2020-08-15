package me.alexisevelyn.randomtech.dimensionhelpers;

import me.alexisevelyn.randomtech.blockentities.VirtualTileBlockEntity;
import me.alexisevelyn.randomtech.utility.registryhelpers.main.RegistryHelper;
import net.minecraft.block.Blocks;
import net.minecraft.block.pattern.BlockPattern;
import net.minecraft.entity.Entity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;

import java.awt.*;

/**
 * The type Void dimension helper.
 */
// This class helps with utilities needed for the void dimension
public class VoidDimensionHelper {
    /**
     * Place entity block pattern . teleport target.
     *
     * @param teleported       the teleported
     * @param destination      the destination
     * @param portalDir        the portal dir
     * @param horizontalOffset the horizontal offset
     * @param verticalOffset   the vertical offset
     * @return the block pattern . teleport target
     */
    public static BlockPattern.TeleportTarget placeEntity(Entity teleported, ServerWorld destination, Direction portalDir, double horizontalOffset, double verticalOffset) {
        return new BlockPattern.TeleportTarget(new Vec3d(0, 100, 0), Vec3d.ZERO, 0);
    }

    /**
     * Place entity in void block pattern . teleport target.
     *
     * @param teleported       the teleported
     * @param destination      the destination
     * @param portalDir        the portal dir
     * @param horizontalOffset the horizontal offset
     * @param verticalOffset   the vertical offset
     * @return the block pattern . teleport target
     */
    public static BlockPattern.TeleportTarget placeEntityInVoid(Entity teleported, ServerWorld destination, Direction portalDir, double horizontalOffset, double verticalOffset) {
        // TODO: Figure out how to get this to run.

        // Setup Block Entity
        VirtualTileBlockEntity virtualTileBlockEntity = new VirtualTileBlockEntity();
        virtualTileBlockEntity.setColor(Color.RED);

        // Set Block and BlockState
        destination.setBlockState(new BlockPos(0, 100, 0), RegistryHelper.VIRTUAL_TILE_BLOCK.getDefaultState());

        // Set Block Entity
        destination.setBlockEntity(new BlockPos(0, 100, 0), virtualTileBlockEntity);

        return new BlockPattern.TeleportTarget(new Vec3d(0.5, 101, 0.5), Vec3d.ZERO, 0);
    }
}
