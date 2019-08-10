package com.yanis48.tsb;

import com.mojang.blaze3d.platform.GlStateManager;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.BlockState;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.entity.ShulkerEntityRenderer;
import net.minecraft.client.render.entity.model.ShulkerEntityModel;
import net.minecraft.util.DyeColor;
import net.minecraft.util.math.Direction;

@Environment(EnvType.CLIENT)
public class TrappedShulkerBoxBlockEntityRenderer extends BlockEntityRenderer<TrappedShulkerBoxBlockEntity> {
	private final ShulkerEntityModel<?> model;
	
	public TrappedShulkerBoxBlockEntityRenderer(ShulkerEntityModel<?> shulkerModel) {
		this.model = shulkerModel;
	}
	
	public void render(TrappedShulkerBoxBlockEntity be, double double_1, double double_2, double double_3, float float_1, int int_1) {
		Direction direction = Direction.UP;
		if (be.hasWorld()) {
			BlockState state = this.getWorld().getBlockState(be.getPos());
			if (state.getBlock() instanceof TrappedShulkerBox) {
				direction = state.get(TrappedShulkerBox.FACING);
			}
		}
		GlStateManager.enableDepthTest();
		GlStateManager.depthFunc(515);
		GlStateManager.depthMask(true);
		GlStateManager.disableCull();
		if (int_1 >= 0) {
			this.bindTexture(DESTROY_STAGE_TEXTURES[int_1]);
			GlStateManager.matrixMode(5890);
			GlStateManager.pushMatrix();
			GlStateManager.scalef(4.0F, 4.0F, 1.0F);
			GlStateManager.translatef(0.0625F, 0.0625F, 0.0625F);
			GlStateManager.matrixMode(5888);
		} else {
			DyeColor color = be.getColor();
			if (color == null) {
				this.bindTexture(ShulkerEntityRenderer.SKIN);
			} else {
				this.bindTexture(ShulkerEntityRenderer.SKIN_COLOR[color.getId()]);
			}
		}
		GlStateManager.pushMatrix();
		GlStateManager.enableRescaleNormal();
		if (int_1 < 0) {
			GlStateManager.color4f(1.0F, 1.0F, 1.0F, 1.0F);
		}
		GlStateManager.translatef((float) double_1 + 0.5F, (float) double_2 + 1.5F, (float) double_3 + 0.5F);
		GlStateManager.scalef(1.0F, -1.0F, -1.0F);
		GlStateManager.translatef(0.0F, 1.0F, 0.0F);
		GlStateManager.scalef(0.9995F, 0.9995F, 0.9995F);
		GlStateManager.translatef(0.0F, -1.0F, 0.0F);
		switch(direction) {
		case DOWN:
			GlStateManager.translatef(0.0F, 2.0F, 0.0F);
			GlStateManager.rotatef(180.0F, 1.0F, 0.0F, 0.0F);
		case UP:
		default:
			break;
		case NORTH:
			GlStateManager.translatef(0.0F, 1.0F, 1.0F);
			GlStateManager.rotatef(90.0F, 1.0F, 0.0F, 0.0F);
			GlStateManager.rotatef(180.0F, 0.0F, 0.0F, 1.0F);
			break;
		case SOUTH:
			GlStateManager.translatef(0.0F, 1.0F, -1.0F);
			GlStateManager.rotatef(90.0F, 1.0F, 0.0F, 0.0F);
			break;
		case WEST:
			GlStateManager.translatef(-1.0F, 1.0F, 0.0F);
			GlStateManager.rotatef(90.0F, 1.0F, 0.0F, 0.0F);
			GlStateManager.rotatef(-90.0F, 0.0F, 0.0F, 1.0F);
			break;
		case EAST:
			GlStateManager.translatef(1.0F, 1.0F, 0.0F);
			GlStateManager.rotatef(90.0F, 1.0F, 0.0F, 0.0F);
			GlStateManager.rotatef(90.0F, 0.0F, 0.0F, 1.0F);
		}
		this.model.method_2831().render(0.0625F);
		GlStateManager.translatef(0.0F, -be.getAnimationProgress(float_1) * 0.5F, 0.0F);
		GlStateManager.rotatef(270.0F * be.getAnimationProgress(float_1), 0.0F, 1.0F, 0.0F);
		this.model.method_2829().render(0.0625F);
		GlStateManager.enableCull();
		GlStateManager.disableRescaleNormal();
		GlStateManager.popMatrix();
		GlStateManager.color4f(1.0F, 1.0F, 1.0F, 1.0F);
		if (int_1 >= 0) {
			GlStateManager.matrixMode(5890);
			GlStateManager.popMatrix();
			GlStateManager.matrixMode(5888);
		}
	}
}
