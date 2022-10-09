package de.powerspieler.tmswadditions.encrypt;

import de.powerspieler.tmswadditions.TMSWAdditions;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class Encrypt implements TabExecutor {
    private static final NamespacedKey ENCRYPTKEY = new NamespacedKey(TMSWAdditions.getPlugin(), "encryptkey");

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if(sender instanceof Player player){
            if(args.length > 0){
                if(args[0].equalsIgnoreCase("set")){
                    if(args.length == 1){
                        player.sendMessage(Component.text("No key specified", NamedTextColor.RED));
                        return true;
                    }
                    if(player.getInventory().getItemInMainHand().getType() == Material.AIR){
                        player.sendMessage(Component.text("No item to encrypt", NamedTextColor.RED));
                        return true;
                    }
                    if(args.length > 2){
                        player.sendMessage(Component.text("Found multiple keys, using first", NamedTextColor.YELLOW));
                    }

                    ItemStack item = player.getInventory().getItemInMainHand();
                    ItemMeta itemmeta = item.getItemMeta();
                    if(!itemmeta.getPersistentDataContainer().has(ENCRYPTKEY)){
                        List<Component> itemlore = new ArrayList<>();
                        if(item.lore() != null && !item.lore().isEmpty()){
                            itemlore = item.lore();
                            itemlore.add(Component.text(""));
                        }
                        itemlore.add(Component.text("Unique ID: ", NamedTextColor.GOLD)
                                .decoration(TextDecoration.ITALIC, false)
                                .append(Component.text("OhThatsATuffOne", NamedTextColor.DARK_PURPLE)
                                        .decoration(TextDecoration.ITALIC, false)
                                        .decoration(TextDecoration.OBFUSCATED, true)));
                        itemmeta.lore(itemlore);
                        player.sendMessage(Component.text("Key ", NamedTextColor.GREEN)
                                .append(Component.text("\"" + args[1] + "\"", NamedTextColor.DARK_PURPLE))
                                .append(Component.text(" has been set", NamedTextColor.GREEN)));
                    } else {
                        player.sendMessage(Component.text("Key has been changed to ", NamedTextColor.GREEN)
                                .append(Component.text("\"" + args[1] + "\"", NamedTextColor.DARK_PURPLE)));
                    }
                    itemmeta.getPersistentDataContainer().set(ENCRYPTKEY, PersistentDataType.STRING,args[1]);
                    item.setItemMeta(itemmeta);


                } else if(args[0].equalsIgnoreCase("remove")){
                    ItemStack item = player.getInventory().getItemInMainHand();
                    ItemMeta itemmeta = item.getItemMeta();
                    if(itemmeta.getPersistentDataContainer().has(ENCRYPTKEY)){
                        if(item.lore() != null){
                            List<Component> lore = new ArrayList<>(item.lore());
                            lore.remove(lore.size() - 1);
                            if(!lore.isEmpty() && lore.get(lore.size() - 1).equals(Component.text(""))){
                                lore.remove(lore.size() - 1);
                            }
                            if(lore.isEmpty()){
                                lore = null;
                            }
                            itemmeta.lore(lore);
                        }
                        itemmeta.getPersistentDataContainer().remove(ENCRYPTKEY);
                        item.setItemMeta(itemmeta);
                        player.sendMessage(Component.text("Key has been removed", NamedTextColor.GREEN));
                    } else {
                        player.sendMessage(Component.text("No key to be removed", NamedTextColor.RED));
                    }
                } else if(args[0].equalsIgnoreCase("help")){
                    showHelp(player);
                } else {
                    player.sendMessage(Component.text("Usage: /encrypt <Help|Set|Remove>", NamedTextColor.RED));
                }
            } else {
                showHelp(player);
            }
        }
        return true;
    }

    private void showHelp(Player player){
        player.sendMessage(Component.text("- Encrypt - \n", NamedTextColor.AQUA)
                .append(Component.text("Place the items you want to encrypt in your mainhand and type:\n")
                        .color(TextColor.fromHexString("#0FFB0F")))
                .append(Component.text("/key set <key>\n", NamedTextColor.GOLD))
                .append(Component.text("key: ")
                        .color(TextColor.fromHexString("#00AAFF")))
                .append(Component.text("String (Passphrase)\n\n")
                        .color(TextColor.fromHexString("#FFFF55")))
                .append(Component.text("Use this feature to create unique items (E.g. Keycards for doors)")
                        .color(TextColor.fromHexString("#0FFB0F"))));
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if(args.length == 1){
            List<String> mode = new ArrayList<>();
            mode.add("help");
            mode.add("set");
            mode.add("remove");
            return mode;
        }
        if(args[0].equalsIgnoreCase("set") && args.length == 2){
            List<String> mode = new ArrayList<>();
            mode.add("<key>");
            return mode;
        }
        return null;
    }
}
