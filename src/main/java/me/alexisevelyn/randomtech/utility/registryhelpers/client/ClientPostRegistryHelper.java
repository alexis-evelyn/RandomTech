package me.alexisevelyn.randomtech.utility.registryhelpers.client;

import me.alexisevelyn.randomtech.api.items.energy.EnergyHelper;
import net.fabricmc.fabric.api.object.builder.v1.client.model.FabricModelPredicateProviderRegistry;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

/**
 * The type Client post registry helper.
 */
public class ClientPostRegistryHelper {
    /**
     * Post register.
     */
    public void postRegister() {
        this.registerEnergyPredicateProviders();
    }

    private void registerEnergyPredicateProviders() {
        // Register All Powered Tools and Armor With FabricModelPredicateProviderRegistry
        Registry.ITEM.stream().forEach(item -> {
            if (item instanceof EnergyHelper) {
                FabricModelPredicateProviderRegistry.register(item, new Identifier("lost_energy"), (stack, world, entity) -> {
                    EnergyHelper energyHelper = (EnergyHelper) item;
                    float currentEnergy = (float) energyHelper.getEnergy(stack);
                    float maxEnergy = (float) energyHelper.getMaxEnergy(stack);

                    return maxEnergy - currentEnergy;
                });
            }
        });
    }
}
