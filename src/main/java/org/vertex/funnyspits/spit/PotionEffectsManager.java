package org.vertex.funnyspits.spit;

import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;

public class PotionEffectsManager {
    public PotionEffectsManager() {}

    public void giveEffects(Player from, Entity to) {
        Bukkit.getLogger().info(from.getName());
        for (PotionEffect effect: from.getActivePotionEffects()) {
            Bukkit.getLogger().info(effect.getType().toString());
            from.removePotionEffect(effect.getType());
            from.addPotionEffect(new PotionEffect(
                    effect.getType(),
                    effect.getDuration() - 100,
                    effect.getAmplifier(),
                    effect.isAmbient()
            ));
        }
    }
}
