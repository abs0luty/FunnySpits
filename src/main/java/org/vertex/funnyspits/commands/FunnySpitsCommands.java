package org.vertex.funnyspits.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.command.*;
import org.bukkit.entity.LlamaSpit;
import org.bukkit.entity.Player;
import org.bukkit.permissions.Permission;
import org.bukkit.permissions.PermissionDefault;
import org.bukkit.util.StringUtil;
import org.vertex.funnyspits.FunnySpits;

import java.util.*;

public class FunnySpitsCommands implements CommandExecutor, TabCompleter {
    private FunnySpits plugin;

    public FunnySpitsCommands(FunnySpits plugin) {
        this.plugin = plugin;
        this.plugin.getCommand("funnyspits").setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command,
                             String label, String[] args) {
        if (args.length == 1) {
            if (args[0].equals("spit")) {
                if (!(sender instanceof Player)) return false;

                Player player = (Player) sender;
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
                return true;
            } else if (args[0].equals("reload")) {
                boolean ableToReload = false;
                if (sender instanceof ConsoleCommandSender) ableToReload = true;
                else if (sender instanceof Player) {
                    if (sender.hasPermission(
                            FunnySpits.configuration
                                    .getString("reload_command_permission")
                    )) ableToReload = true;
                }

                if (!ableToReload) {
                    if (sender instanceof Player) {
                        sender.sendMessage(
                                ChatColor.translateAlternateColorCodes('&',
                                        FunnySpits.configuration.getString(
                                                "reload_command_not_enough_permissions_message"))
                        );
                    }
                    return false;
                } else {
                    plugin.reloadConfig();
                    FunnySpits.configuration = plugin.getConfig();
                }

                if (sender instanceof Player) {
                    sender.sendMessage(
                            ChatColor.translateAlternateColorCodes('&',
                                    FunnySpits.configuration.getString(
                                            "reload_command_success_message"))
                    );
                }
                return true;
            }
        }

        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command,
                                      String label, String[] args) {
        List<String> completions = new ArrayList<>();
        if (args.length == 1)
        {
            StringUtil.copyPartialMatches(
                    args[0], Arrays.asList(new String[]{"spit", "reload"}),
                    completions);
            Collections.sort(completions);
            return completions;
        }

        return null;
    }
}
