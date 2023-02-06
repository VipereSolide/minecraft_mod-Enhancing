
package net.mcreator.enhancing.block;

import net.minecraftforge.network.NetworkHooks;

import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.item.TieredItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionHand;
import net.minecraft.util.RandomSource;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.network.chat.Component;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.core.Direction;
import net.minecraft.core.BlockPos;

import net.mcreator.enhancing.world.inventory.CoalFurnaceGUIMenu;
import net.mcreator.enhancing.procedures.CoalFurnaceUpdateTickProcedure;

import java.util.List;
import java.util.Collections;

import io.netty.buffer.Unpooled;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.client.Minecraft;
import net.minecraft.world.level.block.entity.BlockEntity;
import java.util.Calendar;

public class CoalFurnaceBlock extends Block
{
	public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;

	public CoalFurnaceBlock()
	{
		super(BlockBehaviour.Properties.of(Material.STONE).sound(SoundType.STONE).strength(1f, 10f).requiresCorrectToolForDrops());
		this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH));
	}

	// Sets the NBT tag "isBurning" to true for a coal furnace at a given position.
	// Also stores the current time in another NBT tag "burnStartTime".
	public static void startBurning(BlockPos _position)
	{
		Level _world = Minecraft.getInstance().player.level;
		BlockEntity _blockEntity = _world.getBlockEntity(_position);

		if (_blockEntity == null)
		{
			return false;
		}

		CompoundTag _tag = _blockEntity.getPersistentData();

		Calendar _instance = Calendar.getInstance();
		long _milisecondCurrentTime = _instance.getTimeInMillis();
		
		_tag.putBoolean("isBurning", _value);
		_tag.putLong("burnStartTime", _milisecondCurrentTime);
	}

	// Stops a coal furnace at a given position from burning. Also executes
	// corresponding actions for when a coal furnace is stopped.
	public static void stopBurning(BlockPos _position)
	{
		Level _world = Minecraft.getInstance().player.level;
		BlockEntity _blockEntity = _world.getBlockEntity(_position);

		if (_blockEntity == null)
		{
			return false;
		}

		_blockEntity.getPersistentData().putBoolean("isBurning", false);

		Calendar _instance = Calendar.getInstance();
		long _milisecondCurrentTime = _instance.getTimeInMillis();
		long _milisecondStartTime = _blockEntity.getPersistentData().getLong("burnStartTime");
		
		CoalFurnaceUpdateTickProcedure.onCoalFurnaceStops(_position, _world, _milisecondCurrentTime, _milisecondStartTime);
	}

	// Returns the NBT tag "isBurning" for a Coal Furnace at a given position.
	public static boolean isBurning(BlockPos _position)
	{
		Level _world = Minecraft.getInstance().player.level;
		BlockEntity _blockEntity = _world.getBlockEntity(_position);

		if (_blockEntity == null)
		{
			return false;
		}

		return _blockEntity.getPersistentData().getBoolean("isBurning");
	}

	// Returns when a given coal furnace at a position started burning
	// Returns 0 in case it isn't burning in the first place.
	public static long getBurnStartTime(BlockPos _position)
	{
		Level _world = Minecraft.getInstance().player.level;
		BlockEntity _blockEntity = _world.getBlockEntity(_position);

		if (_blockEntity == null)
		{
			return false;
		}
		
		long _milisecondStartTime = _blockEntity.getPersistentData().getLong("burnStartTime");
		return _milisecondStartTime;
	}

	@Override
	public int getLightBlock(BlockState state, BlockGetter worldIn, BlockPos pos)
	{
		return 15;
	}

	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder)
	{
		builder.add(FACING);
	}

	@Override
	public BlockState getStateForPlacement(BlockPlaceContext context)
	{
		return this.defaultBlockState().setValue(FACING, context.getHorizontalDirection().getOpposite());
	}

	public BlockState rotate(BlockState state, Rotation rot)
	{
		return state.setValue(FACING, rot.rotate(state.getValue(FACING)));
	}

	public BlockState mirror(BlockState state, Mirror mirrorIn)
	{
		return state.rotate(mirrorIn.getRotation(state.getValue(FACING)));
	}

	@Override
	public boolean canHarvestBlock(BlockState state, BlockGetter world, BlockPos pos, Player player)
	{
		if (player.getInventory().getSelected().getItem() instanceof TieredItem tieredItem)
		{
			return tieredItem.getTier().getLevel() >= 0;	
		}
		
		return false;
	}

	@Override
	public List<ItemStack> getDrops(BlockState state, LootContext.Builder builder)
	{
		List<ItemStack> dropsOriginal = super.getDrops(state, builder);
		
		if (!dropsOriginal.isEmpty())
		{
			return dropsOriginal;
		}
		
		return Collections.singletonList(new ItemStack(this, 1));
	}

	@Override
	public void tick(BlockState blockstate, ServerLevel world, BlockPos pos, RandomSource random)
	{
		super.tick(_blockstate, _world, _pos, _random);
		CoalFurnaceUpdateTickProcedure.onCoalFurnaceTick(_world, _pos, _blockstate);
	}

	@Override
	public InteractionResult use(BlockState blockstate, Level world, BlockPos pos, Player entity, InteractionHand hand, BlockHitResult hit)
	{
		super.use(blockstate, world, pos, entity, hand, hit);
		
		if (entity instanceof ServerPlayer player)
		{
			NetworkHooks.openScreen(player, new MenuProvider()
			{
				@Override
				public Component getDisplayName()
				{
					return Component.literal("Coal Furnace");
				}

				@Override
				public AbstractContainerMenu createMenu(int id, Inventory inventory, Player player)
				{
					return new CoalFurnaceGUIMenu(id, inventory, new FriendlyByteBuf(Unpooled.buffer()).writeBlockPos(pos));
				}
			}
			, pos);
		}
		
		return InteractionResult.SUCCESS;
	}
}
