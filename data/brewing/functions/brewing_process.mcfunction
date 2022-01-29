execute unless score @s BrewProcess matches 0..610 run data merge entity @s {Motion:[0.0,0.0,0.0],NoGravity:1b,PickupDelay:32767,Age:-32768,Tags:["brewing"]}

scoreboard players add @s BrewProcess 1

execute if score @s BrewProcess matches 0..599 if block ~ ~2 ~1 #minecraft:walls run particle minecraft:campfire_cosy_smoke ~ ~2.5 ~1 0 0 0 0.1 1
execute if score @s BrewProcess matches 0..599 if block ~ ~2 ~-1 #minecraft:walls run particle minecraft:campfire_cosy_smoke ~ ~2.5 ~-1 0 0 0 0.1 1
execute if score @s BrewProcess matches 0..599 if block ~1 ~2 ~ #minecraft:walls run particle minecraft:campfire_cosy_smoke ~1 ~2.5 ~ 0 0 0 0.1 1
execute if score @s BrewProcess matches 0..599 if block ~-1 ~2 ~ #minecraft:walls run particle minecraft:campfire_cosy_smoke ~-1 ~2.5 ~ 0 0 0 0.1 1

execute if score @s BrewProcess matches 0 run playsound minecraft:ambient.underwater.loop master @a[distance=..15]
execute if score @s BrewProcess matches 100 run playsound minecraft:ambient.underwater.loop master @a[distance=..15]
execute if score @s BrewProcess matches 200 run playsound minecraft:ambient.underwater.loop master @a[distance=..15]
execute if score @s BrewProcess matches 300 run playsound minecraft:ambient.underwater.loop master @a[distance=..15]
execute if score @s BrewProcess matches 400 run playsound minecraft:ambient.underwater.loop master @a[distance=..15]
execute if score @s BrewProcess matches 500 run playsound minecraft:ambient.underwater.loop master @a[distance=..15]

execute if score @s BrewProcess matches 599 run playsound minecraft:block.brewing_stand.brew master @a[distance=..15]
execute if score @s BrewProcess matches 600.. run setblock ~ ~ ~ cauldron
execute if score @s BrewProcess matches 600.. run kill @s
