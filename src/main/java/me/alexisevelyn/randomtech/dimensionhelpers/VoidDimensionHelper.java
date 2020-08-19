package me.alexisevelyn.randomtech.dimensionhelpers;

import net.minecraft.block.pattern.BlockPattern;
import net.minecraft.entity.Entity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.TeleportTarget;

/**
 * The type Void dimension helper.
 */
// This class helps with utilities needed for the void dimension
public class VoidDimensionHelper {
    /**
     *
     * @param entity
     * @param serverWorld
     * @param direction
     * @param v
     * @param v1
     * @return
     */
    public TeleportTarget placeEntity(Entity entity, ServerWorld serverWorld, Direction direction, double v, double v1) {
        return new TeleportTarget(new Vec3d(0, 100, 0), Vec3d.ZERO, 0, 0);
    }
}
