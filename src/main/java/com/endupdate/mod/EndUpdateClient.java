package com.endupdate.mod;

import com.endupdate.mod.entity.EtherSlashEntity;
import com.endupdate.mod.entity.ModEntities;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.item.v1.ItemTooltipCallback;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.state.EntityRenderState;
import net.minecraft.network.chat.Component;

public class EndUpdateClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		EndUpdateMod.LOGGER.info("End Update client loaded");

		EntityRendererRegistry.register(ModEntities.ETHER_SLASH, context ->
				new EntityRenderer<EtherSlashEntity, EntityRenderState>(context) {
					@Override
					public EntityRenderState createRenderState() {
						return new EntityRenderState();
					}
				}
		);

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

		EtherGearBonusClient.initialize();
	}
}
