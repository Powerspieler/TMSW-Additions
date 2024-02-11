package de.powerspieler.tmswadditions.armorstand;

import com.destroystokyo.paper.inventory.meta.ArmorStandMeta;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Optional;

public class Armorstand implements Listener {

    @EventHandler
    public void onRightClick(PlayerInteractAtEntityEvent event){
        if(event.getPlayer().getInventory().getItemInMainHand().getType() == Material.STICK && event.getRightClicked().getType() == EntityType.ARMOR_STAND){
            ArmorStand armorStand = (ArmorStand) event.getRightClicked();
            if(!armorStand.hasArms()){
                armorStand.setArms(true);
                if(!(event.getPlayer().getGameMode() == GameMode.CREATIVE)){
                    ItemStack stick = event.getPlayer().getInventory().getItemInMainHand();
                    stick.setAmount(stick.getAmount() - 1);
                }
                event.setCancelled(true); // Prevent giving a second stick
            }
        }
    }

    @EventHandler
    public void onArmorStandBreak(EntityDeathEvent event){
        if(event.getEntity() instanceof ArmorStand armorStand && armorStand.hasArms()){
            Optional<ItemStack> optionalItemStack = event.getDrops().stream().filter(item -> item.getType() == Material.ARMOR_STAND).findFirst();
            if(optionalItemStack.isPresent()){
                ItemStack item = optionalItemStack.get();
                event.getDrops().remove(item);
                ArmorStandMeta itemMeta = (ArmorStandMeta) item.getItemMeta();
                itemMeta.setShowArms(true);
                item.setItemMeta(itemMeta);
                event.getDrops().add(item);
            }
        }
    }
}
