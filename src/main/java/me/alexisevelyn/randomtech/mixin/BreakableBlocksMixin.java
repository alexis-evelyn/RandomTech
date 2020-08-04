package me.alexisevelyn.randomtech.mixin;

import me.alexisevelyn.randomtech.api.items.tools.generic.GenericPoweredTool;
import net.minecraft.block.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

// This mixin is a 2 in 1. It handles making unbreakable blocks breakable and allows fixing the mining animation for dead tools to not occur.

@SuppressWarnings("UnusedMixin") // The mixin is used, just is loaded by Fabric and not Sponge methods
@Mixin(AbstractBlock.class)
public class BreakableBlocksMixin {
//	@Shadow private float resistance;
//	@Shadow private float hardness;
//	@Shadow private Identifier lootTableId;

//	@Inject(at = @At("TAIL"), method = "strength(FF)Lnet/minecraft/block/AbstractBlock$Settings;", cancellable = true)
//	private void strength(float hardness, float resistance, CallbackInfoReturnable<AbstractBlock.Settings> info) {
//		if (hardness != -1.0)
//			return;
//
//		this.hardness = MiningLevel.POWERED.getValue();
//		this.resistance = Math.max(0.0F, resistance);
//		info.setReturnValue(info.getReturnValue());
//	}
//
//	@Inject(at = @At("TAIL"), method = "dropsNothing()Lnet/minecraft/block/AbstractBlock$Settings;", cancellable = true)
//	public void dropsNothing(CallbackInfoReturnable<AbstractBlock.Settings> info) {
//		// this.lootTableId = LootTables.ABANDONED_MINESHAFT_CHEST;
//
//		info.setReturnValue(info.getReturnValue());
//	}

	// TODO: Test this with an actual server!!!
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

	public boolean disableAnimationForDeadTool(ItemStack itemStack) {
		if (itemStack.getItem() instanceof GenericPoweredTool) {
			GenericPoweredTool genericPoweredTool = (GenericPoweredTool) itemStack.getItem();
			return !genericPoweredTool.isUsable(itemStack);
		}

		return false;
	}

	public boolean isDeniedBlock(Block block) {
		// This is to remove the visual indication of denied blocks which shouldn't be broken in survival.
		// This would've been blocked anyway later on in the code, but I'd like to remove the visual indication of mining the block
		return block instanceof CommandBlock || block instanceof StructureBlock || block instanceof JigsawBlock;
	}
}