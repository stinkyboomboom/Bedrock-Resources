package com.alexvr.bedres.entities.effectball;//Made with Blockbench
//Paste this code into your mod.


import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.ModelRenderer;

public class EffectBallModel<T extends EffectBallEntity> extends EntityModel<T> {
	private final ModelRenderer bone;

	public EffectBallModel() {
		textureWidth = 16;
		textureHeight = 16;

		bone = new ModelRenderer(this);
		ModelRenderer modelrenderer1 = new ModelRenderer(this, 24, 0);
		modelrenderer1.mirror = false;
		modelrenderer1.addBox(0, 0, -2.0F, -10.0F, -2.0F, 4, 4, 4, 0.0F);
		bone.addChild(modelrenderer1);

	}

	@Override
	public void setRotationAngles(T t, float v, float v1, float v2, float v3, float v4) {

	}


	@Override
	public void render(MatrixStack matrixStack, IVertexBuilder iVertexBuilder, int i, int i1, float v, float v1, float v2, float f5) {
		bone.render(matrixStack,iVertexBuilder, i,  i1,  v,  v1,  v2, f5);
	}

}