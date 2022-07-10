package org.vertex.funnyspits.logic;

import org.bukkit.Effect;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class Effects {
    public static void giveEffects(Player from, Player to) {
        to.addPotionEffect(new PotionEffect(
                PotionEffectType.LEVITATION, 4, 5, false,
                false, true));
    }
}
