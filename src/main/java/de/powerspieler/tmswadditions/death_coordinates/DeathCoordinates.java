package de.powerspieler.tmswadditions.death_coordinates;

import de.powerspieler.tmswadditions.TMSWAdditions;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.scheduler.BukkitRunnable;
import org.jetbrains.annotations.NotNull;

public class DeathCoordinates implements Listener, CommandExecutor {
    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event){
        new BukkitRunnable() {
            @Override
            public void run() {
                showDeathMsg(event.getPlayer());
            }
        }.runTaskLater(TMSWAdditions.getPlugin(), 1L);
    }
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if(sender instanceof Player player){
            showDeathMsg(player);
        }
        return true;
    }

    private static void showDeathMsg(Player player){
        Location loc = player.getLastDeathLocation();
        if(loc != null){
            String world = loc.getWorld().getName();
            switch (world) {
                case "world" -> world = "the Overworld";
                case "world_nether" -> world = "the Nether";
                case "world_the_end" -> world = "the End";
            }

            int x = loc.getBlockX();
            int y = loc.getBlockY();
            int z = loc.getBlockZ();

            player.sendMessage(Component.text("[", NamedTextColor.GOLD)
                    .append(Component.text("Death", NamedTextColor.RED))
                    .append(Component.text("] ", NamedTextColor.GOLD))
                    .append(Component.text("Your last point of death is in " + world + " at ", NamedTextColor.GREEN))
                    .append(Component.text("[", NamedTextColor.GOLD))
                    .append(Component.text("" + x + ", " + y + ", " + z , NamedTextColor.BLUE))
                    .append(Component.text("]", NamedTextColor.GOLD))
                    .append(Component.text(".", NamedTextColor.GREEN)));
        } else {
            player.sendMessage(Component.text("You haven't died yet.", NamedTextColor.DARK_RED));
        }
    }
}
