package me.alexisevelyn.randomtech.entities.mob;

import me.alexisevelyn.randomtech.utility.RegistryHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MovementType;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.world.World;

public class WizardEntity extends MobEntity {

    public WizardEntity(World world) {
        this(RegistryHelper.WIZARD, world);
    }

    public WizardEntity(EntityType<WizardEntity> entityEntityType, World world) {
        super(entityEntityType, world);
    }
}
