package net.mcreator.enhancing.procedures;
import net.mcreator.enhancing.PlayerStatusManager;

import net.minecraftforge.items.ItemHandlerHelper;

import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.Entity;
import net.minecraft.client.Minecraft;

public class RetrieveEmeraldsProcedure
{
	public static void execute()
	{
		Player _player = Minecraft.getInstance().player;
		
		if (_player == null)
		{
			return;
		}

		int _count = 0;

		if (_player.getPersistentData().getDouble("Emeralds") >= 64)
		{
			_count = 64;
		}
		else
		{
			_count = (int)_player.getPersistentData().getDouble("Emeralds");
		}
		
		if (_player.isShiftKeyDown())
		{
			_count = (int)_player.getPersistentData().getDouble("Emeralds");
		}

		System.out.println("Trying to retrieve " + _count + " emeralds for player " + _player.getName());
		
		ItemStack _setstack = new ItemStack(Items.EMERALD);
		_setstack.setCount(_count);
		PlayerStatusManager.removeEmeralds(_count);
		
		ItemHandlerHelper.giveItemToPlayer(_player, _setstack);
	}
}
