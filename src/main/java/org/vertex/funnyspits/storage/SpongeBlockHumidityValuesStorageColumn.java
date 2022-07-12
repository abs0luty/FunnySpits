package org.vertex.funnyspits.storage;

import org.bukkit.Location;

public class SpongeBlockHumidityValuesStorageColumn {
    private Location blockLocation;
    private int humidity;

    public SpongeBlockHumidityValuesStorageColumn
            (Location blockLocation, int humidity) {
        this.blockLocation = blockLocation;
        this.humidity = humidity;
    }

    public Location getBlockLocation() {
        return blockLocation;
    }

    public int getHumidity() {
        return humidity;
    }

    public void setHumidity(int humidity) {
        this.humidity = humidity;
    }
}
