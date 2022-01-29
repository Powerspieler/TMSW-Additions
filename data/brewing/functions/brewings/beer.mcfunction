execute unless entity @e[tag=brewing,distance=..0.5] run function brewing:check_cauldron_n
execute unless entity @e[tag=brewing,distance=..0.5] run function brewing:check_cauldron_s
execute unless entity @e[tag=brewing,distance=..0.5] run function brewing:check_cauldron_w
execute unless entity @e[tag=brewing,distance=..0.5] run function brewing:check_cauldron_e

execute if score @s BrewValid matches 1.. if block ~ ~1 ~ #minecraft:trapdoors[open=false] run function brewing:brewing_process

execute if score @s BrewProcess matches 1 run data merge entity @e[type=item,nbt={Item:{id:"minecraft:sugar", Count:1b}},sort=nearest,limit=1,distance=..1] {Motion:[0.0,0.0,0.0],NoGravity:1b,PickupDelay:32767,Age:-32768,Tags:["brewing"]}
execute if score @s BrewProcess matches 1 run data merge entity @e[type=item,nbt={Item:{id:"minecraft:glass_bottle", Count:3b}},sort=nearest,limit=1,distance=..1] {Motion:[0.0,0.0,0.0],NoGravity:1b,PickupDelay:32767,Age:-32768,Tags:["brewing"]}
execute if score @s BrewProcess matches 599 run kill @e[type=item,nbt={Item:{id:"minecraft:sugar", Count:1b}},tag=brewing,sort=nearest,limit=1,distance=..1]
execute if score @s BrewProcess matches 599 run kill @e[type=item,nbt={Item:{id:"minecraft:glass_bottle", Count:3b}},tag=brewing,sort=nearest,limit=1,distance=..1]
execute if score @s BrewProcess matches 599 run loot spawn ~ ~ ~ loot brewing:beer
