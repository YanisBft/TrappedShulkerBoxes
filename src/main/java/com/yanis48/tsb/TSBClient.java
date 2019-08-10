package com.yanis48.tsb;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.render.BlockEntityRendererRegistry;
import net.minecraft.client.render.entity.model.ShulkerEntityModel;

@SuppressWarnings("rawtypes")
public class TSBClient implements ClientModInitializer {
	
	@Override
	public void onInitializeClient() {
		BlockEntityRendererRegistry.INSTANCE.register(TrappedShulkerBoxBlockEntity.class, new TrappedShulkerBoxBlockEntityRenderer(new ShulkerEntityModel()));
	}
}
