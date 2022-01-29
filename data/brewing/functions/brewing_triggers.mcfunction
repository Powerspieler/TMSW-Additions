# Fix item being stuck at BrewingProcess 599
execute as @e[type=item,scores={BrewProcess=599..}] at @s run function brewing:brewing_process

# Delete items not being in a cauldron
execute as @e[tag=brewing] at @s if block ~ ~ ~ air run kill @s

# Wheat + Sugar = Beer
execute as @e[type=item,nbt={Item:{id:"minecraft:glass_bottle", Count:3b}}] at @s if block ~ ~ ~ minecraft:water_cauldron[level=3] as @e[type=item,nbt={Item:{id:"minecraft:sugar", Count:1b}},distance=..0.5] at @s if block ~ ~ ~ minecraft:water_cauldron[level=3] as @e[type=item,nbt={Item:{id:"minecraft:wheat", Count:1b}},distance=..0.5] at @s if block ~ ~ ~ minecraft:water_cauldron[level=3] align xyz positioned ~0.5 ~0.5 ~0.5 run function brewing:brewings/beer

# Potato + Sugar = Vodka
execute as @e[type=item,nbt={Item:{id:"minecraft:glass_bottle", Count:3b}}] at @s if block ~ ~ ~ minecraft:water_cauldron[level=3] as @e[type=item,nbt={Item:{id:"minecraft:sugar", Count:1b}},distance=..0.5] at @s if block ~ ~ ~ minecraft:water_cauldron[level=3] as @e[type=item,nbt={Item:{id:"minecraft:potato", Count:1b}},distance=..0.5] at @s if block ~ ~ ~ minecraft:water_cauldron[level=3] align xyz positioned ~0.5 ~0.5 ~0.5 run function brewing:brewings/vodka

# Sweet_Berries + Sugar = Wine
execute as @e[type=item,nbt={Item:{id:"minecraft:glass_bottle", Count:3b}}] at @s if block ~ ~ ~ minecraft:water_cauldron[level=3] as @e[type=item,nbt={Item:{id:"minecraft:sugar", Count:1b}},distance=..0.5] at @s if block ~ ~ ~ minecraft:water_cauldron[level=3] as @e[type=item,nbt={Item:{id:"minecraft:sweet_berries", Count:1b}},distance=..0.5] at @s if block ~ ~ ~ minecraft:water_cauldron[level=3] align xyz positioned ~0.5 ~0.5 ~0.5 run function brewing:brewings/wine

# Glow_Berries + Sugar = Champagne
execute as @e[type=item,nbt={Item:{id:"minecraft:glass_bottle", Count:3b}}] at @s if block ~ ~ ~ minecraft:water_cauldron[level=3] as @e[type=item,nbt={Item:{id:"minecraft:sugar", Count:1b}},distance=..0.5] at @s if block ~ ~ ~ minecraft:water_cauldron[level=3] as @e[type=item,nbt={Item:{id:"minecraft:glow_berries", Count:1b}},distance=..0.5] at @s if block ~ ~ ~ minecraft:water_cauldron[level=3] align xyz positioned ~0.5 ~0.5 ~0.5 run function brewing:brewings/champagne
