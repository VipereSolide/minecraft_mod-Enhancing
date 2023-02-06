
/*
 *	MCreator note: This file will be REGENERATED on each build.
 */
package net.mcreator.enhancing.init;

import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.api.distmarker.Dist;

import net.minecraft.client.gui.screens.MenuScreens;

import net.mcreator.enhancing.client.gui.SellMenuGUIScreen;
import net.mcreator.enhancing.client.gui.QuiverGUIScreen;
import net.mcreator.enhancing.client.gui.CoalFurnaceGUIScreen;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class EnhancingModScreens {
	@SubscribeEvent
	public static void clientLoad(FMLClientSetupEvent event) {
		event.enqueueWork(() -> {
			MenuScreens.register(EnhancingModMenus.QUIVER_GUI.get(), QuiverGUIScreen::new);
			MenuScreens.register(EnhancingModMenus.SELL_MENU_GUI.get(), SellMenuGUIScreen::new);
			MenuScreens.register(EnhancingModMenus.COAL_FURNACE_GUI.get(), CoalFurnaceGUIScreen::new);
		});
	}
}
