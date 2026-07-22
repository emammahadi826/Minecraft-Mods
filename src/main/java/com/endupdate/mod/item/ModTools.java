package com.endupdate.mod.item;

import com.endupdate.mod.EndUpdateMod;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.HoeItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ShovelItem;

import java.util.function.Function;

public class ModTools {
	public static final Item ETHER_SWORD = register("ether_sword",
			Item::new,
			new Item.Properties().sword(ModToolMaterial.ETHER, 5.0F, -2.4F));

	public static final Item ETHER_PICKAXE = register("ether_pickaxe",
			Item::new,
			new Item.Properties().pickaxe(ModToolMaterial.ETHER, 1.5F, -2.8F));

	public static final Item ETHER_AXE = register("ether_axe",
			settings -> new AxeItem(ModToolMaterial.ETHER, 7.0F, -3.0F, settings),
			new Item.Properties());

	public static final Item ETHER_SHOVEL = register("ether_shovel",
			settings -> new ShovelItem(ModToolMaterial.ETHER, 2.0F, -3.0F, settings),
			new Item.Properties());

	public static final Item ETHER_HOE = register("ether_hoe",
			settings -> new HoeItem(ModToolMaterial.ETHER, -2.5F, 0.0F, settings),
			new Item.Properties());

	private static <T extends Item> T register(String name, Function<Item.Properties, T> factory, Item.Properties properties) {
		ResourceKey<Item> key = ResourceKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath(EndUpdateMod.MOD_ID, name));
		T item = factory.apply(properties.setId(key));
		Registry.register(BuiltInRegistries.ITEM, key, item);
		return item;
	}

	public static void initialize() {
		EndUpdateMod.LOGGER.info("ModTools loaded");
	}
}
