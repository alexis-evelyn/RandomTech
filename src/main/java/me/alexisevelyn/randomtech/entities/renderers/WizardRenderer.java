package me.alexisevelyn.randomtech.entities.renderers;

import me.alexisevelyn.randomtech.Main;
import me.alexisevelyn.randomtech.entities.mob.WizardEntity;
import me.alexisevelyn.randomtech.entities.models.WizardModel;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.util.Identifier;

/**
 * The type Wizard renderer.
 */
public class WizardRenderer extends MobEntityRenderer<WizardEntity, WizardModel> {

    /**
     * Instantiates a new Wizard renderer.
     *
     * @param entityRenderDispatcher the entity render dispatcher
     */
    public WizardRenderer(EntityRenderDispatcher entityRenderDispatcher) {
        super(entityRenderDispatcher, new WizardModel(), 0.5F);
    }

    /**
     * Gets texture.
     *
     * @param entity the entity
     * @return the texture
     */
    @Override
    public Identifier getTexture(WizardEntity entity) {
        return new Identifier(Main.MODID, "textures/entity/wizard/wizard.png");
    }
}
