# DistanceLeveledMonsters

Minecraft Mod to scale monster difficulty based on distance to spawn point

Features:

* Increased Monster Health / Attack based on distance from spawn
	* Configurable per monster
* Increased EXP / Drops

Inspired by:

https://github.com/Rhidlor/ScalingMobDifficulty


```
Commands:

/DistanceLevel		show current monster scaling in your area
```


On first load and whenever "ResetToDefault" is missing from the file "config.yml" the following settings will be set:


```
"ResetToDefault"    false   // Set to True or delete to reset config to defaults at next startup

"LevelDistance"     1000    // Adds a level every X blocks, partial levels apply
"ExpMultiplier"     0.1     // Additional EXP per level, 1.0 meaning for each level you get the full EXP amount again
"DropMultiplier"    0.1     // Additional Drops per level, 1.0 meaning for each level you get the full Drop amount again
                            // only works for stackables, rounds down
```

Replace "Monster" with the monstername, as pregenerated after first starting the plugin.

Adds X health / X attack to the monster every level. 
Enderdragons don't scale.

```
"MonsterHealthScaling"      1.0
"MonsterAttackScaling"      1.0
```