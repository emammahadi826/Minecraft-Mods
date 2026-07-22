# Communication Rule
- Always talk in **Banglish** (Bangla + English mix). Never pure Bangla or pure English.
- Only use English for words/terms that can't be expressed naturally in Banglish.

# Git/GitHub Rule
- Never push to GitHub without asking the user first. Always confirm before `git push`.

# Project Context
- **Mod ID:** `end_update` | **Package:** `com.endupdate.mod`
- **MC 1.21.4, Fabric Loom 1.8.12, Yarn mappings 1.21.4+build.8, Fabric API 0.110.5+1.21.4, Java 21**
- **Env:** `$env:JAVA_HOME` must point to Liberica JDK 21 Lite

# Build
```powershell
./gradlew build
```
- `fabric.mod.json` expands `${version}`, `${loader_version}` etc. from `gradle.properties` at `processResources` time
- Gradle wrapper 8.10 (use `gradlew.bat` in cmd, `./gradlew` in powershell)
- Datagen stub at `ModDatagen.java` is **empty/unused** — all JSON assets are hand-written

# Source Layout
```
src/main/java/com/endupdate/mod/
  EndUpdateMod.java       — calls Mod*.initialize() in onInitialize
  EndUpdateClient.java    — client init log only
  registry/
    ModBlocks.java        — END_ORE, RAW_END_ORE_BLOCK, END_BLOCK
    ModItems.java         — RAW_END_ORE, END_NUGGET, END_INGOT + 3 BlockItems
    ModToolMaterials.java — END_MATERIAL record
    ModTools.java         — END_SWORD/PICKAXE/AXE/SHOVEL/HOE
    ModItemGroups.java    — END_UPDATE_GROUP creative tab
  datagen/ModDatagen.java — empty entrypoint, do not depend on it
```
- `src/main/generated/` is configured in loom sourceSets but **never populated**; all data is in `resources/`
- Registry `initialize()` methods are intentionally empty — static field initializers register everything

# Assets
- All blockstates, models, recipes, loot tables, lang, tags, and advancements are **hand-written JSONs** in `src/main/resources/`
- Texture naming: `lowercase_underscore.png`, resolution 16×16, paired with a same-name `.txt` descriptor
- **Color palette (all textures):** Void Black #1A1824, Dark Purple #3D2A5A, Royal Purple #6546B8, Bright Violet #8E6DFF, End Crystal Cyan #72F4FF, Soft White #F8F8FF
- Do not generate or modify PNG files — use existing names
- Missing texture? Stop and ask: "Bhai, [name].png ta extension folder e nai, apni ki eta add kore niben?"

# Tool Material (END_MATERIAL)
| Property | Value |
|---|---|
| durability | 1621 |
| speed | 8.0 |
| attackDamageBonus | 3.0 |
| enchantability | 14 |
| incorrectFor | `INCORRECT_FOR_DIAMOND_TOOL` |
| repairIngredient | `c:ingots/end` (TagKey) |

Tool attack/speed: Sword (+3, -2.4), Pickaxe (+1, -2.8), Axe (+6, -3.0), Shovel (+1.5, -3.0), Hoe (-3, 0.0)

# Worldgen
- 3-file chain: `configured_feature/end_ore.json` → `placed_feature/end_ore.json` → `biome_modifier/end_ore.json`
- Ore replaces `minecraft:end_stone_replaceable`, Y 10–80, count 10, size 9
- Biomes: `end_highlands`, `end_barrens`, `small_end_islands` (NOT `end_midlands`)

# Tags
- Block: `mineable/pickaxe` (all 3), `needs_diamond_tool` (end_ore only), `needs_iron_tool` (raw block + end block)
- Items (convention): `c/tags/items/ingots.json` includes `end_ingot`, `c/tags/items/nuggets.json` includes `end_nugget`

# Loot Tables
- `end_ore` drops 1–3 `raw_end_ore` with fortune bonus; silk-touch drops the ore block itself
- `raw_end_ore_block` and `end_block` use standard self-drop

# Advancement
- `end_update_root.json` triggers on `inventory_changed` with `end_update:end_ingot`
