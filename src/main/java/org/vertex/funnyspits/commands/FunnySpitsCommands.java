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
 *
 */

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
                return Spit.spit(player);
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
