package de.powerspieler.tmswadditions.playerheads;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.ArrayList;
import java.util.List;

public class DropOnPlayerDeath implements Listener {
    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event){
        OfflinePlayer skullowner = Bukkit.getOfflinePlayer(event.getPlayer().getName());
        ItemStack head = new ItemStack(Material.PLAYER_HEAD);
        SkullMeta headmeta = (SkullMeta) head.getItemMeta();
        headmeta.setOwningPlayer(skullowner);

        if(event.getPlayer().getKiller() != null){
            String killer = event.getPlayer().getKiller().getName();
            List<Component> lore = new ArrayList<>();
            lore.add(Component.text("Killed by " + killer)
                    .decoration(TextDecoration.ITALIC, false));
            headmeta.lore(lore);
        }
        head.setItemMeta(headmeta);
        event.getDrops().add(head);
    }
}
