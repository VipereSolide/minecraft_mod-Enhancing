
package net.mcreator.enhancing.command;

import net.mcreator.enhancing.procedures.BirdStaffRightclickedProcedure;
import net.mcreator.enhancing.procedures.FireMasterStaffRightclickedProcedure;
import net.mcreator.enhancing.item.MysticalCrossbowItem;
import net.mcreator.enhancing.item.HuntingBowItem;
import org.checkerframework.checker.units.qual.s;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.event.RegisterCommandsEvent;

import net.minecraft.commands.Commands;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.arguments.DoubleArgumentType;
import com.mojang.brigadier.arguments.FloatArgumentType;

import net.mcreator.enhancing.enchantment.ReinforcementEnchantment;

@Mod.EventBusSubscriber
public class SetDataCommand {
	@SubscribeEvent
	public static void registerCommand(RegisterCommandsEvent event) {
		event.getDispatcher().register(Commands.literal("setdata").requires(s -> s.hasPermission(2))
			.then(Commands.literal("items")
				.then(Commands.literal("magic")
					.then(Commands.literal("bird_staff")
						.then(Commands.literal("flightHeight")
							.then(Commands.argument("velocity", IntegerArgumentType.integer(0))
								.executes((_e) ->
								{
									BirdStaffRightclickedProcedure.FLIGHT_HEIGHT = IntegerArgumentType.getInteger(_e, "velocity");
									return 0;
								})
							)
						)
						.then(Commands.literal("flightCooldown")
							.then(Commands.argument("seconds", DoubleArgumentType.doubleArg(0))
								.executes((_e) ->
								{
									BirdStaffRightclickedProcedure.USE_COOLDOWN_SECONDS = (float)DoubleArgumentType.getDouble(_e, "seconds");
									return 0;
								})
							)
						)
					)
					.then(Commands.literal("fire_master_staff")
						.then(Commands.literal("fireball_power")
							.then(Commands.argument("power", FloatArgumentType.floatArg(0))
								.executes((_e) ->
								{
									FireMasterStaffRightclickedProcedure.FIREBALL_POWER = FloatArgumentType.getFloat(_e, "power");
									return 0;
								})
							)
						)
						.then(Commands.literal("detection_range")
							.then(Commands.argument("range", IntegerArgumentType.integer(0))
								.executes((_e) ->
								{
									FireMasterStaffRightclickedProcedure.DETECTION_RANGE = IntegerArgumentType.getInteger(_e, "range");
									return 0;
								})
							)
						)
					)
				)
				.then(Commands.literal("weapons")
					.then(Commands.literal("range")
						.then(Commands.literal("hunting_bow")
							.then(Commands.literal("default_range")
								.then(Commands.argument("range", IntegerArgumentType.integer(0))
									.executes((_e) ->
									{
										HuntingBowItem.DEFAULT_RANGE = IntegerArgumentType.getInteger(_e, "range");
										return 0;
									})
								)
							)
							.then(Commands.literal("max_draw_duration")
								.then(Commands.argument("duration", IntegerArgumentType.integer(0))
									.executes((_e) ->
									{
										HuntingBowItem.MAX_DRAW_DURATION = IntegerArgumentType.getInteger(_e, "duration");
										return 0;
									})
								)
							)
							.then(Commands.literal("arrow_power")
								.then(Commands.argument("power", FloatArgumentType.floatArg(0))
									.executes((_e) ->
									{
										HuntingBowItem.ARROW_POWER = FloatArgumentType.getFloat(_e, "power");
										return 0;
									})
								)
							)
						)
						.then(Commands.literal("mystical_crossbow")
							.then(Commands.literal("default_range")
								.then(Commands.argument("range", FloatArgumentType.floatArg(0))
									.executes((_e) ->
									{
										MysticalCrossbowItem.DEFAULT_RANGE = IntegerArgumentType.getInteger(_e, "range");
										return 0;
									})
								)
							)
							.then(Commands.literal("arrow_power")
								.then(Commands.argument("power", FloatArgumentType.floatArg())
									.executes((_e) ->
									{
										MysticalCrossbowItem.ARROW_POWER = FloatArgumentType.getFloat(_e, "power");
										return 0;
									})
								)
							)
							.then(Commands.literal("firework_power")
								.then(Commands.argument("power", FloatArgumentType.floatArg())
									.executes((_e) ->
									{
										MysticalCrossbowItem.FIREWORK_POWER = FloatArgumentType.getFloat(_e, "power");
										return 0;
									})
								)
							)
							.then(Commands.literal("multishot_amplitude")
								.then(Commands.argument("amplitude", FloatArgumentType.floatArg(0))
									.executes((_e) ->
									{
										MysticalCrossbowItem.MULTISHOT_AMPLITUDE = FloatArgumentType.getFloat(_e, "amplitude");
										return 0;
									})
								)
							)
							.then(Commands.literal("multishot_projectile_multiplier")
								.then(Commands.argument("multiplier", FloatArgumentType.floatArg(0))
									.executes((_e) ->
									{
										MysticalCrossbowItem.MULTISHOT_PROJECTILE_MULTIPLIER = FloatArgumentType.getFloat(_e, "multiplier");
										return 0;
									})
								)
							)
						)
					)
				)
			)
			.then(Commands.literal("enchantments")
				.then(Commands.literal("reinforcement")
					.then(Commands.literal("repair_tick_rate")
						.then(Commands.argument("tickrate", IntegerArgumentType.integer(1))
							.executes((_e) ->
							{
								ReinforcementEnchantment.REPAIR_RARITY = IntegerArgumentType.getInteger(_e, "tickrate");
								return 0;
							})
						)
					)
				)
			)
		);
	}
}
