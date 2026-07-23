package com.endupdate.mod;

import com.endupdate.mod.block.ModBlocks;
import com.endupdate.mod.item.ModArmor;
import com.endupdate.mod.item.ModItemGroup;
import com.endupdate.mod.item.ModItems;
import com.endupdate.mod.item.ModToolMaterial;
import com.endupdate.mod.item.ModTools;
import com.endupdate.mod.worldgen.ModWorldGen;
import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EndUpdateMod implements ModInitializer {
	public static final String MOD_ID = "end_update";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		ModToolMaterial.initialize();
		ModBlocks.initialize();
		ModItems.initialize();
		ModTools.initialize();
		ModArmor.initialize();
		ModItemGroup.initialize();
		ModWorldGen.initialize();
		LOGGER.info("End Update loaded!");
	}
}
