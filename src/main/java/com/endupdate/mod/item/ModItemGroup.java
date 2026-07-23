package com.endupdate.mod.item;

import com.endupdate.mod.EndUpdateMod;
import com.endupdate.mod.block.ModBlocks;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.fabricmc.fabric.api.creativetab.v1.FabricCreativeModeTab;

public class ModItemGroup {
	public static final ResourceKey<CreativeModeTab> END_UPDATE_TAB_KEY = ResourceKey.create(
			BuiltInRegistries.CREATIVE_MODE_TAB.key(),
			Identifier.fromNamespaceAndPath(EndUpdateMod.MOD_ID, "creative_tab")
	);

	public static final CreativeModeTab END_UPDATE_TAB = FabricCreativeModeTab.builder()
			.icon(() -> new ItemStack(ModTools.ETHER_SWORD))
			.title(Component.translatable("itemGroup.end_update"))
			.displayItems((params, output) -> {
				output.accept(ModBlocks.ETHER_ORE.asItem());
				output.accept(ModItems.RAW_ETHER);
				output.accept(ModBlocks.RAW_ETHER_BLOCK.asItem());
				output.accept(ModItems.ETHER_INGOT);
				output.accept(ModItems.ETHER_NUGGET);
				output.accept(ModBlocks.ETHER_BLOCK.asItem());
				output.accept(ModTools.ETHER_SWORD);
				output.accept(ModTools.ETHER_PICKAXE);
				output.accept(ModTools.ETHER_AXE);
				output.accept(ModTools.ETHER_SHOVEL);
			output.accept(ModTools.ETHER_HOE);
			output.accept(ModItems.ETHER_UPGRADE_SMITHING_TEMPLATE);
			output.accept(ModArmor.ETHER_HELMET);
			output.accept(ModArmor.ETHER_CHESTPLATE);
			output.accept(ModArmor.ETHER_LEGGINGS);
			output.accept(ModArmor.ETHER_BOOTS);
		})
			.build();

	public static void initialize() {
		Registry.register(BuiltInRegistries.CREATIVE_MODE_TAB, END_UPDATE_TAB_KEY, END_UPDATE_TAB);
		EndUpdateMod.LOGGER.info("ModItemGroup loaded");
	}
}
