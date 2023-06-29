package de.powerspieler.tmswadditions;

import de.powerspieler.tmswadditions.anti_enderman_grief.PreventPickup;
import de.powerspieler.tmswadditions.brewing.AwakeBrewery;
import de.powerspieler.tmswadditions.brewing.BrewingListeners;
import de.powerspieler.tmswadditions.death_coordinates.DeathCoordinates;
import de.powerspieler.tmswadditions.encrypt.Encrypt;
import de.powerspieler.tmswadditions.map_id.MapID;
import de.powerspieler.tmswadditions.playerheads.DropOnPlayerDeath;
import de.powerspieler.tmswadditions.playerheads.HeadRecipe;
import de.powerspieler.tmswadditions.playerheads.Playerheads;
import de.powerspieler.tmswadditions.totemmsg.TotemMsg;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

public final class TMSWAdditions extends JavaPlugin {

    private static TMSWAdditions plugin;

    @Override
    public void onEnable() {
        plugin = this;
        PluginManager pm = Bukkit.getPluginManager();

        this.saveDefaultConfig();
        FileConfiguration config = this.getConfig();



        // Anti Enderman Grief
        if(config.getBoolean("anti-enderman-grief")){
            pm.registerEvents(new PreventPickup(), this);
        }

        // Brewing
        if(config.getBoolean("brewing")){
            pm.registerEvents(new AwakeBrewery(), this);
            pm.registerEvents(new BrewingListeners(), this);
        }

        // DeathCoordinates
        if(config.getBoolean("death")){
            pm.registerEvents(new DeathCoordinates(), this);
            Objects.requireNonNull(getCommand("death")).setExecutor(new DeathCoordinates());
        }

        // Encrypt
        if(config.getBoolean("encrypt")){
            Objects.requireNonNull(getCommand("key")).setExecutor(new Encrypt());
        }

        // Map ID
        if(config.getBoolean("map-id")){
            Objects.requireNonNull(getCommand("map")).setExecutor(new MapID());
        }

        // Playerheads
        if(config.getBoolean("playerheads")){
            new HeadRecipe().registerRecipes();
            Objects.requireNonNull(getCommand("playerhead")).setExecutor(new Playerheads());
            pm.registerEvents(new Playerheads(), this);
            pm.registerEvents(new DropOnPlayerDeath(), this);
        }

        // TotemMSG
        if(config.getBoolean("totemmsg")){
            pm.registerEvents(new TotemMsg(), this);
        }
    }

    public static TMSWAdditions getPlugin(){
        return plugin;
    }
}
