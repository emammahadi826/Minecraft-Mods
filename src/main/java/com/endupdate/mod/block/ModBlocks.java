package com.endupdate.mod.block;

import com.endupdate.mod.EndUpdateMod;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;

import java.util.function.Function;

public class ModBlocks {
	public static final Block ETHER_ORE = register("ether_ore",
			Block::new,
			BlockBehaviour.Properties.of()
					.strength(3.0F, 3.0F)
					.sound(SoundType.STONE)
					.requiresCorrectToolForDrops(),
			true);

	public static final Block RAW_ETHER_BLOCK = register("raw_ether_block",
			Block::new,
			BlockBehaviour.Properties.of()
					.strength(3.0F, 3.0F)
					.sound(SoundType.STONE)
					.requiresCorrectToolForDrops(),
			true);

	public static final Block ETHER_BLOCK = register("ether_block",
			Block::new,
			BlockBehaviour.Properties.of()
					.strength(5.0F, 6.0F)
					.sound(SoundType.METAL)
					.requiresCorrectToolForDrops(),
			true);

	private static Block register(String name, Function<BlockBehaviour.Properties, Block> factory,
								  BlockBehaviour.Properties properties, boolean shouldRegisterItem) {
		ResourceKey<Block> blockKey = ResourceKey.create(Registries.BLOCK, Identifier.fromNamespaceAndPath(EndUpdateMod.MOD_ID, name));
		Block block = factory.apply(properties.setId(blockKey));

		if (shouldRegisterItem) {
			ResourceKey<Item> itemKey = ResourceKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath(EndUpdateMod.MOD_ID, name));
			BlockItem blockItem = new BlockItem(block, new Item.Properties().setId(itemKey).useBlockDescriptionPrefix());
			Registry.register(BuiltInRegistries.ITEM, itemKey, blockItem);
		}

		return Registry.register(BuiltInRegistries.BLOCK, blockKey, block);
	}

	public static void initialize() {
		EndUpdateMod.LOGGER.info("ModBlocks loaded");
	}
}
