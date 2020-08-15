package me.alexisevelyn.randomtech.entities.mob;

import me.alexisevelyn.randomtech.utility.registryhelpers.main.RegistryHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LightningEntity;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.entity.passive.PigEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

/**
 * The type Wizard entity.
 */
public class WizardEntity extends PathAwareEntity {
    /**
     * Instantiates a new Wizard entity.
     *
     * @param world the world
     */
    @SuppressWarnings("unused")
    public WizardEntity(World world) {
        this(RegistryHelper.WIZARD, world);
    }

    /**
     * Instantiates a new Wizard entity.
     *
     * @param entityEntityType the entity entity type
     * @param world            the world
     */
    public WizardEntity(EntityType<WizardEntity> entityEntityType, World world) {
        super(entityEntityType, world);
    }

    /**
     * Init goals.
     */
    @Override
    protected void initGoals() {
        this.goalSelector.add(1, new SwimGoal(this));

        this.goalSelector.add(2, new MeleeAttackGoal(this, 1.0D, false));

        this.goalSelector.add(3, new WanderAroundFarGoal(this, 0.8D));
        this.goalSelector.add(4, new LookAtEntityGoal(this, PlayerEntity.class, 8.0F));
        this.goalSelector.add(4, new LookAroundGoal(this));

        this.targetSelector.add(1, new FollowTargetGoal<>(this, PlayerEntity.class, true));
    }

    /**
     * Gets safe fall distance.
     *
     * @return the safe fall distance
     */
    @Override
    public int getSafeFallDistance() {
        return this.getTarget() == null ? 3 : 3 + (int)(this.getHealth() - 1.0F);
    }

    /**
     * Write custom data to tag.
     *
     * @param tag the tag
     */
    @Override
    public void writeCustomDataToTag(CompoundTag tag) {
        super.writeCustomDataToTag(tag);
    }

    /**
     * Read custom data from tag.
     *
     * @param tag the tag
     */
    @Override
    public void readCustomDataFromTag(CompoundTag tag) {
        super.readCustomDataFromTag(tag);
    }

    /**
     * Tick.
     */
    @Override
    public void tick() {
        if (this.isAlive()) {
            // Do Nothing For Now!!!
        }

        super.tick();
    }

    /**
     * Gets hurt sound.
     *
     * @param source the source
     * @return the hurt sound
     */
    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return SoundEvents.ENTITY_WITCH_HURT;
    }

    /**
     * Gets death sound.
     *
     * @return the death sound
     */
    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.ENTITY_WITCH_DEATH;
    }

    /**
     * Drop equipment.
     *
     * @param source            the source
     * @param lootingMultiplier the looting multiplier
     * @param allowDrops        the allow drops
     */
    @Override
    protected void dropEquipment(DamageSource source, int lootingMultiplier, boolean allowDrops) {
        super.dropEquipment(source, lootingMultiplier, allowDrops);
    }

    /**
     * Try attack boolean.
     *
     * @param target the target
     * @return the boolean
     */
    @Override
    public boolean tryAttack(Entity target) {
        return target instanceof PigEntity;
    }

    /**
     * On struck by lightning.
     *
     * @param lightning the lightning
     */
    @Override
    public void onStruckByLightning(LightningEntity lightning) {
        super.onStruckByLightning(lightning);
    }

    /**
     * Interact mob action result.
     *
     * @param player the player
     * @param hand   the hand
     * @return the action result
     */
    @Override
    protected ActionResult interactMob(PlayerEntity player, Hand hand) {
        return super.interactMob(player, hand);
    }
}
