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
import org.bukkit.plugin.java.JavaPlugin;
import org.vertex.funnyspits.commands.FunnySpitsCommands;
import org.vertex.funnyspits.commands.SpitCommand;
import org.vertex.funnyspits.listeners.*;
import org.vertex.funnyspits.spit.AutoSpitManager;
import org.vertex.funnyspits.spit.BellBlockManager;
import org.vertex.funnyspits.spit.CampFireBlockManager;
import org.vertex.funnyspits.spit.FireBlockManager;
import org.vertex.funnyspits.spit.PotionEffectsManager;
import org.vertex.funnyspits.spit.SpitManager;
import org.vertex.funnyspits.spit.SpongeBlockManager;
import org.vertex.funnyspits.storage.AutoSpitValuesStorage;
import org.vertex.funnyspits.storage.CooldownValuesStorage;
import org.vertex.funnyspits.resource.LocaleManager;
import org.vertex.funnyspits.resource.ResourceManager;
import org.vertex.funnyspits.storage.SpongeBlockHumidityValuesStorage;

public final class FunnySpits extends JavaPlugin {
    private FileConfiguration configuration;
    private FileConfiguration messagesConfiguration;
    private CooldownValuesStorage cooldownValuesStorage;
    private AutoSpitValuesStorage autoSpitValuesStorage;
    private SpongeBlockHumidityValuesStorage spongeBlockHumidityValuesStorage;
    private ResourceManager resourceManager;
    private LocaleManager localeManager;
    private AutoSpitManager autoSpitManager;
    private SpitManager spitsManager;
    private PotionEffectsManager potionEffectsManager;
    private SpongeBlockManager spongeBlockManager;
    private CampFireBlockManager campFireBlockManager;
    private BellBlockManager bellBlockManager;
    private FireBlockManager fireBlockManager;

    public FileConfiguration getConfiguration() {
        return configuration;
    }

    public FileConfiguration getMessagesConfiguration() {
        return messagesConfiguration;
    }

    public CooldownValuesStorage getCooldownValuesStorage() {
        return cooldownValuesStorage;
    }

    public AutoSpitValuesStorage getAutoSpitValuesStorage() {
        return autoSpitValuesStorage;
    }

    public SpongeBlockHumidityValuesStorage getSpongeBlockHumidityValuesStorage() {
        return spongeBlockHumidityValuesStorage;
    }

    public ResourceManager getResourceManager() {
        return resourceManager;
    }

    public LocaleManager getLocaleManager() {
        return localeManager;
    }

    public AutoSpitManager getAutoSpitManager() {
        return autoSpitManager;
    }

    public SpitManager getSpitsManager() {
        return spitsManager;
    }

    public PotionEffectsManager getPotionEffectsManager() {
        return potionEffectsManager;
    }

    public SpongeBlockManager getSpongeBlockManager() {
        return spongeBlockManager;
    }

    public CampFireBlockManager getCampFireBlockManager() {
        return campFireBlockManager;
    }

    public BellBlockManager getBellBlockManager() {
        return bellBlockManager;
    }

    public FireBlockManager getFireBlockManager() {
        return fireBlockManager;
    }

    public void setConfiguration(FileConfiguration configuration) {
        this.configuration = configuration;
    }

    public void setMessagesConfiguration(FileConfiguration messagesConfiguration) {
        this.messagesConfiguration = messagesConfiguration;
    }

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

        configuration = getConfig();

        resourceManager = new ResourceManager(this);

        localeManager = new LocaleManager(this);
        localeManager.loadLocaleConfiguration();

        saveDefaultConfig();

        autoSpitManager = new AutoSpitManager(this);

        autoSpitValuesStorage = new AutoSpitValuesStorage();
        cooldownValuesStorage = new CooldownValuesStorage();
        spongeBlockHumidityValuesStorage = new SpongeBlockHumidityValuesStorage();

        spitsManager = new SpitManager(this);

        potionEffectsManager = new PotionEffectsManager(this);

        spongeBlockManager = new SpongeBlockManager(this);
        campFireBlockManager = new CampFireBlockManager();
        bellBlockManager = new BellBlockManager();
        fireBlockManager = new FireBlockManager();

        ListenersManager listenersManager = new ListenersManager(this);
        listenersManager.registerListeners();

        new FunnySpitsCommands(this);
        new SpitCommand(this);
    }

    @Override
    public void onDisable() {
    }
}
