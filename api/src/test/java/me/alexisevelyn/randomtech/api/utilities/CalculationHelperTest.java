package me.alexisevelyn.randomtech.api.utilities;

import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3i;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/* Helpful Links Provided By Owner of Repo
 * https://github.com/thinkslynk/refinedstorage/tree/fabric-working/src/test/kotlin/com/refinedmods/refinedstorage/data/sync
 * https://github.com/thinkslynk/refinedstorage/blob/c92afa51af0e5e08caded00882f91171652a89e3/src/test/kotlin/com/refinedmods/refinedstorage/data/sync/BiSyncedDataTest.kt
 *
 * Other Helpful Links
 * https://www.youtube.com/watch?v=eILy4p99ac8&ab_channel=Telusko
 * https://www.youtube.com/watch?v=HsQ9OwKA79s&ab_channel=Telusko
 * https://www.jetbrains.com/help/idea/nullable-and-notnull-annotations.html#notnull - Should I Check For NotNull Anyway If I Annotated As Not Null?
 *
 * Explanation
 * These are sample tests to help me learn how to make unit tests. I'll be learning how to mock objects later after I fix a bunch of bugs.
 */
public class CalculationHelperTest {
	@Test
	public void addVectors() {
		// TODO: Add Null Checks
		BlockPos result = CalculationHelper.addVectors(new Vec3i(12, -10,0), new Vec3i(-10, 20, 5));

		Assertions.assertEquals(new BlockPos(2, 10, 5), result);
	}

	@Test
	public void subtractVectors() {
		// TODO: Add Null Checks
		BlockPos result = CalculationHelper.subtractVectors(new Vec3i(-10, -20,200), new Vec3i(10, -10, -90));

		Assertions.assertEquals(new BlockPos(-20, -10, 290), result);
	}

	@Test
	public void multiplyVectors() {
		// TODO: Add Null Checks
		BlockPos result = CalculationHelper.multiplyVectors(new Vec3i(3, 4,5), new Vec3i(4, 3, 2));

		Assertions.assertEquals(new BlockPos(12, 12, 10), result);
	}

	@Test
	public void divideVectors() {
		// TODO: Add Null Checks
		// TODO: Add Zero Checks
		BlockPos result = CalculationHelper.divideVectors(new Vec3i(10, 15,9), new Vec3i(10, 3, 3));

		Assertions.assertEquals(new BlockPos(1, 5, 3), result);
	}

	@Test
	public void distanceVectors() {
		// TODO: Add Null Checks
		double result = CalculationHelper.distanceVectors(new Vec3i(3, 5,9), new Vec3i(3, 2, 1));
		// (0, 3, 8) -> 11

		Assertions.assertEquals(11, result, 0.0);
	}

	@Test
	public void getDirection() {
		// TODO: Add Null Checks
		// TODO: Verify All Directions
		Direction result = CalculationHelper.getDirection(Vec3i.ZERO, Direction.NORTH.getVector());

		Assertions.assertEquals(Direction.NORTH, result);
	}

	@Test
	public void proportionCalculator() {
		// TODO: Check For Invalid Inputs
		// https://www.calculatorsoup.com/calculators/math/ratios.php
		// https://stackoverflow.com/a/8385364/6828099

		double result = CalculationHelper.proportionCalculator(3, 0, 9, 0, 15);

		Assertions.assertEquals(5, result, 0.0);
	}
}