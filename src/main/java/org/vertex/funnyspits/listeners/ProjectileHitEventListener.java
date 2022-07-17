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

package org.vertex.funnyspits.listeners;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.craftbukkit.v1_19_R1.entity.CraftVillager;
import org.bukkit.entity.*;
import org.bukkit.entity.Villager.Profession;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.projectiles.ProjectileSource;
import org.vertex.funnyspits.FunnySpits;
import org.vertex.funnyspits.spit.ProjectileHitEventBlockManager;

public class ProjectileHitEventListener implements Listener {
    private final FunnySpits plugin;

    public ProjectileHitEventListener(FunnySpits plugin) {
        this.plugin = plugin;
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onProjectileHitEvent(ProjectileHitEvent event) {
        Bukkit.getLogger().info(event.toString());

        if (!(event.getEntity() instanceof LlamaSpit))
            return;

        ProjectileSource shooter = event.getEntity().getShooter();
        if (shooter instanceof Player) {
            event.setCancelled(true);

            Block block = event.getHitBlock();
            if (block != null) {
                HashMap<Material, ProjectileHitEventBlockManager> blockManagers = new HashMap<>();
                if (plugin.getConfiguration().getBoolean("campfire_interaction_enabled"))
                    blockManagers.put(Material.CAMPFIRE, plugin.getCampFireBlockManager());

                if (plugin.getConfiguration().getBoolean("fire_interaction_enabled"))
                    blockManagers.put(Material.FIRE, plugin.getFireBlockManager());

                if (plugin.getConfiguration().getBoolean("sponge_blocks_interaction_enabled"))
                    blockManagers.put(Material.SPONGE, plugin.getSpongeBlockManager());

                for (Map.Entry<Material, ProjectileHitEventBlockManager> entry : blockManagers.entrySet()) {
                    if (entry.getKey() == block.getType()) {
                        entry.getValue().onProjectileHit(block);
                        break;
                    }
                }
            }

            Entity entity = event.getHitEntity();

            if (!(entity instanceof LivingEntity))
                return;

            LivingEntity livingEntity = (LivingEntity) entity;

            if (livingEntity instanceof Player && shooter == (Player) livingEntity)
                return;

            if (livingEntity instanceof Player)
                plugin.getPotionEffectsManager().giveEffects((Player) shooter, (Player) livingEntity);

            if (livingEntity instanceof Villager) {
                Bukkit.getLogger().info("Villager hit");

                ((Villager) livingEntity).shakeHead();
            }

            double damage = plugin.getConfiguration().getDouble(
                    "spit_damage");
            if (damage != 0) {
                livingEntity.damage(damage);
            } else {
                if (plugin.getConfiguration().getBoolean("spit_kill")) {
                    livingEntity.setHealth(0);
                }
            }
        }
    }
}
