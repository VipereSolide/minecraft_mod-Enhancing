
package net.mcreator.enhancing.enchantment;

import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.behavior.UpdateActivityFromSchedule;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.item.Item;
import java.util.Random;
import net.minecraft.util.RandomSource;

public class ReinforcementEnchantment extends Enchantment
{
	public static int REPAIR_RARITY = 200;
	private static int REPAIR_CURRENT_TICK = 0;
	
	public ReinforcementEnchantment(EquipmentSlot... slots)
	{
		super(Enchantment.Rarity.COMMON, EnchantmentCategory.BREAKABLE, slots);
	}

	public int getMaxLevel()
   	{
    	return 5;
   	}

	public static void onTickUpdate(ItemStack _itemstack, int _enchantmentLevel, Player _player, Level _world)
	{
		if (REPAIR_CURRENT_TICK >= REPAIR_RARITY)
		{
			float _randomThreshold = 1.0F - (float)_enchantmentLevel / 100;
			System.out.println("Enchantment Threshold: " + _randomThreshold);
		
			if (Math.random() > _randomThreshold)
			{
				_itemstack.hurt(-_enchantmentLevel, RandomSource.create(), null);
			}
		}

		REPAIR_CURRENT_TICK++;
	}
}
