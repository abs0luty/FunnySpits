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

package org.vertex.funnyspits.logic;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.LlamaSpit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.vertex.funnyspits.FunnySpits;

import java.util.Iterator;

public class SpitsManager {
    private FunnySpits plugin;

    public SpitsManager(FunnySpits plugin) {
        this.plugin = plugin;
    }

    public boolean spit(Player player) {
        long cooldownTime = plugin.getConfiguration().getLong(
                "spit_command_cooldown");
        int bonusSpits;
        if (!plugin.getCooldownValuesStorage().playerRegistered(player))
            bonusSpits = 0;
        else bonusSpits = plugin.getCooldownValuesStorage()
                .getPlayerBonusWaterSpits(player);

        if (!player.hasPermission(
                plugin.getConfiguration().getString("spit_command_permission")
        )) {
            player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                    plugin.getMessagesConfiguration().getString(
                            "spit_command_not_enough_permissions_message")));
            return false;
        }

        if (bonusSpits > 0) {
            plugin.getCooldownValuesStorage().decreaseBonusWaterSpits(player);
        } else if (plugin.getCooldownValuesStorage().playerRegistered(player)
                && cooldownTime != 0) {
            long lastUsageTime = plugin.getCooldownValuesStorage()
                    .getPlayerCommandUsageTime(
                    player);
            long timeSinceLastUsageTime = (System.currentTimeMillis() / 1000)
                    - lastUsageTime;
            if (timeSinceLastUsageTime < cooldownTime) {
                player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                        String.format(plugin.getMessagesConfiguration().getString(
                        "spit_command_cooldown_not_over_message"),
                                cooldownTime - timeSinceLastUsageTime)));
                return false;
            }
        }

        int spitIntensity = plugin.getConfiguration().getInt(
                "spit_intensity");

        for (int i = 0; i < spitIntensity; i++) {
            player.launchProjectile(LlamaSpit.class);
        }

        if (plugin.getConfiguration().getBoolean("spit_with_sound")) {
            player.getWorld().playSound(player.getLocation(),
                    Sound.ENTITY_LLAMA_SPIT, 1.0F, 1.0F);
        }

        if (cooldownTime != 0) {
            plugin.getCooldownValuesStorage().setCommandUsageTime(player,
                    System.currentTimeMillis() / 1000);
        }

        return false;
    }
}
