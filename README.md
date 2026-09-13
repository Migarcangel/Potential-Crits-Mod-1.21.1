# Potential Crits

**Potential Crits** adds **special critical hit enchantments** to Minecraft. These aren't your vanilla crits (the ones you get by falling). These are new, unique enchants with their **own mechanics**, **conditions**, and **synergies**.

## System Design

Potential Crits is designed as a modular, event-driven combat system where each crit acts as an independent component with its own logic, conditions, and effects.

The system is built around a modular architecture that allows each crit to function as an independent unit within the combat pipeline.

- Event-driven combat processing
- Modular crit structure with shared interfaces
- Ordered damage calculation pipeline:
  - Flat modifiers
  - Conditional effects
  - Percentage-based scaling
- Rule-based compatibility system between crits

This design allows new crits and mechanics to be added without modifying existing logic.

## ⚔️ Types of Crits

There are **16 crits** currently, distributed across progression stages (early to late game), encouraging exploration and build diversity.

| Stage      |Examples                                                                                                                       |
| ---------- |------------------------------------------------------------------------------------------------------------------------------ |
| <strong>Early Game</strong> |Water Crit (water based scaling), Ice Crit (freeze enemies)                                                     |
| <strong>Early-Mid</strong> |Umbral Crit (blindness, scales with darkness), Ground Crit (heavy, no vanilla crits), Fire Crit (fire based scaling)                                           |
| <strong>Mid Game</strong> |Thunder Crit (lightning), Dark / Light Crit (alive/undead specialization)                                                      |
| <strong>Mid-Late</strong> |Shield Crit (gain Shielded effect), Berserk Crit (risk/reward), Sunder Crit (reduce armor), Vampire Crit (life steal), Harvest Crit (hoes become weapons) |
| <strong>Late Game</strong> |Super Crit (boosts vanilla crits), True Crit (ignores armor), Smash Crit (mace-like smash attacks)                             |

Each crit has:

*   **Activation probability**
*   **Conditions** (in water, in darkness, enemy on fire…)
*   **Incompatibilities** (some crits don't play nice together)

## Effects & Systems

The mod introduces new status effects:

*   **Heavy** – Slows you down, prevents full jumps.
*   **Shielded** – Blocks the next hit entirely.
*   **Berserk** – More damage, heal on kill, but crashes hard when it ends.
*   **Exposed** – Reduces armor.
*   **Accuracy** – Increases crit chance (potion available).

Plus:

*   **Crit Gems** – Mineable currency used in the upcoming Crit Table.
*   **Permanent Upgrades** – Complete challenges to boost your global crit chance (up to +5%).
*   **Crits Progression** – Train your mastery with your crits to upgrade them.

## ⚙️ Tech Stack

- Java 21
- Jsons for configuration.
- Minecraft Modding API (NeoForge)
- Event-driven programming
- Intellij IDEA

## Play & Community

- Download on CurseForge: https://www.curseforge.com/minecraft/mc-mods/potential-crits
- Community & updates: https://discord.gg/8RP3mD9BUw
