package com.yanis48.tsb;

import net.fabricmc.api.ModInitializer;
import net.minecraft.util.DyeColor;

public class TSB implements ModInitializer {
	
	public static final String MOD_ID = "tsb";
	
	public static TrappedShulkerBox TRAPPED_SHULKER_BOX;
	public static TrappedShulkerBox TRAPPED_WHITE_SHULKER_BOX;
	public static TrappedShulkerBox TRAPPED_ORANGE_SHULKER_BOX;
	public static TrappedShulkerBox TRAPPED_MAGENTA_SHULKER_BOX;
	public static TrappedShulkerBox TRAPPED_LIGHT_BLUE_SHULKER_BOX;
	public static TrappedShulkerBox TRAPPED_YELLOW_SHULKER_BOX;
	public static TrappedShulkerBox TRAPPED_LIME_SHULKER_BOX;
	public static TrappedShulkerBox TRAPPED_PINK_SHULKER_BOX;
	public static TrappedShulkerBox TRAPPED_GRAY_SHULKER_BOX;
	public static TrappedShulkerBox TRAPPED_LIGHT_GRAY_SHULKER_BOX;
	public static TrappedShulkerBox TRAPPED_CYAN_SHULKER_BOX;
	public static TrappedShulkerBox TRAPPED_PURPLE_SHULKER_BOX;
	public static TrappedShulkerBox TRAPPED_BLUE_SHULKER_BOX;
	public static TrappedShulkerBox TRAPPED_BROWN_SHULKER_BOX;
	public static TrappedShulkerBox TRAPPED_GREEN_SHULKER_BOX;
	public static TrappedShulkerBox TRAPPED_RED_SHULKER_BOX;
	public static TrappedShulkerBox TRAPPED_BLACK_SHULKER_BOX;
	
	@Override
	public void onInitialize() {
		
		TRAPPED_SHULKER_BOX = new TrappedShulkerBox("trapped_shulker_box", (DyeColor)null);
		TRAPPED_WHITE_SHULKER_BOX = new TrappedShulkerBox("trapped_white_shulker_box", DyeColor.WHITE);
		TRAPPED_ORANGE_SHULKER_BOX = new TrappedShulkerBox("trapped_orange_shulker_box", DyeColor.ORANGE);
		TRAPPED_MAGENTA_SHULKER_BOX = new TrappedShulkerBox("trapped_magenta_shulker_box", DyeColor.MAGENTA);
		TRAPPED_LIGHT_BLUE_SHULKER_BOX = new TrappedShulkerBox("trapped_light_blue_shulker_box", DyeColor.LIGHT_BLUE);
		TRAPPED_YELLOW_SHULKER_BOX = new TrappedShulkerBox("trapped_yellow_shulker_box", DyeColor.YELLOW);
		TRAPPED_LIME_SHULKER_BOX = new TrappedShulkerBox("trapped_lime_shulker_box", DyeColor.LIME);
		TRAPPED_PINK_SHULKER_BOX = new TrappedShulkerBox("trapped_pink_shulker_box", DyeColor.PINK);
		TRAPPED_GRAY_SHULKER_BOX = new TrappedShulkerBox("trapped_gray_shulker_box", DyeColor.GRAY);
		TRAPPED_LIGHT_GRAY_SHULKER_BOX = new TrappedShulkerBox("trapped_light_gray_shulker_box", DyeColor.LIGHT_GRAY);
		TRAPPED_CYAN_SHULKER_BOX = new TrappedShulkerBox("trapped_cyan_shulker_box", DyeColor.CYAN);
		TRAPPED_PURPLE_SHULKER_BOX = new TrappedShulkerBox("trapped_purple_shulker_box", DyeColor.PURPLE);
		TRAPPED_BLUE_SHULKER_BOX = new TrappedShulkerBox("trapped_blue_shulker_box", DyeColor.BLUE);
		TRAPPED_BROWN_SHULKER_BOX = new TrappedShulkerBox("trapped_brown_shulker_box", DyeColor.BROWN);
		TRAPPED_GREEN_SHULKER_BOX = new TrappedShulkerBox("trapped_green_shulker_box", DyeColor.GREEN);
		TRAPPED_RED_SHULKER_BOX = new TrappedShulkerBox("trapped_red_shulker_box", DyeColor.RED);
		TRAPPED_BLACK_SHULKER_BOX = new TrappedShulkerBox("trapped_black_shulker_box", DyeColor.BLACK);
	}
}
