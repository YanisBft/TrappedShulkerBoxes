package com.yanis48.tsb;

import net.fabricmc.api.ModInitializer;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.stat.StatFormatter;
import net.minecraft.stat.Stats;
import net.minecraft.util.DyeColor;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class TSB implements ModInitializer {
	
	public static final String MOD_ID = "tsb";
	
	public static final TrappedShulkerBox TRAPPED_SHULKER_BOX = new TrappedShulkerBox("trapped_shulker_box", (DyeColor) null);
	public static final TrappedShulkerBox TRAPPED_WHITE_SHULKER_BOX = new TrappedShulkerBox("trapped_white_shulker_box", DyeColor.WHITE);
	public static final TrappedShulkerBox TRAPPED_ORANGE_SHULKER_BOX = new TrappedShulkerBox("trapped_orange_shulker_box", DyeColor.ORANGE);
	public static final TrappedShulkerBox TRAPPED_MAGENTA_SHULKER_BOX = new TrappedShulkerBox("trapped_magenta_shulker_box", DyeColor.MAGENTA);
	public static final TrappedShulkerBox TRAPPED_LIGHT_BLUE_SHULKER_BOX = new TrappedShulkerBox("trapped_light_blue_shulker_box", DyeColor.LIGHT_BLUE);
	public static final TrappedShulkerBox TRAPPED_YELLOW_SHULKER_BOX = new TrappedShulkerBox("trapped_yellow_shulker_box", DyeColor.YELLOW);
	public static final TrappedShulkerBox TRAPPED_LIME_SHULKER_BOX = new TrappedShulkerBox("trapped_lime_shulker_box", DyeColor.LIME);
	public static final TrappedShulkerBox TRAPPED_PINK_SHULKER_BOX = new TrappedShulkerBox("trapped_pink_shulker_box", DyeColor.PINK);
	public static final TrappedShulkerBox TRAPPED_GRAY_SHULKER_BOX = new TrappedShulkerBox("trapped_gray_shulker_box", DyeColor.GRAY);
	public static final TrappedShulkerBox TRAPPED_LIGHT_GRAY_SHULKER_BOX = new TrappedShulkerBox("trapped_light_gray_shulker_box", DyeColor.LIGHT_GRAY);
	public static final TrappedShulkerBox TRAPPED_CYAN_SHULKER_BOX = new TrappedShulkerBox("trapped_cyan_shulker_box", DyeColor.CYAN);
	public static final TrappedShulkerBox TRAPPED_PURPLE_SHULKER_BOX = new TrappedShulkerBox("trapped_purple_shulker_box", DyeColor.PURPLE);
	public static final TrappedShulkerBox TRAPPED_BLUE_SHULKER_BOX = new TrappedShulkerBox("trapped_blue_shulker_box", DyeColor.BLUE);
	public static final TrappedShulkerBox TRAPPED_BROWN_SHULKER_BOX = new TrappedShulkerBox("trapped_brown_shulker_box", DyeColor.BROWN);
	public static final TrappedShulkerBox TRAPPED_GREEN_SHULKER_BOX = new TrappedShulkerBox("trapped_green_shulker_box", DyeColor.GREEN);
	public static final TrappedShulkerBox TRAPPED_RED_SHULKER_BOX = new TrappedShulkerBox("trapped_red_shulker_box", DyeColor.RED);
	public static final TrappedShulkerBox TRAPPED_BLACK_SHULKER_BOX = new TrappedShulkerBox("trapped_black_shulker_box", DyeColor.BLACK);
	
	public static final BlockEntityType<TrappedShulkerBoxBlockEntity> TRAPPED_SHULKER_BOX_BE = registerBe("trapped_shulker_box", BlockEntityType.Builder.create(TrappedShulkerBoxBlockEntity::new, TRAPPED_SHULKER_BOX, TRAPPED_WHITE_SHULKER_BOX, TRAPPED_ORANGE_SHULKER_BOX, TRAPPED_MAGENTA_SHULKER_BOX, TRAPPED_LIGHT_BLUE_SHULKER_BOX, TRAPPED_YELLOW_SHULKER_BOX, TRAPPED_LIME_SHULKER_BOX, TRAPPED_PINK_SHULKER_BOX, TRAPPED_GRAY_SHULKER_BOX, TRAPPED_LIGHT_GRAY_SHULKER_BOX, TRAPPED_CYAN_SHULKER_BOX, TRAPPED_PURPLE_SHULKER_BOX, TRAPPED_BLUE_SHULKER_BOX, TRAPPED_BROWN_SHULKER_BOX, TRAPPED_GREEN_SHULKER_BOX, TRAPPED_RED_SHULKER_BOX, TRAPPED_BLACK_SHULKER_BOX));
	
	public static final Identifier TRIGGER_TRAPPED_SHULKER_BOX = registerStat("trigger_trapped_shulker_box", StatFormatter.DEFAULT);
	
	@Override
	public void onInitialize() {
		
	}
	
	private static <T extends BlockEntity> BlockEntityType<T> registerBe(String name, BlockEntityType.Builder<T> builder) {
		return Registry.register(Registry.BLOCK_ENTITY, new Identifier(MOD_ID, name), builder.build(null));
	}
	
	private static Identifier registerStat(String name, StatFormatter formatter) {
		Identifier identifier = new Identifier(MOD_ID, name);
		Registry.register(Registry.CUSTOM_STAT, name, identifier);
		Stats.CUSTOM.getOrCreateStat(identifier, formatter);
		return identifier;
	}
}
