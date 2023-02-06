
/*
 *    MCreator note: This file will be REGENERATED on each build.
 */
package net.mcreator.enhancing.init;

import net.minecraftforge.registries.RegistryObject;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.DeferredRegister;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.BlockItem;

import net.mcreator.enhancing.item.WardencapeItem;
import net.mcreator.enhancing.item.ScytheItem;
import net.mcreator.enhancing.item.QuiverItem;
import net.mcreator.enhancing.item.MysticalCrossbowItem;
import net.mcreator.enhancing.item.HuntingBowItem;
import net.mcreator.enhancing.item.HoneyBallItem;
import net.mcreator.enhancing.item.FireMasterStaffItem;
import net.mcreator.enhancing.item.EnchantingRuneItem;
import net.mcreator.enhancing.item.EnchantingCursedRuneItem;
import net.mcreator.enhancing.item.EdlerBowItem;
import net.mcreator.enhancing.item.BirdStaffItem;
import net.mcreator.enhancing.item.AmethystDaggerItem;
import net.mcreator.enhancing.EnhancingMod;

public class EnhancingModItems {
	public static final DeferredRegister<Item> REGISTRY = DeferredRegister.create(ForgeRegistries.ITEMS, EnhancingMod.MODID);
	public static final RegistryObject<Item> ENCHANTING_RUNE = REGISTRY.register("enchanting_rune", () -> new EnchantingRuneItem());
	public static final RegistryObject<Item> QUIVER = REGISTRY.register("quiver", () -> new QuiverItem());
	public static final RegistryObject<Item> SCYTHE = REGISTRY.register("scythe", () -> new ScytheItem());
	public static final RegistryObject<Item> AMETHYST_DAGGER = REGISTRY.register("amethyst_dagger", () -> new AmethystDaggerItem());
	public static final RegistryObject<Item> EDLER_BOW = REGISTRY.register("edler_bow", () -> new EdlerBowItem());
	public static final RegistryObject<Item> MYSTICAL_CROSSBOW = REGISTRY.register("mystical_crossbow", () -> new MysticalCrossbowItem());
	public static final RegistryObject<Item> ENCHANTING_CURSED_RUNE = REGISTRY.register("enchanting_cursed_rune", () -> new EnchantingCursedRuneItem());
	public static final RegistryObject<Item> BIRD_STAFF = REGISTRY.register("bird_staff", () -> new BirdStaffItem());
	public static final RegistryObject<Item> STONE_VERTICAL_SLAB = block(EnhancingModBlocks.STONE_VERTICAL_SLAB, CreativeModeTab.TAB_BUILDING_BLOCKS);
	public static final RegistryObject<Item> COBBLESTONE_VERTICAL_SLAB = block(EnhancingModBlocks.COBBLESTONE_VERTICAL_SLAB, CreativeModeTab.TAB_BUILDING_BLOCKS);
	public static final RegistryObject<Item> OAK_VERTICAL_SLAB = block(EnhancingModBlocks.OAK_VERTICAL_SLAB, CreativeModeTab.TAB_BUILDING_BLOCKS);
	public static final RegistryObject<Item> SPRUCE_VERTICAL_SLAB = block(EnhancingModBlocks.SPRUCE_VERTICAL_SLAB, CreativeModeTab.TAB_BUILDING_BLOCKS);
	public static final RegistryObject<Item> BIRCH_VERTICAL_SLAB = block(EnhancingModBlocks.BIRCH_VERTICAL_SLAB, CreativeModeTab.TAB_BUILDING_BLOCKS);
	public static final RegistryObject<Item> JUNGLE_VERTICAL_SLAB = block(EnhancingModBlocks.JUNGLE_VERTICAL_SLAB, CreativeModeTab.TAB_BUILDING_BLOCKS);
	public static final RegistryObject<Item> ACACIA_VERTICAL_SLAB = block(EnhancingModBlocks.ACACIA_VERTICAL_SLAB, CreativeModeTab.TAB_BUILDING_BLOCKS);
	public static final RegistryObject<Item> DARK_OAK_VERTICAL_SLAB = block(EnhancingModBlocks.DARK_OAK_VERTICAL_SLAB, CreativeModeTab.TAB_BUILDING_BLOCKS);
	public static final RegistryObject<Item> MANGROVE_VERTICAL_SLAB = block(EnhancingModBlocks.MANGROVE_VERTICAL_SLAB, CreativeModeTab.TAB_BUILDING_BLOCKS);
	public static final RegistryObject<Item> CRIMSON_VERTICAL_SLAB = block(EnhancingModBlocks.CRIMSON_VERTICAL_SLAB, CreativeModeTab.TAB_BUILDING_BLOCKS);
	public static final RegistryObject<Item> WARPED_VERTICAL_SLAB = block(EnhancingModBlocks.WARPED_VERTICAL_SLAB, CreativeModeTab.TAB_BUILDING_BLOCKS);
	public static final RegistryObject<Item> SMOOTH_STONE_VERTICAL_SLAB = block(EnhancingModBlocks.SMOOTH_STONE_VERTICAL_SLAB, CreativeModeTab.TAB_BUILDING_BLOCKS);
	public static final RegistryObject<Item> MOSSY_COBBLESTONE_VERTICAL_SLAB = block(EnhancingModBlocks.MOSSY_COBBLESTONE_VERTICAL_SLAB, CreativeModeTab.TAB_BUILDING_BLOCKS);
	public static final RegistryObject<Item> STONE_BRICK_VERTICAL_SLAB = block(EnhancingModBlocks.STONE_BRICK_VERTICAL_SLAB, CreativeModeTab.TAB_BUILDING_BLOCKS);
	public static final RegistryObject<Item> MOSSY_STONE_BRICK_VERTICAL_SLAB = block(EnhancingModBlocks.MOSSY_STONE_BRICK_VERTICAL_SLAB, CreativeModeTab.TAB_BUILDING_BLOCKS);
	public static final RegistryObject<Item> ANDESITE_VERTICAL_SLAB = block(EnhancingModBlocks.ANDESITE_VERTICAL_SLAB, CreativeModeTab.TAB_BUILDING_BLOCKS);
	public static final RegistryObject<Item> POLISHED_ANDESITE_VERTICAL_SLAB = block(EnhancingModBlocks.POLISHED_ANDESITE_VERTICAL_SLAB, CreativeModeTab.TAB_BUILDING_BLOCKS);
	public static final RegistryObject<Item> DIORITE_VERTICAL_SLAB = block(EnhancingModBlocks.DIORITE_VERTICAL_SLAB, CreativeModeTab.TAB_BUILDING_BLOCKS);
	public static final RegistryObject<Item> POLISHED_DIORITE_VERTICAL_SLAB = block(EnhancingModBlocks.POLISHED_DIORITE_VERTICAL_SLAB, CreativeModeTab.TAB_BUILDING_BLOCKS);
	public static final RegistryObject<Item> GRANITE_VERTICAL_SLAB = block(EnhancingModBlocks.GRANITE_VERTICAL_SLAB, CreativeModeTab.TAB_BUILDING_BLOCKS);
	public static final RegistryObject<Item> POLISHED_GRANITE_VERTICAL_SLAB = block(EnhancingModBlocks.POLISHED_GRANITE_VERTICAL_SLAB, CreativeModeTab.TAB_BUILDING_BLOCKS);
	public static final RegistryObject<Item> SANDSTONE_VERTICAL_SLAB = block(EnhancingModBlocks.SANDSTONE_VERTICAL_SLAB, CreativeModeTab.TAB_BUILDING_BLOCKS);
	public static final RegistryObject<Item> RED_SANDSTONE_VERTICAL_SLAB = block(EnhancingModBlocks.RED_SANDSTONE_VERTICAL_SLAB, CreativeModeTab.TAB_BUILDING_BLOCKS);
	public static final RegistryObject<Item> BRICK_VERTICAL_SLAB = block(EnhancingModBlocks.BRICK_VERTICAL_SLAB, CreativeModeTab.TAB_BUILDING_BLOCKS);
	public static final RegistryObject<Item> PRISMARINE_VERTICAL_SLAB = block(EnhancingModBlocks.PRISMARINE_VERTICAL_SLAB, CreativeModeTab.TAB_BUILDING_BLOCKS);
	public static final RegistryObject<Item> PRISMARINE_BRICK_VERTICAL_SLAB = block(EnhancingModBlocks.PRISMARINE_BRICK_VERTICAL_SLAB, CreativeModeTab.TAB_BUILDING_BLOCKS);
	public static final RegistryObject<Item> DARK_PRISMARINE_VERTICAL_SLAB = block(EnhancingModBlocks.DARK_PRISMARINE_VERTICAL_SLAB, CreativeModeTab.TAB_BUILDING_BLOCKS);
	public static final RegistryObject<Item> NETHER_BRICK_VERTICAL_SLAB = block(EnhancingModBlocks.NETHER_BRICK_VERTICAL_SLAB, CreativeModeTab.TAB_BUILDING_BLOCKS);
	public static final RegistryObject<Item> RED_NETHER_BRICK_VERTICAL_SLAB = block(EnhancingModBlocks.RED_NETHER_BRICK_VERTICAL_SLAB, CreativeModeTab.TAB_BUILDING_BLOCKS);
	public static final RegistryObject<Item> QUARTZ_VERTICAL_SLAB = block(EnhancingModBlocks.QUARTZ_VERTICAL_SLAB, CreativeModeTab.TAB_BUILDING_BLOCKS);
	public static final RegistryObject<Item> PURPUR_VERTICAL_SLAB = block(EnhancingModBlocks.PURPUR_VERTICAL_SLAB, CreativeModeTab.TAB_BUILDING_BLOCKS);
	public static final RegistryObject<Item> END_STONE_BRICK_VERTICAL_SLAB = block(EnhancingModBlocks.END_STONE_BRICK_VERTICAL_SLAB, CreativeModeTab.TAB_BUILDING_BLOCKS);
	public static final RegistryObject<Item> BLACKSTONE_VERTICAL_SLAB = block(EnhancingModBlocks.BLACKSTONE_VERTICAL_SLAB, CreativeModeTab.TAB_BUILDING_BLOCKS);
	public static final RegistryObject<Item> POLISHED_BLACKSTONE_VERTICAL_SLAB = block(EnhancingModBlocks.POLISHED_BLACKSTONE_VERTICAL_SLAB, CreativeModeTab.TAB_BUILDING_BLOCKS);
	public static final RegistryObject<Item> POLISHED_BLACKSTONE_BRICK_VERTICAL_SLAB = block(EnhancingModBlocks.POLISHED_BLACKSTONE_BRICK_VERTICAL_SLAB, CreativeModeTab.TAB_BUILDING_BLOCKS);
	public static final RegistryObject<Item> CUT_COPPER_VERTICAL_SLAB = block(EnhancingModBlocks.CUT_COPPER_VERTICAL_SLAB, CreativeModeTab.TAB_BUILDING_BLOCKS);
	public static final RegistryObject<Item> EXPOSED_CUT_COPPER_VERTICAL_SLAB = block(EnhancingModBlocks.EXPOSED_CUT_COPPER_VERTICAL_SLAB, CreativeModeTab.TAB_BUILDING_BLOCKS);
	public static final RegistryObject<Item> WEATHERED_CUT_COPPER_VERTICAL_SLAB = block(EnhancingModBlocks.WEATHERED_CUT_COPPER_VERTICAL_SLAB, null);
	public static final RegistryObject<Item> OXIDIZED_CUT_COPPER_VERTICAL_SLAB = block(EnhancingModBlocks.OXIDIZED_CUT_COPPER_VERTICAL_SLAB, CreativeModeTab.TAB_BUILDING_BLOCKS);
	public static final RegistryObject<Item> WAXED_CUT_COPPER_VERTICAL_SLAB = block(EnhancingModBlocks.WAXED_CUT_COPPER_VERTICAL_SLAB, null);
	public static final RegistryObject<Item> WAXED_EXPOSED_CUT_COPPER_VERTICAL_SLAB = block(EnhancingModBlocks.WAXED_EXPOSED_CUT_COPPER_VERTICAL_SLAB, null);
	public static final RegistryObject<Item> WAXED_WEATHERED_CUT_COPPER_VERTICAL_SLAB = block(EnhancingModBlocks.WAXED_WEATHERED_CUT_COPPER_VERTICAL_SLAB, null);
	public static final RegistryObject<Item> WAXED_OXIDIZED_CUT_COPPER_VERTICAL_SLAB = block(EnhancingModBlocks.WAXED_OXIDIZED_CUT_COPPER_VERTICAL_SLAB, null);
	public static final RegistryObject<Item> COBBLED_DEEPSLATE_VERTICAL_SLAB = block(EnhancingModBlocks.COBBLED_DEEPSLATE_VERTICAL_SLAB, CreativeModeTab.TAB_BUILDING_BLOCKS);
	public static final RegistryObject<Item> POLISHED_DEEPSLATE_VERTICAL_SLAB = block(EnhancingModBlocks.POLISHED_DEEPSLATE_VERTICAL_SLAB, CreativeModeTab.TAB_BUILDING_BLOCKS);
	public static final RegistryObject<Item> DEEPSLATE_BRICK_VERTICAL_SLAB = block(EnhancingModBlocks.DEEPSLATE_BRICK_VERTICAL_SLAB, CreativeModeTab.TAB_BUILDING_BLOCKS);
	public static final RegistryObject<Item> DEEPSLATE_TILE_VERTICAL_SLAB = block(EnhancingModBlocks.DEEPSLATE_TILE_VERTICAL_SLAB, CreativeModeTab.TAB_BUILDING_BLOCKS);
	public static final RegistryObject<Item> MUD_BRICK_VERTICAL_SLAB = block(EnhancingModBlocks.MUD_BRICK_VERTICAL_SLAB, CreativeModeTab.TAB_BUILDING_BLOCKS);
	public static final RegistryObject<Item> OAKREDROOFTILES = block(EnhancingModBlocks.OAKREDROOFTILES, CreativeModeTab.TAB_BUILDING_BLOCKS);
	public static final RegistryObject<Item> HONEY_BALL = REGISTRY.register("honey_ball", () -> new HoneyBallItem());
	public static final RegistryObject<Item> FIRE_MASTER_STAFF = REGISTRY.register("fire_master_staff", () -> new FireMasterStaffItem());
	public static final RegistryObject<Item> RUNED_DEEPSLATE_BRICKS_ETA_ON = block(EnhancingModBlocks.RUNED_DEEPSLATE_BRICKS_ETA_ON, CreativeModeTab.TAB_BUILDING_BLOCKS);
	public static final RegistryObject<Item> RUNED_DEEPSLATE_BRICKS_ETA_OFF = block(EnhancingModBlocks.RUNED_DEEPSLATE_BRICKS_ETA_OFF, CreativeModeTab.TAB_BUILDING_BLOCKS);
	public static final RegistryObject<Item> RUNED_DEEPSLATE_BRICKS_GAMMA_ON = block(EnhancingModBlocks.RUNED_DEEPSLATE_BRICKS_GAMMA_ON, CreativeModeTab.TAB_BUILDING_BLOCKS);
	public static final RegistryObject<Item> RUNED_DEEPSLATE_BRICKS_GAMMA_OFF = block(EnhancingModBlocks.RUNED_DEEPSLATE_BRICKS_GAMMA_OFF, CreativeModeTab.TAB_BUILDING_BLOCKS);
	public static final RegistryObject<Item> RUNED_DEEPSLATE_BRICKS_BETA_OFF = block(EnhancingModBlocks.RUNED_DEEPSLATE_BRICKS_BETA_OFF, CreativeModeTab.TAB_BUILDING_BLOCKS);
	public static final RegistryObject<Item> RUNED_DEEPSLATE_BRICKS_BETA_ON = block(EnhancingModBlocks.RUNED_DEEPSLATE_BRICKS_BETA_ON, CreativeModeTab.TAB_BUILDING_BLOCKS);
	public static final RegistryObject<Item> BURNT_OUT_LANTERN = block(EnhancingModBlocks.BURNT_OUT_LANTERN, CreativeModeTab.TAB_BUILDING_BLOCKS);
	public static final RegistryObject<Item> DIRT_SLAB = block(EnhancingModBlocks.DIRT_SLAB, CreativeModeTab.TAB_BUILDING_BLOCKS);
	public static final RegistryObject<Item> DIRT_STAIRS = block(EnhancingModBlocks.DIRT_STAIRS, CreativeModeTab.TAB_BUILDING_BLOCKS);
	public static final RegistryObject<Item> WARDEN_ROBE_HELMET = REGISTRY.register("warden_robe_helmet", () -> new WardencapeItem.Helmet());
	public static final RegistryObject<Item> WARDEN_ROBE_CHESTPLATE = REGISTRY.register("warden_robe_chestplate", () -> new WardencapeItem.Chestplate());
	public static final RegistryObject<Item> WARDEN_ROBE_LEGGINGS = REGISTRY.register("warden_robe_leggings", () -> new WardencapeItem.Leggings());
	public static final RegistryObject<Item> WARDEN_ROBE_BOOTS = REGISTRY.register("warden_robe_boots", () -> new WardencapeItem.Boots());
	public static final RegistryObject<Item> HUNTING_BOW = REGISTRY.register("hunting_bow", () -> new HuntingBowItem());
	public static final RegistryObject<Item> COAL_FURNACE = block(EnhancingModBlocks.COAL_FURNACE, CreativeModeTab.TAB_DECORATIONS);

	private static RegistryObject<Item> block(RegistryObject<Block> block, CreativeModeTab tab) {
		return REGISTRY.register(block.getId().getPath(), () -> new BlockItem(block.get(), new Item.Properties().tab(tab)));
	}
}
