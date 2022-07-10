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

import org.bukkit.ChatColor;
import org.bukkit.command.*;
import org.bukkit.entity.Player;
import org.bukkit.util.StringUtil;
import org.vertex.funnyspits.FunnySpits;

import java.util.*;

public class FunnySpitsCommands implements CommandExecutor, TabCompleter {
    private final FunnySpits plugin;

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

                return plugin.getSpitsManager().spit((Player) sender);
            } else if (args[0].equals("reload")) {
                boolean ableToReload = false;
                if (sender instanceof ConsoleCommandSender) ableToReload = true;
                else if (sender instanceof Player) {
                    if (sender.hasPermission(
                            plugin.getConfiguration()
                                    .getString("reload_command_permission")
                    )) ableToReload = true;
                }

                if (!ableToReload) {
                    if (sender instanceof Player) {
                        sender.sendMessage(
                                ChatColor.translateAlternateColorCodes('&',
                                        plugin.getMessagesConfiguration()
                                                .getString(
                                                "reload_command_not_enough_permissions_message"))
                        );
                    }
                    return false;
                } else {
                    plugin.saveDefaultConfig();
                    plugin.reloadConfig();
                    plugin.setConfiguration(plugin.getConfig());
                    plugin.getLocaleManager().loadLocaleConfiguration();
                }

                if (sender instanceof Player) {
                    sender.sendMessage(
                            ChatColor.translateAlternateColorCodes('&',
                                    plugin.getMessagesConfiguration().getString(
                                            "reload_command_success_message"))
                    );
                }
                return true;
            }
        } else if (args.length == 2) {
            if (args[0].equals("autospit") && plugin.getConfiguration()
                    .getBoolean("autospit_enabled")) {
                if (!(sender instanceof Player)) return false;

                Player player = (Player) sender;
                if (args[1].equals("on")) return plugin.getAutoSpitManager()
                        .turnAutoSpitOn(player);
                if (args[1].equals("off")) return plugin.getAutoSpitManager()
                        .turnAutoSpitOff(player);
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
            ArrayList<String> completionsList = new ArrayList<>();

            if (sender instanceof Player) {
                Player player = ((Player) sender);

                if (player.hasPermission(plugin.getConfiguration()
                        .getString("spit_command_permission")))
                    completionsList.add("spit");

                if (player.hasPermission(plugin.getConfiguration()
                        .getString("reload_command_permission")))
                    completionsList.add("reload");

                if (plugin.getConfiguration().getBoolean("autospit_enabled") &&
                        player.hasPermission(plugin.getConfiguration()
                                .getString("spit_command_permission")))
                    completionsList.add("autospit");
            } else if (sender instanceof ConsoleCommandSender) {
                completionsList.add("reload");
            }

            StringUtil.copyPartialMatches(
                    args[0], completionsList,
                    completions);
            Collections.sort(completions);
            return completions;
        } else if (args.length == 2 && plugin.getConfiguration().getBoolean(
                "autospit_enabled")) {
            StringUtil.copyPartialMatches(
                    args[1], Arrays.asList(
                            new String[]{"on", "off"}),
                    completions);
            Collections.sort(completions);
            return completions;
        }

        return null;
    }
}
