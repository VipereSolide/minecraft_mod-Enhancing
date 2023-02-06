
/*
 *    MCreator note: This file will be REGENERATED on each build.
 */
package net.mcreator.enhancing.init;

import net.minecraftforge.registries.RegistryObject;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.fml.common.Mod;

import net.minecraft.world.level.levelgen.feature.Feature;

import net.mcreator.enhancing.world.features.AncientPortalRoomFeature;
import net.mcreator.enhancing.EnhancingMod;

@Mod.EventBusSubscriber
public class EnhancingModFeatures {
	public static final DeferredRegister<Feature<?>> REGISTRY = DeferredRegister.create(ForgeRegistries.FEATURES, EnhancingMod.MODID);
	public static final RegistryObject<Feature<?>> ANCIENT_PORTAL_ROOM = REGISTRY.register("ancient_portal_room", AncientPortalRoomFeature::feature);
}
