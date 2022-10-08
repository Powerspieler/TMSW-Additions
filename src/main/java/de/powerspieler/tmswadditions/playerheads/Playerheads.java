package de.powerspieler.tmswadditions.playerheads;

import com.destroystokyo.paper.profile.PlayerProfile;
import com.destroystokyo.paper.profile.ProfileProperty;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.profile.PlayerTextures;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Playerheads implements TabExecutor, Listener {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if(sender instanceof Player player){
            if(args.length > 0){
                if(player.getInventory().getItemInMainHand().getType() == Material.PLAYER_HEAD){
                    int amount = 1;
                    if(args.length >= 2){
                        try {
                            amount = Integer.parseInt(args[1]);
                        } catch (NumberFormatException ignored) {
                            player.sendMessage(Component.text("\"" + args[1] + "\" is not a number!", NamedTextColor.RED));
                            return true;
                        }
                    }

                    ItemStack inHand = player.getInventory().getItemInMainHand();
                    int available = inHand.getAmount();
                    if(available < amount){
                        amount = available;
                    }

                    String value = args[0];
                    if(value.length() <= 16){
                        OfflinePlayer skullowner = Bukkit.getOfflinePlayer(value);
                        ItemStack head = new ItemStack(Material.PLAYER_HEAD);
                        SkullMeta headmeta = (SkullMeta) player.getInventory().getItemInMainHand().getItemMeta();
                        headmeta.setOwningPlayer(skullowner);
                        head.setItemMeta(headmeta);
                        head.setAmount(amount);

                        inHand.setAmount(available - amount);
                        player.getInventory().addItem(head);
                    } else {
                        PlayerProfile pp = Bukkit.createProfile(UUID.fromString("913a282b-6275-4d2a-a4b0-29ece217e151"));
                        pp.getProperties().add(new ProfileProperty("textures", value));

                        ItemStack head = new ItemStack(Material.PLAYER_HEAD);
                        SkullMeta headmeta = (SkullMeta) player.getInventory().getItemInMainHand().getItemMeta();
                        headmeta.setPlayerProfile(pp);
                        head.setItemMeta(headmeta);
                        head.setAmount(amount);

                        inHand.setAmount(available - amount);
                        player.getInventory().addItem(head);

                    }
                } else {
                    player.sendMessage(Component.text("No playerhead in mainhand", NamedTextColor.RED));
                }
            } else {
                player.sendMessage(Component.text("- Change Texture of Playerhead -\n", NamedTextColor.AQUA)
                        .append(Component.text("Place a ")
                                .color(TextColor.fromHexString("#0FFB0F")))
                        .append(Component.text(">craftable<")
                                .decoration(TextDecoration.BOLD, true)
                                .hoverEvent(Component.text("Boneblock surrounded by 8 Rotten Flesh", NamedTextColor.LIGHT_PURPLE)))
                        .append(Component.text(" playerhead in your mainhand and type:\n")
                                .color(TextColor.fromHexString("#0FFB0F")))
                        .append(Component.text("/playerhead <value> [<amount>]\n", NamedTextColor.GOLD))
                        .append(Component.text("value: ")
                                .color(TextColor.fromHexString("#00AAFF")))
                        .append(Component.text("Playername or Texture-Value\n")
                                .color(TextColor.fromHexString("#FFFF55")))
                        .append(Component.text("amount: ")
                                .color(TextColor.fromHexString("#00AAFF")))
                        .append(Component.text("Amount to be converted. Default: 1\n\n")
                                .color(TextColor.fromHexString("#FFFF55")))
                        .append(Component.text("Website for TextureValues: \n")
                                .color(TextColor.fromHexString("#0FFB0F")))
                        .append(Component.text(">https://minecraft-heads.com/<", NamedTextColor.BLUE)
                                .hoverEvent(Component.text("Open in Browser"))
                                .clickEvent(ClickEvent.openUrl("https://minecraft-heads.com/")))
                        .append(Component.text("\nUse the string in 'Value' at the bottom of the page of your selected playerhead.")
                                .color(TextColor.fromHexString("#0FFB0F"))));
                player.discoverRecipe(HeadRecipe.PLAYERHEADRECIPEKEY);
            }
        }
        return true;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if(args.length == 1){
            List<String> mode = new ArrayList<>();
            mode.add("<value>");
            return mode;
        }
        if(args.length == 2){
            List<String> mode = new ArrayList<>();
            mode.add("[<amount>]");
            return mode;
        }
        return null;
    }
}
