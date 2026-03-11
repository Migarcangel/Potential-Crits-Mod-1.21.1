# Potential Crits

Potential Crits is a mod that adds special Critical Hits to Minecraft. These are enchantments and can be obtained by various methods, not only the enchantment table. They are mostly applied to swords. However, it’s important to define what critical means in this context.

## Two types of criticals

There will be two types of criticals when using this mod: the vanilla one and the enchantment one. When speaking about crits:
- Crit = Mod’s Crit
- Vanilla Crit = Minecraft Vanilla Critical Hit

Keep in mind that those are different things. They are not equivalent. But they do coexist.

Friendly reminder that vanilla crits are hits that do 150% damage, achieved by hitting while falling.

## Crit Mechanics

All of the mod crits will have a percentage chance. This means they are all based on luck, there is no guarantee of them being applied. They also have conditions that may:
- Be necessary for the crit being applied.
- Boost the damage.
- Improve probabilities of critting.
- A mix of the previous ones.

In order to trigger the crits, there is always the condition of dealing 4 damage or higher. This is a generous minimum but assures that you cannot spam click.

## Crit compatibilities

There are some incompatibilities within the crits. For example, fire crit and water crit. This is an attempt to achieve some “balance”. Nevertheless, most crits are compatible and you can easily have 5 crits at the same time.

All of the crits can be triggered at the same time if the conditions are met and you get lucky enough. For these cases, there is a specific order in which crits will be triggered.

This is the current order:
1. Super Crit (vanilla crit)
2. Fire Crit (raw damage)
3. Thunder Crit
4. Umbral Crit
5. Shield Crit
6. Berserk Crit
7. Smash Crit
8. Water Crit (mixed damage)
9. Dark Crit (percentage damage)
10. Light Crit
11. Ground Crit
12. True Crit

There are crits that will apply damage independently of the base damage (+3 damage for example) and others that will apply percentage damage (+15%). 

The percentage damages will always be applied later than the independent damages.
+3 * (1 + 0.15) = 3.45. (¡+0.45 damage!)

Each crit is tested to be balanced. But combinations may not be. 

Vanilla crits, on the other hand, will always be calculated before the mod’s crits. That is why super crit is first in the list (it affects vanilla crits).

## Crit Obtainments

There are currently three ways of obtaining crits:
- Random loots. Crits are treated as a treasure enchantment, so they can appear in any enchanted book that you find in chests or fishing. There is an extra possibility in strongholds libraries, so it’s worth checking them.
- Wandering Traders. They can (not always) sell a specific crit. You better not kill them before checking them.
- Specific loots. Each crit will have an specific place where to find the crits. This is to encourage players to obtain the crits and not waiting to be lucky with other methods. Additionally, encourages exploring places that you won’t normally do.

## List of Crits

Now, I will list all the crits currently available and all of their info. They have different levels and ways to be obtained.

### Early Game Crits

#### Fire Crit
Consume enemy’s flames and drive them deep within.
- Weapons: Sword.
- Max level: 2
- Percentage per level: 5%
- Conditions to apply: Enemy is on fire.
- Description: It will consume the enemy's fire, converting that fire into additional damage. Each remaining second of burning will be +1 damage.
- Obtained in: Nether Fortresses.
- Incompatibilities: Water Crit, Umbral Crit.

#### Water Crit
Become lord of the deep waters.
- Weapons: Sword, Trident.
- Max level: 3
- Percentage per level: 5%
- Conditions to apply: Being in water
- Description: Deal +3 damage. Another +3 damage if enemy is also in water. More additional damage depending on how much water is above you.
  - 5+ blocks = 5%
  - 15+ blocks = 10%
  - 30+ blocks = 10%
  Deals 10% damage to aquatic mobs and drowneds.
- Obtained in: Ocean Ruins
- Incompatibilities: Fire Crit

### Early-Mid Game Crits

#### Umbral Crit
Lurk in their shadows.
- Weapons: Sword.
- Max level: 3
- Percentage per level: 10%
- Conditions to apply: Enemy is in light level 7 or below and is not on fire.
- Description: Applies Blindness I for 1 second per level of the enchantment (3 seconds at level 3). Damage, chance and duration scale with descending light level:
  - 7-5: 0% extra chance of crit. Deals +2 damage.
  - 5-2: 5% extra chance of crit. Deals +3 damage.
  - 1-0: 10% extra chance of crit. Deals +4 damage. Grants 2 additional seconds to blindness.
- Obtained in: Simple Dungeons, Mineshafts.
- Incompatibilities: Fire Crit, Thunder Crit, Light Crit.

#### Ground Crit
Sink them into the ground.
- Weapons: Sword, Axe, Pickaxe, Shovel.
- Max level: 1
- Percentage per level: 25%
- Conditions to apply: Enemy and attacker are standing on ground.
- Description: Deals 50% extra damage and gives the enemy the effect of Heavy. You no longer have the capability to do vanilla crits.
- Obtained in: Mineshafts.
- Incompatibilities: Super Crit, True Crit, Smash Crit.

### Mid Game Crits

#### Thunder Crit
Strike lightning upon your enemies.
- Weapons: Sword, Trident.
- Max level: 2
- Percentage per level: 10%. 20% if thundering.
- Conditions to apply: Enemy and attacker both exposed to the sky.
- Description: Deals 4 damage to the target by summoning a lightning bolt. 
- Obtained in: Enchanting
- Incompatibilities: Umbral Crit.

#### Dark Crit
Drag the living into darkness.
- Weapons: Sword, Axe, Trident, Mace.
- Max level: 5
- Percentage per level: 5%
- Conditions to apply: None
- Description: Deals 25% more damage if enemy is alive (not undead). If it is undead, it deals 10% less damage.
- Obtained in: Witches, Evokers and Ancient Cities.
- Incompatibilities: Light Crit

#### Light Crit
Lead the dead into the light.
- Weapons: Sword, Axe, Trident, Mace.
- Max level: 5
- Percentage per level: 5%
- Conditions to apply: None
- Description: Deals 25% more damage if enemy is undead (not alive). If it is not undead, it deals 10% less damage.
- Obtained in: Trading with Clerics.
- Incompatibilities: Dark Crit, Umbral Crit.

### Mid-Late Game Crits

#### Shield Crit
Make them think you are invincible.
- Weapons: Sword, Shield, Axe.
- Max level: 1
- Percentage per level: 20%
- [ SWORD / AXE ]
  - Conditions to apply: None
  - Description: Deals +3 damage. Another +1.5 if the user has a shield in offhand. Grants the user Shielded 1. If the user is holding a shield with shield crit, there is a 50% chance to get Shielded 2.
- [ SHIELD ]
  - Conditions to apply: Block damage with the shield.
  - Description: Gives Shielded 1 to the user. If the user already has the effect, it adds 10 seconds extra of duration and there is a 50% chance of achieving Shielded 2.
- Obtained in: Strongholds and Elder Guardians.
- Incompatibilities: Berserk Crit.

#### Berserk Crit
Unleash your fury from its cage.
- Weapons: Sword, Axe.
- Max level: 1
- Percentage per level: 15%
- Conditions to apply: User has less than 55% of health.
- Description: Deals +2 damage, user loses 15% of remaining health and gains Berserk effect level 1 for 10 seconds. If User is already in berserk mode, adds 5 seconds of duration (Max 10 seconds) and +2 additional damage (+4).
- Obtained in: Killing Piglin Brutes (15%) and Bastion Treasure (50%).
- Incompatibilities: Shield Crit

### Late Game Crits

#### Super Crit
Drive your blade even deeper.
- Weapons: Sword, Axe, Trident, Mace.
- Max level: 5
- Percentage per level: 5%
- Conditions to apply: Make a vanilla crit.
- Description: Deals 150% (base vanilla crit) + 10% * level when making vanilla crits. Applies weakness for 3 seconds.
- Obtained in: End Cities. Treasure
- Incompatibilities: Ground Crit, Smash Crit, True Crit.

#### True Crit
No armor will deflect your strikes.
- Weapons: Sword, Axe, Trident.
- Max level: 5
- Percentage per level: 5%
- Conditions to apply: Make a vanilla crit.
- Description: Deals 4% extra true damage per level (max 20% with level 5). Also deals 1% of the max health of the enemy as true damage (Max +3 damage). True damage is a type of damage that ignores all the defenses of the enemy.
- Obtained in: Ominous Vaults.
- Incompatibilities: Ground Crit, Super Crit, Smash Crit.

#### Smash Crit
Want a mace in your weapon? There you go.
- Weapons: Sword, Axe.
- Max level: 4
- Percentage per level: 10%. Adds 1% extra for each block you fall. (Max +35%)
- Conditions to apply: Falling +3 blocks
- Description: Deals 50% damage of an unenchanted mace based on fall distance. Nullifies fall damage. 
- Obtained in: Ominous Vaults.
- Incompatibilities: Super Crit, Ground Crit, True Crit.

## Effects

These are crucial to understand some of the previous enchantments listed, and some of them depend a lot on their respective crits.

#### Heavy Effect
Reduces 10% of your speed and you can no longer jump full blocks.

#### Shielded Effect
Blocks fully next incoming damage, reducing one level of the effect. If shielded is level 2, it downgrades to level 1. if it is level 1, it downgrades to 0 (revokes the effect).

#### Berserk Effect
When in Berserk Mode, deals 25% more damage in each attack. If the user kills an enemy, heals 10% of your missing health. When the effect ends, grants weakness, nausea and heavy for 10 seconds. You cannot enter Berserk again for these 10 seconds.

## Crits to come

These are the crits that are still developing and are not yet in the mod. These may be only concepts or may be implemented soon.

#### Vampire Crit
Sustain from their vitality… or wither from its absence.
- Max level: 4
- Percentage per level: 5%
- Conditions to apply: None
- Description: It varies if target is undead or not.
  - [ Not Undead] Deals and heals +10% damage based on the current health of the enemy (max +3). 
  - [ Undead ] With a 50% chance grants 4 seconds of wither 2 to the attackant. 
- Obtained in: Ancient Cities, Mineshafts.
- Incompatibilities: Dark Crit.

#### Sunder Crit
Crush their armors and their bones.
- Weapons: Axe, Mace.
- Max level: 4
- Percentage per level: 5%
- Conditions to apply: Do 10 damage or higher. (Before armor reduction)
- Description: Deals +5 damage and enemy receives Expose debuff for 15 seconds.
- Obtained in: Vindicator, Piglin Brutes.
- Incompatibilities: True Crit, Shield Crit.

#### Harvest Crit
Harvest their souls and vitality.
- Weapons: Hoe.
- Max level: 8
- Percentage: 33%. Always stays the same.
- Conditions to apply: None.
- Description: Your hoe will always deal +4 damage instead of 1 on every attack, even without making a crit. Deals +1 damage per level. If the crit kills the enemy:
  - Grants +5% experience per level (+40% at max).
  - Restores 0.25 hunger (shanks) per level (+2 at max).
  - Grants Regeneration I for 0.5 seconds per level (+4 seconds at max).
- Obtained in: Ancient Cities.
- Incompatibilities: None.

#### Expose
User’s armor is reduced by 25%.

#### Sweeping Crit
- Weapons: Sword.
- Max level: 3
- Percentage per level: 15%
- Conditions to apply: None
- Description: It adds the chance of doing crits while sweeping enemies. It is disabled without this crit, using it will enable this feature. It will ignore the 4+ damage requirement for secondary targets.
- Obtained in: Vindicator, Piglin Brutes.
- Incompatibilities: Nose.

#### Ice Crit
- Weapons: Sword.
- Max level: 4
- Percentage per level: 5%
- Conditions to apply: Cold biomes or enemy already freezing.
- Description: Deals +2 damage and applies freeze to the enemy for 8 seconds. If the enemy is already freezing, it breaks their freezing and deals +4 damage.
- Obtained in: Igloos and Strays.
- Incompatibilities: Fire Crit.

#### Crits Crit
King of the crits.
- Weapons: Sword, Axe, Trident, Mace.
- Max level: 1
- Percentage per level: 25%
- Conditions to apply: None.
- Description: Deal +1 damage for each crit that gets activated. ¿ Improve chance of dealing other crits ?
- Obtained in: Warden. It will not drop if warden has suffocated for even a tick.
- Incompatibilities: None.

#### Ender Crit
You deal more damage to the endermen and shulkers, like 10%. Additionally, you teleport behind their back and if you hit again before touching the floor you deal extra damage.

## Updates to come

#### Potion Crit
- Potion Crit I: 5% crit chance.
- Potion Crit II: 10% crit chance.

#### Permanent Upgrades
Gains 1% extra chance for achieving different goals.
- Free the end.
- Activate 3 crits at the same time.
- Deal a total amount of 1000 crits.
- Kill the wither by doing a crit.
- Activate each crit at least once.

#### Area Crits
Disabled by default. You can only crit to a single target. If combined with sweeping crit, you can now do crits in area. Sweeping crits ignore the limit of +4 damage.

### Crit Knowledge Book

For avoiding using the Internet. Information about crits will be displayed once you activate the crit for the first time. Crits will appear as the following example:

Fire Crit
???
Weapons: Sword.
Max level: 2
Percentage per level: ???
Conditions to apply: Enemy is on fire.
Description: ???
Obtained in: Nether Fortresses.
Incompatibilities: ???

Crit Knowledge Book will be crafted with: 1 book and 1 crit gem.

### Crit Gems

A new mineable gem found in the underground. Requires iron pickaxe to be obtained. Used in the Crit Table. Serves as a currency item to upgrade crits or manage them.

### Crit Table

A table where to put and extract crits from weapons using crit gems. It can also be used to upgrade crits, but only in enchanted books with one single crit. Costs will vary depending on the level and the difficulty to obtain of the crit. Crit table will be crafted with: 1 enchanted table, 4 crit gems and 4 diamonds.

### Progression to crits

Activating crits do give permanent upgrades. The progress is tied to the player, not the weapon, so the player won’t be punished for changing crits or weapons.

#### Fire Crit
- 100 crits: 5% extra chance
- 250 crits: If the sword has fire aspect 2, upgrades to fire aspect 3.
- 500 crits: Damage scale from 1 for each second of fire to 1.25.

Similar upgrades to the rest of the crits.
