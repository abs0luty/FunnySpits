package org.vertex.funnyspits.resource;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.vertex.funnyspits.FunnySpits;

import java.io.File;
import java.util.List;

public class LocaleManager {
    private final FunnySpits plugin;

    public LocaleManager(FunnySpits plugin) {
        this.plugin = plugin;
    }

    public void loadLocaleConfiguration() {
        saveDefaultLocales(plugin);

        plugin.setMessagesConfiguration(YamlConfiguration
                .loadConfiguration(
                new File(
                    plugin.getDataFolder(),
                    String.format(
                        "locales/%s.yml",
                        plugin.getConfig().getString("locale")
                    )
                )
            )
        );
    }

    private void saveDefaultLocales(FunnySpits plugin) {
        plugin.getResourceManager().overwriteResource("locales.yml");

        FileConfiguration localesConfiguration = YamlConfiguration
                .loadConfiguration(new File(plugin.getDataFolder(),
                        "locales.yml"));

        List<String> localesList = localesConfiguration.getStringList(
                "locales");
        for (String localeName: localesList) {
            this.plugin.getLogger().info(localeName);
            plugin.getResourceManager().saveResource(String.format("locales/%s.yml",
                    localeName));
        }
    }
}
