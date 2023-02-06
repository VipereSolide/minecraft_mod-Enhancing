/**
 * The code of this mod element is always locked.
 *
 * You can register new events in this class too.
 *
 * If you want to make a plain independent class, create it using
 * Project Browser -> New... and make sure to make the class
 * outside net.mcreator.enhancing as this package is managed by MCreator.
 *
 * If you change workspace package, modid or prefix, you will need
 * to manually adapt this file to these changes or remake it.
 *
 * This class will be added in the mod root package.
*/
package viperesolide.math;

import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraft.world.phys.Vec3;
import net.minecraft.util.Mth;

public class VMath
{
	public static Vec3 normalize(Vec3 _vec)
	{
		double _avgMag = Mth.sqrt((float)(_vec.x * _vec.x) + (float)(_vec.y * _vec.y) + (float)(_vec.z * _vec.z));
		double _x = _vec.x;
		double _y = _vec.y;
		double _z = _vec.z;

		if (_x != 0)
		{
			_x = _vec.x / _avgMag;
		}

		if (_y != 0)
		{
			_y = _vec.y / _avgMag;
		}

		if (_y != 0)
		{
			_y = _vec.y / _avgMag;
		}

		return new Vec3(_x,_y,_z);
	}
}
