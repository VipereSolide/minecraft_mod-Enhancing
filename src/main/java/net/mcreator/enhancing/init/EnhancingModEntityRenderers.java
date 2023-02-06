
/*
 *    MCreator note: This file will be REGENERATED on each build.
 */
package net.mcreator.enhancing.init;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.api.distmarker.Dist;

import net.minecraft.client.renderer.entity.ThrownItemRenderer;

import net.mcreator.enhancing.client.renderer.EdlerBowRenderer;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class EnhancingModEntityRenderers {
	@SubscribeEvent
	public static void registerEntityRenderers(EntityRenderersEvent.RegisterRenderers event) {
		event.registerEntityRenderer(EnhancingModEntities.EDLER_BOW.get(), EdlerBowRenderer::new);
		event.registerEntityRenderer(EnhancingModEntities.MYSTICAL_CROSSBOW.get(), ThrownItemRenderer::new);
	}
}
