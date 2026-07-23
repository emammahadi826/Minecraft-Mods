package com.endupdate.mod;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.client.particle.SingleQuadParticle;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.util.RandomSource;

public class EtherSlashParticle extends SingleQuadParticle {
    private final boolean isLarge;
    private final float targetSize;
    private final float rollSpeed;
    private double prevDrift;
    private float prevQuadSize;

    protected EtherSlashParticle(ClientLevel level, double x, double y, double z,
                                  double xd, double yd, double zd,
                                  TextureAtlasSprite sprite, boolean isLarge,
                                  float targetSize, int lifetime,
                                  float rollSpeed, float initialRoll) {
        super(level, x, y, z, 0, 0, 0, sprite);
        this.isLarge = isLarge;
        this.targetSize = targetSize;
        this.rollSpeed = rollSpeed;
        this.prevDrift = 0;
        this.gravity = 0;
        this.friction = 1.0f;
        this.hasPhysics = false;
        this.rCol = 1.0f;
        this.gCol = 1.0f;
        this.bCol = 1.0f;

        this.xd = xd;
        this.yd = yd;
        this.zd = zd;

        this.roll = initialRoll;
        this.oRoll = this.roll;
        this.lifetime = lifetime;
        this.alpha = isLarge ? 0.95f : 0.8f;
        this.quadSize = 0.1f;
        this.prevQuadSize = this.quadSize;
    }

    @Override
    public SingleQuadParticle.FacingCameraMode getFacingCameraMode() {
        return SingleQuadParticle.FacingCameraMode.LOOKAT_Y;
    }

    @Override
    public void tick() {
        this.xo = this.x;
        this.yo = this.y;
        this.zo = this.z;
        this.oRoll = this.roll;
        this.prevQuadSize = this.quadSize;

        if (this.age++ >= this.lifetime) {
            this.remove();
            return;
        }

        float progress = (float) this.age / (float) this.lifetime;

        this.quadSize = this.targetSize * easeOutCubic(Math.min(progress * 2.0f, 1.0f));

        if (isLarge) {
            this.alpha = progress < 0.8f ? 0.95f : Math.max(0.0f, 0.95f * (1.0f - (progress - 0.8f) / 0.2f));
        } else {
            this.alpha = 0.8f * (1.0f - progress);
        }

        this.roll += this.rollSpeed;

        this.move(this.xd, this.yd, this.zd);

        if (!isLarge) {
            double speed = Math.sqrt(this.xd * this.xd + this.zd * this.zd);
            if (speed > 0.001) {
                double perpX = -this.zd / speed;
                double perpZ = this.xd / speed;
                double newDrift = Math.sin(this.age * 0.3) * 0.05;
                double delta = newDrift - this.prevDrift;
                this.prevDrift = newDrift;
                this.setPos(this.x + perpX * delta, this.y, this.z + perpZ * delta);
            }
        }

        if (this.alpha < 0.01f) {
            this.remove();
        }
    }

    private static float easeOutCubic(float t) {
        float clamped = Math.min(Math.max(t, 0), 1);
        return 1.0f - (float) Math.pow(1.0 - clamped, 3);
    }

    @Override
    public float getQuadSize(float partialTick) {
        return this.prevQuadSize + (this.quadSize - this.prevQuadSize) * partialTick;
    }

    @Override
    public ParticleRenderType getGroup() {
        return ParticleRenderType.SINGLE_QUADS;
    }

    @Override
    protected Layer getLayer() {
        return Layer.TRANSLUCENT;
    }

    public static class LargeProvider implements ParticleProvider<SimpleParticleType> {
        private final SpriteSet sprites;

        public LargeProvider(SpriteSet sprites) {
            this.sprites = sprites;
        }

        @Override
        public Particle createParticle(SimpleParticleType type, ClientLevel level,
                                        double x, double y, double z,
                                        double dx, double dy, double dz,
                                        RandomSource random) {
            return new EtherSlashParticle(level, x, y, z,
                    dx * 0.50, 0, dz * 0.50,
                    this.sprites.get(random), true,
                    5.0f, 20, 0, (float) Math.atan2(-dx, dz));
        }
    }

    public static class Small1Provider implements ParticleProvider<SimpleParticleType> {
        private final SpriteSet sprites;

        public Small1Provider(SpriteSet sprites) {
            this.sprites = sprites;
        }

        @Override
        public Particle createParticle(SimpleParticleType type, ClientLevel level,
                                        double x, double y, double z,
                                        double dx, double dy, double dz,
                                        RandomSource random) {
            float roll = random.nextFloat() * (float) (Math.PI * 2);
            return new EtherSlashParticle(level, x, y, z,
                    dx * 0.44, 0, dz * 0.44,
                    this.sprites.get(random), false,
                    2.8f, 16 + random.nextInt(3),
                    (float) (random.nextDouble() * 0.02 - 0.01), roll);
        }
    }

    public static class Small2Provider implements ParticleProvider<SimpleParticleType> {
        private final SpriteSet sprites;

        public Small2Provider(SpriteSet sprites) {
            this.sprites = sprites;
        }

        @Override
        public Particle createParticle(SimpleParticleType type, ClientLevel level,
                                        double x, double y, double z,
                                        double dx, double dy, double dz,
                                        RandomSource random) {
            float roll = random.nextFloat() * (float) (Math.PI * 2);
            return new EtherSlashParticle(level, x, y, z,
                    dx * 0.40, 0, dz * 0.40,
                    this.sprites.get(random), false,
                    2.5f, 16 + random.nextInt(3),
                    (float) (random.nextDouble() * 0.02 - 0.01), roll);
        }
    }

    public static class Small3Provider implements ParticleProvider<SimpleParticleType> {
        private final SpriteSet sprites;

        public Small3Provider(SpriteSet sprites) {
            this.sprites = sprites;
        }

        @Override
        public Particle createParticle(SimpleParticleType type, ClientLevel level,
                                        double x, double y, double z,
                                        double dx, double dy, double dz,
                                        RandomSource random) {
            float roll = random.nextFloat() * (float) (Math.PI * 2);
            return new EtherSlashParticle(level, x, y, z,
                    dx * 0.36, 0, dz * 0.36,
                    this.sprites.get(random), false,
                    2.2f, 16 + random.nextInt(3),
                    (float) (random.nextDouble() * 0.02 - 0.01), roll);
        }
    }
}
