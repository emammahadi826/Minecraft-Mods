package com.endupdate.mod;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.item.v1.ItemTooltipCallback;
import net.minecraft.network.chat.Component;

public class EndUpdateClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		EndUpdateMod.LOGGER.info("End Update client loaded");

		ItemTooltipCallback.EVENT.register((stack, context, tooltipType, lines) -> {
			if (stack.isDamageableItem()) {
				int damage = stack.getDamageValue();
				int maxDamage = stack.getMaxDamage();
				int remaining = maxDamage - damage;
				lines.add(Component.literal(
						String.format("\u00A77Durability: %d / %d", remaining, maxDamage)
				));
			}
		});
	}
}
