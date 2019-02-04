# DistanceLeveledMonsters

Minecraft Mod to scale monster difficulty based on distance to spawn point

Features:

* Increased Monster Health / Attack based on distance from spawn
	* Configurable per monster
* Increased EXP / Drops
* Everything configurable per world

Inspired by:

https://github.com/Rhidlor/ScalingMobDifficulty


Commands:

```
/DistanceLevel                                show current monster scaling in your area
/DistanceLevel [X-Coord] [Z-Coord]            show current monster scaling at the coordinates
/DistanceLevel [X-Coord] [Z-Coord] [world]    show current monster scaling at the coordinates / world
                                              only usable from the console
```

Add any worlds you wish to manage into the list in config.yml

```
Worlds:
  - world
  - world_nether
  - world_the_end
```

Any world managed needs a configuration like the one below, copy and past the example in the file and rename "world" to your world name

```
world:
  LevelDistance: 1000
  Scaling:
    ExpMultiplier: 0.1
    DropMultiplier: 0.1
    Blaze:
      Health: 0.5
      Attack: 0.3
...
```

Adds X health / X attack to the monster every level. 
Enderdragons don't scale.