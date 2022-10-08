package de.powerspieler.tmswadditions.brewing.results;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class Champagne implements Brewing{
    @Override
    public ItemStack build() {
        ItemStack item = new ItemStack(Material.POTION);
        PotionMeta itemmeta = (PotionMeta) item.getItemMeta();
        itemmeta.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);
        itemmeta.addCustomEffect(new PotionEffect(PotionEffectType.CONFUSION, 200, 4, true, false), true);
        itemmeta.addCustomEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 200, 1, true, false), true);
        itemmeta.setColor(Color.PURPLE);
        itemmeta.displayName(Component.text("Champagne", NamedTextColor.YELLOW)
                .decoration(TextDecoration.ITALIC, false));
        item.setItemMeta(itemmeta);
        return item;
    }
}
