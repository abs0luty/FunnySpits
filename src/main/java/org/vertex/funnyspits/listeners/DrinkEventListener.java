package org.vertex.funnyspits.listeners;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.vertex.funnyspits.FunnySpits;
import org.vertex.funnyspits.logic.CooldownValuesStorage;

public class DrinkEventListener implements Listener {
    private final FunnySpits plugin;

    public DrinkEventListener(FunnySpits plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onDrink(PlayerItemConsumeEvent event) {
        if (event.getItem().getType() != Material.POTION) return;

        plugin.getCooldownValuesStorage().setBonusWaterSpits(event.getPlayer(),
            plugin.getConfiguration().getInt("bonus_water_spits"));
    }
}
