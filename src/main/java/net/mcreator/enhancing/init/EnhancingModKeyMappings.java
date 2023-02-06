
/*
 *    MCreator note: This file will be REGENERATED on each build.
 */
package net.mcreator.enhancing.init;

import org.lwjgl.glfw.GLFW;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.api.distmarker.Dist;

import net.minecraft.client.Minecraft;
import net.minecraft.client.KeyMapping;

import net.mcreator.enhancing.network.RetrieveEmeraldsKeyMessage;
import net.mcreator.enhancing.EnhancingMod;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, value = {Dist.CLIENT})
public class EnhancingModKeyMappings {
	public static final KeyMapping RETRIEVE_EMERALDS_KEY = new KeyMapping("key.enhancing.retrieve_emeralds_key", GLFW.GLFW_KEY_P, "key.categories.inventory") {
		private boolean isDownOld = false;

		@Override
		public void setDown(boolean isDown) {
			super.setDown(isDown);
			if (isDownOld != isDown && isDown) {
				EnhancingMod.PACKET_HANDLER.sendToServer(new RetrieveEmeraldsKeyMessage(0, 0));
				RetrieveEmeraldsKeyMessage.pressAction(Minecraft.getInstance().player, 0, 0);
			}
			isDownOld = isDown;
		}
	};

	@SubscribeEvent
	public static void registerKeyMappings(RegisterKeyMappingsEvent event) {
		event.register(RETRIEVE_EMERALDS_KEY);
	}

	@Mod.EventBusSubscriber({Dist.CLIENT})
	public static class KeyEventListener {
		@SubscribeEvent
		public static void onClientTick(TickEvent.ClientTickEvent event) {
			if (Minecraft.getInstance().screen == null) {
				RETRIEVE_EMERALDS_KEY.consumeClick();
			}
		}
	}
}
