package net.mcreator.enhancing.procedures;

import java.util.Random;
import java.util.Map;

import net.minecraftforge.server.ServerLifecycleHooks;
import net.minecraftforge.registries.ForgeRegistries;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.Level;

import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.item.enchantment.Enchantment;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.ItemStack;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;

import net.minecraft.server.MinecraftServer;

import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundSource;

import net.minecraft.network.chat.Component;

public class EnchantingRuneRightclickedProcedure
{
	public static void execute(LevelAccessor world, Entity entity, ItemStack itemstack)
	{	
		if (entity == null)
		{
			return;
		}

		ItemStack __offHandItemStack = (entity instanceof LivingEntity _livEnt ? _livEnt.getOffhandItem() : ItemStack.EMPTY);
		ItemStack __mainHandItemStack = (entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY);
		Boolean __runeInOffHand = __offHandItemStack.getItem() == itemstack.getItem();
		Boolean __holdItemInMainHand = __mainHandItemStack.getItem() != (ItemStack.EMPTY).getItem();

		if (__runeInOffHand && __holdItemInMainHand)
		{	
			// No need to do anything if the item in the main hand is actually not enchanted.
			if (!__mainHandItemStack.isEnchanted())
			{
				return;
			}

			Map<Enchantment, Integer> __enchants = EnchantmentHelper.getEnchantments(__mainHandItemStack);
			int __randomIndex = getRandomNumberInRange(0, __enchants.size());
			int __currentIndex = 0; // Will be "i" in a for (int i) pattern.
			
			for (Map.Entry<Enchantment, Integer> __entry : __enchants.entrySet())
			{
				Boolean __isRightEnchantment = __currentIndex == __randomIndex;
				Boolean __enchantmentBelow10 = __entry.getValue() < 10;
				
				if (__isRightEnchantment)
				{
					// We don't want to upgrade the item if it already is level 10.
					if (__enchantmentBelow10)
					{
						ApplyEnchantments(world, entity, __enchants, __entry, __mainHandItemStack, itemstack);
						return;
					}
					else
					{
						// We pick a new random number here so tries and level up another enchantment. You can disable that
						// if you think this is too op.
						__randomIndex = getRandomNumberInRange(0, __enchants.size());
					}
				}
				
				__currentIndex++;
			}
		}
	}

	public static void ApplyEnchantments(LevelAccessor _world, Entity _entity, Map<Enchantment, Integer> _enchantments, Map.Entry<Enchantment, Integer> _entry, ItemStack _mainHandItemStack, ItemStack _itemstack)
	{
		// Upgrade the enchantment to the next level.
		_enchantments.replace(_entry.getKey(), _entry.getValue(), _entry.getValue() + 1);
		EnchantmentHelper.setEnchantments(_enchantments, _mainHandItemStack);

		// Renders a totem like animation with the rune.
		if (_world.isClientSide())
		{
			Minecraft.getInstance().gameRenderer.displayItemActivation(_itemstack);
		}

		// Destroys the rune (clear the off-hand of the player).
		if (_entity instanceof LivingEntity __entity)
		{
			// Plays smithing table sound and enchanting table sound.
			if (_world instanceof Level __level)
			{
				if (!__level.isClientSide())
				{
					__level.playSound(null, new BlockPos(__entity.getX(), __entity.getY(), __entity.getZ()), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("block.smithing_table.use")), SoundSource.PLAYERS, 1, 1);
					__level.playSound(null, new BlockPos(__entity.getX(), __entity.getY(), __entity.getZ()), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("block.enchantment_table.use")), SoundSource.PLAYERS, 1, 1);
				}
				else
				{
					__level.playLocalSound(__entity.getX(), __entity.getY(), __entity.getZ(), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("block.smithing_table.use")), SoundSource.PLAYERS, 1, 1, false);
					__level.playLocalSound(__entity.getX(), __entity.getY(), __entity.getZ(), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("block.enchantment_table.use")), SoundSource.PLAYERS, 1, 1, false);
				}
			}
				
			ItemStack __setstack = (ItemStack.EMPTY);
			__setstack.setCount(1);
			__entity.setItemInHand(InteractionHand.OFF_HAND, __setstack);
		
			if (__entity instanceof Player __player)
			{
				__player.getInventory().setChanged();
			}		
		}	
	}

	public static void SendChat(String message, LevelAccessor world)
	{
		if (!world.isClientSide())
		{
			MinecraftServer _mcserv = ServerLifecycleHooks.getCurrentServer();
			
			if (_mcserv != null)
			{
				_mcserv.getPlayerList().broadcastSystemMessage(Component.literal(message), false);
			}
		}
	}

	public static int getRandomNumberInRange(int min, int max)
	{

		if (min >= max)
		{
			throw new IllegalArgumentException("max must be greater than min");
		}

		Random r = new Random();
		return r.nextInt((max - min) + 1) + min;
	}
}
