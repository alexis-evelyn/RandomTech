package me.alexisevelyn.randomtech.mixin;

import me.alexisevelyn.randomtech.utility.registryhelpers.main.RegistryHelper;
import net.minecraft.block.*;
import net.minecraft.block.enums.WireConnection;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.BlockView;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
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

	@SuppressWarnings("unused")
	public CobaltWiringMixin(Settings settings) {
		super(settings);
	}

	@Shadow protected abstract boolean canRunOnTop(BlockView world, BlockPos pos, BlockState floor);
	// @Shadow protected abstract BlockState method_27843(BlockView blockView, BlockState blockState, BlockPos blockPos);

	@Shadow @Final private BlockState dotShape;

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

	@Inject(at = @At("INVOKE"), method = "getPlacementState(Lnet/minecraft/item/ItemPlacementContext;)Lnet/minecraft/block/BlockState;", cancellable = true)
	public void getPlacementState(ItemPlacementContext ctx, CallbackInfoReturnable<BlockState> info) {
		// This allows for fixing most of the visual connection issues provided by redstone wire wanting to connect to cobalt wire and vice versa.
		neighborUpdate(this.dotShape, ctx.getWorld(), ctx.getBlockPos(), this.dotShape.getBlock(), ctx.getBlockPos(), true);
	}

	public boolean isSeparateWire(Block blockOne, Block blockTwo) {
		// We only care about preventing Cobalt Wire and Redstone Wire from Connecting to each other

		if (blockOne.is(Blocks.REDSTONE_WIRE) && blockTwo.is(RegistryHelper.COBALT_WIRE))
			return true;

		return blockOne.is(RegistryHelper.COBALT_WIRE) && blockTwo.is(Blocks.REDSTONE_WIRE);
	}

	public boolean isSameWire(Block blockOne, Block blockTwo) {
		// Only check if these are the same wires and nothing else.
		// If one of these is not a wire, it's false.
		
		if (blockOne.is(Blocks.REDSTONE_WIRE) && blockTwo.is(Blocks.REDSTONE_WIRE))
			return true;

		return blockOne.is(RegistryHelper.COBALT_WIRE) && blockTwo.is(RegistryHelper.COBALT_WIRE);
	}
}
