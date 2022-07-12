package org.vertex.funnyspits.resource;

import org.vertex.funnyspits.FunnySpits;

import java.io.File;

public class ResourceManager {
    private FunnySpits plugin;

    public ResourceManager(FunnySpits plugin) {
        this.plugin = plugin;
    }

    public void overwriteResource(String path) {
        try {
            File file = new File(plugin.getDataFolder(), path);
            plugin.saveResource(path,
                    false);
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void saveResource(String path) {
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
