package de.powerspieler.tmswadditions.map_id;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.MapMeta;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MapID implements TabExecutor {
    @Override @SuppressWarnings("deprecation")
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (sender instanceof Player player) {
            if (args.length > 0) {
                if (args[0].equalsIgnoreCase("set")) {
                    if (player.getInventory().getItemInMainHand().getType() == Material.MAP) {
                        if (args.length > 1) {
                            int id;
                            try {
                                id = Integer.parseInt(args[1]);
                            } catch (NumberFormatException ignored) {
                                player.sendMessage(Component.text("\"" + args[1] + "\" is not a number!", NamedTextColor.RED));
                                return true;
                            }
                            if (id >= 100000) {
                                ItemStack blanks = player.getInventory().getItemInMainHand();
                                int amount = blanks.getAmount();
                                amount--;
                                blanks.setAmount(amount);

                                ItemStack map = new ItemStack(Material.FILLED_MAP);
                                MapMeta mapmeta = (MapMeta) map.getItemMeta();
                                mapmeta.setMapId(id);
                                map.setItemMeta(mapmeta);
                                HashMap<Integer, ItemStack> leftover = player.getInventory().addItem(map);
                                if(!leftover.isEmpty()){
                                    for(ItemStack item : leftover.values()){
                                        player.getWorld().dropItemNaturally(player.getLocation(), item);
                                    }
                                }
                            } else {
                                player.sendMessage(Component.text("ID mustn't be below 100000", NamedTextColor.RED));
                            }
                        } else {
                            player.sendMessage(Component.text("No ID provided /map set <Number>", NamedTextColor.RED));
                        }
                    } else {
                        player.sendMessage(Component.text("No empty map in mainhand", NamedTextColor.RED));
                    }
                }
                if (args[0].equalsIgnoreCase("list")) {
                    player.sendMessage(Component.text("\n-----------------[List]-----------------", NamedTextColor.AQUA));
                    player.sendMessage(Component.text(" Example Picture", NamedTextColor.GRAY).append(Component.text("          100000-100007", NamedTextColor.RED)));
                }
                if (args[0].equalsIgnoreCase("help")) {
                    player.sendMessage(Component.text("Convert empty maps into pictures by setting their id.\nTake an empty map in your mainhand and type /map set <ID>\nContact the admin to add pictures", NamedTextColor.AQUA));
                }
            } else {
                player.sendMessage(Component.text("Usage: /map <Help|List|Set>", NamedTextColor.RED));
            }
        }
        return true;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if(args.length == 1){
            List<String> mode = new ArrayList<>();
            mode.add("help");
            mode.add("list");
            mode.add("set");
            return mode;
        }
        if(args.length == 2){
            List<String> num = new ArrayList<>();
            num.add("100000");
            return num;
        }
        return null;
    }
}
