package net.mcreator.enhancing.procedures;
import net.mcreator.enhancing.PlayerStatusManager;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;

import javax.annotation.Nullable;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.InteractionHand;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.GameType;
import net.minecraft.world.entity.vehicle.Minecart;
import net.minecraft.client.Minecraft;

@Mod.EventBusSubscriber
public class CollectEmeraldsProcedure {
	@SubscribeEvent
	public static void onRightClickItem(PlayerInteractEvent.RightClickItem event) {
		if (event.getHand() != event.getEntity().getUsedItemHand())
			return;
		execute(event, event.getLevel(), event.getPos().getX(), event.getPos().getY(), event.getPos().getZ(), event.getEntity());
	}

	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
		execute(null, world, x, y, z, entity);
	}

	public static boolean isCreative(Entity _ent)
	{
		if (_ent instanceof ServerPlayer _serverPlayer)
		{
			return _serverPlayer.gameMode.getGameModeForPlayer() == GameType.CREATIVE;
		}
		else if (_ent.level.isClientSide() && _ent instanceof Player _player)
		{
			return 	Minecraft.getInstance().getConnection().getPlayerInfo(_player.getGameProfile().getId()) != null &&
					Minecraft.getInstance().getConnection().getPlayerInfo(_player.getGameProfile().getId()).getGameMode() == GameType.CREATIVE;
		}

		return false;
	}

	private static void execute(@Nullable Event event, LevelAccessor world, double x, double y, double z, Entity entity)
	{
		if (entity == null)
		{
			return;
		}

		if (entity instanceof LivingEntity _livEnt)
		{
			if (_livEnt instanceof Player _player)
			{
				ItemStack _emeraldStack = _player.getItemInHand(InteractionHand.MAIN_HAND);

				if (_emeraldStack.getItem() != Items.EMERALD)
				{
					_emeraldStack = _player.getItemInHand(InteractionHand.OFF_HAND);

					if (_emeraldStack.getItem() != Items.EMERALD)
					{
						return;
					}
				}

				int _itemsCount = 1;

				if (_player.isShiftKeyDown())
				{
					_itemsCount = _emeraldStack.getCount();
				}
				
				if (!isCreative(_player))
				{
					_player.getInventory().clearOrCountMatchingItems(p -> new ItemStack(Items.EMERALD).getItem() == p.getItem(), _itemsCount, _player.inventoryMenu.getCraftSlots());
					_emeraldStack.shrink(_itemsCount);
				}

				PlayerStatusManager.addEmeralds(_itemsCount);
			}
		}
	}
}
