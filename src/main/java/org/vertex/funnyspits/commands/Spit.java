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

package org.vertex.funnyspits.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.LlamaSpit;
import org.bukkit.entity.Player;
import org.vertex.funnyspits.FunnySpits;
import org.vertex.funnyspits.storage.CooldownValuesStorage;

import java.time.LocalTime;
import java.util.Iterator;

public class Spit {
    public static boolean spit(Player player) {
        long cooldownTime = FunnySpits.configuration.getLong(
                "spit_command_cooldown");

        if (CooldownValuesStorage.playerRegistered(player) && cooldownTime != 0) {
            long lastUsageTime = CooldownValuesStorage.getPlayerCommandUsageTime(
                    player);
            long timeSinceLastUsageTime = (System.currentTimeMillis() / 1000)
                    - lastUsageTime;
            if (timeSinceLastUsageTime < cooldownTime) {
                player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                        String.format(FunnySpits.configuration.getString(
                        "spit_command_cooldown_not_over_message"), cooldownTime - timeSinceLastUsageTime)));
                return false;
            }
        }

        if (!player.hasPermission(
                FunnySpits.configuration.getString("spit_command_permission")
        )) {
            player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                    FunnySpits.configuration.getString(
                            "spit_command_not_enough_permissions_message")));
            return false;
        }

        int spitIntensity = FunnySpits.configuration.getInt(
                "spit_intensity");

        for (int i = 0; i < spitIntensity; i++) {
            player.launchProjectile(LlamaSpit.class);
        }

        if (FunnySpits.configuration.getBoolean("spit_with_sound")) {
            Player onlinePlayer;
            for (Iterator<? extends Player> onlinePlayersIterator =
                 Bukkit.getOnlinePlayers().iterator(); onlinePlayersIterator
                         .hasNext(); ) {
                onlinePlayer = onlinePlayersIterator.next();
                onlinePlayer.playSound(onlinePlayer.getLocation(),
                        Sound.ENTITY_LLAMA_SPIT, 1.0F, 1.0F);
            }
        }

        if (cooldownTime != 0) {
            CooldownValuesStorage.setCommandUsageTime(player,
                    System.currentTimeMillis() / 1000);
        }

        return false;
    }
}
