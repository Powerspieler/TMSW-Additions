scoreboard players enable @a Encrypt
execute as @a[scores={Encrypt=1..}] run function encrypt:validation
execute as @a[scores={Encrypt=..-1}] run function encrypt:validation

execute as @a[scores={Encrypt=1..}] run scoreboard players reset @s Encrypt
execute as @a[scores={Encrypt=..-1}] run scoreboard players reset @s Encrypt
