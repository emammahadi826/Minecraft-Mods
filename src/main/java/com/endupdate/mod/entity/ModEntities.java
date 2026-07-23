package com.endupdate.mod.entity;

import com.endupdate.mod.EndUpdateMod;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;

public class ModEntities {
    private static final ResourceKey<EntityType<?>> ETHER_SLASH_KEY = ResourceKey.create(
            Registries.ENTITY_TYPE, Identifier.fromNamespaceAndPath(EndUpdateMod.MOD_ID, "ether_slash"));

    @SuppressWarnings("unchecked")
    public static final EntityType<EtherSlashEntity> ETHER_SLASH = (EntityType<EtherSlashEntity>) (EntityType<?>) Registry.register(
            BuiltInRegistries.ENTITY_TYPE,
            ETHER_SLASH_KEY,
            EntityType.Builder.<EtherSlashEntity>of(EtherSlashEntity::new, MobCategory.MISC)
                    .sized(0.5f, 0.5f)
                    .clientTrackingRange(64)
                    .updateInterval(1)
                    .noSave()
                    .build(ETHER_SLASH_KEY)
    );

    public static void initialize() {
        EndUpdateMod.LOGGER.info("ModEntities loaded");
    }
}
