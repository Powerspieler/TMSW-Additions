execute if entity @s[nbt={Inventory:[{Slot:-106b,id:"minecraft:writable_book"}]}] run item modify entity @s weapon.mainhand encrypt:set
execute unless entity @s[nbt={Inventory:[{Slot:-106b,id:"minecraft:writable_book"}]}] run function encrypt:help
