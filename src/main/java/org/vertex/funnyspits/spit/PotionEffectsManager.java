/*
 * MIT License
 *
 * Copyright (c) 2022 Adi Salimgereyev
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package org.vertex.funnyspits.spit;

import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.vertex.funnyspits.FunnySpits;

public class PotionEffectsManager {
    private final FunnySpits plugin;

    public PotionEffectsManager(FunnySpits plugin) {
        this.plugin = plugin;
    }

    /**
     * Get the potion effect object from its type.
     * 
     * @param player player to get the potion effect from.
     * @param type   potion effect type.
     * @return spigot potion effect object.
     */
    private PotionEffect getPotionEffect(Player player, PotionEffectType type) {
        for (PotionEffect effect : player.getActivePotionEffects()) {
            if (effect.getType().equals(type)) {
                return effect;
            }
        }
        return null;
    }

    /**
     * Give the player a potion effect from another player.
     * 
     * @param from player to give the potion effect from.
     * @param to   player to give the potion effect to.
     */
    public void giveEffects(Player from, Player to) {
        if (!(plugin.getConfiguration().getBoolean("potion_effects_transmission_enabled")))
            return;

        for (PotionEffect effect : from.getActivePotionEffects()) {
            Bukkit.getLogger().info(effect.getType().toString());

            int durationPart = (int) (effect.getDuration() / 100 * plugin.getConfiguration()
                    .getDouble("potion_effects_transmission_percentage"));

            // Subtract the potion effect duration from the player "from".
            from.removePotionEffect(effect.getType());
            from.addPotionEffect(new PotionEffect(
                    effect.getType(),
                    effect.getDuration() - durationPart,
                    effect.getAmplifier(),
                    effect.isAmbient()));

            // Add the potion effect duration to the player "to".
            PotionEffect toEffect = getPotionEffect(
                    to, effect.getType());
            if (toEffect == null)
                toEffect = new PotionEffect(
                        effect.getType(),
                        0,
                        effect.getAmplifier(),
                        effect.isAmbient());
            else
                to.removePotionEffect(effect.getType());

            from.addPotionEffect(new PotionEffect(
                    toEffect.getType(),
                    toEffect.getDuration() + durationPart,
                    toEffect.getAmplifier(),
                    toEffect.isAmbient()));
        }
    }
}