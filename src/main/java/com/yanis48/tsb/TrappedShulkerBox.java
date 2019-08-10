package com.yanis48.tsb;

import net.fabricmc.fabric.api.block.FabricBlockSettings;
import net.minecraft.block.BlockState;
import net.minecraft.block.Material;
import net.minecraft.block.ShulkerBoxBlock;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.container.NameableContainerProvider;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.BlockItem;
import net.minecraft.util.DyeColor;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

public class TrappedShulkerBox extends ShulkerBoxBlock {
	private DyeColor color;
	
	public TrappedShulkerBox(String name, DyeColor color) {
		super(color, FabricBlockSettings.of(Material.SHULKER_BOX).strength(0.25f, 30.0f).dynamicBounds().build());
		this.color = color;
		Registry.register(Registry.BLOCK, new Identifier(TSB.MOD_ID, name), this);
		Registry.register(Registry.ITEM, new Identifier(TSB.MOD_ID, name), new BlockItem(this, new Item.Settings().maxCount(1).group(ItemGroup.REDSTONE)));
	}
	
	@Override
	public BlockEntity createBlockEntity(BlockView blockView) {
		return new TrappedShulkerBoxBlockEntity(this.color);
	}
	
	@Override
	public boolean activate(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hitResult) {
		if (world.isClient) {
			return true;
		} else {
			NameableContainerProvider containerProvider = this.createContainerProvider(state, world, pos);
			if (containerProvider != null) {
				player.openContainer(containerProvider);
				player.incrementStat(TSB.TRIGGER_TRAPPED_SHULKER_BOX);
			}
			return true;
		}
	}
	
	public boolean emitsRedstonePower(BlockState state) {
		return true;
	}
	
	public int getWeakRedstonePower(BlockState state, BlockView blockView, BlockPos pos, Direction direction) {
		return MathHelper.clamp(TrappedShulkerBoxBlockEntity.getPlayersLookingInShulkerBoxCount(blockView, pos), 0, 15);
	}
	
	public int getStrongRedstonePower(BlockState state, BlockView blockView, BlockPos pos, Direction direction) {
		return direction == Direction.UP ? state.getWeakRedstonePower(blockView, pos, direction) : 0;
	}

}
