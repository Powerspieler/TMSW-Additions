package de.powerspieler.tmswadditions.totemmsg;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityResurrectEvent;

import java.util.Objects;

public class TotemMsg implements Listener {
    @EventHandler
    public void onTotemUse(EntityResurrectEvent event){
        if(!event.isCancelled()){
            if(event.getEntity() instanceof Player player) {
                try {
                    String cause = Objects.requireNonNull(player.getLastDamageCause()).getCause().name().toLowerCase().replace("_", " ");
                    Bukkit.broadcast(Component.text(player.getName() + " saved their life from ")
                            .append(Component.text(cause, NamedTextColor.DARK_PURPLE))
                            .append(Component.text(" with a "))
                            .append(Component.text("[Totem of Undying]", NamedTextColor.YELLOW)));

                } catch (NullPointerException ignored) {
                }
            }
        }
    }
}
