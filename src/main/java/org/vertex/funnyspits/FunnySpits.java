package org.vertex.funnyspits;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.vertex.funnyspits.commands.FunnySpitsCommands;
import org.vertex.funnyspits.commands.SpitCommand;
import org.vertex.funnyspits.listeners.ProjectileHitEventListener;

public final class FunnySpits extends JavaPlugin {
    public static FileConfiguration configuration;

    @Override
    public void onEnable() {
        saveDefaultConfig();

        configuration = getConfig();

        int spitIntensity = configuration.getInt("spit_intensity");
        if (spitIntensity < 1 || spitIntensity > 10) {
            getLogger().severe("spit_intensity value in config.yml must be in the interval of 1 to 10");
        }

        new FunnySpitsCommands(this);
        new SpitCommand(this);

        PluginManager manager = getServer().getPluginManager();
        manager.registerEvents(new ProjectileHitEventListener(), this);
    }

    @Override
    public void onDisable() { }
}
