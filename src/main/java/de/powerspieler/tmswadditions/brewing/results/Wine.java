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

public class Wine implements Brewing{

    @Override
    public ItemStack build() {
        ItemStack item = new ItemStack(Material.POTION);
        PotionMeta itemmeta = (PotionMeta) item.getItemMeta();
        itemmeta.addItemFlags(ItemFlag.HIDE_ADDITIONAL_TOOLTIP);
        itemmeta.addCustomEffect(new PotionEffect(PotionEffectType.NAUSEA, 200, 2, true, false), true);
        itemmeta.addCustomEffect(new PotionEffect(PotionEffectType.SPEED, 200, 0, true, false), true);
        itemmeta.setColor(Color.RED);
        itemmeta.displayName(Component.text("Wine", NamedTextColor.YELLOW)
                .decoration(TextDecoration.ITALIC, false));
        item.setItemMeta(itemmeta);
        return item;
    }
}
