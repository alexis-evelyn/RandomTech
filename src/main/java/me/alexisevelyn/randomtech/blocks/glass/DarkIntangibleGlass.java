package me.alexisevelyn.randomtech.blocks.glass;

import me.alexisevelyn.randomtech.utility.GenericBlockHelper;
import me.alexisevelyn.randomtech.utility.Materials;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.api.renderer.v1.Renderer;
import net.fabricmc.fabric.api.renderer.v1.RendererAccess;
import net.minecraft.block.AbstractGlassBlock;
import net.minecraft.block.BlockState;
import net.minecraft.client.gl.ShaderEffect;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.text.LiteralText;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

// TODO: Add inverted intangible glass which only blocks the player
// TODO: Fix rendering issue on edges of glass - This shows up on every glass block from my mod, but not vanilla (artifacts?)
// TODO: Allow connecting different types of glass together
// TODO: Fix xray ability caused by dark glass
// TODO: Investigate if these suggestions help: https://discordapp.com/channels/507304429255393322/721100785936760876/731144592644374538
public class DarkIntangibleGlass extends AbstractGlassBlock {
    public DarkIntangibleGlass() {
        super(FabricBlockSettings
                .of(Materials.DARK_GLASS_MATERIAL)
                .sounds(BlockSoundGroup.GLASS)
                .nonOpaque() // Fixes xray issue. Also allows light pass through block
//                .noCollision() // Allows walking through block. Also allows light pass through block
                .collidable(false) // Allows Disabling Collision Without Allowing Light to Pass Through (Cannot set after block initialized)
                .allowsSpawning(GenericBlockHelper::never) // Allows or denies spawning
                .solidBlock(GenericBlockHelper::never) // ??? - Seems to have no apparent effect
                .suffocates(GenericBlockHelper::never) // Suffocates player
                .blockVision(GenericBlockHelper::never) // Blocks Vision inside of block
                .strength(0.3F, 0.3F)
        );
    }

    @Override
    public int getOpacity(BlockState state, BlockView world, BlockPos pos) {
        return 15;
    }

    // Only gets called if block is not collidable by Block Settings
    @Override
    public void onEntityCollision(BlockState state, World world, BlockPos pos, Entity entity) {
        //super.onEntityCollision(state, world, pos, entity);

        //entity.setVelocity(entity.getVelocity().negate().multiply(2));

        if (entity instanceof PlayerEntity)
            ((PlayerEntity) entity).sendMessage(new LiteralText("Entity Collision Detected!!!"), true);
    }

    // Only works if block is collidable by Block Settings
    @Override
    public void onProjectileHit(World world, BlockState state, BlockHitResult hit, ProjectileEntity projectile) {
        super.onProjectileHit(world, state, hit, projectile);

        //if (projectile.getOwner() instanceof PlayerEntity)
        //    ((PlayerEntity) projectile.getOwner()).sendMessage(new LiteralText("Projectile Collision Detected!!!"), false);
    }

    // Only works if entity is on top (not even falling) while block is collidable by Block Settings
    @Override
    public void onEntityLand(BlockView world, Entity entity) {
        //super.onEntityLand(world, entity);

        //entity.doesNotCollide(0, 0, 0);

        //if (entity instanceof PlayerEntity)
        //    ((PlayerEntity) entity).sendMessage(new LiteralText("On Entity Land Detected!!!"), true);
    }

    // Only works if entity lands on top (falling only) while block is collidable by Block Settings
    @Override
    public void onLandedUpon(World world, BlockPos pos, Entity entity, float distance) {
        super.onLandedUpon(world, pos, entity, distance);

//        if (entity instanceof PlayerEntity)
//            ((PlayerEntity) entity).sendMessage(new LiteralText("On Landed Upon Detected!!!"), false);
    }
}
