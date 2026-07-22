package com.endupdate.mod.item;

import com.endupdate.mod.EndUpdateMod;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ToolMaterial;
import net.minecraft.world.level.block.Block;

public class ModToolMaterial {
	public static final TagKey<Block> INCORRECT_FOR_ETHER_TOOL = TagKey.create(
			BuiltInRegistries.BLOCK.key(),
			Identifier.fromNamespaceAndPath(EndUpdateMod.MOD_ID, "incorrect_for_ether_tool")
	);

	public static final TagKey<Item> REPAIRS_ETHER = TagKey.create(
			BuiltInRegistries.ITEM.key(),
			Identifier.fromNamespaceAndPath(EndUpdateMod.MOD_ID, "repairs_ether")
	);

	public static final ToolMaterial ETHER = new ToolMaterial(
			INCORRECT_FOR_ETHER_TOOL,
			2500,
			9.5F,
			5.0F,
			16,
			REPAIRS_ETHER
	);

	public static void initialize() {
		EndUpdateMod.LOGGER.info("ModToolMaterial loaded");
	}
}
