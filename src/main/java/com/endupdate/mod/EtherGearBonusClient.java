package com.endupdate.mod;

import com.endupdate.mod.entity.EtherSlashEntity;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.particle.v1.ParticleProviderRegistry;
import net.fabricmc.fabric.api.event.player.AttackEntityCallback;
import net.fabricmc.fabric.api.event.player.UseItemCallback;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class EtherGearBonusClient {
    private static boolean wasHoldingSword = false;
    private static boolean wasHoldingAxe = false;
    private static final Set<UUID> visualSpawned = new HashSet<>();

    public static void initialize() {
        ParticleProviderRegistry.getInstance().register(ModParticles.ETHER_SLASH, EtherSlashParticle.LargeProvider::new);
        ParticleProviderRegistry.getInstance().register(ModParticles.ETHER_SLASH_SMALL_1, EtherSlashParticle.Small1Provider::new);
        ParticleProviderRegistry.getInstance().register(ModParticles.ETHER_SLASH_SMALL_2, EtherSlashParticle.Small2Provider::new);
        ParticleProviderRegistry.getInstance().register(ModParticles.ETHER_SLASH_SMALL_3, EtherSlashParticle.Small3Provider::new);

        AttackEntityCallback.EVENT.register((player, level, hand, entity, hitResult) -> {
            if (hand != InteractionHand.MAIN_HAND || !(level instanceof ClientLevel clientLevel)) return InteractionResult.PASS;
            ItemStack stack = player.getMainHandItem();
            if (stack.isEmpty()) return InteractionResult.PASS;

            Vec3 pos = entity.position();
            if (isEtherSword(stack)) {
                spawnSwordHitParticles(clientLevel, pos);
            } else if (isEtherAxe(stack)) {
                spawnAxeHitParticles(clientLevel, pos);
            } else if (isEtherPickaxe(stack)) {
                spawnPickaxeHitParticles(clientLevel, pos);
            }
            return InteractionResult.PASS;
        });

        UseItemCallback.EVENT.register((player, level, hand) -> {
            if (hand != InteractionHand.MAIN_HAND || !(level instanceof ClientLevel clientLevel)) return InteractionResult.PASS;
            ItemStack stack = player.getMainHandItem();
            if (isEtherShovel(stack)) {
                Vec3 look = player.getLookAngle();
                Vec3 spawn = player.position().add(look.x * 2, 0.5, look.z * 2);
                clientLevel.addParticle(ParticleTypes.CLOUD, spawn.x, spawn.y, spawn.z, 0, 0.02, 0);
            }
            return InteractionResult.PASS;
        });

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (client.player == null || client.level == null) return;
            ItemStack mainHand = client.player.getMainHandItem();

            boolean holdingSword = isEtherSword(mainHand);
            boolean holdingAxe = isEtherAxe(mainHand);

            if (holdingSword && !wasHoldingSword) {
                client.level.addParticle(ParticleTypes.ENCHANT,
                        client.player.getX(), client.player.getY() + 1.5, client.player.getZ(),
                        0, 0.05, 0);
            }
            if (holdingAxe && !wasHoldingAxe) {
                client.level.addParticle(ParticleTypes.SOUL_FIRE_FLAME,
                        client.player.getX(), client.player.getY() + 1.5, client.player.getZ(),
                        0, 0.02, 0);
            }

            wasHoldingSword = holdingSword;
            wasHoldingAxe = holdingAxe;

            visualSpawned.removeIf(uuid -> client.level.getEntity(uuid) == null);

            for (Entity entity : client.level.entitiesForRendering()) {
                if (entity instanceof EtherSlashEntity slash && !visualSpawned.contains(slash.getUUID())) {
                    visualSpawned.add(slash.getUUID());

                    Vec3 pos = slash.position();
                    Vec3 vel = slash.getDeltaMovement();
                    double len = vel.length();
                    if (len > 0.001) {
                        Vec3 dir = vel.normalize();
                        spawnSlashVisual(client.level, pos, dir);
                    }
                }
            }
        });

        EndUpdateMod.LOGGER.info("EtherGearBonusClient loaded");
    }

    private static void spawnSlashVisual(ClientLevel level, Vec3 pos, Vec3 dir) {
        double perpX = -dir.z;
        double perpZ = dir.x;

        level.addParticle(ModParticles.ETHER_SLASH,
                pos.x, pos.y + 0.10, pos.z,
                dir.x, 0, dir.z);

        level.addParticle(ModParticles.ETHER_SLASH_SMALL_1,
                pos.x + perpX * (-0.20), pos.y - 0.05, pos.z + perpZ * (-0.20),
                dir.x, 0, dir.z);

        level.addParticle(ModParticles.ETHER_SLASH_SMALL_2,
                pos.x + perpX * 0.20, pos.y - 0.05, pos.z + perpZ * 0.20,
                dir.x, 0, dir.z);

        level.addParticle(ModParticles.ETHER_SLASH_SMALL_3,
                pos.x, pos.y - 0.10, pos.z,
                dir.x, 0, dir.z);
    }

    private static void spawnSwordHitParticles(ClientLevel level, Vec3 pos) {
        for (int i = 0; i < 8; i++) {
            double x = pos.x + (level.getRandom().nextDouble() - 0.5) * 0.8;
            double y = pos.y + level.getRandom().nextDouble() * 1.2;
            double z = pos.z + (level.getRandom().nextDouble() - 0.5) * 0.8;
            level.addParticle(ParticleTypes.ENCHANTED_HIT, x, y, z, 0, 0, 0);
        }
    }

    private static void spawnAxeHitParticles(ClientLevel level, Vec3 pos) {
        for (int i = 0; i < 6; i++) {
            double x = pos.x + (level.getRandom().nextDouble() - 0.5) * 0.6;
            double y = pos.y + level.getRandom().nextDouble() * 1.0;
            double z = pos.z + (level.getRandom().nextDouble() - 0.5) * 0.6;
            level.addParticle(ParticleTypes.SOUL_FIRE_FLAME, x, y, z, 0, 0.03, 0);
        }
    }

    private static void spawnPickaxeHitParticles(ClientLevel level, Vec3 pos) {
        for (int i = 0; i < 6; i++) {
            double x = pos.x + (level.getRandom().nextDouble() - 0.5) * 0.6;
            double y = pos.y + level.getRandom().nextDouble() * 1.0;
            double z = pos.z + (level.getRandom().nextDouble() - 0.5) * 0.6;
            level.addParticle(ParticleTypes.ENCHANT, x, y, z, 0, 0.02, 0);
        }
    }

    private static boolean isEtherSword(ItemStack stack) {
        return stack.is(com.endupdate.mod.item.ModTools.ETHER_SWORD);
    }

    private static boolean isEtherAxe(ItemStack stack) {
        return stack.is(com.endupdate.mod.item.ModTools.ETHER_AXE);
    }

    private static boolean isEtherPickaxe(ItemStack stack) {
        return stack.is(com.endupdate.mod.item.ModTools.ETHER_PICKAXE);
    }

    private static boolean isEtherShovel(ItemStack stack) {
        return stack.is(com.endupdate.mod.item.ModTools.ETHER_SHOVEL);
    }
}
