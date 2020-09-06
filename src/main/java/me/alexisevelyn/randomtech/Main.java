package me.alexisevelyn.randomtech;

import me.alexisevelyn.randomtech.utility.registryhelpers.main.PostRegistryHelper;
import me.alexisevelyn.randomtech.utility.registryhelpers.main.PreRegistryHelper;
import me.alexisevelyn.randomtech.utility.registryhelpers.main.RegistryHelper;
import net.fabricmc.api.ModInitializer;

/* TODO: Ordered by importance and logical steps
 *
 * Make enchants useless if armor/tool is broken.
 *
 * Adjust cobalt fuser recipes to be balanced with redstone fuser recipes.
 *
 * Work on entities
 * Add cloud demon to bottle
 *
 * Consider how to obtain Death Ingot
 * Move api to separate jar so people can implement it without needing my mod to be installed?
 *
 * Check if a block needs silk touch for right click info on pickaxe
 *
 * Note: Only suppress warnings which cannot be fixed or should not be fixed (e.g. Mixins)
 * PMD (Codacy) Warnings to Suppress - https://pmd.github.io/latest/pmd_rules_java.html
 * PMD Annotations - https://pmd.github.io/latest/pmd_userdocs_suppressing_warnings.html#annotations
 */

/**
 * The type Main.
 */
// This runs on both the server and the client
public class Main implements ModInitializer {
	public static final String MODID = "randomtech";

	final protected PreRegistryHelper preRegistryHelper = new PreRegistryHelper();
	final protected PostRegistryHelper postRegistryHelper = new PostRegistryHelper();
	final protected RegistryHelper registryHelper = new RegistryHelper();

    /**
     * On initialize.
     */
    @Override
	public void onInitialize() {
		// Client and Server Side!!!

		preRegistryHelper.preRegister();
		registryHelper.register();
		postRegistryHelper.postRegister();
	}
}
