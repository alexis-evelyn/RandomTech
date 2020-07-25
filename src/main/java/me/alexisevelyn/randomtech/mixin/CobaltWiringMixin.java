package me.alexisevelyn.randomtech.mixin;

import me.alexisevelyn.randomtech.utility.RegistryHelper;
import net.minecraft.block.*;
import net.minecraft.block.enums.WireConnection;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3i;
import net.minecraft.world.BlockView;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@SuppressWarnings("UnusedMixin") // The mixin is used, just is loaded by Fabric and not Sponge methods
@Mixin(RedstoneWireBlock.class)
public abstract class CobaltWiringMixin extends Block {
	/* TODO:
	 * Get blocks to show up visually correctly when placed down
	 * Fix bug where a top level piece of wire can power a lower wire of a different type.
	 */

	public CobaltWiringMixin(Settings settings) {
		super(settings);
	}

	@Shadow protected abstract boolean canRunOnTop(BlockView world, BlockPos pos, BlockState floor);
	@Shadow protected abstract BlockState method_27843(BlockView blockView, BlockState blockState, BlockPos blockPos);

	// The method is currently unmapped (as method_27841), I believe it should be mapped as getRenderConnectionType.
	@Inject(at = @At("INVOKE"), method = "method_27841(Lnet/minecraft/world/BlockView;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/util/math/Direction;Z)Lnet/minecraft/block/enums/WireConnection;", cancellable = true)
	public void getRenderConnectionType(BlockView blockView, BlockPos blockPos, Direction direction, boolean isNotUpwardSolidBlock, CallbackInfoReturnable<WireConnection> info) {
		BlockState currentBlockState = blockView.getBlockState(blockPos);
		BlockPos neighborBlock = blockPos.offset(direction);
		BlockState neighborBlockState = blockView.getBlockState(neighborBlock);

		// If block above neighbor is not a solid block
		if (isNotUpwardSolidBlock) {
			boolean canRunOnTop = this.canRunOnTop(blockView, neighborBlock, neighborBlockState);

			if (canRunOnTop && this.connectsTo(currentBlockState, blockView.getBlockState(neighborBlock.up()), null)) {
				if (neighborBlockState.isSideSolidFullSquare(blockView, neighborBlock, direction.getOpposite())) {
					// Allows the wire to visually go up a block
					info.setReturnValue(WireConnection.UP);
					return;
				}

				// Allows the wire to visually connect to other blocks next to it (visually on the same y level, physically up 1)
				info.setReturnValue(WireConnection.SIDE);
				return;
			}
		}

		info.setReturnValue(!this.connectsTo(currentBlockState, neighborBlockState, direction) && (neighborBlockState.isSolidBlock(blockView, neighborBlock) || !this.connectsTo(currentBlockState, blockView.getBlockState(neighborBlock.down()), null)) ? WireConnection.NONE : WireConnection.SIDE);
	}

	// Is only used visually? Nope
	public boolean connectsTo(BlockState currentBlockState, BlockState neighborBlockState, @Nullable Direction dir) {
		if (isSameWire(currentBlockState.getBlock(), neighborBlockState.getBlock())) {
			return true;
		} else if (isSeparateWire(currentBlockState.getBlock(), neighborBlockState.getBlock())) {
			return false;
		} else if (neighborBlockState.isOf(Blocks.REPEATER)) {
			Direction direction = neighborBlockState.get(RepeaterBlock.FACING);
			return direction == dir || direction.getOpposite() == dir;
		} else if (neighborBlockState.isOf(Blocks.OBSERVER)) {
			return dir == neighborBlockState.get(ObserverBlock.FACING);
		} else {
			return neighborBlockState.emitsRedstonePower() && dir != null;
		}
	}

	// When first placed down?
	@Inject(at = @At("INVOKE"), method = "method_27840(Lnet/minecraft/world/BlockView;Lnet/minecraft/block/BlockState;Lnet/minecraft/util/math/BlockPos;)Lnet/minecraft/block/BlockState;", cancellable = true)
	public void getPlacementState(BlockView world, BlockState currentBlockState, BlockPos pos, CallbackInfoReturnable<BlockState> info) {
		boolean isConnectedNorth = currentBlockState.get(RedstoneWireBlock.WIRE_CONNECTION_NORTH).isConnected();
		boolean isConnectedSouth = currentBlockState.get(RedstoneWireBlock.WIRE_CONNECTION_SOUTH).isConnected();
		boolean isConnectedEast = currentBlockState.get(RedstoneWireBlock.WIRE_CONNECTION_EAST).isConnected();
		boolean isConnectedWest = currentBlockState.get(RedstoneWireBlock.WIRE_CONNECTION_WEST).isConnected();
		boolean isNotConnectedNorthSouth = !isConnectedNorth && !isConnectedSouth;
		boolean isNotConnectedEastWest = !isConnectedEast && !isConnectedWest;

//		boolean isNotConnected = isNotConnected(currentBlockState);
//		currentBlockState = this.method_27843(world, this.getDefaultState().with(RedstoneWireBlock.POWER, currentBlockState.get(RedstoneWireBlock.POWER)), pos);

//		if (isNotConnected && isNotConnected(currentBlockState)) {
//			info.setReturnValue(currentBlockState);
//			return;
//		}

		Vec3i north = sumVectors(pos, Direction.NORTH.getVector());
		Vec3i south = sumVectors(pos, Direction.SOUTH.getVector());
		Vec3i east = sumVectors(pos, Direction.EAST.getVector());
		Vec3i west = sumVectors(pos, Direction.WEST.getVector());

		BlockState northBlockState = world.getBlockState(convertVectorToBlockPos(north));
		BlockState southBlockState = world.getBlockState(convertVectorToBlockPos(south));
		BlockState eastBlockState = world.getBlockState(convertVectorToBlockPos(east));
		BlockState westBlockState = world.getBlockState(convertVectorToBlockPos(west));

//		// This makes it possible to have the wire fully extend to the sides it has nothing to connect to.
//		if (isNotConnectedEastWest && !isConnectedNorth) {
//			currentBlockState.with(RedstoneWireBlock.WIRE_CONNECTION_NORTH, WireConnection.SIDE);
//			modifiedState = true;
//		}
//		if (isNotConnectedEastWest && !isConnectedSouth) {
//			currentBlockState.with(RedstoneWireBlock.WIRE_CONNECTION_SOUTH, WireConnection.SIDE);
//			modifiedState = true;
//		}
//		if (isNotConnectedNorthSouth && !isConnectedEast) {
//			currentBlockState.with(RedstoneWireBlock.WIRE_CONNECTION_EAST, WireConnection.SIDE);
//			modifiedState = true;
//		}
//		if (isNotConnectedNorthSouth && !isConnectedWest) {
//			currentBlockState.with(RedstoneWireBlock.WIRE_CONNECTION_WEST, WireConnection.SIDE);
//			modifiedState = true;
//		}

		// This prevents redstone wire and my wire from connecting to each other visually
		if (isSeparateWire(currentBlockState.getBlock(), northBlockState.getBlock())) {
			currentBlockState.with(RedstoneWireBlock.WIRE_CONNECTION_NORTH, WireConnection.NONE);
			info.setReturnValue(currentBlockState);
		}
		if (isSeparateWire(currentBlockState.getBlock(), southBlockState.getBlock())) {
			currentBlockState.with(RedstoneWireBlock.WIRE_CONNECTION_SOUTH, WireConnection.NONE);
			info.setReturnValue(currentBlockState);
		}
		if (isSeparateWire(currentBlockState.getBlock(), eastBlockState.getBlock())) {
			currentBlockState.with(RedstoneWireBlock.WIRE_CONNECTION_EAST, WireConnection.NONE);
			info.setReturnValue(currentBlockState);
		}
		if (isSeparateWire(currentBlockState.getBlock(), westBlockState.getBlock())) {
			currentBlockState.with(RedstoneWireBlock.WIRE_CONNECTION_WEST, WireConnection.NONE);
			info.setReturnValue(currentBlockState);
		}
	}

	public Vec3i sumVectors(Vec3i vectorOne, Vec3i vectorTwo) {
		int x1 = vectorOne.getX();
		int y1 = vectorOne.getY();
		int z1 = vectorOne.getZ();

		int x2 = vectorTwo.getX();
		int y2 = vectorTwo.getY();
		int z2 = vectorTwo.getZ();

		return new Vec3i(x1+x2, y1+y2, z1+z2);
	}

	public BlockPos convertVectorToBlockPos(Vec3i vector) {
		return new BlockPos(vector);
	}

	public boolean isSeparateWire(Block blockOne, Block blockTwo) {
		// We only care about preventing Cobalt Wire and Redstone Wire from Connecting to each other

		if (blockOne.is(Blocks.REDSTONE_WIRE) && blockTwo.is(RegistryHelper.COBALT_DUST))
			return true;

		return blockOne.is(RegistryHelper.COBALT_DUST) && blockTwo.is(Blocks.REDSTONE_WIRE);
	}

	public boolean isSameWire(Block blockOne, Block blockTwo) {
		// Only check if these are the same wires and nothing else.
		// If one of these is not a wire, it's false.
		
		if (blockOne.is(Blocks.REDSTONE_WIRE) && blockTwo.is(Blocks.REDSTONE_WIRE))
			return true;

		return blockOne.is(RegistryHelper.COBALT_DUST) && blockTwo.is(RegistryHelper.COBALT_DUST);
	}

	private static boolean isNotConnected(BlockState state) {
		return !state.get(RedstoneWireBlock.WIRE_CONNECTION_NORTH).isConnected()
				&& !state.get(RedstoneWireBlock.WIRE_CONNECTION_SOUTH).isConnected()
				&& !state.get(RedstoneWireBlock.WIRE_CONNECTION_EAST).isConnected()
				&& !state.get(RedstoneWireBlock.WIRE_CONNECTION_WEST).isConnected();
	}
}
