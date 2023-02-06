package net.mcreator.enhancing.procedures;

import net.minecraftforge.server.ServerLifecycleHooks;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.event.TickEvent;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.entity.Entity;
import net.minecraft.server.MinecraftServer;
import net.minecraft.network.chat.Component;

import net.minecraft.world.item.ItemStack;

import javax.annotation.Nullable;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.mcreator.enhancing.init.EnhancingModEnchantments;
import net.mcreator.enhancing.enchantment.MagmaWalkerEnchantment;
import net.mcreator.enhancing.enchantment.ReinforcementEnchantment;
import com.google.common.base.Objects;
import java.util.Iterator;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import java.util.concurrent.atomic.AtomicReference;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import java.util.Collection;

@Mod.EventBusSubscriber
public class MagmaWalkerEnchantmentBehaviourProcedure
{
	private static Vec3 _playerLastPosition;
	
	@SubscribeEvent
	public static void onPlayerTick(TickEvent.PlayerTickEvent event)
	{
		if (event.phase == TickEvent.Phase.END)
		{
			execute(event, event.player.level, event.player);
		}
	}

	public static void execute(LevelAccessor _world, Entity _entity)
	{
		execute(null, _world, _entity);
	}

	public static <T> T getByIndex(Iterable<T> iterable, int index)
	{
        T el = null;
        Iterator<T> it = iterable.iterator();
        
        for (int i = 0; it.hasNext(); i++)
        {
            T cur = it.next();
            
            if (i == index)
            {
                el = cur;
                break;
            }
        }

    	return el;
    }

	private static void execute(@Nullable Event _event, LevelAccessor _world, Entity _playerEntity)
	{
		if (_playerEntity == null)
		{
			return;
		}

		if (_playerEntity instanceof Player _player)
		{
			//System.out.println("Armor slots: " + sizeof(_playerEntity.getArmorSlots()));
			
			// Reinforcement
			for (int _i = 0; _i < 4; _i++)
			{
				ItemStack _itemStack = getByIndex(_playerEntity.getArmorSlots(), _i);
				int _enchantmentLevel = EnchantmentHelper.getItemEnchantmentLevel(EnhancingModEnchantments.REINFORCEMENT.get(), _itemStack);

				if (_enchantmentLevel > 0)
				{
					ReinforcementEnchantment.onTickUpdate(_itemStack, _enchantmentLevel, _player, _playerEntity.level);
				}
			}

			if (_playerEntity.isAlive())
			{
				Vec3 _playerPos = new Vec3(_playerEntity.getX(), _playerEntity.getY(), _playerEntity.getZ());
			
				if (!Objects.equal(_playerLastPosition, _playerPos))
				{
					_playerLastPosition = _playerPos;

					// Magma Walker
					ItemStack _boots = getByIndex(_playerEntity.getArmorSlots(), 0);
					int _enchantmentLevel = EnchantmentHelper.getItemEnchantmentLevel(EnhancingModEnchantments.MAGMA_WALKER.get(), _boots);

					if (_enchantmentLevel > 0)
					{
						MagmaWalkerEnchantment.onEntityMoved(_player, _player.level, new BlockPos((int)_playerPos.x, (int)_playerPos.y, (int)_playerPos.z), _enchantmentLevel, _boots);
					}
				}
			}
		}
	}
}
