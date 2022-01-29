# Start Brewing Triggers if players nearby a cauldron
execute at @a store success storage tmsw cauldron_nearby int 1 run clone ~-10 ~-10 ~-10 ~10 ~10 ~10 ~-10 ~-10 ~-10 filtered minecraft:water_cauldron[level=3] force
execute if data storage minecraft:tmsw {cauldron_nearby:1} run data merge storage minecraft:tmsw {loadbrewing:1}
execute if data storage minecraft:tmsw {loadbrewing:1} run function brewing:brewing_triggers

# Stop Brewing Triggers if there is no item brewing
execute unless entity @e[type=item,scores={BrewProcess=1..}] run data merge storage minecraft:tmsw {loadbrewing:0}


# 4t = 5 / sec => 0.2 sec
execute if data storage minecraft:tmsw {loadbrewing:0} run schedule function brewing:schedule 4t
execute if data storage minecraft:tmsw {loadbrewing:1} run schedule function brewing:schedule 1t
