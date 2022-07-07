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

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.data.type.Bell;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.util.BlockIterator;
import org.vertex.funnyspits.FunnySpits;

public class ProjectileHitEventListener implements Listener {
    private FunnySpits plugin;

    public ProjectileHitEventListener(FunnySpits plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onProjectileHitEvent(ProjectileHitEvent event) {
        if (!(event.getEntity() instanceof LlamaSpit)) return;

        if (event.getEntity().getShooter() instanceof Player) {
            event.setCancelled(true);

            LivingEntity entity = (LivingEntity) event.getHitEntity();
            if (entity == null) return;

            if (entity instanceof Villager &&
                    FunnySpits.configuration.getBoolean("spit_villager_interaction")) {
                ((Villager) entity).shakeHead();
            }

            if (entity instanceof Bell &&
                    FunnySpits.configuration.getBoolean("spit_bell_interaction")) {
                ((Bell) entity).setPowered(true);
            }

            Block block = event.getEntity().getLocation().getBlock();

            plugin.getLogger().info(block.getType().toString());

            double damage = FunnySpits.configuration.getDouble("spit_damage");
            if (damage != 0) {
                entity.damage(damage);
            } else {
                if (FunnySpits.configuration.getBoolean("spit_kill")) {
                    entity.setHealth(0);
                }
            }
        }
    }
}
