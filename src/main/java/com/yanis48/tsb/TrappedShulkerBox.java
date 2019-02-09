package com.yanis48.tsb;

import net.fabricmc.fabric.block.FabricBlockSettings;
import net.minecraft.block.BlockState;
import net.minecraft.block.Material;
import net.minecraft.block.ShulkerBoxBlock;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.block.BlockItem;
import net.minecraft.util.DyeColor;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.BlockView;

public class TrappedShulkerBox extends ShulkerBoxBlock {
	
	public TrappedShulkerBox(String name, DyeColor color) {
		super(color, FabricBlockSettings.of(Material.SHULKER_BOX).strength(0.25f, 30.0f).dynamicBounds().build());
		Registry.BLOCK.register(new Identifier(TSB.MOD_ID, name), this);
		Registry.ITEM.register(new Identifier(TSB.MOD_ID, name), new BlockItem(this, new Item.Settings().stackSize(1).itemGroup(ItemGroup.REDSTONE)));
	}
	
	@Override
	public BlockEntity createBlockEntity(BlockView blockView_1) {
		return new TrappedShulkerBoxBlockEntity();
	}
	
	public boolean emitsRedstonePower(BlockState blockState_1) {
		return true;
	}
	
	public int getWeakRedstonePower(BlockState blockState_1, BlockView blockView_1, BlockPos blockPos_1, Direction direction_1) {
		return MathHelper.clamp(TrappedShulkerBoxBlockEntity.getPlayersLookingInShulkerBoxCount(blockView_1, blockPos_1), 0, 15);
	}
	
	public int getStrongRedstonePower(BlockState blockState_1, BlockView blockView_1, BlockPos blockPos_1, Direction direction_1) {
		return direction_1 == Direction.UP ? blockState_1.getWeakRedstonePower(blockView_1, blockPos_1, direction_1) : 0;
	}

}
