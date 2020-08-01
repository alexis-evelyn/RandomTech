package me.alexisevelyn.randomtech.api.utilities;

import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3i;

public class CalculationHelper {
    public static BlockPos addVectors(Vec3i oneVector, Vec3i secondVector) {
        return new BlockPos(oneVector.getX() + secondVector.getX(), oneVector.getY() + secondVector.getY(), oneVector.getZ() + secondVector.getZ());
    }
}
