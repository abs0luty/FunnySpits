package org.vertex.funnyspits.listeners;

import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.vertex.funnyspits.FunnySpits;

public class ProjectileHitEventListener implements Listener {
    @EventHandler
    public void onProjectileHitEvent(ProjectileHitEvent event) {
        if (event.getEntity().getShooter() instanceof Player) {
            event.setCancelled(true);
            ((LivingEntity) event.getHitEntity()).damage(
                    FunnySpits.configuration.getDouble("spit_damage")
            );
        }
    }
}
