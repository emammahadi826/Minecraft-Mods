package com.endupdate.mod;

import com.endupdate.mod.entity.EtherSlashEntity;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.fabricmc.fabric.api.event.player.AttackEntityCallback;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class EtherGearBonus {
    private static final int REAPPLY_INTERVAL = 200;
    private static final int EFFECT_DURATION = 400;
    private static int tickCounter = 0;

    public static void initialize() {
        ServerTickEvents.END_SERVER_TICK.register(server -> {
            tickCounter++;
            if (tickCounter >= REAPPLY_INTERVAL) {
                tickCounter = 0;
                for (ServerPlayer player : server.getPlayerList().getPlayers()) {
                    applyArmorBonuses(player);
                }
            }
        });

        AttackEntityCallback.EVENT.register((player, level, hand, entity, hitResult) -> {
            if (hand != InteractionHand.MAIN_HAND) return InteractionResult.PASS;
            if (!(level instanceof ServerLevel)) return InteractionResult.PASS;
            if (!isEtherSword(player.getMainHandItem()) && !isEtherAxe(player.getMainHandItem())) return InteractionResult.PASS;
            if (!(entity instanceof LivingEntity)) return InteractionResult.PASS;
            if (!isCriticalHit(player)) return InteractionResult.PASS;

            Vec3 eyePos = player.getEyePosition();
            Vec3 targetPos = entity.position().add(0, entity.getBbHeight() * 0.5, 0);
            Vec3 rawDir = new Vec3(targetPos.x - eyePos.x, 0, targetPos.z - eyePos.z);
            if (rawDir.lengthSqr() < 0.001) {
                EndUpdateMod.LOGGER.warn("[EtherGearBonus] Zero direction vector, skipping slash spawn");
                return InteractionResult.PASS;
            }
            Vec3 direction = rawDir.normalize();

            Vec3 spawnPos = eyePos.add(direction.scale(0.5));
            EtherSlashEntity slash = new EtherSlashEntity(level, spawnPos, direction, player);
            slash.setIsAxe(isEtherAxe(player.getMainHandItem()));
            level.addFreshEntity(slash);
            EndUpdateMod.LOGGER.info("[EtherGearBonus] Slash spawned for {} at pos={} dir={} target={}", player.getName().getString(), String.format("%.1f,%.1f,%.1f", spawnPos.x, spawnPos.y, spawnPos.z), String.format("%.2f,%.2f,%.2f", direction.x, direction.y, direction.z), entity.getName().getString());

            return InteractionResult.PASS;
        });

        EndUpdateMod.LOGGER.info("EtherGearBonus loaded");
    }

    private static void applyArmorBonuses(ServerPlayer player) {
        if (player.level().dimension() != Level.END || !isWearingFullEtherSet(player)) {
            removeBonus(player);
            return;
        }
        if (!player.hasEffect(net.minecraft.world.effect.MobEffects.RESISTANCE)
                || player.getEffect(net.minecraft.world.effect.MobEffects.RESISTANCE).getDuration() < 60) {
            player.addEffect(new net.minecraft.world.effect.MobEffectInstance(
                    net.minecraft.world.effect.MobEffects.RESISTANCE, EFFECT_DURATION, 0, false, false, true));
        }
    }

    private static boolean isWearingFullEtherSet(ServerPlayer player) {
        return player.getItemBySlot(net.minecraft.world.entity.EquipmentSlot.HEAD).getItem() == com.endupdate.mod.item.ModArmor.ETHER_HELMET
                && player.getItemBySlot(net.minecraft.world.entity.EquipmentSlot.CHEST).getItem() == com.endupdate.mod.item.ModArmor.ETHER_CHESTPLATE
                && player.getItemBySlot(net.minecraft.world.entity.EquipmentSlot.LEGS).getItem() == com.endupdate.mod.item.ModArmor.ETHER_LEGGINGS
                && player.getItemBySlot(net.minecraft.world.entity.EquipmentSlot.FEET).getItem() == com.endupdate.mod.item.ModArmor.ETHER_BOOTS;
    }

    private static void removeBonus(ServerPlayer player) {
        if (player.hasEffect(net.minecraft.world.effect.MobEffects.RESISTANCE)) {
            player.removeEffect(net.minecraft.world.effect.MobEffects.RESISTANCE);
        }
    }

    private static boolean isEtherSword(ItemStack stack) {
        return stack.is(com.endupdate.mod.item.ModTools.ETHER_SWORD);
    }

    private static boolean isEtherAxe(ItemStack stack) {
        return stack.is(com.endupdate.mod.item.ModTools.ETHER_AXE);
    }

    private static boolean isCriticalHit(Player player) {
        return player.fallDistance > 0
                && !player.onGround()
                && !player.onClimbable()
                && !player.isInWater()
                && !player.isPassenger()
                && !player.isSprinting();
    }
}
