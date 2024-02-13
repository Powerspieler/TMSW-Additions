package de.powerspieler.tmswadditions.totemmsg;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByBlockEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityResurrectEvent;
import org.bukkit.potion.PotionEffectType;

public class TotemMsg implements Listener {
    @EventHandler
    public void onTotemUse(EntityResurrectEvent event) {
        if (!event.isCancelled()) {
            if (event.getEntity() instanceof Player player) {
                if (player.getLastDamageCause() != null) {
                    Component damageType = Component.text(player.getLastDamageCause().getCause().name().toLowerCase().replace("_", " "), NamedTextColor.DARK_PURPLE);
                    Component cause = Component.text("error");
                    try {
                        EntityDamageByEntityEvent entityEvent = (EntityDamageByEntityEvent) player.getLastDamageCause();
                        EntityType entityType = entityEvent.getDamager().getType();


                        if(entityType == EntityType.ARROW){
                            Arrow arrow = (Arrow) entityEvent.getDamager();
                            PotionEffectType potionEffectType = arrow.getBasePotionData().getType().getEffectType();

                            Player shooter = null;
                            try{
                                shooter = (Player) arrow.getShooter();
                            } catch (ClassCastException ignored){
                            }


                            if(shooter == null){
                                if(potionEffectType == null){
                                    cause = parseTextWithArticle(entityType);
                                } else {
                                    cause = parseTextWithArticle(entityType).append(parseText(" of ", potionEffectType.translationKey()));
                                }
                            } else {
                                if(potionEffectType == null){
                                    cause = parseTextWithArticle(entityType).append(parseText(" shot by ", shooter.getName()));
                                } else {
                                    cause = parseTextWithArticle(entityType)
                                            .append(parseText(" of ", potionEffectType.translationKey())
                                                    .append(parseText(" shot by ", shooter.getName())));
                                }
                            }

                        } else if (entityType == EntityType.AREA_EFFECT_CLOUD) {
                            AreaEffectCloud cloud = (AreaEffectCloud) entityEvent.getDamager();
                            PotionEffectType potionEffectType = cloud.getBasePotionData().getType().getEffectType();

                            if(cloud.hasCustomEffect(PotionEffectType.HARM)){
                                cause = parseTextWithArticle(entityType).append(Component.text(" of the Ender Dragon", NamedTextColor.DARK_PURPLE));
                            } else if(potionEffectType == null){
                                cause = parseTextWithArticle(entityType);
                            } else {
                                cause = parseTextWithArticle(entityType).append(parseText(" of ", potionEffectType.translationKey()));
                            }

                        } else if(entityType == EntityType.EVOKER_FANGS){
                            cause = parseText("", entityType.translationKey());

                        } else if (entityType == EntityType.FALLING_BLOCK) {
                            FallingBlock fallingBlock = (FallingBlock) entityEvent.getDamager();
                            cause = parseText("falling ", fallingBlock.getBlockData().getMaterial().translationKey());

                        } else if (entityType == EntityType.PLAYER) {
                            Player playerOther = (Player) entityEvent.getDamager();
                            cause = parseText("", playerOther.getName());

                        } else if (entityType == EntityType.SPLASH_POTION) {
                            ThrownPotion potion = (ThrownPotion) entityEvent.getDamager();
                            PotionEffectType potionEffectType = potion.getPotionMeta().getBasePotionData().getType().getEffectType();

                            if(potionEffectType == null){
                                cause = parseTextWithArticle(entityType);
                            } else {
                                cause = parseTextWithArticle(entityType).append(parseText(" of ", potionEffectType.translationKey()));
                            }

                        } else {
                            cause = parseTextWithArticle(entityType);
                        }
                        
                    } catch (ClassCastException entityE) {
                        try {
                            EntityDamageByBlockEvent blockEvent = (EntityDamageByBlockEvent) player.getLastDamageCause();
                            Block block = blockEvent.getDamager();

                            if(block != null){
                                cause = parseTextWithArticle(block.getBlockData().getMaterial());
                            } else {
                                if(blockEvent.getDamagerBlockState() != null){
                                    cause = parseTextWithArticle(blockEvent.getDamagerBlockState().getBlockData().getMaterial());
                                }
                            }


                        } catch (ClassCastException blockE) {
                            cause = damageType;
                        }
                    }
                    Bukkit.broadcast(Component.text(player.getName() + " saved their life from ")
                            .append(cause)
                            .append(Component.text(" with a "))
                            .append(Component.text("[Totem of Undying]", NamedTextColor.YELLOW)));

                }
            }
        }
    }

    private boolean startWithVowel(String input) {
        String letter = input.substring(0, 1);
        return letter.equals("A") || letter.equals("E") || letter.equals("I") || letter.equals("O") || letter.equals("U");
    }


    private Component parseTextWithArticle(EntityType entityType){
        if(startWithVowel(entityType.toString())){
            return Component.text("an ", NamedTextColor.DARK_PURPLE).append(Component.translatable(entityType.translationKey()));
        }
        return Component.text("a ", NamedTextColor.DARK_PURPLE).append(Component.translatable(entityType.translationKey()));
    }
    private Component parseTextWithArticle(Material material){
        if(startWithVowel(material.toString())){
            return Component.text("an ", NamedTextColor.DARK_PURPLE).append(Component.translatable(material.translationKey()));
        }
        return Component.text("a ", NamedTextColor.DARK_PURPLE).append(Component.translatable(material.translationKey()));
    }


    private Component parseText(String prefix, String translationKey) {
        return Component.text(prefix, NamedTextColor.DARK_PURPLE)
                .append(Component.translatable(translationKey, NamedTextColor.DARK_PURPLE));
    }
}
