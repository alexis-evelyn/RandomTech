package me.alexisevelyn.randomtech.api.utilities;

import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3i;
import org.apiguardian.api.API;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * The type Calculation helper.
 */
public class CalculationHelper {
    /**
     * Adds two {@link Vec3i} together and returns a {@link BlockPos}
     *
     * @param firstVector  the first vector
     * @param secondVector the second vector
     * @return the block pos
     */
    @API(status = API.Status.STABLE)
    public static BlockPos addVectors(@NotNull Vec3i firstVector, @NotNull Vec3i secondVector) {
        return new BlockPos(firstVector.getX() + secondVector.getX(), firstVector.getY() + secondVector.getY(), firstVector.getZ() + secondVector.getZ());
    }

    /**
     * Subtacts two {@link Vec3i} together and returns a {@link BlockPos}
     *
     * @param firstVector  the first vector
     * @param secondVector the second vector
     * @return the block pos
     */
    @API(status = API.Status.STABLE)
    public static BlockPos subtractVectors(@NotNull Vec3i firstVector, @NotNull Vec3i secondVector) {
        return new BlockPos(firstVector.getX() - secondVector.getX(), firstVector.getY() - secondVector.getY(), firstVector.getZ() - secondVector.getZ());
    }

    /**
     * Multiplies two {@link Vec3i} together and returns a {@link BlockPos}
     *
     * @param firstVector  the first vector
     * @param secondVector the second vector
     * @return the block pos
     */
    @API(status = API.Status.STABLE)
    public static BlockPos multiplyVectors(@NotNull Vec3i firstVector, @NotNull Vec3i secondVector) {
        return new BlockPos(firstVector.getX() * secondVector.getX(), firstVector.getY() * secondVector.getY(), firstVector.getZ() * secondVector.getZ());
    }

    /**
     * Divides two {@link Vec3i} together and returns a {@link BlockPos}
     *
     * @param firstVector  the first vector
     * @param secondVector the second vector
     * @return the block pos
     */
    @API(status = API.Status.STABLE)
    public static BlockPos divideVectors(@NotNull Vec3i firstVector, @NotNull Vec3i secondVector) {
        return new BlockPos(firstVector.getX() / secondVector.getX(), firstVector.getY() / secondVector.getY(), firstVector.getZ() / secondVector.getZ());
    }

    /**
     * Returns the absolute distance between two {@link Vec3i} and returns the distance as a double
     *
     * @param firstVector  the first vector
     * @param secondVector the second vector
     * @return the distance in blocks
     */
    @API(status = API.Status.STABLE)
    public static double distanceVectors(@NotNull Vec3i firstVector, @NotNull Vec3i secondVector) {
        BlockPos subtractedVectors = subtractVectors(firstVector, secondVector);
        return MathHelper.abs(subtractedVectors.getX()) + MathHelper.abs(subtractedVectors.getY()) + MathHelper.abs(subtractedVectors.getZ());
    }

    /**
     * Returns the direction the second {@link Vec3i} is from the first {@link Vec3i}
     *
     * @param firstVector The vector of the block you are checking from
     * @param secondVector The vector of the block you are checking
     * @return the direction
     */
    @API(status = API.Status.STABLE)
    @Nullable
    public static Direction getDirection(@NotNull Vec3i firstVector, @NotNull Vec3i secondVector) {
        Vec3i result = subtractVectors(secondVector, firstVector);

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

    /**
     * Takes two ranges in the form input and output as well as the current input and returns the output calculated from the current input
     *
     * Formula Pulled From: https://stackoverflow.com/a/929107/6828099
     *
     * @param currentInput The current input from inside input range
     * @param minInput The minimum point of the input range (inclusive)
     * @param maxInput The maximum point of the input range (inclusive)
     * @param minOutput The minimum point of the output range (inclusive)
     * @param maxOutput The maximum point of the output range (inclusive)
     * @return The corresponding output within the output range
     */
    @API(status = API.Status.STABLE)
    public static double proportionCalculator(double currentInput, double minInput, double maxInput, double minOutput, double maxOutput) {
        return (((currentInput - minInput) * maxOutput) / maxInput) + minOutput;
    }
}
