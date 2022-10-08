package de.powerspieler.tmswadditions.anti_enderman_grief;

import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityChangeBlockEvent;

public class PreventPickup implements Listener {
    @EventHandler
    public void onPickup(EntityChangeBlockEvent event){
        if(event.getEntityType() == EntityType.ENDERMAN){
            event.setCancelled(true);
        }
    }
}
