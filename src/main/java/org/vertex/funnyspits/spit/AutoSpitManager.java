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

import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.vertex.funnyspits.FunnySpits;

public class AutoSpitManager {
    private final FunnySpits plugin;

    public AutoSpitManager(FunnySpits plugin) {
        this.plugin = plugin;
    }

    public boolean setAutoSpitEnabledValue(Player player, boolean value) {
        if (plugin.getConfiguration().getStringList("worlds_prohibited").contains(
                player.getWorld().getName()))
            return false;

        plugin.getAutoSpitValuesStorage().setAutoSpitAbility(player, value);
        player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                plugin.getMessagesConfiguration().getString(
                        String.format("autospit_turned_%s_message", value ? "on" : "off"))));
        player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_XYLOPHONE,
                1f, 1f);
        return true;
    }

    public boolean turnAutoSpitOn(Player player) {
        return setAutoSpitEnabledValue(player, true);
    }

    public boolean turnAutoSpitOff(Player player) {
        return setAutoSpitEnabledValue(player, false);
    }
}
