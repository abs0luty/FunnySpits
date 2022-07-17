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

package org.vertex.funnyspits.storage;

import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class AutoSpitValuesStorage {
    private static List<AutoSpitValuesStorageColumn> columns = new ArrayList<>();

    public AutoSpitValuesStorage() {
    }

    public boolean playerRegistered(Player player) {
        for (AutoSpitValuesStorageColumn column : columns) {
            if (column.getPlayer().equals(player))
                return true;
        }

        return false;
    }

    public boolean getAutoSpitEnabled(Player player) {
        for (AutoSpitValuesStorageColumn column : columns) {
            if (column.getPlayer().equals(player)) {
                return column.getEnabled();
            }
        }

        return false;
    }

    private void addColumn(Player player, boolean enabled) {
        columns.add(new AutoSpitValuesStorageColumn(player, enabled));
    }

    public void setAutoSpitAbility(Player player, boolean enabled) {
        for (AutoSpitValuesStorageColumn column : columns) {
            if (column.getPlayer().equals(player)) {
                column.setEnabled(enabled);
                return;
            }
        }

        addColumn(player, enabled);
    }

    public void setAutoSpitEnabled(Player player) {
        setAutoSpitAbility(player, true);
    }

    public void setAutoSpitDisabled(Player player) {
        setAutoSpitAbility(player, false);
    }
}
