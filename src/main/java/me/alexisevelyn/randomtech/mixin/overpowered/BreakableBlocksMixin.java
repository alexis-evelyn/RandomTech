package me.alexisevelyn.randomtech.mixin.overpowered;

import me.alexisevelyn.randomtech.api.items.tools.generic.GenericPoweredTool;
import net.minecraft.block.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.LootTables;
import net.minecraft.loot.context.LootContext;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.ArrayList;
import java.util.List;

// This mixin is a 2 in 1. It handles making unbreakable blocks breakable and allows fixing the mining animation for dead tools to not occur.

/**
 * The type Breakable blocks mixin.
 */
@SuppressWarnings("UnusedMixin") // The mixin is used, just is loaded by Fabric and not Sponge methods
@Mixin(AbstractBlock.class)
public abstract class BreakableBlocksMixin {
	/**
	 * Gets loot table id.
	 *
	 * @return the loot table id
	 */
	@Shadow @Final public abstract Identifier getLootTableId();

	@Shadow @Final protected AbstractBlock.Settings settings;

	/**
	 * Calc block breaking delta.
	 *
	 * @param state  the state
	 * @param player the player
	 * @param world  the world
	 * @param pos    the pos
	 * @param info   the info
	 */
	@Inject(at = @At("INVOKE"), method = "calcBlockBreakingDelta(Lnet/minecraft/block/BlockState;Lnet/minecraft/entity/player/PlayerEntity;Lnet/minecraft/world/BlockView;Lnet/minecraft/util/math/BlockPos;)F", cancellable = true)
	public void calcBlockBreakingDelta(BlockState state, PlayerEntity player, BlockView world, BlockPos pos, CallbackInfoReturnable<Float> info) {
		float blockHardness = state.getHardness(world, pos);
		ItemStack itemStack = player.getMainHandStack();

		// Disables Mining Animation For Dead Tools
		if (disableAnimationForDeadTool(itemStack)) {
			info.setReturnValue(0.0F);
			return;
		}

		if (blockHardness == -1.0F) {
			if (itemStack.getItem() instanceof GenericPoweredTool) {
				GenericPoweredTool genericPoweredTool = (GenericPoweredTool) itemStack.getItem();
				Block block = state.getBlock();

				if (genericPoweredTool.canBreakUnbreakableBlock(state, player, world, pos) && !isDeniedBlock(block)) {
					float dynamicBlockHardness = genericPoweredTool.getUnbreakableBlockDifficultyMultiplier(state, player, world, pos);

					// To ensure the hardness is always above 0.
					if (dynamicBlockHardness <= 0.0F)
						dynamicBlockHardness = 1.0F;

					info.setReturnValue(player.getBlockBreakingSpeed(state) / dynamicBlockHardness / 100.0F); // Makes the block have the expected mining speed as if it wasn't unbreakable
					return;
				}
			}

			info.setReturnValue(0.0F); // Makes the block unmineable if not the right tool
			return;
		}

		int effectiveToolMultiplier = player.isUsingEffectiveTool(state) ? 30 : 100;
		info.setReturnValue(player.getBlockBreakingSpeed(state) / blockHardness / effectiveToolMultiplier);
	}

	/**
	 * Disable animation for dead tool boolean.
	 *
	 * @param itemStack the item stack
	 * @return the boolean
	 */
	public boolean disableAnimationForDeadTool(ItemStack itemStack) {
		if (itemStack.getItem() instanceof GenericPoweredTool) {
			GenericPoweredTool genericPoweredTool = (GenericPoweredTool) itemStack.getItem();
			return !genericPoweredTool.isUsable(itemStack);
		}

		return false;
	}

	/**
	 * Is denied block boolean.
	 *
	 * @param block the block
	 * @return the boolean
	 */
	public boolean isDeniedBlock(Block block) {
		// This is to remove the visual indication of denied blocks which shouldn't be broken in survival.
		// This would've been blocked anyway later on in the code, but I'd like to remove the visual indication of mining the block
		return block instanceof CommandBlock || block instanceof StructureBlock || block instanceof JigsawBlock;
	}

	/**
	 * Gets dropped stacks.
	 *
	 * @param state   the state
	 * @param builder the builder
	 * @param info    the info
	 */
    // Modifies Block Drops
	@Inject(at = @At("INVOKE"), method = "getDroppedStacks(Lnet/minecraft/block/BlockState;Lnet/minecraft/loot/context/LootContext$Builder;)Ljava/util/List;", cancellable = true)
	public void getDroppedStacks(BlockState state, LootContext.Builder builder, CallbackInfoReturnable<List<ItemStack>> info) {
		Identifier identifier = this.getLootTableId();
		List<ItemStack> drops = new ArrayList<>();

		// Takes empty loot tables and make drop its own blocks
		if (identifier == LootTables.EMPTY && isUnbreakableBlock(state, builder.getWorld())) {
			ItemStack itemStack = new ItemStack(state.getBlock());
			// builder.getWorld().getBlockEntity(blockPos)

			drops.add(itemStack);
			info.setReturnValue(drops);
		}
	}

	// This below method either doesn't grab the block entity data or is simply not called. No further testing performed.
	// Modify Dropped Stacks to Have Block Entity Data if Any
//	@Inject(at = @At("HEAD"), method = "onStacksDropped(Lnet/minecraft/block/BlockState;Lnet/minecraft/world/World;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/item/ItemStack;)V", cancellable = true)
//	public void onStacksDropped(BlockState state, World world, BlockPos pos, ItemStack stack, CallbackInfo info) {
//		BlockEntity blockEntity = world.getBlockEntity(pos);
//
//		if (blockEntity == null)
//			return;
//
//		CompoundTag rootTag = stack.getOrCreateTag();
//		// CompoundTag blockEntityTag = rootTag.getCompound("BlockEntityTag");
//		CompoundTag blockEntityData = blockEntity.toTag(rootTag);
//
//		stack.putSubTag("BlockEntityTag", blockEntityData);
//		info.cancel();
//	}

	/**
	 * Is unbreakable block boolean.
	 *
	 * @param blockState the block state
	 * @param world      the world
	 * @return the boolean
	 */
	public boolean isUnbreakableBlock(BlockState blockState, World world) {
		// This is a hack. The only reason it works is because getHardness doesn't actually check the world or blockpos.
		return blockState.getHardness(world, new BlockPos(0, 0, 0)) == -1.0;
	}
}