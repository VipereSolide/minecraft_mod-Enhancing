package net.mcreator.enhancing.procedures;

import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.util.RandomSource;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;

import java.util.stream.Collectors;
import java.util.List;
import java.util.Comparator;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.ItemStack;
import viperesolide.math.VMath;
import io.netty.util.internal.MathUtil;

public class ScytheAttackProcedure
{
	private static final float RADIUS = 3f;
	private static final float KNOCKBACK = 0.5f;
	private static final float VERTICAL_KNOCKBACK = 0.25f;
	private static final float MIN_DAMAGE = 5f;
	private static final float MAX_DAMAGE = 7.5f;

	public static void execute(LevelAccessor _world, double _x, double _y, double _z, Entity _hitEnemy, Entity _playerEntity)
	{
		if (_hitEnemy == null || _playerEntity == null)
		{
			return;
		}

		Vec3 _playerPos = new Vec3(_x, _y, _z);
		final Vec3 _center = new Vec3(((_hitEnemy.getX() + _x) / 2), ((_hitEnemy.getY() + _y) / 2), ((_hitEnemy.getZ() + _z) / 2));

		float _finalRadius = RADIUS;
		float _knockbackMultiplier = 1;
		float _damageMultiplier = 1;
		boolean _hasFireAspect = false;

		if (_playerEntity instanceof Player _player)
		{
			ItemStack _mainHandItems = _player.getItemInHand(InteractionHand.MAIN_HAND);
			int _sweepingEdgeLevel = EnchantmentHelper.getItemEnchantmentLevel(Enchantments.SWEEPING_EDGE, _mainHandItems);
			int _knockbackLevel = EnchantmentHelper.getItemEnchantmentLevel(Enchantments.KNOCKBACK, _mainHandItems);
			int _sharpnessLevel = EnchantmentHelper.getItemEnchantmentLevel(Enchantments.SHARPNESS, _mainHandItems);
			_hasFireAspect = EnchantmentHelper.getItemEnchantmentLevel(Enchantments.FIRE_ASPECT, _mainHandItems) > 0;
			
			if (_sweepingEdgeLevel > 0)
			{
				_finalRadius = RADIUS + _sweepingEdgeLevel;
			}

			if (_knockbackLevel > 0)
			{
				_knockbackMultiplier = 1 + _finalRadius * 0.25F;
			}

			if (_sharpnessLevel > 0)
			{
				_damageMultiplier = 1 + _sharpnessLevel * 0.5F;
			}
		}
		
		List<Entity> _entfound = _world.getEntitiesOfClass(Entity.class, new AABB(_center, _center).inflate(_finalRadius / 2d), e -> true).stream().sorted(Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_center))).collect(Collectors.toList());
			
		for (Entity _enemy : _entfound)
		{
			if (!(_playerEntity == _enemy) && (_playerEntity instanceof Player _player))
			{
				_enemy.hurt(DamageSource.playerAttack(_player), Mth.nextFloat(RandomSource.create(), MIN_DAMAGE, MAX_DAMAGE) * _damageMultiplier);

				if (_hasFireAspect)
				{
					_enemy.setSecondsOnFire(10);
				}
				
				Vec3 _enemyPos = new Vec3(_enemy.getX(), _enemy.getY(), _enemy.getZ());
				Vec3 _directionFromEnemyToPlayer = VMath.normalize(new Vec3(_playerPos.x - _enemyPos.x, _playerPos.y - _enemyPos.y, _playerPos.z - _enemyPos.z));
				
				_enemy.setDeltaMovement(
					new Vec3(
						(_enemy.getDeltaMovement().x() + -_directionFromEnemyToPlayer.x * KNOCKBACK * _knockbackMultiplier),
						(_enemy.getDeltaMovement().y() + -_directionFromEnemyToPlayer.y * VERTICAL_KNOCKBACK * _knockbackMultiplier),
						(_enemy.getDeltaMovement().z() + -_directionFromEnemyToPlayer.z * KNOCKBACK * _knockbackMultiplier)
					)
				);
			}
		}
	}
}
