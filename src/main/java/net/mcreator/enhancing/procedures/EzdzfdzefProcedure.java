package net.mcreator.enhancing.procedures;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.entity.Entity;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.core.particles.ParticleTypes;

import net.mcreator.enhancing.network.EnhancingModVariables;

public class EzdzfdzefProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
		if (entity == null)
			return;
		if (world instanceof ServerLevel _level)
			_level.sendParticles(ParticleTypes.LARGE_SMOKE, x, y, z, 5, 1, 1, 1, 1);
		{
			double _setval = (entity.getCapability(EnhancingModVariables.PLAYER_VARIABLES_CAPABILITY, null).orElse(new EnhancingModVariables.PlayerVariables())).EMERALD_COUNT + 5;
			entity.getCapability(EnhancingModVariables.PLAYER_VARIABLES_CAPABILITY, null).ifPresent(capability -> {
				capability.EMERALD_COUNT = _setval;
				capability.syncPlayerVariables(entity);
			});
		}
	}
}
