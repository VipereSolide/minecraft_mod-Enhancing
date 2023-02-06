package net.mcreator.enhancing.procedures;

import net.minecraftforge.server.ServerLifecycleHooks;

import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.server.MinecraftServer;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;

import java.util.stream.Collectors;
import java.util.List;
import java.util.Comparator;

public class AmethystDaggerAttackProcedure
{
	public static void execute(LevelAccessor world, Entity entity, Entity sourceentity, ItemStack itemstack)
	{
		if (entity == null || sourceentity == null)
		{
			return;
		}
		
		entity.hurt(DamageSource.GENERIC, 3);
		double __knockback = -0.25;
		double __verticalKnockback = -0.1;
		
		if (entity instanceof LivingEntity _entity)
		{
			_entity.addEffect(new MobEffectInstance(MobEffects.WITHER, 35, 3));
			_entity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 35, 1));

			final Vec3 __center = new Vec3(entity.getX(), entity.getY(), entity.getZ());
			List<Entity> __entfound = world.getEntitiesOfClass(Entity.class, new AABB(__center, __center).inflate(2 / 2d), e -> true).stream().sorted(Comparator.comparingDouble(__entcnd -> __entcnd.distanceToSqr(__center))).collect(Collectors.toList());
			
			for (Entity __entityiterator : __entfound)
			{
				if (entity instanceof LivingEntity __livingEntityIterrator)
				{
					__livingEntityIterrator.addEffect(new MobEffectInstance(MobEffects.WITHER, 35, 3));
					__livingEntityIterrator.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 35, 1));
				}
			}
		}
		
		entity.setDeltaMovement(new Vec3(
				(entity.getDeltaMovement().x() + sourceentity.getLookAngle().x * __knockback),
				(entity.getDeltaMovement().y() + sourceentity.getLookAngle().y * __verticalKnockback),
				(entity.getDeltaMovement().z() + sourceentity.getLookAngle().z * __knockback)
		));
	}
}