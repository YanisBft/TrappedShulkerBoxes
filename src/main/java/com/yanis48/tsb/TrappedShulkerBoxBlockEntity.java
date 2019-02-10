package com.yanis48.tsb;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.ShulkerBoxBlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;

public class TrappedShulkerBoxBlockEntity extends ShulkerBoxBlockEntity {
	public int viewerCount;
	
	@Override
	public void onInvOpen(PlayerEntity playerEntity_1) {
		if (!playerEntity_1.method_7325()) {
			if (this.viewerCount < 0) {
				this.viewerCount = 0;
			}
			++this.viewerCount;
			this.onInvOpenOrClose();
			if (this.viewerCount == 1) {
				this.world.playSound((PlayerEntity)null, this.pos, SoundEvents.BLOCK_SHULKER_BOX_OPEN, SoundCategory.BLOCK, 0.5F, this.world.random.nextFloat() * 0.1F + 0.9F);
			}
		}
	}
	
	@Override
	public void onInvClose(PlayerEntity playerEntity_1) {
		if (!playerEntity_1.method_7325()) {
			--this.viewerCount;
			this.onInvOpenOrClose();
		}
		if (this.viewerCount <= 0) {
			this.world.playSound((PlayerEntity)null, this.pos, SoundEvents.BLOCK_SHULKER_BOX_CLOSE, SoundCategory.BLOCK, 0.5F, this.world.random.nextFloat() * 0.1F + 0.9F);
		}
	}
	
	public void onInvOpenOrClose() {
		Block block_1 = this.getCachedState().getBlock();
		if (block_1 instanceof TrappedShulkerBox) {
			this.world.addBlockAction(this.pos, block_1, 1, this.viewerCount);
			this.world.updateNeighborsAlways(this.pos, block_1);
		}
	}
	
	public static int getPlayersLookingInShulkerBoxCount(BlockView blockView_1, BlockPos blockPos_1) {
		BlockState blockState_1 = blockView_1.getBlockState(blockPos_1);
		if (blockState_1.getBlock().hasBlockEntity()) {
			BlockEntity blockEntity_1 = blockView_1.getBlockEntity(blockPos_1);
			if (blockEntity_1 instanceof TrappedShulkerBoxBlockEntity) {
				return ((TrappedShulkerBoxBlockEntity)blockEntity_1).viewerCount;
			}
		}
		return 0;
	}	
}
