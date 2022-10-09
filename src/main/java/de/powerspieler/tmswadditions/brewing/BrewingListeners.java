package de.powerspieler.tmswadditions.brewing;

import de.powerspieler.tmswadditions.TMSWAdditions;
import de.powerspieler.tmswadditions.brewing.events.BrewItemEvent;
import de.powerspieler.tmswadditions.brewing.results.*;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.sound.Sound;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Tag;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.List;

import static de.powerspieler.tmswadditions.brewing.AwakeBrewery.ALREADY_BREWING;

public class BrewingListeners implements Listener {
    @EventHandler
    public void onIngredientDrop(BrewItemEvent event){
        List<Item> raw = new ArrayList<>(event.getCauldron().getNearbyEntitiesByType(Item.class, 1,1,1));
        List<Item> items = raw.stream().filter(item -> item.getPersistentDataContainer().has(AwakeBrewery.BREWING_CANDIDATE)).toList();
        // Beer
        if(items.stream().anyMatch(item -> item.getItemStack().getType() == Material.GLASS_BOTTLE) && items.stream().anyMatch(item -> item.getItemStack().getType() == Material.WHEAT) && items.stream().anyMatch(item -> item.getItemStack().getType() == Material.SUGAR)){
            List<Item> ingredients = items.stream()
                    .filter(item -> {
                        Material type = item.getItemStack().getType();
                        return (type == Material.GLASS_BOTTLE && item.getItemStack().getAmount() == 3) || (type == Material.WHEAT && item.getItemStack().getAmount() == 1) || (type == Material.SUGAR  && item.getItemStack().getAmount() == 1);
                    }).toList();
            if(ingredients.stream().anyMatch(item -> item.getItemStack().getType() == Material.GLASS_BOTTLE) && ingredients.stream().anyMatch(item -> item.getItemStack().getType() == Material.WHEAT) && ingredients.stream().anyMatch(item -> item.getItemStack().getType() == Material.SUGAR)){
                Brewing result = new Beer();
                brewItem(event.getCauldron(), ingredients, result.build());

            }
            return;
        }
        // Champagne
        if(items.stream().anyMatch(item -> item.getItemStack().getType() == Material.GLASS_BOTTLE) && items.stream().anyMatch(item -> item.getItemStack().getType() == Material.GLOW_BERRIES) && items.stream().anyMatch(item -> item.getItemStack().getType() == Material.SUGAR)){
            List<Item> ingredients = items.stream()
                    .filter(item -> {
                        Material type = item.getItemStack().getType();
                        return (type == Material.GLASS_BOTTLE && item.getItemStack().getAmount() == 3) || (type == Material.GLOW_BERRIES && item.getItemStack().getAmount() == 1) || (type == Material.SUGAR  && item.getItemStack().getAmount() == 1);
                    }).toList();
            if(ingredients.stream().anyMatch(item -> item.getItemStack().getType() == Material.GLASS_BOTTLE) && ingredients.stream().anyMatch(item -> item.getItemStack().getType() == Material.GLOW_BERRIES) && ingredients.stream().anyMatch(item -> item.getItemStack().getType() == Material.SUGAR)){
                Brewing result = new Champagne();
                brewItem(event.getCauldron(), ingredients, result.build());

            }
            return;
        }
        // Vodka
        if(items.stream().anyMatch(item -> item.getItemStack().getType() == Material.GLASS_BOTTLE) && items.stream().anyMatch(item -> item.getItemStack().getType() == Material.POTATO) && items.stream().anyMatch(item -> item.getItemStack().getType() == Material.SUGAR)){
            List<Item> ingredients = items.stream()
                    .filter(item -> {
                        Material type = item.getItemStack().getType();
                        return (type == Material.GLASS_BOTTLE && item.getItemStack().getAmount() == 3) || (type == Material.POTATO && item.getItemStack().getAmount() == 1) || (type == Material.SUGAR  && item.getItemStack().getAmount() == 1);
                    }).toList();
            if(ingredients.stream().anyMatch(item -> item.getItemStack().getType() == Material.GLASS_BOTTLE) && ingredients.stream().anyMatch(item -> item.getItemStack().getType() == Material.POTATO) && ingredients.stream().anyMatch(item -> item.getItemStack().getType() == Material.SUGAR)){
                Brewing result = new Vodka();
                brewItem(event.getCauldron(), ingredients, result.build());

            }
            return;
        }
        // Wine
        if(items.stream().anyMatch(item -> item.getItemStack().getType() == Material.GLASS_BOTTLE) && items.stream().anyMatch(item -> item.getItemStack().getType() == Material.SWEET_BERRIES) && items.stream().anyMatch(item -> item.getItemStack().getType() == Material.SUGAR)){
            List<Item> ingredients = items.stream()
                    .filter(item -> {
                        Material type = item.getItemStack().getType();
                        return (type == Material.GLASS_BOTTLE && item.getItemStack().getAmount() == 3) || (type == Material.SWEET_BERRIES && item.getItemStack().getAmount() == 1) || (type == Material.SUGAR  && item.getItemStack().getAmount() == 1);
                    }).toList();
            if(ingredients.stream().anyMatch(item -> item.getItemStack().getType() == Material.GLASS_BOTTLE) && ingredients.stream().anyMatch(item -> item.getItemStack().getType() == Material.SWEET_BERRIES) && ingredients.stream().anyMatch(item -> item.getItemStack().getType() == Material.SUGAR)){
                Brewing result = new Wine();
                brewItem(event.getCauldron(), ingredients, result.build());

            }
            // Insert Return here;
        }
    }
    
    private void brewItem(Location location, List<Item> ingredients, ItemStack result){
        for(Item ingred : ingredients){
            ingred.setVelocity(new Vector(0,0,0));
            ingred.setCanPlayerPickup(false);
            ingred.setGravity(false);
            ingred.setWillAge(false);
            ingred.getPersistentDataContainer().set(ALREADY_BREWING, PersistentDataType.INTEGER, 1);
        }

        new BukkitRunnable() {
            int process = 0;
            Location particleloc;
            final Audience targets = location.getWorld().filterAudience(member -> member instanceof Player player && player.getLocation().distanceSquared(location) < 625);
            @Override
            public void run() {
                if(!(location.getBlock().getType() == Material.WATER_CAULDRON)){
                    for(Item ingred : ingredients){
                        ingred.setCanPlayerPickup(true);
                        ingred.setGravity(true);
                        ingred.setWillAge(true);
                        ingred.getPersistentDataContainer().remove(ALREADY_BREWING);
                    }
                    cancel();
                }

                if((process == 0 || process % 100 == 0) && process % 2 == 0){
                    targets.playSound(Sound.sound(Key.key("ambient.underwater.loop"), Sound.Source.AMBIENT, 1f, 0.1f), Sound.Emitter.self());
                }

                if(Tag.WALLS.getValues().contains(location.getBlock().getRelative(1,2,0).getType())){
                    particleloc = new Location(location.getWorld(), location.getX(),location.getY(),location.getZ());
                    location.getWorld().spawnParticle(Particle.CAMPFIRE_COSY_SMOKE, particleloc.add(1,2.5,0), 1, 0,0,0,0.1);
                }
                if(Tag.WALLS.getValues().contains(location.getBlock().getRelative(-1,2,0).getType())){
                    particleloc = new Location(location.getWorld(), location.getX(),location.getY(),location.getZ());
                    location.getWorld().spawnParticle(Particle.CAMPFIRE_COSY_SMOKE, particleloc.add(-1,2.5,0), 1, 0,0,0,0.1);
                }
                if(Tag.WALLS.getValues().contains(location.getBlock().getRelative(0,2,1).getType())){
                    particleloc = new Location(location.getWorld(), location.getX(),location.getY(),location.getZ());
                    location.getWorld().spawnParticle(Particle.CAMPFIRE_COSY_SMOKE, particleloc.add(0,2.5,1), 1, 0,0,0,0.1);
                }
                if(Tag.WALLS.getValues().contains(location.getBlock().getRelative(0,2,-1).getType())){
                    particleloc = new Location(location.getWorld(), location.getX(),location.getY(),location.getZ());
                    location.getWorld().spawnParticle(Particle.CAMPFIRE_COSY_SMOKE, particleloc.add(0,2.5,-1), 1, 0,0,0,0.1);
                }

                if(process == 590){
                    targets.playSound(Sound.sound(Key.key("block.brewing_stand.brew"), Sound.Source.AMBIENT, 1f, 1f), Sound.Emitter.self());
                    location.getBlock().setType(Material.CAULDRON);
                    for(Item ingred : ingredients){
                        ingred.remove();
                    }

                    for(int i = 0; i < 3; i++){
                        location.getWorld().dropItem(location.add(0,1,0), result);
                    }
                    cancel();
                }
                process++;
            }
        }.runTaskTimer(TMSWAdditions.getPlugin(), 0L, 1L);
    }
}
