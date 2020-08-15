package me.alexisevelyn.randomtech.entities.models;

import me.alexisevelyn.randomtech.entities.mob.CloudDemonEntity;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.util.math.MatrixStack;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

/**
 * The type Cloud demon model.
 */
// From Tutorial: https://fabricmc.net/wiki/tutorial:entity
public class CloudDemonModel extends EntityModel<CloudDemonEntity> {
    private final ModelPart base;

    /**
     * Instantiates a new Cloud demon model.
     */
    public CloudDemonModel() {
        this.textureHeight = 64;
        this.textureWidth = 64;

        base = new ModelPart(this, 0, 0);
        base.addCuboid(-6, -6, -6, 12, 12, 12);
    }

    /**
     * Sets angles.
     *
     * @param entity            the entity
     * @param limbAngle         the limb angle
     * @param limbDistance      the limb distance
     * @param animationProgress the animation progress
     * @param headYaw           the head yaw
     * @param headPitch         the head pitch
     */
    @Override
    public void setAngles(CloudDemonEntity entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {
        // ???
    }

    /**
     * Render.
     *
     * @param matrices the matrices
     * @param vertices the vertices
     * @param light    the light
     * @param overlay  the overlay
     * @param red      the red
     * @param green    the green
     * @param blue     the blue
     * @param alpha    the alpha
     */
    @Override
    public void render(MatrixStack matrices, VertexConsumer vertices, int light, int overlay, float red, float green, float blue, float alpha) {
        // translate model down
        matrices.translate(0, 1.125, 0);

        // render cube
        base.render(matrices, vertices, light, overlay);
    }

    /**
     * Returns a composed {@code Consumer} that performs, in sequence, this
     * operation followed by the {@code after} operation. If performing either
     * operation throws an exception, it is relayed to the caller of the
     * composed operation.  If performing this operation throws an exception,
     * the {@code after} operation will not be performed.
     *
     * @param after the operation to perform after this operation
     * @return a composed {@code Consumer} that performs in sequence this
     * operation followed by the {@code after} operation
     * @throws NullPointerException if {@code after} is null
     */
    @NotNull
    @Override
    public Consumer<ModelPart> andThen(@NotNull Consumer<? super ModelPart> after) {
        return super.andThen(after);
    }
}
