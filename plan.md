# End Update Mod — Complete Development Plan

## Project Overview

A large vanilla-quality **End dimension expansion** mod for Minecraft Java Edition, built with Fabric. The mod adds a new End-exclusive material called **Ether** with a full progression system.

---

## Development Rules

The project must be developed strictly one phase at a time.

Only implement the phase that is currently requested.

Never automatically continue to the next phase.

After completing a phase:

- Stop development.
- Verify the project builds successfully.
- Wait for my approval before starting the next phase.

Do not create placeholder implementations for future phases unless explicitly requested.

---

## Target Environment

| Component | Version | Notes |
|-----------|---------|-------|
| Minecraft | 26.1.2 | Unobfuscated (no mappings needed) |
| Fabric Loader | 0.19.3 | Latest stable |
| Fabric API | 0.155.2+26.1.2 | Latest for MC 26.1.2 |
| Loom | 1.17.12 | Plugin: `net.fabricmc.fabric-loom` |
| Java | 25 | Required by MC 26.1.2 |
| Gradle | 9.5.0 | Fabric recommended for Loom 1.17 |

### MC 26.1 Key Changes

- **Unobfuscated** — no Yarn mappings, use Mojang names directly
- **Dependencies** use `implementation` not `modImplementation`
- **No `mappings` line** in `build.gradle`
- **`ResourceKey` based registration** — blocks/items need `properties.setId(key)`
- **`Identifier.fromNamespaceAndPath()`** for creating identifiers
- **`splitEnvironmentSourceSets()`** — `src/main/java` (common) and `src/client/java` (client)
- **`ToolMaterial` record** with 6 parameters
- **Tool items**: `.sword()`, `AxeItem`, `ShovelItem`, `HoeItem`
- **`ArmorMaterial` record** — defense(boots,leggings,chest,helmet,body), enchantability, equipSound, toughness, knockbackResistance, repairIngredient, assetId
- **`humanoidArmor()`** on Item.Properties — no HelmetItem/ChestplateItem classes needed
- **`smithing_transform`** recipe format: `template` + `base` + `addition` → `result`

---

## Mod Details

- **Mod ID:** `end_update`
- **Package:** `com.endupdate.mod`
- **Name:** End Update

---

## Asset Rules

All existing PNG textures are final assets.

Never regenerate, replace, redraw, recolor, upscale, compress, or overwrite unless explicitly requested.

---

## Existing Assets

### Block Textures (3)
- `textures/block/ether_ore.png`
- `textures/block/raw_ether_block.png`
- `textures/block/ether_block.png`

### Item Textures — Tools + Materials (8)
- `textures/item/raw_ether.png`
- `textures/item/ether_ingot.png`
- `textures/item/ether_nugget.png`
- `textures/item/ether_sword.png`
- `textures/item/ether_pickaxe.png`
- `textures/item/ether_axe.png`
- `textures/item/ether_shovel.png`
- `textures/item/ether_hoe.png`

### Item Textures — Armor (4, need rename)
- `textures/item/helmetn16by16.png` → rename to `ether_helmet.png`
- `textures/item/Chest_pelt_16by16.png` → rename to `ether_chestplate.png`
- `textures/item/lagging16by16.png` → rename to `ether_leggings.png`
- `textures/item/boots16by16.png` → rename to `ether_boots.png`

### Item Texture — Smithing Template (1, needs rename)
- `textures/item/ethar smithhing tamplate.png` → rename to `ether_upgrade_smithing_template.png`

### Mod Icon (1)
- `icon.png`

### Missing — Need User to Provide
- `textures/entity/equipment/humanoid/ether.png` (64×32) — worn armor layer 1
- `textures/entity/equipment/humanoid_leggings/ether.png` (64×32) — worn armor layer 2

---

## Naming Convention

All IDs follow Minecraft's `lowercase_underscore` convention.

---

## Coding Standards

- No duplicate code
- Keep registry code modular
- Keep classes small
- Use constants where appropriate
- Follow Mojang naming conventions
- Single responsibility per method

---

## Development Phases

### Phase 1: Project Structure & Dependencies ✅ COMPLETE

### Phase 2: Blocks & Items Registration ✅ COMPLETE

### Phase 3: Tool Material ✅ COMPLETE

### Phase 4: Models, Blockstates & Textures ✅ COMPLETE

### Phase 5: Loot Tables ✅ COMPLETE

### Phase 6: Recipes ✅ COMPLETE

### Phase 7: Tags ✅ COMPLETE

### Phase 8: World Generation ✅ COMPLETE

### Phase 9: Advancements ✅ COMPLETE

---

### Phase 10: Ether Armor + Smithing Upgrade System

**Goal:** Add Ether armor (helmet, chestplate, leggings, boots) + convert all ether tools from crafting recipes to smithing upgrade path.

#### Progression
- Diamond → Netherite (vanilla smithing) → Ether (our smithing template)
- Netherite tool/armor + ether_upgrade_smithing_template + ether_ingot → Ether tool/armor

#### A. New Java Files

**`item/ModArmorMaterial.java` (NEW)**
```java
ArmorMaterial ETHER_ARMOR = new ArmorMaterial(
    durabilityMultiplier: 5,         // boots=19, leggings=22, chest=25, helmet=15
    defense: boots=3, leggings=6, chest=8, helmet=3,
    enchantability: 15,
    equipSound: SoundEvents.ARMOR_EQUIP_NETHERITE,
    toughness: 3.0f,
    knockbackResistance: 0.1f,
    repairIngredient: c:ingots/ether tag,
    assetId: EquipmentAssets.createId("end_update:ether")
);
```

**`item/ModArmor.java` (NEW)**
```java
ETHER_HELMET     = register("ether_helmet",     Item::new, Properties.humanoidArmor(ETHER_ARMOR, ArmorType.HELMET).fireResistant())
ETHER_CHESTPLATE = register("ether_chestplate",  Item::new, Properties.humanoidArmor(ETHER_ARMOR, ArmorType.CHESTPLATE).fireResistant())
ETHER_LEGGINGS   = register("ether_leggings",    Item::new, Properties.humanoidArmor(ETHER_ARMOR, ArmorType.LEGGINGS).fireResistant())
ETHER_BOOTS      = register("ether_boots",       Item::new, Properties.humanoidArmor(ETHER_ARMOR, ArmorType.BOOTS).fireResistant())
```

#### B. Existing Files to Edit

**`item/ModItems.java` — add:**
```java
ETHER_UPGRADE_SMITHING_TEMPLATE = register("ether_upgrade_smithing_template", Item::new, new Item.Properties().stacksTo(64));
```

**`item/ModItemGroup.java` — add to creative tab:**
```java
output.accept(ModItems.ETHER_UPGRADE_SMITHING_TEMPLATE);
output.accept(ModArmor.ETHER_HELMET);
output.accept(ModArmor.ETHER_CHESTPLATE);
output.accept(ModArmor.ETHER_LEGGINGS);
output.accept(ModArmor.ETHER_BOOTS);
```

**`EndUpdateMod.java` — add init call:**
```java
ModArmor.initialize();  // after ModTools.initialize()
```

#### C. Recipe Changes

**DELETE (5 files):**
- `recipe/ether_sword.json`
- `recipe/ether_pickaxe.json`
- `recipe/ether_axe.json`
- `recipe/ether_shovel.json`
- `recipe/ether_hoe.json`

**CREATE — 5 tool smithing_transform recipes:**
- `ether_sword_smithing.json` — netherite_sword + template + ether_ingot → ether_sword
- `ether_pickaxe_smithing.json` — netherite_pickaxe + template + ether_ingot → ether_pickaxe
- `ether_axe_smithing.json` — netherite_axe + template + ether_ingot → ether_axe
- `ether_shovel_smithing.json` — netherite_shovel + template + ether_ingot → ether_shovel
- `ether_hoe_smithing.json` — netherite_hoe + template + ether_ingot → ether_hoe

**CREATE — 4 armor smithing_transform recipes:**
- `ether_helmet_smithing.json` — netherite_helmet + template + ether_ingot → ether_helmet
- `ether_chestplate_smithing.json` — netherite_chestplate + template + ether_ingot → ether_chestplate
- `ether_leggings_smithing.json` — netherite_leggings + template + ether_ingot → ether_leggings
- `ether_boots_smithing.json` — netherite_boots + template + ether_ingot → ether_boots

**CREATE — Template duplication (crafting_shapeless):**
- `ether_upgrade_smithing_template_duplication.json`
- 1 template + 8x ether_nugget → 2 templates

#### D. Template Chest Spawning (Loot Table)

**Create:** `data/end_update/loot_table/chests/ether_upgrade_template.json`

**Override vanilla loot tables** (add pool with template, very rare):
- `data/minecraft/loot_table/chests/simple_dungeon.json`
- `data/minecraft/loot_table/chests/stronghold_library.json`
- `data/minecraft/loot_table/chests/end_city_treasure.json`

#### E. Asset Files

**Rename 5 textures** to correct names.

**Create 5 item definitions** (`items/` folder).

**Create 5 item models** (`models/item/` folder).

**Update lang file** — add 5 new entries.

#### F. Missing — Need From User
- Worn armor textures (64×32): `textures/entity/equipment/humanoid/ether.png` + `humanoid_leggings/ether.png`

#### G. Verify
`./gradlew build` compiles successfully.

---

### Phase 11: Armor Set Bonuses (Future)

**Goal:** Wear full Ether armor set → gain special effect.

Ideas:
- Full set bonus: slow falling + resistance in End
- Set piece bonuses (2-piece, 4-piece)

---

### Phase 12: Ether Enchantments (Future)

**Goal:** Custom enchantments exclusive to Ether tools/armor.

Ideas:
- End Smite (extra damage to Endermen/Shulkers)
- Void Walker (no fall damage in End)
- Ether Shield (projectile deflection)

---

### Phase 13: Ether Particles & Effects (Future)

**Goal:** Visual feedback for Ether gear.

Ideas:
- Purple particle trail on sprint
- Ender-like teleport particles on hit
- Custom armorsmith villager trades

---

### Phase 14: New Mobs (Future)

**Goal:** End-native hostile/passive mobs.

Ideas:
- Ether Guardian (protects ore veins)
- Void Wraith (hostile, teleports player)
- Ether Sprite (passive, drops ether nugget)

---

### Phase 15: New Structures (Future)

**Goal:** End-exclusive structures.

Ideas:
- Ether Temple (contains loot + template)
- Void Fortress (dungeon-style, boss mob)
- Floating Islands (small, loot-only)

---

### Phase 16: New Biomes (Future)

**Goal:** Custom End biomes with unique generation.

Ideas:
- Ether Wastes (large ore veins, hostile mobs)
- Crystal Gardens (aesthetic, chorus variant)
- Void Depths (below Y=0, dangerous)

---

### Phase 17: Sound Effects (Future)

**Goal:** Custom sounds for Ether tools, armor, and mobs.

---

### Phase 18: Data Pack Integration (Future)

**Goal:** Advancements, loot tables, recipes as optional data pack.

---

## Future Expansion

The project will later include:

- New mobs
- New structures
- New biomes
- New plants
- New mechanics
- Sound effects
- Particle effects
- Enchantments
- Set bonuses
- Villager trades

Do not implement these until explicitly requested.
