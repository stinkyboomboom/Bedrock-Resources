package com.alexvr.bedres.entities.fluxedcreep;//Made with Blockbench
//Paste this code into your mod.


import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.entity.model.RendererModel;
import net.minecraft.client.renderer.model.ModelBox;
import net.minecraft.entity.Entity;

public class FluxedCreepModel<T extends Entity> extends EntityModel<T> {
	private final RendererModel bone;

	public FluxedCreepModel() {
		textureWidth = 32;
		textureHeight = 32;

		bone = new RendererModel(this);
		bone.setRotationPoint(0.0F, 24.0F, 0.0F);
		bone.cubeList.add(new ModelBox(bone, 0, 0, -4.0F, -12.0F, -4.0F, 8, 8, 8, 0.0F, false));
		bone.cubeList.add(new ModelBox(bone, 0, 16, -2.0F, -10.0F, -2.0F, 4, 4, 4, 0.0F, false));
	}

	@Override
	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
		bone.render(f5);
	}

}