
package net.mcreator.enhancing.item;
import net.mcreator.enhancing.enchantment.StrengthEnchantment;
import net.mcreator.enhancing.init.EnhancingModItems;
import net.mcreator.enhancing.init.EnhancingModEnchantments;

import net.minecraft.world.level.Level;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.item.ProjectileWeaponItem;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ArrowItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionHand;
import net.minecraft.server.level.ServerPlayer;
import com.google.common.collect.Lists;
import com.mojang.math.Quaternion;
import com.mojang.math.Vector3f;
import java.util.List;
import java.util.function.Predicate;
import javax.annotation.Nullable;
import net.minecraft.ChatFormatting;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.CrossbowAttackMob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.FireworkRocketEntity;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.item.TooltipFlag;

import net.mcreator.enhancing.entity.MysticalCrossbowEntity;
import net.minecraft.world.item.Vanishable;
import net.minecraft.server.commands.EnchantCommand;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;

public class MysticalCrossbowItem extends ProjectileWeaponItem implements Vanishable
{
	private static final String TAG_CHARGED = "Charged";
   	private static final String TAG_CHARGED_PROJECTILES = "ChargedProjectiles";
   	
   	private static final int MAX_CHARGE_DURATION = 25;
   	public static int DEFAULT_RANGE = 8;
   	
   	private boolean startSoundPlayed = false;
   	private boolean midLoadSoundPlayed = false;
   	
   	private static final float START_SOUND_PERCENT = 0.2F;
   	private static final float MID_SOUND_PERCENT = 0.5F;
   	public static float ARROW_POWER = 3.15F;
   	public static float FIREWORK_POWER = 1.6F;
   	public static float MULTISHOT_AMPLITUDE = 10F;
   	public static float MULTISHOT_PROJECTILE_MULTIPLIER = 2F;

	public MysticalCrossbowItem()
	{
		super(new Item.Properties().tab(CreativeModeTab.TAB_COMBAT).stacksTo(1));
	}

	public int getDefaultProjectileRange()
	{
		return 8;
	}

	public Predicate<ItemStack> getSupportedHeldProjectiles()
	{
    	return ARROW_OR_FIREWORK;
   	}

   	public Predicate<ItemStack> getAllSupportedProjectiles()
   	{
    	return ARROW_ONLY;
   	}

	@Override
	public boolean useOnRelease(ItemStack _itemstack)
	{
		return _itemstack.getItem() == Items.CROSSBOW || _itemstack.getItem() == EnhancingModItems.MYSTICAL_CROSSBOW.get();
	}

   	@Override
	public void releaseUsing(ItemStack _itemstack, Level _world, LivingEntity _entityLiving, int _timeLeft)
	{
		
		if (isCharged(_itemstack))
		{
			return;
		}
		
		System.out.println("releaseUsing()");
		
		int _useDuration = this.getUseDuration(_itemstack) - _timeLeft;
      	float _power = getPowerForTime(_useDuration, _itemstack);

      	System.out.println("Variables: ");
      	System.out.println(_useDuration + " (useDuration)");
      	System.out.println(_power + " (power)");
      	System.out.println(_timeLeft + " (timeLeft)");
      	
      	if (_power >= 1.0F && tryLoadProjectiles(_entityLiving, _itemstack))
      	{
      		ChargeCrossbow(_itemstack, _entityLiving, _world);
      	}
	}

	private void ChargeCrossbow(ItemStack _itemstack, LivingEntity _entityLiving, Level _world)
	{
		System.out.println("> setCharged and play sound.");
        setCharged(_itemstack, true);
         	
        SoundSource soundsource = _entityLiving instanceof Player ? SoundSource.PLAYERS : SoundSource.HOSTILE;
    	_world.playSound((Player)null, _entityLiving.getX(), _entityLiving.getY(), _entityLiving.getZ(), SoundEvents.CROSSBOW_LOADING_END, soundsource, 1.0F, 1.0F / (_world.getRandom().nextFloat() * 0.5F + 1.0F) + 0.2F);
	}

	@Override
	public InteractionResultHolder<ItemStack> use(Level _world, Player _entity, InteractionHand _hand)
	{		
	System.out.println("0");
		ItemStack itemstack = _entity.getItemInHand(_hand);
		
      if (isCharged(itemstack))
      {
      	System.out.println("1");
         performShooting(_world, _entity, _hand, itemstack, getShootingPower(itemstack), 1.0F);
         setCharged(itemstack, false);
         
         return InteractionResultHolder.consume(itemstack);
         
      } else if (!_entity.getProjectile(itemstack).isEmpty()) {
      	System.out.println("2");
         if (!isCharged(itemstack)) {
         	System.out.println("3");
            this.startSoundPlayed = false;
            this.midLoadSoundPlayed = false;
            _entity.startUsingItem(_hand);
         }

         return InteractionResultHolder.consume(itemstack);
      } else {
      	System.out.println("4");
         return InteractionResultHolder.fail(itemstack);
      }
	}

	@Override
	public void appendHoverText(ItemStack _itemstack, Level _world, List<Component> _list, TooltipFlag _flag)
	{
		super.appendHoverText(_itemstack, _world, _list, _flag);
		_list.add(Component.literal("\u00A7dEpic"));
		_list.add(Component.literal("\u00A7eVery fast crossbow that can shoot up to 21"));
		_list.add(Component.literal("\u00A7eprojectiles at the same time using the Multishot"));
		_list.add(Component.literal("\u00A7eenchantment in pair with enchanting runes."));
		
		_list.add(Component.literal(""));

		_list.add(Component.literal("\u00A77Power \u00A7b|||||\u00A78|||||"));
		_list.add(Component.literal("\u00A77Knockback \u00A7b|||\u00A78|||||||"));
		_list.add(Component.literal("\u00A77Speed \u00A7b|||||||\u00A78||"));

		_list.add(Component.literal(""));
	}

	private static float getShootingPower(ItemStack _itemstack)
	{
    	return containsChargedProjectile(_itemstack, Items.FIREWORK_ROCKET) ? 1.6F : 3.15F;
   	}

   	public static boolean containsChargedProjectile(ItemStack _crossbow, Item _projectile)
   	{
      	return getChargedProjectiles(_crossbow).stream().anyMatch((_e) ->
      	{
        	return _e.is(_projectile);
    	});
   	}

	@Override
	public UseAnim getUseAnimation(ItemStack _itemstack)
	{
		return UseAnim.CROSSBOW;
	}

	@Override
	public int getUseDuration(ItemStack _itemstack)
	{
		return getChargeDuration(_itemstack) + 3;
	}

	public void onUseTick(Level _world, LivingEntity _entity, ItemStack _crossbow, int _timeLeft)
	{
		if (!_world.isClientSide)
		{
         	int _quickChargeLevel = EnchantmentHelper.getItemEnchantmentLevel(Enchantments.QUICK_CHARGE, _crossbow);
         	SoundEvent _soundEvent = _quickChargeLevel == 0 ? SoundEvents.CROSSBOW_LOADING_MIDDLE : null;
         	
         	float _life = (float)(_crossbow.getUseDuration() - _timeLeft) / (float)getChargeDuration(_crossbow);
         	
         	if (_life < 0.2F)
         	{
            	this.startSoundPlayed = false;
            	this.midLoadSoundPlayed = false;
         	}

         	if (_life >= 0.2F && !this.startSoundPlayed)
         	{
            	this.startSoundPlayed = true;
            	_world.playSound((Player)null, _entity.getX(), _entity.getY(), _entity.getZ(), this.getStartSound(_quickChargeLevel), SoundSource.PLAYERS, 0.5F, 1.0F);
         	}

         	if (_life >= 0.5F && _soundEvent != null && !this.midLoadSoundPlayed)
         	{
            	this.midLoadSoundPlayed = true;
            	_world.playSound((Player)null, _entity.getX(), _entity.getY(), _entity.getZ(), _soundEvent, SoundSource.PLAYERS, 0.5F, 1.0F);
        	}
		}
	}

	private SoundEvent getStartSound(int _quickChargeLevel)
	{
      	switch (_quickChargeLevel)
      	{
         	case 1:
            	return SoundEvents.CROSSBOW_QUICK_CHARGE_1;
         	case 2:
            	return SoundEvents.CROSSBOW_QUICK_CHARGE_2;
         	case 3:
            	return SoundEvents.CROSSBOW_QUICK_CHARGE_3;
         	default:
        		return SoundEvents.CROSSBOW_LOADING_START;
    	}
   	}

	public static void performShooting(Level _world, LivingEntity _entity, InteractionHand _hand, ItemStack _itemstack, float _power, float _knockback)
	{
      	if (_entity instanceof Player _player &&
      		net.minecraftforge.event.ForgeEventFactory.onArrowLoose(_itemstack, _entity.level, _player, 1, true) < 0)
      	{
      		return;
      	}
      	
      	List<ItemStack> _chargedProjectiles = getChargedProjectiles(_itemstack);
      	float[] _shotPitches = getShotPitches(_entity.getRandom(), _chargedProjectiles.size());

      	float _currentAmplitude = (_chargedProjectiles.size() == 1) ? 0 : -MULTISHOT_AMPLITUDE / 2;

      	for(int _i = 0; _i < _chargedProjectiles.size(); _i++)
      	{
         	ItemStack _stack = _chargedProjectiles.get(_i);
         	boolean _isCreative = _entity instanceof Player && ((Player)_entity).getAbilities().instabuild;
         	
         	if (!_stack.isEmpty() || _isCreative)
         	{
				int _powerLevel = EnchantmentHelper.getItemEnchantmentLevel(EnhancingModEnchantments.STRENGTH.get(), _itemstack);
         		
            	shootProjectile(
            		_world,
            		_entity,
            		_hand,
            		_itemstack,
            		_stack,
            		_shotPitches[_i],
            		_isCreative,
            		StrengthEnchantment.getIncreasedPower(_powerLevel, _power),
            		_knockback,
            		_currentAmplitude
            	);
            	
            	_currentAmplitude += MULTISHOT_AMPLITUDE / _chargedProjectiles.size();
        	}
      	}

    	onCrossbowShot(_world, _entity, _itemstack);
   	}

	private static void shootProjectile(Level _world, LivingEntity _entity, InteractionHand _hand, ItemStack _itemstack, ItemStack _projectilesStack, float _random, boolean _pickupAuxiliaryProjectiles, float _power, float _knockback, float _angle)
	{
    	if (!_world.isClientSide)
    	{
        	boolean _isUsingRockets = _projectilesStack.is(Items.FIREWORK_ROCKET);
        	Projectile _projectile;
        	
        	if (_isUsingRockets)
        	{
        		_projectile = new FireworkRocketEntity(_world, _projectilesStack, _entity, _entity.getX(), _entity.getEyeY() - (double)0.15F, _entity.getZ(), true);
        	}
        	else
        	{
        		_projectile = getArrow(_world, _entity, _itemstack, _projectilesStack);
        		
            	if (_pickupAuxiliaryProjectiles || _angle != 0.0F)
            	{
            		((AbstractArrow)_projectile).pickup = AbstractArrow.Pickup.CREATIVE_ONLY;
            	}
         	}

         	if (_entity instanceof CrossbowAttackMob)
         	{
         		CrossbowAttackMob crossbowattackmob = (CrossbowAttackMob)_entity;
            	crossbowattackmob.shootCrossbowProjectile(crossbowattackmob.getTarget(), _itemstack, _projectile, _angle);
         	}
         	else
         	{
            	Vec3 _upVector = _entity.getUpVector(1.0F);
            	Quaternion _rotation = new Quaternion(new Vector3f(_upVector), _angle, true);
            	
            	Vec3 _forwardVector = _entity.getViewVector(1.0F);
            	Vector3f _forwardFloatVector = new Vector3f(_forwardVector);
            	_forwardFloatVector.transform(_rotation);
            	
            	_projectile.shoot(
            		(double)_forwardFloatVector.x(),
            		(double)_forwardFloatVector.y(),
            		(double)_forwardFloatVector.z(),
            		_power,
            		_knockback
            	);
         	}

         	_itemstack.hurtAndBreak(_isUsingRockets ? 3 : 1, _entity, (_e) ->
         	{
            	_e.broadcastBreakEvent(_hand);
         	});
         
         	_world.addFreshEntity(_projectile);
         	_world.playSound((Player)null, _entity.getX(), _entity.getY(), _entity.getZ(), SoundEvents.CROSSBOW_SHOOT, SoundSource.PLAYERS, 1.0F, _random);
      	}
   }

	private static List<ItemStack> getChargedProjectiles(ItemStack _itemstack)
	{
      	List<ItemStack> _projectiles = Lists.newArrayList();
      	CompoundTag _compoundTag = _itemstack.getTag();
      	
      	if (_compoundTag != null && _compoundTag.contains(TAG_CHARGED_PROJECTILES, 9))
      	{
         	ListTag _listTag = _compoundTag.getList(TAG_CHARGED_PROJECTILES, 10);
         	
         	if (_listTag != null)
         	{
            	for(int i = 0; i < _listTag.size(); ++i)
            	{
               		CompoundTag _compoundTag1 = _listTag.getCompound(i);
               		_projectiles.add(ItemStack.of(_compoundTag1));
            	}
        	}
      	}

    	return _projectiles;
   	}

	private static void onCrossbowShot(Level _world, LivingEntity _entity, ItemStack _itemstack)
	{
      	if (_entity instanceof ServerPlayer _serverPlayer)
      	{
         	if (!_world.isClientSide)
         	{
            	CriteriaTriggers.SHOT_CROSSBOW.trigger(_serverPlayer, _itemstack);
         	}

        	_serverPlayer.awardStat(Stats.ITEM_USED.get(_itemstack.getItem()));
      	}

    	clearChargedProjectiles(_itemstack);
   	}

	private static void clearChargedProjectiles(ItemStack _itemstack)
	{
      	CompoundTag _compoundTag = _itemstack.getTag();
      	
      	if (_compoundTag != null)
      	{
         	ListTag _listTag = _compoundTag.getList(TAG_CHARGED_PROJECTILES, 9);
         	_listTag.clear();
         	
        	_compoundTag.put(TAG_CHARGED_PROJECTILES, _listTag);
      	}
   	}

	private static AbstractArrow getArrow(Level _world, LivingEntity _entity, ItemStack _itemstack, ItemStack _arrowStack)
	{
    	ArrowItem _arrowItem = (ArrowItem)(_arrowStack.getItem() instanceof ArrowItem ? _arrowStack.getItem() : Items.ARROW);
      	AbstractArrow _abstractArrow = _arrowItem.createArrow(_world, _arrowStack, _entity);
      	
      	if (_entity instanceof Player)
      	{
        	_abstractArrow.setCritArrow(true);
      	}

      	_abstractArrow.setSoundEvent(SoundEvents.CROSSBOW_HIT);
      	_abstractArrow.setShotFromCrossbow(true);
      	
      	int _piercingLevel = EnchantmentHelper.getItemEnchantmentLevel(Enchantments.PIERCING, _itemstack);
      	if (_piercingLevel > 0)
      	{
        	_abstractArrow.setPierceLevel((byte)_piercingLevel);
      	}

      	int _powerLevel = EnchantmentHelper.getItemEnchantmentLevel(Enchantments.POWER_ARROWS, _itemstack);
      	if (_powerLevel > 0)
      	{
      		double _powerEnchantmentAddedValue = (double)_powerLevel * 0.5D + 0.5D;
            _abstractArrow.setBaseDamage(_abstractArrow.getBaseDamage() + _powerEnchantmentAddedValue);
      	}

      	int _flameLevel = EnchantmentHelper.getItemEnchantmentLevel(Enchantments.FLAMING_ARROWS, _itemstack);
      	if (_flameLevel > 0)
      	{
      		_abstractArrow.setSecondsOnFire(100);
      	}

      	return _abstractArrow;
   }

	private static float[] getShotPitches(RandomSource _random, int _shotCount)
	{
      	float[] _pitches = new float[_shotCount];

      	for(int _i = 0; _i < _shotCount; _i++)
      	{
      		_pitches[_i] = getRandomShotPitch(_random.nextBoolean(), _random);
      	}

      	return _pitches;
	}

	private static float getRandomShotPitch(boolean _loud, RandomSource _random)
	{
      	float _f = _loud ? 0.63F : 0.43F;
    	return 1.0F / (_random.nextFloat() * 0.5F + 1.8F) + _f;
   	}

	private static boolean tryLoadProjectiles(LivingEntity _entity, ItemStack _itemstack)
	{
		
    	int _multishotLevel = EnchantmentHelper.getItemEnchantmentLevel(Enchantments.MULTISHOT, _itemstack);
      	int _projectileCount = (_multishotLevel == 0) ? 1 : 1 + (int)(_multishotLevel * MULTISHOT_PROJECTILE_MULTIPLIER);
      	
      	boolean _isCreative = _entity instanceof Player && ((Player)_entity).getAbilities().instabuild;
      	
      	ItemStack _stack = _entity.getProjectile(_itemstack);
      	ItemStack _stack1 = _stack.copy();

      	for(int _projectileIterrator = 0; _projectileIterrator < _projectileCount; _projectileIterrator++)
      	{
        	if (_projectileIterrator > 0)
        	{
            	_stack = _stack1.copy();
         	}

         	if (_stack.isEmpty() && _isCreative)
         	{
            	_stack = new ItemStack(Items.ARROW);
            	_stack1 = _stack.copy();

            	System.out.println("stack is empty: " + _stack.isEmpty());
         	}

         	if (!loadProjectile(_entity, _itemstack, _stack, _projectileIterrator > 0, _isCreative))
         	{
            	return false;
         	}
      	}

      	return true;
   	}

   	private static boolean loadProjectile(LivingEntity _entity, ItemStack _itemstack, ItemStack _itemstack1, boolean _isNotFirstProjectile, boolean _isCreative)
   	{
   		System.out.println("loadProjectile()");
   		
		if (_itemstack1.isEmpty())
      	{
        	return false;
      	}
      
      	boolean _unlimitedAmmo = _itemstack1.getItem() instanceof ArrowItem;
      	ItemStack _stack;
      
      	if (!_isCreative && _unlimitedAmmo && !_isNotFirstProjectile)
      	{
      		_stack = _itemstack1.split(1);
      	
        	if (_itemstack1.isEmpty() && _entity instanceof Player)
        	{
        		((Player)_entity).getInventory().removeItem(_itemstack1);
        	}
      	}
      	else
      	{
			_stack = _itemstack1.copy();
      	}

      	addChargedProjectile(_itemstack, _stack);
      	return true;
   	}

   	public static boolean isCharged(ItemStack _itemstack)
   	{
    	CompoundTag _compoundTag = _itemstack.getTag();
      	return _compoundTag != null && _compoundTag.getBoolean(TAG_CHARGED);
   	}

   	public static void setCharged(ItemStack _itemstack, boolean _charged)
   	{
      	CompoundTag _compoundTag = _itemstack.getOrCreateTag();
    	_compoundTag.putBoolean(TAG_CHARGED, _charged);
   	}

   	private static void addChargedProjectile(ItemStack _itemstack, ItemStack _projectilesItemstack)
   	{
      	CompoundTag _compoundTag = _itemstack.getOrCreateTag();
      	ListTag _listTag;
      	
      	if (_compoundTag.contains(TAG_CHARGED_PROJECTILES, 9))
      	{
        	_listTag = _compoundTag.getList(TAG_CHARGED_PROJECTILES, 10);
      	} 
      	else
      	{
        	_listTag = new ListTag();
      	}

      	CompoundTag _compoundTag1 = new CompoundTag();
      	_projectilesItemstack.save(_compoundTag1);
      	
      	_listTag.add(_compoundTag1);
    	_compoundTag.put(TAG_CHARGED_PROJECTILES, _listTag);
   	}

   	public static int getChargeDuration(ItemStack _itemstack)
   	{
      	int _quickChargeLevel = EnchantmentHelper.getItemEnchantmentLevel(Enchantments.QUICK_CHARGE, _itemstack);
    	return _quickChargeLevel == 0 ? 10 : 10 - 3 * _quickChargeLevel;
   	}

   	private static float getPowerForTime(int _duration, ItemStack _itemstack)
   	{
      	float f = (float)_duration / (float)getChargeDuration(_itemstack);
      	
      	if (f > 1.0F)
      	{
        	f = 1.0F;
      	}

    	return f;
   	}
}