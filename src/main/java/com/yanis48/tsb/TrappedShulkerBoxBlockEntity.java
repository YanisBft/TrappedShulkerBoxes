package com.yanis48.tsb;

import java.util.Iterator;
import java.util.List;
import java.util.stream.IntStream;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.LootableContainerBlockEntity;
import net.minecraft.block.entity.ShulkerBoxBlockEntity;
import net.minecraft.block.piston.PistonBehavior;
import net.minecraft.container.Container;
import net.minecraft.container.ShulkerBoxContainer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.MovementType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventories;
import net.minecraft.inventory.SidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.DefaultedList;
import net.minecraft.util.DyeColor;
import net.minecraft.util.Tickable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;

@SuppressWarnings("rawtypes")
public class TrappedShulkerBoxBlockEntity extends LootableContainerBlockEntity implements SidedInventory, Tickable {
	private static final int[] AVAILABLE_SLOTS = IntStream.range(0, 27).toArray();
	private DefaultedList<ItemStack> inventory;
	private int viewerCount;
	private ShulkerBoxBlockEntity.AnimationStage animationStage;
	private float animationProgress;
	private float prevAnimationProgress;
	private DyeColor cachedColor;
	private boolean cachedColorUpdateNeeded;
	
	public TrappedShulkerBoxBlockEntity(DyeColor color) {
		super(TSB.TRAPPED_SHULKER_BOX_BE);
		this.inventory = DefaultedList.ofSize(27, ItemStack.EMPTY);
		this.animationStage = ShulkerBoxBlockEntity.AnimationStage.CLOSED;
		this.cachedColor = color;
	}
	
	public TrappedShulkerBoxBlockEntity() {
		this(null);
		this.cachedColorUpdateNeeded = true;
	}
	
	public void tick() {
		this.updateAnimation();
		if (this.animationStage == ShulkerBoxBlockEntity.AnimationStage.OPENING || this.animationStage == ShulkerBoxBlockEntity.AnimationStage.CLOSING) {
			this.pushEntities();
		}
	}
	
	protected void updateAnimation() {
		this.prevAnimationProgress = this.animationProgress;
		switch(this.animationStage) {
		case CLOSED:
			this.animationProgress = 0.0F;
			break;
		case OPENING:
			this.animationProgress += 0.1F;
			if (this.animationProgress >= 1.0F) {
				this.pushEntities();
				this.animationStage = ShulkerBoxBlockEntity.AnimationStage.OPENED;
				this.animationProgress = 1.0F;
				this.updateNeighborStates();
			}
			break;
		case CLOSING:
			this.animationProgress -= 0.1F;
			if (this.animationProgress <= 0.0F) {
				this.animationStage = ShulkerBoxBlockEntity.AnimationStage.CLOSED;
	            this.animationProgress = 0.0F;
	            this.updateNeighborStates();
			}
			break;
		case OPENED:
			this.animationProgress = 1.0F;
		}
	}
	
	public ShulkerBoxBlockEntity.AnimationStage getAnimationStage() {
		return this.animationStage;
	}
	
	public Box getBoundingBox(BlockState state) {
		return this.getBoundingBox(state.get(TrappedShulkerBox.FACING));
	}
	
	public Box getBoundingBox(Direction direction) {
		float animationProgress = this.getAnimationProgress(1.0F);
		return VoxelShapes.fullCube().getBoundingBox().stretch(0.5F * animationProgress * direction.getOffsetX(), 0.5F * animationProgress * direction.getOffsetY(), 0.5F * animationProgress * direction.getOffsetZ());
	}
	
	private Box getCollisionBox(Direction direction) {
		Direction opposite = direction.getOpposite();
		return this.getBoundingBox(direction).shrink(opposite.getOffsetX(), opposite.getOffsetY(), opposite.getOffsetZ());
	}
	
	private void pushEntities() {
		BlockState state = this.world.getBlockState(this.getPos());
		if (state.getBlock() instanceof TrappedShulkerBox) {
			Direction direction = state.get(TrappedShulkerBox.FACING);
			Box box = this.getCollisionBox(direction).offset(this.pos);
			List<Entity> list = this.world.getEntities((Entity) null, box);
			if (!list.isEmpty()) {
				for(int i = 0; i < list.size(); ++i) {
					Entity entity = list.get(i);
					if (entity.getPistonBehavior() != PistonBehavior.IGNORE) {
						double x = 0.0D;
						double y = 0.0D;
						double z = 0.0D;
						Box boundingBox = entity.getBoundingBox();
						switch(direction.getAxis()) {
						case X:
							if (direction.getDirection() == Direction.AxisDirection.POSITIVE) {
								x = box.maxX - boundingBox.minX;
							} else {
								x = boundingBox.maxX - box.minX;
							}
							x += 0.01D;
							break;
						case Y:
							if (direction.getDirection() == Direction.AxisDirection.POSITIVE) {
								y = box.maxY - boundingBox.minY;
							} else {
								y = boundingBox.maxY - box.minY;
							}
							y += 0.01D;
							break;
						case Z:
							if (direction.getDirection() == Direction.AxisDirection.POSITIVE) {
								z = box.maxZ - boundingBox.minZ;
							} else {
								z = boundingBox.maxZ - box.minZ;
							}
							z += 0.01D;
						}
						entity.move(MovementType.SHULKER_BOX, new Vec3d(x * direction.getOffsetX(), y * direction.getOffsetY(), z * direction.getOffsetZ()));
					}
				}
			}
		}
	}
	
	public int getInvSize() {
		return this.inventory.size();
	}
	
	public boolean onBlockAction(int int_1, int int_2) {
		if (int_1 == 1) {
			this.viewerCount = int_2;
			if (int_2 == 0) {
				this.animationStage = ShulkerBoxBlockEntity.AnimationStage.CLOSING;
				this.updateNeighborStates();
			}
			if (int_2 == 1) {
				this.animationStage = ShulkerBoxBlockEntity.AnimationStage.OPENING;
				this.updateNeighborStates();
			}
			return true;
		} else {
			return super.onBlockAction(int_1, int_2);
		}
	}
	
	private void updateNeighborStates() {
		this.getCachedState().updateNeighborStates(this.getWorld(), this.getPos(), 3);
	}
	
	public void onInvOpen(PlayerEntity player) {
		if (!player.isSpectator()) {
			if (this.viewerCount < 0) {
				this.viewerCount = 0;
			}
			++this.viewerCount;
			this.onInvOpenOrClose();
			if (this.viewerCount == 1) {
				this.world.playSound(null, this.pos, SoundEvents.BLOCK_SHULKER_BOX_OPEN, SoundCategory.BLOCKS, 0.5F, this.world.random.nextFloat() * 0.1F + 0.9F);
			}
		}
	}
	
	public void onInvClose(PlayerEntity player) {
		if (!player.isSpectator()) {
			--this.viewerCount;
			this.onInvOpenOrClose();
			if (this.viewerCount <= 0) {
				this.world.playSound(null, this.pos, SoundEvents.BLOCK_SHULKER_BOX_CLOSE, SoundCategory.BLOCKS, 0.5F, this.world.random.nextFloat() * 0.1F + 0.9F);
			}
		}
	}
	
	private void onInvOpenOrClose() {
		Block block = this.getCachedState().getBlock();
		if (block instanceof TrappedShulkerBox) {
			this.world.addBlockAction(this.pos, block, 1, this.viewerCount);
			this.world.updateNeighborsAlways(this.pos, block);
		}
	}
	
	public static int getPlayersLookingInShulkerBoxCount(BlockView blockView, BlockPos pos) {
		BlockState state = blockView.getBlockState(pos);
		if (state.getBlock().hasBlockEntity()) {
			BlockEntity be = blockView.getBlockEntity(pos);
			if (be instanceof TrappedShulkerBoxBlockEntity) {
				return ((TrappedShulkerBoxBlockEntity) be).viewerCount;
			}
		}
		return 0;
	}
	
	protected Text getContainerName() {
		return new TranslatableText("container.shulkerBox", new Object[0]);
	}
	
	public void fromTag(CompoundTag tag) {
		super.fromTag(tag);
		this.deserializeInventory(tag);
	}
	
	public CompoundTag toTag(CompoundTag tag) {
		super.toTag(tag);
		return this.serializeInventory(tag);
	}
	
	public void deserializeInventory(CompoundTag tag) {
		this.inventory = DefaultedList.ofSize(this.getInvSize(), ItemStack.EMPTY);
		if (!this.deserializeLootTable(tag) && tag.containsKey("Items", 9)) {
			Inventories.fromTag(tag, this.inventory);
		}
	}
	
	public CompoundTag serializeInventory(CompoundTag tag) {
		if (!this.serializeLootTable(tag)) {
			Inventories.toTag(tag, this.inventory, false);
		}
		return tag;
	}
	
	protected DefaultedList<ItemStack> getInvStackList() {
		return this.inventory;
	}
	
	protected void setInvStackList(DefaultedList<ItemStack> list) {
		this.inventory = list;
	}
	
	public boolean isInvEmpty() {
		Iterator i = this.inventory.iterator();
		ItemStack stack;
		do {
			if (!i.hasNext()) {
				return true;
			}
			stack = (ItemStack) i.next();
		} while(stack.isEmpty());
		return false;
	}
	
	public int[] getInvAvailableSlots(Direction direction) {
		return AVAILABLE_SLOTS;
	}
	
	public boolean canInsertInvStack(int int_1, ItemStack stack, Direction direction) {
		return !(Block.getBlockFromItem(stack.getItem()) instanceof TrappedShulkerBox);
	}
	
	public boolean canExtractInvStack(int int_1, ItemStack stack, Direction direction) {
		return true;
	}
	
	public float getAnimationProgress(float float_1) {
		return MathHelper.lerp(float_1, this.prevAnimationProgress, this.animationProgress);
	}
	
	@Environment(EnvType.CLIENT)
	public DyeColor getColor() {
		if (this.cachedColorUpdateNeeded) {
			this.cachedColor = TrappedShulkerBox.getColor(this.getCachedState().getBlock());
			this.cachedColorUpdateNeeded = false;
		}
		return this.cachedColor;
	}
	
	protected Container createContainer(int int_1, PlayerInventory playerInv) {
		return new ShulkerBoxContainer(int_1, playerInv, this);
	}
}
