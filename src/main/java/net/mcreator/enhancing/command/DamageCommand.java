
package net.mcreator.enhancing.command;

import org.checkerframework.checker.units.qual.s;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.common.util.FakePlayerFactory;

import net.minecraft.world.entity.Entity;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.core.Direction;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.commands.Commands;

import net.mcreator.enhancing.procedures.MagmaWalkerEnchantmentBehaviourProcedure;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.ItemStack;
import net.minecraft.util.RandomSource;
import com.mojang.brigadier.arguments.DoubleArgumentType;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import net.minecraft.world.level.Level;

@Mod.EventBusSubscriber
public class DamageCommand
{
	@SubscribeEvent
	public static void registerCommand(RegisterCommandsEvent event)
	{
		event.getDispatcher().register(Commands.literal("damage").requires(s -> s.hasPermission(2))
			.then(Commands.argument("entity", EntityArgument.entity()).executes(arguments ->
			{
				Entity _entity = arguments.getSource().getEntity();

				if (_entity instanceof LivingEntity _livingEntity)
				{
					DamageUnknownAmount(_livingEntity);
				}
				
				return 0;
				
			}).then(Commands.argument("amount", IntegerArgumentType.integer()).executes(arguments ->
			{
				Entity _entity = arguments.getSource().getEntity();
				int _amount = IntegerArgumentType.getInteger(arguments, "amount");
			
				if (_entity instanceof LivingEntity _livingEntity)
				{
					Damage(_livingEntity, _amount);
				}

				return 0;
			})
		)));
	}

	public static void DamageUnknownAmount(LivingEntity _entity)
	{
		Damage(_entity, 1);
	}

	public static void Damage(LivingEntity _entity, int _amount)
	{
		ItemStack _itemstack = _entity.getItemInHand(InteractionHand.MAIN_HAND);
		_itemstack.hurt(_amount, RandomSource.create(), null);
	}
}
