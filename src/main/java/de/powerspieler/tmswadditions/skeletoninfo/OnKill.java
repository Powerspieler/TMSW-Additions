package de.powerspieler.tmswadditions.skeletoninfo;

import de.powerspieler.tmswadditions.TMSWAdditions;
import net.kyori.adventure.text.Component;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Transformation;
import org.joml.AxisAngle4f;
import org.joml.Vector3f;

public class OnKill implements Listener {
    @EventHandler
    public void onSkeletonKIll(EntityDeathEvent event){
        if(event.getEntityType() == EntityType.SKELETON){
            Mob skeleton = (Mob) event.getEntity();
            if(!skeleton.getEquipment().getItemInMainHand().getEnchantments().isEmpty()){
                ItemStack bow = skeleton.getEquipment().getItemInMainHand();

                Location displayloc = new Location(skeleton.getLocation().getWorld(), skeleton.getLocation().getX(), skeleton.getLocation().getY(), skeleton.getLocation().getZ());
                displayloc.add(0,0.75,0);
                ItemDisplay ench_book = (ItemDisplay) skeleton.getWorld().spawnEntity(displayloc, EntityType.ITEM_DISPLAY);
                ench_book.setItemStack(new ItemStack(Material.ENCHANTED_BOOK));

                Transformation transf = new Transformation(new Vector3f(0F, 0F, 0F), new AxisAngle4f(1,0,0,0), new Vector3f(0.5F, 0.5F, 0.5F), new AxisAngle4f(1,0,0,0));
                ench_book.setTransformation(transf);
                ench_book.setInterpolationDelay(0);
                ench_book.setInterpolationDuration(100);

                new BukkitRunnable() {
                    @Override
                    public void run() {
                        Vector3f transl = new Vector3f(0F, 1F, 0F);
                        AxisAngle4f leftRot = new AxisAngle4f(1,0,0,0);
                        Vector3f scale = new Vector3f(0.5F, 0.5F, 0.5F);
                        AxisAngle4f rightRot = new AxisAngle4f(1,0,0,0);
                        Transformation transf = new Transformation(transl, leftRot, scale, rightRot);
                        ench_book.setTransformation(transf);
                    }
                }.runTaskLater(TMSWAdditions.getPlugin(), 2L);

                Location textloc = new Location(skeleton.getLocation().getWorld(), skeleton.getLocation().getX(), skeleton.getLocation().getY(), skeleton.getLocation().getZ());
                textloc.add(0,1,0);
                TextDisplay enchantment = (TextDisplay) skeleton.getWorld().spawnEntity(textloc, EntityType.TEXT_DISPLAY);

                final Component component = bow.getEnchantments().entrySet().stream()
                        .map(e -> Component.translatable(e.getKey()).append(Component.text(" ")).append(Component.translatable("enchantment.level." + e.getValue())))
                        .collect(Component.toComponent(Component.newline()));

                enchantment.text(component);
                enchantment.setBillboard(Display.Billboard.VERTICAL);
                enchantment.setTransformation(transf);
                enchantment.setInterpolationDelay(0);
                enchantment.setInterpolationDuration(100);

                new BukkitRunnable() {
                    @Override
                    public void run() {
                        Vector3f transl = new Vector3f(0F, 1F, 0F);
                        AxisAngle4f leftRot = new AxisAngle4f(1,0,0,0);
                        Vector3f scale = new Vector3f(0.5F, 0.5F, 0.5F);
                        AxisAngle4f rightRot = new AxisAngle4f(1,0,0,0);
                        Transformation transf = new Transformation(transl, leftRot, scale, rightRot);
                        enchantment.setTransformation(transf);
                    }
                }.runTaskLater(TMSWAdditions.getPlugin(), 2L);
                start(ench_book, ench_book.getLocation(), enchantment);
            }
        }
    }
    private void start(ItemDisplay book,Location location, TextDisplay text){
        new BukkitRunnable() {
            int yaw = 0;
            final Location loc = new Location(location.getWorld(), location.getX(), location.getY(), location.getZ());
            @Override
            public void run() {
                book.teleport(loc);
                yaw = yaw + 2;
                loc.setYaw(yaw);

                if(yaw >= 201){
                    book.remove();
                    text.remove();
                    cancel();
                }
            }
        }.runTaskTimer(TMSWAdditions.getPlugin(), 0L, 1L);
    }
}
