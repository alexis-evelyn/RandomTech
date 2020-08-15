package me.alexisevelyn.randomtech.entities.renderers;

import me.alexisevelyn.randomtech.Main;
import me.alexisevelyn.randomtech.entities.mob.CloudDemonEntity;
import me.alexisevelyn.randomtech.entities.models.CloudDemonModel;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.util.Identifier;

/**
 * The type Cloud demon renderer.
 */
public class CloudDemonRenderer extends MobEntityRenderer<CloudDemonEntity, CloudDemonModel> {

    /**
     * Instantiates a new Cloud demon renderer.
     *
     * @param entityRenderDispatcher the entity render dispatcher
     */
    public CloudDemonRenderer(EntityRenderDispatcher entityRenderDispatcher) {
        super(entityRenderDispatcher, new CloudDemonModel(), 0.5F);
    }

    /**
     * Gets texture.
     *
     * @param entity the entity
     * @return the texture
     */
    @Override
    public Identifier getTexture(CloudDemonEntity entity) {
        return new Identifier(Main.MODID, "textures/entity/demon/cloud_demon.png");
    }
}
