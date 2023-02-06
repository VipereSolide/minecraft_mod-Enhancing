
/*
 *	MCreator note: This file will be REGENERATED on each build.
 */
package net.mcreator.enhancing.init;

import net.minecraftforge.registries.RegistryObject;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.common.extensions.IForgeMenuType;

import net.minecraft.world.inventory.MenuType;

import net.mcreator.enhancing.world.inventory.SellMenuGUIMenu;
import net.mcreator.enhancing.world.inventory.QuiverGUIMenu;
import net.mcreator.enhancing.world.inventory.CoalFurnaceGUIMenu;
import net.mcreator.enhancing.EnhancingMod;

public class EnhancingModMenus {
	public static final DeferredRegister<MenuType<?>> REGISTRY = DeferredRegister.create(ForgeRegistries.MENU_TYPES, EnhancingMod.MODID);
	public static final RegistryObject<MenuType<QuiverGUIMenu>> QUIVER_GUI = REGISTRY.register("quiver_gui", () -> IForgeMenuType.create(QuiverGUIMenu::new));
	public static final RegistryObject<MenuType<SellMenuGUIMenu>> SELL_MENU_GUI = REGISTRY.register("sell_menu_gui", () -> IForgeMenuType.create(SellMenuGUIMenu::new));
	public static final RegistryObject<MenuType<CoalFurnaceGUIMenu>> COAL_FURNACE_GUI = REGISTRY.register("coal_furnace_gui", () -> IForgeMenuType.create(CoalFurnaceGUIMenu::new));
}
