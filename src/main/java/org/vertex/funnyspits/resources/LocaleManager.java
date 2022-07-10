package org.vertex.funnyspits.resources;

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

        ((FunnySpits) plugin).setMessagesConfiguration(YamlConfiguration
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
        SaveDefaultResource.save("locales.yml", plugin);

        FileConfiguration localesConfiguration = YamlConfiguration
                .loadConfiguration(new File(plugin.getDataFolder(),
                        "locales.yml"));

        List<String> localesList = localesConfiguration.getStringList(
                "locales");
        for (String localeName: localesList) {
            this.plugin.getLogger().info(localeName);
            SaveDefaultResource.save(String.format("locales/%s.yml",
                    localeName), this.plugin);
        }
    }
}
