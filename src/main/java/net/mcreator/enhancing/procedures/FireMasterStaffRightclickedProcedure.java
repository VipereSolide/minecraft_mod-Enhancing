package net.mcreator.enhancing.procedures;

import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.Level;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.projectile.LargeFireball;
import net.minecraft.world.entity.projectile.Fireball;
import net.minecraft.world.entity.projectile.AbstractHurtingProjectile;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Entity;

import java.util.stream.Collectors;
import java.util.List;
import java.util.Comparator;
import viperesolide.math.VMath;

public class FireMasterStaffRightclickedProcedure
{
	public static float FIREBALL_POWER = 0.75f;
	public static int DETECTION_RANGE = 24;

	public static Projectile getFireball(Level _world, Entity _shooter, double _velX, double _velY, double _velZ, boolean _normalize)
	{
		AbstractHurtingProjectile _fireball = new LargeFireball(EntityType.FIREBALL, _world);
		_fireball.setOwner(_shooter);

		Vec3 _normalized = new Vec3(_velX, _velY, _velZ);

		if (_normalize)
		{
			_normalized = VMath.normalize(_normalized);
		}

		_normalized = new Vec3(_normalized.x * FIREBALL_POWER, _normalized.y * FIREBALL_POWER, _normalized.z * FIREBALL_POWER);
			
		_fireball.xPower = _normalized.x;
		_fireball.yPower = _normalized.y;
		_fireball.zPower = _normalized.z;
		
		return _fireball;
	}


	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity)
	{
		if (entity == null)
		{
			return;
		}
		
		final Vec3 _center = new Vec3(x, y, z);
		List<Entity> _entfound = world.getEntitiesOfClass(Entity.class, new AABB(_center, _center).inflate(DETECTION_RANGE / 2d), e -> true)
										.stream()
										.sorted(Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_center)))
										.collect(Collectors.toList());

		for (Entity _possibleEnemy : _entfound)
		{
			if (
				_possibleEnemy == entity ||
				_possibleEnemy.getType() == EntityType.FIREBALL ||
				_possibleEnemy.getType() == EntityType.ITEM ||
				_possibleEnemy.getType() == EntityType.PAINTING ||
				_possibleEnemy.getType() == EntityType.ITEM_FRAME ||
				_possibleEnemy.getType() == EntityType.EXPERIENCE_ORB ||
				_possibleEnemy.getType() == EntityType.EXPERIENCE_BOTTLE ||
				_possibleEnemy.getType() == EntityType.POTION ||
				_possibleEnemy.getType() == EntityType.AREA_EFFECT_CLOUD ||
				_possibleEnemy.getType() == EntityType.ARMOR_STAND ||
				_possibleEnemy.getType() == EntityType.ARROW ||
				_possibleEnemy.getType() == EntityType.SPECTRAL_ARROW ||
				_possibleEnemy.getType() == EntityType.TNT_MINECART ||
				_possibleEnemy.getType() == EntityType.MINECART ||
				_possibleEnemy.getType() == EntityType.CHEST_MINECART ||
				_possibleEnemy.getType() == EntityType.HOPPER_MINECART ||
				_possibleEnemy.getType() == EntityType.FURNACE_MINECART ||
				_possibleEnemy.getType() == EntityType.BOAT ||
				_possibleEnemy.getType() == EntityType.COMMAND_BLOCK_MINECART ||
				_possibleEnemy.getType() == EntityType.SMALL_FIREBALL ||
				_possibleEnemy.getType() == EntityType.DRAGON_FIREBALL ||
				_possibleEnemy.getType() == EntityType.LLAMA_SPIT ||
				_possibleEnemy.getType() == EntityType.EGG ||
				_possibleEnemy.getType() == EntityType.SNOWBALL ||
				_possibleEnemy.getType() == EntityType.END_CRYSTAL ||
				_possibleEnemy.getType() == EntityType.ENDER_PEARL ||
				_possibleEnemy.getType() == EntityType.EYE_OF_ENDER ||
				_possibleEnemy.getType() == EntityType.FALLING_BLOCK ||
				_possibleEnemy.getType() == EntityType.GLOW_ITEM_FRAME ||
				_possibleEnemy.getType() == EntityType.COMMAND_BLOCK_MINECART ||
				_possibleEnemy.getType() == EntityType.MARKER ||
				_possibleEnemy.getType() == EntityType.LEASH_KNOT ||
				_possibleEnemy.getType() == EntityType.LIGHTNING_BOLT ||
				_possibleEnemy.getType() == EntityType.TNT ||
				!_possibleEnemy.isAlive()
			)
			{
				continue;
			}
			
			boolean _isTamedByPlayer =
			(
				(_possibleEnemy instanceof TamableAnimal _tamable) && (entity instanceof LivingEntity _player)
				? _tamable.isOwnedBy(_player) : false
			);
			
			boolean _isInvisible = _possibleEnemy.isInvisible();
				
			if (!_isTamedByPlayer && !_isInvisible)
			{
				if (_possibleEnemy instanceof LivingEntity _enemyLivingEntity)
				{
					Entity _shootFrom = entity;
					Level projectileLevel = _shootFrom.level;
							
					if (!projectileLevel.isClientSide())
					{
						Vec3 _normalized = new Vec3(
							(_possibleEnemy.getX() - entity.getX()),
							(_possibleEnemy.getY() - entity.getY()),
							(_possibleEnemy.getZ() - entity.getZ())
						);

						_normalized = VMath.normalize(_normalized);

						Projectile _fireball = getFireball(
							projectileLevel,
							entity,
							_normalized.x,
							_normalized.y,
							_normalized.z,
							false
						);

						_fireball.setPos(_shootFrom.getX(), _shootFrom.getEyeY() - 0.1, _shootFrom.getZ());
						_fireball.shoot(_normalized.x, _normalized.y, _normalized.z, 1, (float) 0.001);
						projectileLevel.addFreshEntity(_fireball);
					}
				}
			}
		}
	}
}