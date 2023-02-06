
package net.mcreator.enhancing.enchantment;
import net.mcreator.enhancing.init.EnhancingModEnchantments;

import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;

import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.InteractionHand;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.enchantment.EnchantmentHelper;

public class MagmaWalkerEnchantment extends Enchantment
{
	public MagmaWalkerEnchantment(EquipmentSlot... slots)
	{
		super(Enchantment.Rarity.COMMON, EnchantmentCategory.BREAKABLE, slots);
	}

	public int getMinCost(int p_45017_)
	{
    	return p_45017_ * 10;
   	}

   	public int getMaxCost(int p_45027_)
   	{
    	return this.getMinCost(p_45027_) + 15;
   	}

   	public boolean isTreasureOnly()
   	{
    	return true;
   	}

   	public int getMaxLevel()
   	{
    	return 2;
   	}

   	public static void onEntityMoved(LivingEntity _player, Level _world, BlockPos _blockPos, int p_45022_, ItemStack _itemstack)
   	{   		
    	if (_player.isOnGround())
    	{
        	BlockState blockstate = Blocks.BASALT.defaultBlockState();
         	float f = (float)Math.min(16, 2 + p_45022_);
         	BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();

         	for(BlockPos blockpos : BlockPos.betweenClosed(_blockPos.offset((double)(-f), -1.0D, (double)(-f)), _blockPos.offset((double)f, -1.0D, (double)f)))
         	{
            	if (blockpos.closerToCenterThan(_player.position(), (double)f))
            	{
               		blockpos$mutableblockpos.set(blockpos.getX(), blockpos.getY() + 1, blockpos.getZ());
               		BlockState blockstate1 = _world.getBlockState(blockpos$mutableblockpos);
               		if (blockstate1.isAir())
               		{
                  		BlockState blockstate2 = _world.getBlockState(blockpos);
                  		boolean isFull = blockstate2.getBlock() == Blocks.LAVA && blockstate2.getValue(LiquidBlock.LEVEL) == 0; //TODO: Forge, modded waters?
                  		if (
                  			blockstate2.getMaterial() == Material.LAVA &&
                  			isFull &&
                  			blockstate.canSurvive(_world, blockpos) &&
                  			_world.isUnobstructed(blockstate, blockpos, CollisionContext.empty()) &&
                  			!net.minecraftforge.event.ForgeEventFactory.onBlockPlace(_player, net.minecraftforge.common.util.BlockSnapshot.create(_world.dimension(), _world, blockpos), net.minecraft.core.Direction.UP)
                  		)
                  		{
                     		_world.setBlockAndUpdate(blockpos, blockstate);
                     		_world.scheduleTick(blockpos, Blocks.BASALT, Mth.nextInt(_player.getRandom(), 60, 120));

							if (Math.random() < 0.05f)
							{
								int _hurtAmount = EnchantmentHelper.getItemEnchantmentLevel(EnhancingModEnchantments.MAGMA_WALKER.get(), _itemstack);
								
								if (_itemstack.hurt(_hurtAmount, RandomSource.create(), null))
								{
									_itemstack.shrink(_hurtAmount);
									_itemstack.setDamageValue(0);
								}
							}
                  		}
               		}
            	}
         	}
      	}
   	}

   	public boolean checkCompatibility(Enchantment p_45024_)
   	{
    	return super.checkCompatibility(p_45024_) && p_45024_ != Enchantments.FROST_WALKER;
   	}
}