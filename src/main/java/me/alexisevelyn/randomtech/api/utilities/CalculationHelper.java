package me.alexisevelyn.randomtech.api.utilities;

import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3i;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * The type Calculation helper.
 */
public class CalculationHelper {
    /**
     * Add vectors block pos.
     *
     * @param firstVector  the first vector
     * @param secondVector the second vector
     * @return the block pos
     */
    public static BlockPos addVectors(@NotNull Vec3i firstVector, @NotNull Vec3i secondVector) {
        return new BlockPos(firstVector.getX() + secondVector.getX(), firstVector.getY() + secondVector.getY(), firstVector.getZ() + secondVector.getZ());
    }

    /**
     * Subtract vectors block pos.
     *
     * @param firstVector  the first vector
     * @param secondVector the second vector
     * @return the block pos
     */
    public static BlockPos subtractVectors(@NotNull Vec3i firstVector, @NotNull Vec3i secondVector) {
        return new BlockPos(firstVector.getX() - secondVector.getX(), firstVector.getY() - secondVector.getY(), firstVector.getZ() - secondVector.getZ());
    }

    /**
     * Multiply vectors block pos.
     *
     * @param firstVector  the first vector
     * @param secondVector the second vector
     * @return the block pos
     */
    public static BlockPos multiplyVectors(@NotNull Vec3i firstVector, @NotNull Vec3i secondVector) {
        return new BlockPos(firstVector.getX() * secondVector.getX(), firstVector.getY() * secondVector.getY(), firstVector.getZ() * secondVector.getZ());
    }

    /**
     * Divide vectors block pos.
     *
     * @param firstVector  the first vector
     * @param secondVector the second vector
     * @return the block pos
     */
    public static BlockPos divideVectors(@NotNull Vec3i firstVector, @NotNull Vec3i secondVector) {
        return new BlockPos(firstVector.getX() / secondVector.getX(), firstVector.getY() / secondVector.getY(), firstVector.getZ() / secondVector.getZ());
    }

    /**
     * Distance vectors double.
     *
     * @param firstVector  the first vector
     * @param secondVector the second vector
     * @return the double
     */
    public static double distanceVectors(@NotNull Vec3i firstVector, @NotNull Vec3i secondVector) {
        BlockPos subtractedVectors = subtractVectors(firstVector, secondVector);
        return MathHelper.abs(subtractedVectors.getX()) + MathHelper.abs(subtractedVectors.getY()) + MathHelper.abs(subtractedVectors.getZ());
    }

    /**
     * Gets direction.
     *
     * @param neighborPos the neighbor pos
     * @param ourPos      the our pos
     * @return the direction
     */
    @Nullable
    public static Direction getDirection(@NotNull Vec3i neighborPos, @NotNull Vec3i ourPos) {
        Vec3i result = subtractVectors(neighborPos, ourPos);

        if (result.equals(Direction.NORTH.getVector()))
            return Direction.NORTH;

        if (result.equals(Direction.SOUTH.getVector()))
            return Direction.SOUTH;

        if (result.equals(Direction.EAST.getVector()))
            return Direction.EAST;

        if (result.equals(Direction.WEST.getVector()))
            return Direction.WEST;

        if (result.equals(Direction.UP.getVector()))
            return Direction.UP;

        if (result.equals(Direction.DOWN.getVector()))
            return Direction.DOWN;

        return Direction.fromVector(result.getX(), result.getY(), result.getZ());
    }
}
