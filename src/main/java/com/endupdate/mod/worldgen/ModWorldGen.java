package com.endupdate.mod.worldgen;

import com.endupdate.mod.EndUpdateMod;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;

public class ModWorldGen {
	public static final ResourceKey<ConfiguredFeature<?, ?>> ETHER_ORE_CONFIGURED = ResourceKey.create(
			Registries.CONFIGURED_FEATURE,
			Identifier.fromNamespaceAndPath(EndUpdateMod.MOD_ID, "ether_ore")
	);

	public static final ResourceKey<PlacedFeature> ETHER_ORE_PLACED = ResourceKey.create(
			Registries.PLACED_FEATURE,
			Identifier.fromNamespaceAndPath(EndUpdateMod.MOD_ID, "ether_ore")
	);

	public static void initialize() {
		BiomeModifications.addFeature(
				BiomeSelectors.foundInTheEnd(),
				GenerationStep.Decoration.UNDERGROUND_ORES,
				ETHER_ORE_PLACED
		);
		EndUpdateMod.LOGGER.info("ModWorldGen loaded");
	}
}
