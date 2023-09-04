package de.powerspieler.tmswadditions.armorstand;

import org.bukkit.Material;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.inventory.ItemStack;

public class Armorstand implements Listener {

    @EventHandler
    public void onRightClick(PlayerInteractAtEntityEvent event){
        if(event.getPlayer().getInventory().getItemInMainHand().getType() == Material.STICK && event.getRightClicked().getType() == EntityType.ARMOR_STAND){
            ArmorStand armorStand = (ArmorStand) event.getRightClicked();
            if(!armorStand.hasArms()){
                armorStand.setArms(true);
                ItemStack stick = event.getPlayer().getInventory().getItemInMainHand();
                stick.setAmount(stick.getAmount() - 1);
                event.setCancelled(true); // Prevent giving a second stick
            }
        }
    }
}
