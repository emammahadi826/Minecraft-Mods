package com.endupdate.mod.item;

import com.endupdate.mod.EndUpdateMod;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item;

import java.util.function.Function;

public class ModItems {
	public static final Item RAW_ETHER = register("raw_ether",
			Item::new,
			new Item.Properties());

	public static final Item ETHER_INGOT = register("ether_ingot",
			Item::new,
			new Item.Properties());

	public static final Item ETHER_NUGGET = register("ether_nugget",
			Item::new,
			new Item.Properties());

	private static <T extends Item> T register(String name, Function<Item.Properties, T> factory, Item.Properties properties) {
		ResourceKey<Item> key = ResourceKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath(EndUpdateMod.MOD_ID, name));
		T item = factory.apply(properties.setId(key));
		Registry.register(BuiltInRegistries.ITEM, key, item);
		return item;
	}

	public static void initialize() {
		EndUpdateMod.LOGGER.info("ModItems loaded");
	}
}
