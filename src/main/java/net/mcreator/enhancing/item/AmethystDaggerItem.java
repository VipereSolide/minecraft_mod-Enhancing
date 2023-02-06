
package net.mcreator.enhancing.item;

import net.minecraft.world.level.Level;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.entity.LivingEntity;

import net.minecraft.world.entity.EquipmentSlot;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;

import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;

import net.mcreator.enhancing.procedures.AmethystDaggerAttackProcedure;

import java.util.List;
import java.util.UUID;
import java.io.Console;

public class AmethystDaggerItem extends SwordItem
{
   	protected final Multimap<Attribute, AttributeModifier> defaultModifiers;
   
	public AmethystDaggerItem()
	{
		super(new Tier()
		{
			public int getUses()
			{
				return 0;
			}

			public float getSpeed()
			{
				return 4f;
			}

			public float getAttackDamageBonus()
			{
				return 0f;
			}

			public int getLevel()
			{
				return 1;
			}

			public int getEnchantmentValue()
			{
				return 24;
			}

			public Ingredient getRepairIngredient()
			{
				return Ingredient.EMPTY;
			}
		}, 0, -0.8f, new Item.Properties().tab(CreativeModeTab.TAB_COMBAT));

		ImmutableMultimap.Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
      	builder.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(BASE_ATTACK_DAMAGE_UUID, "Weapon modifier", (double)0, AttributeModifier.Operation.ADDITION));
      	builder.put(Attributes.ATTACK_SPEED, new AttributeModifier(BASE_ATTACK_SPEED_UUID, "Weapon modifier", (double)-0.8f, AttributeModifier.Operation.ADDITION));
      	builder.put(Attributes.ATTACK_KNOCKBACK, new AttributeModifier(UUID.randomUUID(), "Weapon modifier", (double)0, AttributeModifier.Operation.ADDITION));
      	
      	defaultModifiers = builder.build();
	}

	@Override
	public Multimap<Attribute, AttributeModifier> getDefaultAttributeModifiers(EquipmentSlot p_43274_) {
      return p_43274_ == EquipmentSlot.MAINHAND ? defaultModifiers : super.getDefaultAttributeModifiers(p_43274_);
   }

	@Override
	public boolean hurtEnemy(ItemStack itemstack, LivingEntity entity, LivingEntity sourceentity)
	{
		double __squaredDistanceX = (entity.getX() - sourceentity.getX()) * (entity.getX() - sourceentity.getX());
		double __squaredDistanceY = (entity.getY() - sourceentity.getY()) * (entity.getY() - sourceentity.getY());
		double __squaredDistanceZ = (entity.getZ() - sourceentity.getZ()) * (entity.getZ() - sourceentity.getZ());
		double __distance = Math.sqrt(__squaredDistanceX + __squaredDistanceY + __squaredDistanceZ);

		System.out.println("[DAGGER] Entity position: " + entity.getX() + ", " + entity.getY() + ", " + entity.getZ());
		System.out.println("[DAGGER] Source entity position: " + sourceentity.getX() + ", " + sourceentity.getY() + ", " + sourceentity.getZ());
		System.out.println("[DAGGER] Distance between entity and you: " + __distance);

		if (__distance <= 2.5f)
		{
			boolean __retval = super.hurtEnemy(itemstack, entity, sourceentity);
			AmethystDaggerAttackProcedure.execute(entity.level, entity, sourceentity, itemstack);
			
			return __retval;
		}
		else
		{
			return false;
		}
	}

	@Override
	public void appendHoverText(ItemStack itemstack, Level world, List<Component> list, TooltipFlag flag) {
		super.appendHoverText(itemstack, world, list, flag);
		list.add(Component.literal("\u00A7dEpic"));
		list.add(Component.literal("\u00A7eMagical short range weapon. Deals violent poison"));
		list.add(Component.literal("\u00A7eon hit, and slows down enemies."));
		list.add(Component.literal(""));
		list.add(Component.literal("\u00A77Power \u00A7b|\u00A78|||||||||"));
		list.add(Component.literal("\u00A77Knockback \u00A7b|\u00A78|||||||||"));
		list.add(Component.literal("\u00A77Attack speed \u00A7b|||||||\u00A78|||"));
		list.add(Component.literal("\u00A7dPoison strength \u00A7b||||||\u00A78||||"));
		list.add(Component.literal("\u00A7dSlowness strength \u00A7b|||\u00A78|||||||||"));
		list.add(Component.literal(""));
	}
}
