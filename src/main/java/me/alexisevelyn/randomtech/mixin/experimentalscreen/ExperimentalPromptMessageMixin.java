package me.alexisevelyn.randomtech.mixin.experimentalscreen;

import net.minecraft.client.MinecraftClient;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

// https://discordapp.com/channels/142425412096491520/626802111455297538/746517959672987830

/**
 * The type Gamerule based backup screen mixin.
 */
@SuppressWarnings("UnusedMixin") // The mixin is used, just is loaded by Fabric and not Sponge methods
@Mixin(MinecraftClient.class)
public abstract class ExperimentalPromptMessageMixin {
    private static final String BACKUP_QUESTION = "selectWorld.backupQuestion.experimental"; // Worlds using Experimental Settings are not supported
    private static final String BACKUP_WARNING = "selectWorld.backupWarning.experimental"; // This world uses experimental settings that could stop working at any time. We cannot guarantee it will load or work. Here be dragons!

    private static final String NEW_BACKUP_QUESTION = "selectWorld.backupQuestion.experimental.replaced";
    private static final String NEW_BACKUP_WARNING = "selectWorld.backupWarning.experimental.replaced";

    /**
     *
     * @param identifier
     * @return
     */
    @ModifyArg(method = "method_29601(Lnet/minecraft/client/MinecraftClient$WorldLoadAction;Ljava/lang/String;ZLjava/lang/Runnable;)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/text/TranslatableText;<init>(Ljava/lang/String;)V"))
    private String modifyExperimentalMessages(String identifier) {
        if (identifier.equals(BACKUP_QUESTION))
            return NEW_BACKUP_QUESTION;
        else if (identifier.equals(BACKUP_WARNING))
            return NEW_BACKUP_WARNING;

        return identifier;
    }
}