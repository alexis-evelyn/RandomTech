package me.alexisevelyn.randomtech.api.utilities;

import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3i;

public class CalculationHelper {
    public static BlockPos addVectors(Vec3i oneVector, Vec3i secondVector) {
        return new BlockPos(oneVector.getX() + secondVector.getX(), oneVector.getY() + secondVector.getY(), oneVector.getZ() + secondVector.getZ());
    }

    public static BlockPos subtractVectors(Vec3i oneVector, Vec3i secondVector) {
        return new BlockPos(oneVector.getX() - secondVector.getX(), oneVector.getY() - secondVector.getY(), oneVector.getZ() - secondVector.getZ());
    }

    public static BlockPos multiplyVectors(Vec3i oneVector, Vec3i secondVector) {
        return new BlockPos(oneVector.getX() * secondVector.getX(), oneVector.getY() * secondVector.getY(), oneVector.getZ() * secondVector.getZ());
    }

    public static BlockPos divideVectors(Vec3i oneVector, Vec3i secondVector) {
        return new BlockPos(oneVector.getX() / secondVector.getX(), oneVector.getY() / secondVector.getY(), oneVector.getZ() / secondVector.getZ());
    }

    public static double distanceVectors(Vec3i oneVector, Vec3i secondVector) {
        BlockPos subtractedVectors = subtractVectors(oneVector, secondVector);
        return MathHelper.abs(subtractedVectors.getX()) + MathHelper.abs(subtractedVectors.getY()) + MathHelper.abs(subtractedVectors.getZ());
    }
}
