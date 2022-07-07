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

package org.vertex.funnyspits;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.vertex.funnyspits.commands.FunnySpitsCommands;
import org.vertex.funnyspits.commands.SpitCommand;
import org.vertex.funnyspits.listeners.*;
import org.vertex.funnyspits.logic.update_checker.UpdateChecker;

public final class FunnySpits extends JavaPlugin {
    public static FileConfiguration configuration;

    @Override
    public void onEnable() {
        new UpdateChecker(this, 103160).getVersion(version -> {
            if (this.getDescription().getVersion().equals(version)) {
                getLogger().info("\u001B[32mPlugin is up to date!\u001B[37m");
            } else {
                getLogger().info(String.format("\u001B[31mThere is a new update available (%s). " +
                        "It is recommended to download it! Current plugin version: %s\u001B[37m",
                        version,
                        this.getDescription().getVersion()));
            }
        });

        saveDefaultConfig();

        configuration = getConfig();

        int spitIntensity = configuration.getInt("spit_intensity");
        if (spitIntensity < 1 || spitIntensity > 10) {
            getLogger().severe("spit_intensity value in config.yml must be in the interval of 1 to 10");
        }

        new FunnySpitsCommands(this);
        new SpitCommand(this);

        PluginManager manager = getServer().getPluginManager();
        manager.registerEvents(new ProjectileHitEventListener(this), this);
        manager.registerEvents(new LMBEventListener(), this);
        manager.registerEvents(new EntityDamageEventListener(), this);
    }

    @Override
    public void onDisable() { }
}
