package me.alexisevelyn.randomtech.mixin;

import me.alexisevelyn.randomtech.utility.RegistryHelper;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;
import org.apache.commons.lang3.mutable.MutableInt;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

// Check ChunkBlockLightProvider.class
//@Mixin(AbstractBlock.class)
//public class AllowDarkGlass {
//	@Inject(at = @At("HEAD"), method = "getOpacity", cancellable = true)
//
//	private void getOpacity(BlockState state, BlockView world, BlockPos pos, CallbackInfoReturnable<Integer> info) {
//		if (state.getBlock().equals(RegistryHelper.DARK_GLASS))
//			System.out.println("Found Dark Glass At: (" + pos.getX() + ", " + pos.getY() + ", " + pos.getZ() + ")!!!");
//	}
//}


/*
@Mixin(ChunkLightProvider.class)
public class AllowDarkGlass {
	@ModifyVariable(at = @At(value = "FIELD", ordinal = 3), method = "getStateForLighting", print = true)

	protected void getStateForLighting(boolean opacity) {
		opacity = opacity || true;
	}
}
 */