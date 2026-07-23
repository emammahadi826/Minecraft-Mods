package com.endupdate.mod.item;

import com.endupdate.mod.EndUpdateMod;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.equipment.ArmorType;
import net.minecraft.world.item.Item;

import java.util.function.Function;

public class ModArmor {
	public static final Item ETHER_HELMET = register("ether_helmet",
			Item::new,
			new Item.Properties().humanoidArmor(ModArmorMaterial.ETHER_ARMOR, ArmorType.HELMET).fireResistant());

	public static final Item ETHER_CHESTPLATE = register("ether_chestplate",
			Item::new,
			new Item.Properties().humanoidArmor(ModArmorMaterial.ETHER_ARMOR, ArmorType.CHESTPLATE).fireResistant());

	public static final Item ETHER_LEGGINGS = register("ether_leggings",
			Item::new,
			new Item.Properties().humanoidArmor(ModArmorMaterial.ETHER_ARMOR, ArmorType.LEGGINGS).fireResistant());

	public static final Item ETHER_BOOTS = register("ether_boots",
			Item::new,
			new Item.Properties().humanoidArmor(ModArmorMaterial.ETHER_ARMOR, ArmorType.BOOTS).fireResistant());

	private static <T extends Item> T register(String name, Function<Item.Properties, T> factory, Item.Properties properties) {
		ResourceKey<Item> key = ResourceKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath(EndUpdateMod.MOD_ID, name));
		T item = factory.apply(properties.setId(key));
		Registry.register(BuiltInRegistries.ITEM, key, item);
		return item;
	}

	public static void initialize() {
		EndUpdateMod.LOGGER.info("ModArmor loaded");
	}
}
