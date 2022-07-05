package org.vertex.funnyspits;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import org.vertex.funnyspits.commands.FunnySpitsCommands;

public final class FunnySpits extends JavaPlugin {
    public static FileConfiguration configuration;

    @Override
    public void onEnable() {
        saveDefaultConfig();

        configuration = getConfig();

        int spitIntensity = configuration.getInt("spit_intensity");
        if (spitIntensity < 1 || spitIntensity > 10) {
            getLogger().severe("split_intensity value in config.yml must be in the interval of 1 to 10");
        }

        new FunnySpitsCommands(this);
    }

    @Override
    public void onDisable() { }
}
