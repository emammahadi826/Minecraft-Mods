# End Update Mod

A vanilla-quality Minecraft mod that expands the End dimension with new **Ether** material, tools, armor, blocks, and world generation.

**Minecraft 26.1.2** | **Fabric** | **Java 25**

## Features

### Ether Material
- **Raw Ether** — mined from Ether Ore in the End dimension
- **Ether Ingot** — smelted from Raw Ether
- **Ether Nugget** — 1 Ingot = 9 Nuggets (shapeless), 9 Nuggets = 1 Ingot
- **Ether Block** — 9 Ingots = 1 Block (and reverse)

### Ether Tools
All tools are strictly better than Netherite. Obtained via **smithing upgrade** (not crafting table).

| Tool | Damage | Durability | Speed |
|------|--------|------------|-------|
| Sword | 10.0 | 2500 | 9.5 |
| Pickaxe | 6.5 | 2500 | 9.5 |
| Axe | 12.0 | 2500 | 9.5 |
| Shovel | 7.0 | 2500 | 9.5 |
| Hoe | 2.5 | 2500 | 9.5 |

### Ether Armor
Full armor set with Netherite-tier defense. Obtained via **smithing upgrade** (not crafting table).

| Armor | Defense | Durability |
|-------|---------|------------|
| Helmet | 3 | 15 |
| Chestplate | 8 | 25 |
| Leggings | 6 | 22 |
| Boots | 3 | 19 |

- Toughness: 3.0
- Knockback Resistance: 0.1
- Enchantability: 15
- Fire Resistant (like Netherite)
- Full worn armor textures (humanoid + leggings layers) via `equipment/ether.json`

### Ether Upgrade Smithing Template
- Found in **dungeon chests**, **stronghold libraries**, and **End city treasure chests** (very rare)
- Duplicate it by combining 1 template + 8 Ether Nuggets in a crafting table → 2 templates

### Blocks
- **Ether Ore** — spawns rarely in End Stone (Y 10–60), drops 1 Raw Ether
- **Raw Ether Block** — storage block (9 Raw Ether)
- **Ether Block** — crafted from 9 Ether Ingots

### World Generation
- Ether Ore spawns in **The End** dimension only
- Very rare: ~1 vein per 10 chunks, Y 10–60
- Vein size: 1–3 blocks

### Durability Tooltip
- Hover over **any** damageable item (swords, pickaxes, axes, armor, etc.) to see durability
- Works for all items — vanilla and modded
- Shows: `Durability: 1561 / 1561`

## Crafting Recipes

### Storage
| Recipe | Result | Type |
|--------|--------|------|
| 9x Ether Ingot | 1x Ether Block | Shaped |
| 1x Ether Block | 9x Ether Ingot | Shapeless |
| 1x Ether Ingot | 9x Ether Nugget | Shapeless |
| 9x Ether Nugget | 1x Ether Ingot | Shapeless |

### Smelting
| Input | Output | Type |
|-------|--------|------|
| Raw Ether | Ether Ingot | Smelting/Blasting |

### Smithing Upgrade (Netherite → Ether)
Use a **Smithing Table** with:
- **Template**: Ether Upgrade Smithing Template
- **Base**: Netherite tool/armor piece
- **Addition**: Ether Ingot

| Base | + Template + Ingot | Result |
|------|--------------------|--------|
| Netherite Sword | Ether Upgrade + Ether Ingot | Ether Sword |
| Netherite Pickaxe | Ether Upgrade + Ether Ingot | Ether Pickaxe |
| Netherite Axe | Ether Upgrade + Ether Ingot | Ether Axe |
| Netherite Shovel | Ether Upgrade + Ether Ingot | Ether Shovel |
| Netherite Hoe | Ether Upgrade + Ether Ingot | Ether Hoe |
| Netherite Helmet | Ether Upgrade + Ether Ingot | Ether Helmet |
| Netherite Chestplate | Ether Upgrade + Ether Ingot | Ether Chestplate |
| Netherite Leggings | Ether Upgrade + Ether Ingot | Ether Leggings |
| Netherite Boots | Ether Upgrade + Ether Ingot | Ether Boots |

### Template Duplication
| Recipe | Result | Type |
|--------|--------|------|
| 1x Ether Upgrade Template + 8x Ether Nugget | 2x Ether Upgrade Template | Shapeless |

## Installation

1. Install [Fabric Loader](https://fabricmc.net/) for Minecraft 26.1.2
2. Install [Fabric API](https://modrinth.com/mod/fabric-api)
3. Place `end_update-1.0.0.jar` in your `.minecraft/mods/` folder
4. Launch Minecraft with Fabric profile

## Requirements

| Dependency | Version |
|------------|---------|
| Minecraft | 26.1.2 |
| Fabric Loader | >=0.19.3 |
| Fabric API | >=0.154.2 |
| Java | >=25 |

## Building from Source

```bash
git clone https://github.com/emammahadi826/Minecraft-Mods.git
cd Minecraft-Mods
./gradlew build
```

The built JAR will be in `build/libs/`.

## License

MIT License — see [LICENSE](LICENSE) for details.
