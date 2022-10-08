package de.powerspieler.tmswadditions.playerheads;

import de.powerspieler.tmswadditions.TMSWAdditions;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.scheduler.BukkitRunnable;

public class HeadRecipe {
    protected static final NamespacedKey PLAYERHEADRECIPEKEY = new NamespacedKey(TMSWAdditions.getPlugin(), "playerhead");
    public void registerRecipes(){
        new BukkitRunnable() {
            @Override
            public void run() {
                ShapedRecipe ph_Recipe = new ShapedRecipe(PLAYERHEADRECIPEKEY, new ItemStack(Material.PLAYER_HEAD, 4));
                ph_Recipe.shape("+++","+#+","+++");
                ph_Recipe.setIngredient('+', Material.ROTTEN_FLESH);
                ph_Recipe.setIngredient('#', Material.BONE_BLOCK);
                Bukkit.addRecipe(ph_Recipe);
            }
        }.runTaskLater(TMSWAdditions.getPlugin(), 5L);
    }
}
