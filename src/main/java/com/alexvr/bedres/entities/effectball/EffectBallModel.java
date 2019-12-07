package com.alexvr.bedres.entities.effectball;//Made with Blockbench
//Paste this code into your mod.


import com.mojang.blaze3d.platform.GlStateManager;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.entity.model.RendererModel;
import net.minecraft.client.renderer.model.ModelBox;
import net.minecraft.item.DyeColor;
import net.minecraft.item.DyeItem;
import org.lwjgl.opengl.GL11;

public class EffectBallModel<T extends EffectBallEntity> extends EntityModel<T> {
	private final RendererModel bone;

	public EffectBallModel() {
		textureWidth = 16;
		textureHeight = 16;

		bone = new RendererModel(this);
		bone.setRotationPoint(0.0F, 24.0F, 0.0F);
		bone.cubeList.add(new ModelBox(bone, 0, 0, -2.0F, -10.0F, -2.0F, 4, 4, 4, 0.0F, false));
	}

	@Override
	public void render(EffectBallEntity entity, float f, float f1, float f2, float f3, float f4, float f5) {
		if (((DyeItem)entity.getDye().getItem()).getDyeColor() == DyeColor.WHITE){
            GlStateManager.enableBlend();
            GlStateManager.blendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
			GL11.glColor4d(0,0,0,.3);
		}else if (((DyeItem)entity.getDye().getItem()).getDyeColor() == DyeColor.ORANGE){
			GlStateManager.enableBlend();
			GlStateManager.blendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
			GL11.glColor4d(255,128,0,.3);
		}else if (((DyeItem)entity.getDye().getItem()).getDyeColor() == DyeColor.MAGENTA){
			GlStateManager.enableBlend();
			GlStateManager.blendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
			GL11.glColor4d(128,0,128,.3);
		}else if (((DyeItem)entity.getDye().getItem()).getDyeColor() == DyeColor.LIGHT_BLUE){
			GlStateManager.enableBlend();
			GlStateManager.blendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
			GL11.glColor4d(153,204,255,.3);
		}else if (((DyeItem)entity.getDye().getItem()).getDyeColor() == DyeColor.YELLOW){
			GlStateManager.enableBlend();
			GlStateManager.blendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
			GL11.glColor4d(255,255,0,.3);
		}else if (((DyeItem)entity.getDye().getItem()).getDyeColor() == DyeColor.LIME){
			GlStateManager.enableBlend();
			GlStateManager.blendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
			GL11.glColor4d(128,255,0,.3);
		}else if (((DyeItem)entity.getDye().getItem()).getDyeColor() == DyeColor.PINK){
			GlStateManager.enableBlend();
			GlStateManager.blendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
			GL11.glColor4d(255,204,204,.3);
		}else if (((DyeItem)entity.getDye().getItem()).getDyeColor() == DyeColor.GRAY){
			GlStateManager.enableBlend();
			GlStateManager.blendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
			GL11.glColor4d(128,128,128,.3);
		}else if (((DyeItem)entity.getDye().getItem()).getDyeColor() == DyeColor.LIGHT_GRAY){
			GlStateManager.enableBlend();
			GlStateManager.blendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
			GL11.glColor4d(192,192,192,.3);
		}else if (((DyeItem)entity.getDye().getItem()).getDyeColor() == DyeColor.CYAN){
			GlStateManager.enableBlend();
			GlStateManager.blendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
			GL11.glColor4d(153,255,255,.3);
		}else if (((DyeItem)entity.getDye().getItem()).getDyeColor() == DyeColor.PURPLE){
			GlStateManager.enableBlend();
			GlStateManager.blendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
			GL11.glColor4d(127,0,255,.3);
		}else if (((DyeItem)entity.getDye().getItem()).getDyeColor() == DyeColor.BLUE){
			GlStateManager.enableBlend();
			GlStateManager.blendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
			GL11.glColor4d(51,51,255,.3);
		}else if (((DyeItem)entity.getDye().getItem()).getDyeColor() == DyeColor.BROWN){
			GlStateManager.enableBlend();
			GlStateManager.blendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
			GL11.glColor4d(102,51,0,.3);
		}else if (((DyeItem)entity.getDye().getItem()).getDyeColor() == DyeColor.GREEN){
			GlStateManager.enableBlend();
			GlStateManager.blendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
			GL11.glColor4d(0,102,0,.3);
		}else if (((DyeItem)entity.getDye().getItem()).getDyeColor() == DyeColor.RED){
			GlStateManager.enableBlend();
			GlStateManager.blendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
			GL11.glColor4d(255,0,0,.3);
		}else if (((DyeItem)entity.getDye().getItem()).getDyeColor() == DyeColor.BLACK){
			GlStateManager.enableBlend();
			GlStateManager.blendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
			GL11.glColor4d(0,0,0,.3);
		}
		bone.render(f5);
	}

}