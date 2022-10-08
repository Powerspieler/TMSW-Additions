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
                String causeraw = null;
                String cause = null;
                try {
                    causeraw = Objects.requireNonNull(player.getLastDamageCause()).getCause().name().toLowerCase();
                } catch (NullPointerException ignored) {
                    cause = "Other";
                }

                if(causeraw != null){
                    cause = causeraw.substring(0,1).toUpperCase() + causeraw.substring(1);
                }
                Bukkit.broadcast(Component.text("" + player.getName() + " saved their life with a ")
                        .append(Component.text("[Totem of Undying]\n", NamedTextColor.YELLOW))
                        .append(Component.text("Cause: "))
                        .append(Component.text("" + cause, NamedTextColor.DARK_PURPLE)));
            }
        }
    }
}
