package org.vertex.funnyspits.resources;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public class SaveDefaultResource {
    public static void save(String path, JavaPlugin plugin) {
        try {
            File file = new File(plugin.getDataFolder(), path);
            if (!file.exists()) {
                plugin.saveResource(path,
                        false);
            }
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
