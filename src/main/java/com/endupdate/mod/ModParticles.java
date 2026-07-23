package com.endupdate.mod;

import net.fabricmc.fabric.api.particle.v1.FabricParticleTypes;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.resources.Identifier;

public class ModParticles {
    public static final SimpleParticleType ETHER_SLASH = FabricParticleTypes.simple();
    public static final SimpleParticleType ETHER_SLASH_SMALL_1 = FabricParticleTypes.simple();
    public static final SimpleParticleType ETHER_SLASH_SMALL_2 = FabricParticleTypes.simple();
    public static final SimpleParticleType ETHER_SLASH_SMALL_3 = FabricParticleTypes.simple();
    public static final SimpleParticleType ETHER_AXE_SLASH = FabricParticleTypes.simple();
    public static final SimpleParticleType ETHER_AXE_SLASH_SMALL_1 = FabricParticleTypes.simple();
    public static final SimpleParticleType ETHER_AXE_SLASH_SMALL_2 = FabricParticleTypes.simple();
    public static final SimpleParticleType ETHER_AXE_SLASH_SMALL_3 = FabricParticleTypes.simple();

    public static void initialize() {
        Registry.register(BuiltInRegistries.PARTICLE_TYPE,
                Identifier.fromNamespaceAndPath(EndUpdateMod.MOD_ID, "ether_slash"), ETHER_SLASH);
        Registry.register(BuiltInRegistries.PARTICLE_TYPE,
                Identifier.fromNamespaceAndPath(EndUpdateMod.MOD_ID, "ether_slash_small_1"), ETHER_SLASH_SMALL_1);
        Registry.register(BuiltInRegistries.PARTICLE_TYPE,
                Identifier.fromNamespaceAndPath(EndUpdateMod.MOD_ID, "ether_slash_small_2"), ETHER_SLASH_SMALL_2);
        Registry.register(BuiltInRegistries.PARTICLE_TYPE,
                Identifier.fromNamespaceAndPath(EndUpdateMod.MOD_ID, "ether_slash_small_3"), ETHER_SLASH_SMALL_3);
        Registry.register(BuiltInRegistries.PARTICLE_TYPE,
                Identifier.fromNamespaceAndPath(EndUpdateMod.MOD_ID, "ether_axe_slash"), ETHER_AXE_SLASH);
        Registry.register(BuiltInRegistries.PARTICLE_TYPE,
                Identifier.fromNamespaceAndPath(EndUpdateMod.MOD_ID, "ether_axe_slash_small_1"), ETHER_AXE_SLASH_SMALL_1);
        Registry.register(BuiltInRegistries.PARTICLE_TYPE,
                Identifier.fromNamespaceAndPath(EndUpdateMod.MOD_ID, "ether_axe_slash_small_2"), ETHER_AXE_SLASH_SMALL_2);
        Registry.register(BuiltInRegistries.PARTICLE_TYPE,
                Identifier.fromNamespaceAndPath(EndUpdateMod.MOD_ID, "ether_axe_slash_small_3"), ETHER_AXE_SLASH_SMALL_3);
        EndUpdateMod.LOGGER.info("ModParticles loaded");
    }
}
