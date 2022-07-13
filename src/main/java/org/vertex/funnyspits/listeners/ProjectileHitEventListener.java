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

import net.minecraft.core.BlockPosition;
import net.minecraft.network.protocol.game.PacketPlayOutBlockAction;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.craftbukkit.v1_19_R1.entity.CraftPlayer;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.projectiles.ProjectileSource;
import org.vertex.funnyspits.FunnySpits;

public class ProjectileHitEventListener implements Listener {
    private final FunnySpits plugin;

    public ProjectileHitEventListener(FunnySpits plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onProjectileHitEvent(ProjectileHitEvent event) {
        if (!(event.getEntity() instanceof LlamaSpit)) return;

        ProjectileSource shooter = event.getEntity().getShooter();
        if (shooter instanceof Player) {
            event.setCancelled(true);

            Block block = event.getHitBlock();
            if (block != null) {
                if (block.getType() == Material.SPONGE) {
                    plugin.getSpongeBlockHumidityValuesStorage().increaseHumidity(
                            block.getLocation());
                    int humidity = plugin.getSpongeBlockHumidityValuesStorage().getHumidity(
                                    block.getLocation());
                    if (humidity >= plugin.getConfiguration()
                            .getInt("sponge_blocks_spits_required")) {
                        block.setType(Material.WET_SPONGE);
                    }
                }
            }


            Entity entity = event.getHitEntity();

            if (!(entity instanceof LivingEntity)) return;

            LivingEntity livingEntity = (LivingEntity) entity;

            if (livingEntity instanceof Player && shooter ==
                    (Player) livingEntity) return;

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
