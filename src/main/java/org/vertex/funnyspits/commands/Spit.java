package org.vertex.funnyspits.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.LlamaSpit;
import org.bukkit.entity.Player;
import org.vertex.funnyspits.FunnySpits;

import java.util.Iterator;

public class Spit {
    public static boolean spit(Player player) {
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
        return false;
    }
}
