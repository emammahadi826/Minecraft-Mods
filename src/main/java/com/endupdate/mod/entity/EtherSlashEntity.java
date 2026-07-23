package com.endupdate.mod.entity;

import com.endupdate.mod.EndUpdateMod;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

public class EtherSlashEntity extends Entity {
    public static final double SPEED = 0.5;
    public static final double MAX_DISTANCE = 10.0;
    public static final float DAMAGE = 6.0f;
    private static final double HIT_RADIUS = 2.0;

    private Player owner;
    private double distanceTraveled;
    private final Set<UUID> damagedEntities = new HashSet<>();

    public EtherSlashEntity(EntityType<?> type, Level level) {
        super(type, level);
        this.setNoGravity(true);
    }

    public EtherSlashEntity(Level level, Vec3 pos, Vec3 direction, Player owner) {
        this(ModEntities.ETHER_SLASH, level);
        this.setPos(pos.x, pos.y, pos.z);
        this.owner = owner;
        this.setDeltaMovement(direction.normalize().scale(SPEED));
    }

    public Player getOwner() {
        return this.owner;
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
    }

    @Override
    public void tick() {
        super.tick(); // ponytail: baseTick() increments tickCount, updates chunk section tracking

        this.xo = this.getX();
        this.yo = this.getY();
        this.zo = this.getZ();

        Vec3 vel = this.getDeltaMovement();
        this.setPos(this.getX() + vel.x, this.getY() + vel.y, this.getZ() + vel.z);

        this.distanceTraveled += SPEED;

        if (!this.level().isClientSide()) {
            checkEntityCollision();
            spawnServerParticles();
        }

        if (this.distanceTraveled >= MAX_DISTANCE || this.tickCount > 40) {
            EndUpdateMod.LOGGER.info("[EtherSlash] Discarding at pos={} dist={} ticks={}", String.format("%.1f,%.1f,%.1f", this.getX(), this.getY(), this.getZ()), String.format("%.1f", this.distanceTraveled), this.tickCount);
            this.discard();
        }
    }

    private void checkEntityCollision() {
        if (this.owner == null) {
            EndUpdateMod.LOGGER.warn("[EtherSlash] owner is null at pos={}", String.format("%.1f,%.1f,%.1f", this.getX(), this.getY(), this.getZ()));
            return;
        }

        AABB box = this.getBoundingBox().inflate(1.5);
        Vec3 startPos = new Vec3(this.xo, this.yo, this.zo);
        Vec3 vel = this.getDeltaMovement();
        double velLen = vel.length();
        if (velLen < 0.001) {
            EndUpdateMod.LOGGER.warn("[EtherSlash] zero velocity at pos={}", String.format("%.1f,%.1f,%.1f", this.getX(), this.getY(), this.getZ()));
            return;
        }
        Vec3 dir = vel.normalize();

        List<LivingEntity> entities = this.level().getEntitiesOfClass(LivingEntity.class, box,
                e -> e.isAlive() && e != this.owner && !this.damagedEntities.contains(e.getUUID()));

        DamageSource source = this.level().damageSources().playerAttack(this.owner);

        EndUpdateMod.LOGGER.info("[EtherSlash] Collision check: {} candidates at pos={} box_size={}", entities.size(), String.format("%.1f,%.1f,%.1f", this.getX(), this.getY(), this.getZ()), String.format("%.1f", box.getXsize()));

        for (LivingEntity entity : entities) {
            Vec3 entityCenter = entity.position().add(0, entity.getBbHeight() * 0.5, 0);
            Vec3 toEntity = entityCenter.subtract(startPos);
            double projection = toEntity.dot(dir);
            if (projection < 0 || projection > velLen + 1.0) continue;

            Vec3 closest = startPos.add(dir.scale(projection));
            double dist = entityCenter.distanceTo(closest);
            if (dist < HIT_RADIUS) {
                EndUpdateMod.LOGGER.info("[EtherSlash] HIT {} at dist={} hp_before={}", entity.getName().getString(), String.format("%.2f", dist), String.format("%.1f", entity.getHealth()));
                entity.hurt(source, DAMAGE);
                EndUpdateMod.LOGGER.info("[EtherSlash] HIT {} hp_after={}", entity.getName().getString(), String.format("%.1f", entity.getHealth()));
                this.damagedEntities.add(entity.getUUID());
            }
        }
    }

    private void spawnServerParticles() {
        if (this.level() instanceof ServerLevel serverLevel) {
            serverLevel.sendParticles(ParticleTypes.END_ROD,
                    this.getX(), this.getY(), this.getZ(),
                    2, 0.1, 0.1, 0.1, 0.01);
        }
    }

    @Override
    protected void addAdditionalSaveData(ValueOutput output) {
    }

    @Override
    protected void readAdditionalSaveData(ValueInput input) {
    }

    @Override
    public float getPickRadius() {
        return 0.5f;
    }

    @Override
    public boolean isNoGravity() {
        return true;
    }

    @Override
    public boolean hurtServer(ServerLevel serverLevel, net.minecraft.world.damagesource.DamageSource source, float amount) {
        return false;
    }
}
