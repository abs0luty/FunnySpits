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

package org.vertex.funnyspits.spit;

import org.vertex.funnyspits.FunnySpits;
import org.bukkit.Material;
import org.bukkit.block.Block;

public class SpongeBlockManager implements ProjectileHitEventBlockManager {
    private FunnySpits plugin;

    public SpongeBlockManager(FunnySpits plugin) {
        this.plugin = plugin;
    }

    @Override
    public void onProjectileHit(Block hitBlock) {
        plugin.getSpongeBlockHumidityValuesStorage().increaseHumidity(
                hitBlock.getLocation());

        int humidity = plugin.getSpongeBlockHumidityValuesStorage()
                .getHumidity(hitBlock.getLocation());

        if (humidity >= plugin.getConfiguration()
                .getInt("sponge_blocks_spits_required")) {
            hitBlock.setType(Material.WET_SPONGE);
        }
    }
}
