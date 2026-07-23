package com.endupdate.mod.item;

import com.endupdate.mod.EndUpdateMod;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.equipment.ArmorMaterial;
import net.minecraft.world.item.equipment.ArmorType;
import net.minecraft.world.item.equipment.EquipmentAsset;
import net.minecraft.world.item.equipment.EquipmentAssets;

import java.util.Map;

public class ModArmorMaterial {
	public static final ResourceKey<EquipmentAsset> ETHER_ARMOR_KEY = ResourceKey.create(EquipmentAssets.ROOT_ID, Identifier.fromNamespaceAndPath(EndUpdateMod.MOD_ID, "ether"));

	public static final ArmorMaterial ETHER_ARMOR = new ArmorMaterial(
			5,
			Map.of(
					ArmorType.BOOTS, 3,
					ArmorType.LEGGINGS, 6,
					ArmorType.CHESTPLATE, 8,
					ArmorType.HELMET, 3,
					ArmorType.BODY, 11
			),
			15,
			SoundEvents.ARMOR_EQUIP_NETHERITE,
			3.0F,
			0.1F,
			ModToolMaterial.REPAIRS_ETHER,
			ETHER_ARMOR_KEY
	);

	public static void initialize() {
		EndUpdateMod.LOGGER.info("ModArmorMaterial loaded");
	}
}
