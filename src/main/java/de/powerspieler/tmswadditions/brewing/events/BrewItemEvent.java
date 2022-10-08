package de.powerspieler.tmswadditions.brewing.events;

import org.bukkit.Location;
import org.bukkit.entity.Item;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class BrewItemEvent extends Event {
    private static final HandlerList handlers = new HandlerList();

    private final Item item;
    private final Location cauldron;

    public BrewItemEvent(Item item, Location cauldron) {
        this.item = item;
        this.cauldron = cauldron;
    }

    public Item getItem() {
        return item;
    }

    public Location getCauldron() {
        return cauldron;
    }

    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
}
