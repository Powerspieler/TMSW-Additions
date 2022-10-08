package de.powerspieler.tmswadditions.brewing;

import de.powerspieler.tmswadditions.TMSWAdditions;
import de.powerspieler.tmswadditions.brewing.events.BrewItemEvent;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.data.Levelled;
import org.bukkit.entity.Item;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class AwakeBrewery implements Listener {
    private static HashSet<Material> itemlist;
    public static final NamespacedKey BREWING_CANDIDATE = new NamespacedKey(TMSWAdditions.getPlugin(), "brewing_candidate");
    public static final NamespacedKey ALREADY_BREWING = new NamespacedKey(TMSWAdditions.getPlugin(), "already_brewing");

    public AwakeBrewery(){
        itemlist = new HashSet<>(10);
        itemlist.add(Material.GLASS_BOTTLE);
        itemlist.add(Material.SUGAR);
        itemlist.add(Material.WHEAT);
        itemlist.add(Material.POTATO);
        itemlist.add(Material.SWEET_BERRIES);
        itemlist.add(Material.GLOW_BERRIES);
    }

    @EventHandler
    public void onItemDrop(PlayerDropItemEvent event){
        Item itementity = event.getItemDrop();
        if(itemlist.contains(itementity.getItemStack().getType())){
            new BukkitRunnable() {
                @Override
                public void run() {
                    if(itementity.isOnGround()){
                        Location layingin = itementity.getLocation();
                        if(layingin.getBlock().getType() == Material.WATER_CAULDRON){
                            Levelled level = (Levelled) layingin.getBlock().getBlockData();
                            if(level.getLevel() == 3){
                                if(insideValidBrewN(layingin) || insideValidBrewE(layingin) || insideValidBrewS(layingin) || insideValidBrewW(layingin)){
                                    if(isBrewAvailable(itementity.getLocation())){
                                        itementity.getPersistentDataContainer().set(BREWING_CANDIDATE, PersistentDataType.INTEGER, 1);
                                        Bukkit.getPluginManager().callEvent(new BrewItemEvent(itementity, itementity.getLocation().getBlock().getLocation().add(0.5, 0.5, 0.5)));
                                    }
                                }
                            }
                        }
                        cancel();
                    }
                }
            }.runTaskTimer(TMSWAdditions.getPlugin(), 0L, 5L);
        }
    }

    private boolean insideValidBrewN(Location cauldron){
        Block center = cauldron.getBlock();
        if(Tag.TRAPDOORS.getValues().contains(center.getRelative(0,1,0).getType())){
            if(Tag.STAIRS.getValues().contains(center.getRelative(0,0,-1).getType())){
                if(Tag.WALLS.getValues().contains(center.getRelative(0,1,-1).getType())){
                    return Tag.WALLS.getValues().contains(center.getRelative(0, 2, -1).getType());
                }
            }
        }
        return false;
    }

    private boolean insideValidBrewE(Location cauldron){
        Block center = cauldron.getBlock();
        if(Tag.TRAPDOORS.getValues().contains(center.getRelative(0,1,0).getType())){
            if(Tag.STAIRS.getValues().contains(center.getRelative(1,0,0).getType())){
                if(Tag.WALLS.getValues().contains(center.getRelative(1,1,0).getType())){
                    return Tag.WALLS.getValues().contains(center.getRelative(1, 2, 0).getType());
                }
            }
        }
        return false;
    }

    private boolean insideValidBrewS(Location cauldron){
        Block center = cauldron.getBlock();
        if(Tag.TRAPDOORS.getValues().contains(center.getRelative(0,1,0).getType())){
            if(Tag.STAIRS.getValues().contains(center.getRelative(0,0,1).getType())){
                if(Tag.WALLS.getValues().contains(center.getRelative(0,1,1).getType())){
                    return Tag.WALLS.getValues().contains(center.getRelative(0, 2, 1).getType());
                }
            }
        }
        return false;
    }

    private boolean insideValidBrewW(Location cauldron){
        Block center = cauldron.getBlock();
        if(Tag.TRAPDOORS.getValues().contains(center.getRelative(0,1,0).getType())){
            if(Tag.STAIRS.getValues().contains(center.getRelative(-1,0,0).getType())){
                if(Tag.WALLS.getValues().contains(center.getRelative(-1,1,0).getType())){
                    return Tag.WALLS.getValues().contains(center.getRelative(-1, 2, 0).getType());
                }
            }
        }
        return false;
    }

    private boolean isBrewAvailable(Location smith){
        List<Item> raw = new ArrayList<>(smith.getNearbyEntitiesByType(Item.class, 0.5, 0.5 ,0.5));
        List<Item> itemsdis = raw.stream().filter(item -> item.getPersistentDataContainer().has(ALREADY_BREWING)).toList();
        return itemsdis.isEmpty();
    }
}
