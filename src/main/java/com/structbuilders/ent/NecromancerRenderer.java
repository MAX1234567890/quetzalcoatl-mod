package com.structbuilders.ent;

import com.structbuilders.QZEntryPoint;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.render.entity.model.IllagerEntityModel;
import net.minecraft.util.Identifier;

public class NecromancerRenderer extends MobEntityRenderer<NecromancerEntity, IllagerEntityModel<NecromancerEntity>> {
    public NecromancerRenderer(EntityRenderDispatcher renderManager) {
        super(renderManager, new IllagerEntityModel<>(0, 0, 64, 64),1);
    }

    @Override
    public Identifier getTexture(NecromancerEntity entity) {
        return new Identifier(QZEntryPoint.MOD_ID + ":textures/entity/necromancer.png");
    }
}
