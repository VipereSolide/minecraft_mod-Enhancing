
package net.mcreator.enhancing.item;

import net.minecraft.world.level.Level;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.ProjectileWeaponItem;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionHand;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;

import net.mcreator.enhancing.entity.EdlerBowEntity;
import net.mcreator.enhancing.init.EnhancingModEnchantments;

import java.util.List;

public class EdlerBowItem extends Item {
	public EdlerBowItem() {
		super(new Item.Properties().tab(null).stacksTo(1));
	}

	@Override
	public InteractionResultHolder<ItemStack> use(Level p_40672_, Player p_40673_, InteractionHand p_40674_) {
		ItemStack itemstack = p_40673_.getItemInHand(p_40674_);
      	boolean flag = !p_40673_.getProjectile(itemstack).isEmpty();

      	InteractionResultHolder<ItemStack> ret = net.minecraftforge.event.ForgeEventFactory.onArrowNock(itemstack, p_40672_, p_40673_, p_40674_, flag);
      	if (ret != null) return ret;

      	if (!p_40673_.getAbilities().instabuild && !flag) {
        	return InteractionResultHolder.fail(itemstack);
      	} else {
         	p_40673_.startUsingItem(p_40674_);
         	return InteractionResultHolder.consume(itemstack);
      	}
	}

	@Override
	public void appendHoverText(ItemStack itemstack, Level world, List<Component> list, TooltipFlag flag) {
		super.appendHoverText(itemstack, world, list, flag);
		list.add(Component.literal("\u00A7aRare"));
		list.add(Component.literal("\u00A7ePowerful bow."));
		list.add(Component.literal(""));
		list.add(Component.literal("\u00A77Power \u00A7b||||\u00A78||||||"));
		list.add(Component.literal("\u00A77Knockback \u00A7b||\u00A78||||||||"));
		list.add(Component.literal(""));
	}

	@Override
	public UseAnim getUseAnimation(ItemStack itemstack) {
		return UseAnim.BOW;
	}

	@Override
	public int getUseDuration(ItemStack itemstack) {
		return 72000;
	}

	public static float getPowerForTime(int _itemDuratin) {
      float f = (float)_itemDuratin / 20.0F;
      f = (f * f + f * 2.0F) / 3.0F;
      if (f > 1.0F) {
         f = 1.0F;
      }

      return f;
   }

	@Override
	public void releaseUsing(ItemStack _itemstack, Level _world, LivingEntity _entityLiving, int _timeLeft)
	{
		if (!_world.isClientSide() && _entityLiving instanceof ServerPlayer _entity)
		{
			double _x = _entity.getX();
			double _y = _entity.getY();
			double _z = _entity.getZ();
			
			ItemStack _stack = ProjectileWeaponItem.getHeldProjectile(_entity, _e -> _e.getItem() == Items.ARROW || _e.getItem() == Items.SPECTRAL_ARROW || _e.getItem() == Items.TIPPED_ARROW);
			Boolean _infiniteAmmo = _entity.getAbilities().instabuild || EnchantmentHelper.getItemEnchantmentLevel(Enchantments.INFINITY_ARROWS, _itemstack) > 0;

			int _useDuration = this.getUseDuration(_itemstack) - _timeLeft;
         	_useDuration = net.minecraftforge.event.ForgeEventFactory.onArrowLoose(_itemstack, _world, _entity, _useDuration, !_stack.isEmpty() || _infiniteAmmo);
         	
         	if (_useDuration < 0)
         	{
         		return;
         	}
			
			if (_stack == ItemStack.EMPTY) 
			{
				for (int _itemIterrator = 0; _itemIterrator < _entity.getInventory().items.size(); _itemIterrator++)
				{
					ItemStack _teststack = _entity.getInventory().items.get(_itemIterrator);
					if (_teststack != null && _teststack.getItem() == Items.ARROW)
					{
						_stack = _teststack;
						break;
					}
				}
			}

			float _shootForce = getPowerForTime(_useDuration);
			Boolean _canShoot = _entity.getAbilities().instabuild || _stack != ItemStack.EMPTY || EnchantmentHelper.getItemEnchantmentLevel(Enchantments.INFINITY_ARROWS, _itemstack) > 0;
			
            if ((double)_shootForce >= 0.1D)
            {
				if (_canShoot)
				{
					// Arrow creation and properties applying.
					int _strengthEnchantment = EnchantmentHelper.getItemEnchantmentLevel(EnhancingModEnchantments.STRENGTH.get(), _itemstack);
                  	float _strengthEnchantmentAddedValue = 1;
                  	
                  	if (_strengthEnchantment > 0)
                  	{
                  		_strengthEnchantmentAddedValue = (float)_strengthEnchantment / 10;
                  	}
                  	
					EdlerBowEntity _arrowEntity = EdlerBowEntity.shoot(_world, _entity, _world.getRandom(), _shootForce * 1.5f + _strengthEnchantmentAddedValue, _shootForce, Math.round(_shootForce * 0.25f));

					if (_shootForce == 1.0F)
					{
                    	_arrowEntity.setCritArrow(true);
                  	}

					int _powerEnchantment = EnchantmentHelper.getItemEnchantmentLevel(Enchantments.POWER_ARROWS, _itemstack);
                  	if (_powerEnchantment > 0)
                  	{
                  		double _powerEnchantmentAddedValue = (double)_powerEnchantment * 0.5D + 0.5D;
                    	_arrowEntity.setBaseDamage(_arrowEntity.getBaseDamage() + _powerEnchantmentAddedValue);
                  	}

                  	// Inventory and pickup		
                  	_itemstack.hurtAndBreak(1, _entity, _e -> _e.broadcastBreakEvent(_entity.getUsedItemHand()));


					if (_entity.getAbilities().instabuild)
					{
						_arrowEntity.pickup = AbstractArrow.Pickup.CREATIVE_ONLY;
					}
					else
					{
					_arrowEntity.pickup = AbstractArrow.Pickup.ALLOWED;


						if (new ItemStack(Items.ARROW).isDamageableItem())
						{
							if (_stack.hurt(1, _world.getRandom(), _entity))
							{
								_stack.shrink(1);
								_stack.setDamageValue(0);
								
								if (_stack.isEmpty())
								{
									_entity.getInventory().removeItem(_stack);
								}
							}
						}
						else
						{
							_stack.shrink(1);
						
							if (_stack.isEmpty())
							{
								_entity.getInventory().removeItem(_stack);
							}
						}
					}
				}
			}
		}
	}
}