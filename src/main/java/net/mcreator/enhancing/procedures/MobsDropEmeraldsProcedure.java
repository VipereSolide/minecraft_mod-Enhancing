package net.mcreator.enhancing.procedures;

import org.checkerframework.checker.units.qual.min;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.event.entity.living.LivingDeathEvent;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.Level;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.monster.warden.Warden;
import net.minecraft.world.entity.monster.WitherSkeleton;
import net.minecraft.world.entity.monster.Ghast;
import net.minecraft.world.entity.monster.Blaze;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.boss.wither.WitherBoss;
import net.minecraft.world.entity.boss.enderdragon.EnderDragon;
import net.minecraft.world.entity.ambient.Bat;
import net.minecraft.world.entity.Entity;
import net.minecraft.util.RandomSource;
import net.minecraft.util.Mth;

import javax.annotation.Nullable;

@Mod.EventBusSubscriber
public class MobsDropEmeraldsProcedure {
	@SubscribeEvent
	public static void onEntityDeath(LivingDeathEvent event) {
		if (event != null && event.getEntity() != null) {
			execute(event, event.getEntity().level, event.getEntity().getX(), event.getEntity().getY(), event.getEntity().getZ(), event.getEntity(), event.getSource().getEntity());
		}
	}

	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity, Entity sourceentity) {
		execute(null, world, x, y, z, entity, sourceentity);
	}

	private static void execute(@Nullable Event event, LevelAccessor world, double x, double y, double z, Entity entity, Entity sourceentity) {
		if (entity == null || sourceentity == null)
			return;
		double min = 0;
		double max = 0;
		double random = 0;
		if (sourceentity instanceof Player) {
			min = 1;
			max = 2;
			random = 0.1;
			if (entity instanceof Bat) {
				random = 0.15;
			}
			if (entity instanceof Warden) {
				min = 100;
				max = 150;
				random = 1;
			}
			if (entity instanceof Ghast) {
				min = 1;
				max = 1;
				random = 0.5;
			}
			if (entity instanceof Blaze) {
				min = 1;
				max = 1;
				random = 0.5;
			}
			if (entity instanceof WitherSkeleton) {
				min = 1;
				max = 1;
				random = 0.5;
			}
			if (entity instanceof WitherBoss) {
				min = 100;
				max = 200;
				random = 1;
			}
			if (entity instanceof EnderDragon) {
				min = 300;
				max = 400;
				random = 1;
			}
			if (Math.random() < random) {
				for (int index0 = 0; index0 < (int) (Mth.nextInt(RandomSource.create(), (int) min, (int) max)); index0++) {
					if (world instanceof Level _level && !_level.isClientSide()) {
						ItemEntity entityToSpawn = new ItemEntity(_level, (x + Mth.nextDouble(RandomSource.create(), -0.1, 0.1)), (y + Mth.nextDouble(RandomSource.create(), -0.1, 0.1)), (z + Mth.nextDouble(RandomSource.create(), -0.1, 0.1)),
								new ItemStack(Items.EMERALD));
						entityToSpawn.setPickUpDelay(10);
						entityToSpawn.setUnlimitedLifetime();
						_level.addFreshEntity(entityToSpawn);
					}
				}
			}
		}
	}
}
