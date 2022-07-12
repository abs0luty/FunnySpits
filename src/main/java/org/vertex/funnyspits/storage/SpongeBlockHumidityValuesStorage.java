package org.vertex.funnyspits.storage;

import org.bukkit.Location;
import org.bukkit.Material;

import java.util.ArrayList;
import java.util.List;

public class SpongeBlockHumidityValuesStorage {
    private List<SpongeBlockHumidityValuesStorageColumn>
            columns = new ArrayList<>();

    private boolean blockExists(Location location) {
        return location.getBlock().getType() == Material.AIR;
    }

    private boolean blockRegistered(Location location) {
        for (SpongeBlockHumidityValuesStorageColumn column: columns) {
            if (column.getBlockLocation().equals(location)) {
                return true;
            }
        }

        return false;
    }

    public void increaseHumidity(Location location) {
        for (SpongeBlockHumidityValuesStorageColumn column: columns) {
            if (column.getBlockLocation().equals(location)) {
                column.setHumidity(column.getHumidity() + 1);
                return;
            }
        }

        columns.add(new SpongeBlockHumidityValuesStorageColumn(
                location, 0));
    }

    public int getHumidity(Location location) {
        for (SpongeBlockHumidityValuesStorageColumn column: columns) {
            if (column.getBlockLocation().equals(location)) {
                return column.getHumidity();
            }
        }

        return 0;
    }

    public void removeBlockAt(Location location) {
        columns.removeIf(column ->
                column.getBlockLocation().equals(location));
    }
}
