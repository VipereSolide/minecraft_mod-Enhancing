package net.mcreator.enhancing;
import net.mcreator.enhancing.network.EnhancingModVariables;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import java.util.Random;
import net.minecraft.client.Minecraft;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.level.Level;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Player;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.ai.behavior.warden.Emerging;
import net.minecraft.network.syncher.EntityDataAccessor;

public class PlayerStatusManager
{
	public static int EMERALD_COUNT = 0;

	static LevelAccessor _level;
	static Player _player;

	
	public static void addEmeralds(int _amount)
	{
		setEmeraldCount(getEmeraldCount() + _amount);
	}

	public static void removeEmeralds(int _amount)
	{
		setEmeraldCount(getEmeraldCount() - _amount);
	}

	private static void getPlayer()
	{
		if (_player != null)
		{
			return;
		}
		
		_player = Minecraft.getInstance().player;
	}

	public static int getEmeraldCount()
	{
		getPlayer();
		return (int)(_player.getPersistentData().getDouble("Emeralds"));
	}

	public static void setEmeraldCount(int _amount)
	{
		getPlayer();
		
		_player.getPersistentData().putDouble("Emeralds", _amount);
	}
}