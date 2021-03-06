package me.alexisevelyn.randomtech.items;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtOps;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.dynamic.GlobalPos;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import reborncore.common.chunkloading.ChunkLoaderManager;

import java.util.List;
import java.util.Optional;

/**
 * The type Teleporter linker.
 */
public class TeleporterLinker extends Item {

    /**
     * Instantiates a new Teleporter linker.
     *
     * @param settings the settings
     */
    public TeleporterLinker(Settings settings) {
        super(settings);
    }

    /**
     * Use typed action result.
     *
     * @param world        the world
     * @param playerEntity the player entity
     * @param hand         the hand
     * @return the typed action result
     */
    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity playerEntity, Hand hand) {
        //playerEntity.playSound(SoundEvents.MUSIC_DISC_PIGSTEP, 1.0F, 2.0F);

        if (playerEntity.isInSneakingPose()) {
            ItemStack stack = playerEntity.getStackInHand(hand);

            GlobalPos globalPos = GlobalPos.create(ChunkLoaderManager.getDimensionRegistryKey(world), playerEntity.getBlockPos());

            GlobalPos.CODEC.encodeStart(NbtOps.INSTANCE, globalPos).result()
                    .ifPresent(tag -> stack.getOrCreateTag().put("pos", tag));

            alertPositionSaved(playerEntity, globalPos);
        }

        return new TypedActionResult<>(ActionResult.SUCCESS, playerEntity.getStackInHand(hand));
    }

    /**
     * Alert position saved.
     *
     * @param playerEntity the player entity
     * @param globalPos    the global pos
     */
    private void alertPositionSaved(PlayerEntity playerEntity, GlobalPos globalPos) {
        BlockPos blockPos = globalPos.getPos();

        Text message = new TranslatableText("message.randomtech.teleporter_linker_saved",
                new LiteralText(String.valueOf(blockPos.getX()))
                        .formatted(Formatting.DARK_GREEN, Formatting.BOLD),
                new LiteralText(String.valueOf(blockPos.getY()))
                        .formatted(Formatting.DARK_GREEN, Formatting.BOLD),
                new LiteralText(String.valueOf(blockPos.getZ()))
                        .formatted(Formatting.DARK_GREEN, Formatting.BOLD),
                new LiteralText(globalPos.getDimension().getValue().toString())
                        .formatted(Formatting.DARK_GREEN, Formatting.BOLD))
                .formatted(Formatting.GOLD, Formatting.BOLD);

        playerEntity.sendMessage(message, true);
    }

    /**
     * Append tooltip.
     *
     * @param stack   the stack
     * @param worldIn the world in
     * @param tooltip the tooltip
     * @param flagIn  the flag in
     */
    @Environment(EnvType.CLIENT)
    @Override
    public void appendTooltip(ItemStack stack, @Nullable World worldIn, List<Text> tooltip, TooltipContext flagIn) {
        getPosition(stack).ifPresent(globalPos -> {
            BlockPos blockPos = globalPos.getPos();

            Text coordinates = new TranslatableText("message.randomtech.teleporter_linker_tooltip_coordinates",
                    new LiteralText(String.valueOf(blockPos.getX()))
                            .formatted(Formatting.DARK_GREEN, Formatting.BOLD),
                    new LiteralText(String.valueOf(blockPos.getY()))
                            .formatted(Formatting.DARK_GREEN, Formatting.BOLD),
                    new LiteralText(String.valueOf(blockPos.getZ()))
                            .formatted(Formatting.DARK_GREEN, Formatting.BOLD))
                    .formatted(Formatting.GOLD, Formatting.BOLD);

            Text dimension = new TranslatableText("message.randomtech.teleporter_linker_tooltip_dimension",
                    new LiteralText(globalPos.getDimension().getValue().toString())
                            .formatted(Formatting.DARK_GREEN, Formatting.BOLD))
                    .formatted(Formatting.GOLD, Formatting.BOLD);

            tooltip.add(coordinates);
            tooltip.add(dimension);
        });
    }

    /**
     * Gets position.
     *
     * @param stack the stack
     * @return the position
     */
    private Optional<GlobalPos> getPosition(ItemStack stack) {
        if (!stack.hasTag() || !stack.getOrCreateTag().contains("pos"))
            return Optional.empty();

        return GlobalPos.CODEC.parse(NbtOps.INSTANCE, stack.getOrCreateTag().getCompound("pos")).result();
    }
}