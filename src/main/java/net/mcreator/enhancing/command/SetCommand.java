
package net.mcreator.enhancing.command;

import org.checkerframework.checker.units.qual.s;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.event.RegisterCommandsEvent;

import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.coordinates.BlockPosArgument;
import net.minecraft.world.entity.Entity;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.ai.goal.BegGoal;
import net.minecraft.world.phys.Vec3;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.server.level.ServerLevel;
import java.util.List;
import javax.annotation.Nullable;
import com.google.common.collect.Lists;
import net.minecraft.world.level.block.state.pattern.BlockInWorld;
import net.minecraft.world.level.block.state.pattern.BlockInWorld;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.nbt.CompoundTag;

@Mod.EventBusSubscriber
public class SetCommand
{
	public static BlockPos COPY_BEGIN_POSITION = null;
	public static BlockPos COPY_END_POSITION = null;
	public static BlockPos COPY_COPY_ORIGIN_POSITION = null;
	public static boolean IS_CLIPBOARD_EMPTY = true;

	public static List<PasteBlockInfo> UNDO = Lists.newArrayList();

	@SubscribeEvent
	public static void registerCommand(RegisterCommandsEvent event)
	{
		event.getDispatcher().register(Commands.literal("set").requires(s -> s.hasPermission(2))
			.then(
				Commands.literal("copy")
				.then(
					Commands.argument("begin", BlockPosArgument.blockPos())
					.then(
						Commands.argument("end", BlockPosArgument.blockPos())
						.executes((_context) ->
						{
							Entity _entity = _context.getSource().getEntityOrException();
							
							copySelectionInClipboard(
								_context.getSource(),
								BlockPosArgument.getLoadedBlockPos(_context, "begin"),
								BlockPosArgument.getLoadedBlockPos(_context, "end"),
								_entity.getOnPos()
							);

							return 0;
						})
					)
				)
			)
			.then(
				Commands.literal("paste")
				.then(
					Commands.argument("pastePosition", BlockPosArgument.blockPos())
					.executes((_context) ->
					{
						pasteSelectionFromClipboard(
							_context.getSource(),
							BlockPosArgument.getLoadedBlockPos(_context, "pastePosition")
						);
						
						return 0;
					})
				)
			)
			.then(
				Commands.literal("clearMemory")
				.executes((_context) ->
				{
					clearClipboard();
					
					return 0;
				})
			)
			.then(
				Commands.literal("undo")
				.executes((_context) ->
				{
					undoPaste(_context.getSource());

					return 0;
				})
			)
		);
	}

	public static void undoPaste(CommandSourceStack _source)
	{
		List<PasteBlockInfo> _tempUndo = UNDO;
		UNDO.clear();

		ServerLevel _level = _source.getLevel();
		
		for(int _i = 0; _i < _tempUndo.size(); _i++)
		{
			BlockPos _offset = _source.getEntity().getOnPos().subtract(COPY_COPY_ORIGIN_POSITION);
			BlockPos _pos = _tempUndo.get(_i).pos.offset(_offset);
				
			System.out.println("Undoing: " + _pos.getX() + ", " + _pos.getY() + ", " + _pos.getZ());

			BlockInWorld _blockInWorld = new BlockInWorld(_level, _pos, false);
			BlockState _blockState = _blockInWorld.getState();
			UNDO.add(new PasteBlockInfo(_pos, _blockState, null));
			
			_level.setBlock(_pos, _tempUndo.get(_i).state, 2);
		}
	}

	public static void clearClipboard()
	{
		IS_CLIPBOARD_EMPTY = true;
	}

	public static void copySelectionInClipboard(CommandSourceStack _source, BlockPos _begin, BlockPos _end, BlockPos _playerPosition)
	{
		BoundingBox _boundings = BoundingBox.fromCorners(_begin, _end);
		int _volume = _boundings.getXSpan() * _boundings.getYSpan() * _boundings.getZSpan();

		String _beginPos = "[" + _begin.getX() + "; " + _begin.getY() + "; " + _begin.getZ() + "]";
		String _endPos = "[" + _end.getX() + "; " + _end.getY() + "; " + _end.getZ() + "]";

		COPY_BEGIN_POSITION = _begin;
		COPY_END_POSITION = _end;
		COPY_COPY_ORIGIN_POSITION = _playerPosition;
		IS_CLIPBOARD_EMPTY = false;
		
		_source.sendSuccess(Component.literal("Successfully copied " + _volume + " blocks in memory (from " + _beginPos + " to " + _endPos + ")."), true);
	}

	public static void pasteSelectionFromClipboard(CommandSourceStack _source, BlockPos _pastePosition)
	{
		if (IS_CLIPBOARD_EMPTY)
		{
			_source.sendFailure(Component.literal("The memory was empty. Please store a selection in memory before pasting it (you can use /set copy to get a selection into memory)."));
			return;
		}

		UNDO.clear();

		BoundingBox _boundings = BoundingBox.fromCorners(COPY_BEGIN_POSITION, COPY_END_POSITION);
		int _volume = _boundings.getXSpan() * _boundings.getYSpan() * _boundings.getZSpan();

		ServerLevel _level = _source.getLevel();
		boolean _isSelectionInsideWorld = _level.hasChunksAt(COPY_BEGIN_POSITION, COPY_END_POSITION);
		boolean _isPastedInsideWorld = _level.hasChunksAt(
			COPY_COPY_ORIGIN_POSITION,
			COPY_COPY_ORIGIN_POSITION.offset(_boundings.getLength())
		);

		if (_isSelectionInsideWorld && _isPastedInsideWorld)
		{
			List<PasteBlockInfo> _pasteBlocks = Lists.newArrayList();
			
			for (int _x = _boundings.minX(); _x <= _boundings.maxX(); ++_x)
			{
				for (int _y = _boundings.minY(); _y <= _boundings.maxY(); ++_y)
				{
					for (int _z = _boundings.minZ(); _z <= _boundings.maxZ(); ++_z)
					{
						System.out.println("Position: " + _x + ", " + _y + ", " + _z);
						
						BlockPos _blockPos = new BlockPos(_x,_y,_z);
						BlockInWorld _blockInWorld = new BlockInWorld(_level, _blockPos, false);
						BlockState _blockState = _blockInWorld.getState();
						
						_pasteBlocks.add(new PasteBlockInfo(_blockPos, _blockState, null));
					}	
				}
			}

			System.out.println("Blocks amount: " + _pasteBlocks.size());

			for(int _i = 0; _i < _pasteBlocks.size(); _i++)
			{
				BlockPos _offset = _source.getEntity().getOnPos().subtract(COPY_COPY_ORIGIN_POSITION);
				BlockPos _pos = _pasteBlocks.get(_i).pos.offset(_offset);
				
				System.out.println("Creating block at: " + _pos.getX() + ", " + _pos.getY() + ", " + _pos.getZ());

				BlockInWorld _blockInWorld = new BlockInWorld(_level, _pos, false);
				BlockState _blockState = _blockInWorld.getState();
				UNDO.add(new PasteBlockInfo(_pos, _blockState, null));
				
				_level.setBlock(_pos, _pasteBlocks.get(_i).state, 2);
			}
		}
		
		_source.sendSuccess(Component.literal("Successfully pasted " + _volume + " blocks."), true);
	}

	static class PasteBlockInfo
	{
      	public final BlockPos pos;
      	public final BlockState state;

      	@Nullable
      	public final CompoundTag tag;

      	public PasteBlockInfo(BlockPos _pos, BlockState _state, @Nullable CompoundTag _tag)
      	{
         	this.pos = _pos;
         	this.state = _state;
         	this.tag = _tag;
    	}
   	}
}