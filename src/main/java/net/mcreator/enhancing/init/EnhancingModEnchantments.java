
/*
 *    MCreator note: This file will be REGENERATED on each build.
 */
package net.mcreator.enhancing.init;

import net.minecraftforge.registries.RegistryObject;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.DeferredRegister;

import net.minecraft.world.item.enchantment.Enchantment;

import net.mcreator.enhancing.enchantment.StrengthEnchantment;
import net.mcreator.enhancing.enchantment.ReinforcementEnchantment;
import net.mcreator.enhancing.enchantment.MagmaWalkerEnchantment;
import net.mcreator.enhancing.EnhancingMod;

public class EnhancingModEnchantments {
	public static final DeferredRegister<Enchantment> REGISTRY = DeferredRegister.create(ForgeRegistries.ENCHANTMENTS, EnhancingMod.MODID);
	public static final RegistryObject<Enchantment> STRENGTH = REGISTRY.register("strength", () -> new StrengthEnchantment());
	public static final RegistryObject<Enchantment> MAGMA_WALKER = REGISTRY.register("magma_walker", () -> new MagmaWalkerEnchantment());
	public static final RegistryObject<Enchantment> REINFORCEMENT = REGISTRY.register("reinforcement", () -> new ReinforcementEnchantment());
}
