package com.alexvr.bedres.entities.fluxedcreep;//Made with Blockbench
//Paste this code into your mod.


import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class FluxedCreepModel<T extends Entity> extends EntityModel<T> {
	private final ModelRenderer bone;

	public FluxedCreepModel() {
		textureWidth = 32;
		textureHeight = 32;


		bone = new ModelRenderer(this);
		ModelRenderer modelrenderer1 = new ModelRenderer(this, 24, 0);
		modelrenderer1.mirror = false;
		modelrenderer1.addBox(0, 0, -4.0F, -12.0F, -4.0F, 8, 8, 8, 0.0F);
		bone.addChild(modelrenderer1);
		ModelRenderer modelrenderer2 = new ModelRenderer(this, 24, 0);
		modelrenderer1.mirror = false;
		modelrenderer1.addBox(0, 16, -2.0F, -10.0F, -2.0F, 4, 4, 4, 0.0F);
		bone.addChild(modelrenderer2);


	}

	@Override
	public void setRotationAngles(T t, float v, float v1, float v2, float v3, float v4) {

	}

	@Override
	public void render(MatrixStack matrixStack, IVertexBuilder iVertexBuilder, int i, int i1, float v, float v1, float v2, float f5) {
		bone.render(matrixStack,iVertexBuilder, i,  i1,  v,  v1,  v2, f5);
	}

}