
package net.mcreator.enhancing.enchantment;

import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Item;
import net.minecraft.world.entity.EquipmentSlot;

import net.mcreator.enhancing.init.EnhancingModItems;

import java.util.List;

public class StrengthEnchantment extends Enchantment {
	public StrengthEnchantment(EquipmentSlot... slots) {
		super(Enchantment.Rarity.RARE, EnchantmentCategory.VANISHABLE, slots);
	}

	@Override
	public int getMaxLevel() {
		return 3;
	}

	@Override
	public boolean canApplyAtEnchantingTable(ItemStack stack) {
		Item item = stack.getItem();
		return List.of(Items.BOW, Items.CROSSBOW, EnhancingModItems.MYSTICAL_CROSSBOW.get(), EnhancingModItems.HUNTING_BOW.get()).contains(item);
	}

	public static float getIncreasedPower(int _enchantmentLevel, float _startPower)
	{
		return _startPower + (_enchantmentLevel * 0.33F);
	}
}
