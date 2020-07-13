package me.alexisevelyn.randomtech.entities.renderers;

import me.alexisevelyn.randomtech.Main;
import me.alexisevelyn.randomtech.entities.mob.WizardEntity;
import me.alexisevelyn.randomtech.entities.models.WizardModel;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.util.Identifier;

public class WizardRenderer extends MobEntityRenderer<WizardEntity, WizardModel> {

    public WizardRenderer(EntityRenderDispatcher entityRenderDispatcher) {
        super(entityRenderDispatcher, new WizardModel(), 0.5F);
    }

    @Override
    public Identifier getTexture(WizardEntity entity) {
        return new Identifier(Main.MODID, "textures/entity/wizard/wizard.png");
    }
}
