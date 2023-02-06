package net.mcreator.enhancing.procedures;

import net.minecraft.world.phys.Vec3;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import net.minecraft.sounds.SoundSource;
import net.minecraft.core.BlockPos;

public class BirdStaffRightclickedProcedure
{
	private static float PARTICLE_CIRCLE_RADIUS = 1.5F;
	private static float POINT_PRECISION = 18F;
	public static float FLIGHT_HEIGHT = 3F;
	public static float USE_COOLDOWN_SECONDS = 7.5F;

	public static void execute(Entity _player, LevelAccessor _world, boolean _isInOffhand)
	{
		double _x = _player.getX();
		double _y = _player.getY();
		double _z = _player.getZ();

		// Impulsion
		_player.setDeltaMovement(_player.getDeltaMovement().x, (_isInOffhand) ? -FLIGHT_HEIGHT : FLIGHT_HEIGHT, _player.getDeltaMovement().z);

		// Graphics (particles + sounds)
		for (float _angle = 0; _angle < 360; _angle += POINT_PRECISION)
		{
			double _particleX = _player.getX() + (double)(PARTICLE_CIRCLE_RADIUS * Math.cos((double)(-_angle * Math.PI / 180)));
			double _particleZ = _player.getZ() + (double)(PARTICLE_CIRCLE_RADIUS * Math.sin((double)(-_angle * Math.PI / 180)));
			
			_world.addParticle(ParticleTypes.CAMPFIRE_COSY_SMOKE, _particleX, _player.getY(), _particleZ, 0,0,0);
		}

		if (_world instanceof Level _level)
		{
			if (_level.isClientSide())
			{
				_level.playLocalSound(_x, _y, _z, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.allay.ambient_with_item")), SoundSource.AMBIENT, 1, 1, false);
				_level.playLocalSound(_x, _y, _z, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.ender_dragon.flap")), SoundSource.AMBIENT, 1, 1, false);
			}
			else
			{
				_level.playSound(null, new BlockPos(_x, _y, _z), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.allay.ambient_with_item")), SoundSource.AMBIENT, 1, 1);
				_level.playSound(null, new BlockPos(_x, _y, _z), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.ender_dragon.flap")), SoundSource.AMBIENT, 1, 1);
			}
		}
	}
}