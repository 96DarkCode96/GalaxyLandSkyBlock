package me.darkcode.galaxylandskyblock.objects;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;

import java.util.UUID;

public class Island {

    private final UUID playerUUID;
    private final long id;
    private Location middle;
    private int achievement;
    private final IslandDimension isDimension;

    public Island(UUID playerUUID, long id, Location middle, IslandDimension isDimension, int achievement){
        this.playerUUID = playerUUID;
        this.id = id;
        this.isDimension = isDimension;
        this.middle = middle;
        this.achievement = achievement;
    }

    public UUID getPlayerUUID() {
        return playerUUID;
    }

    public long getId() {
        return id;
    }

    public void setMiddle(Location middle) {
        this.middle = middle;
    }

    public void setAchievement(int achievement) {
        this.achievement = achievement;
    }

    public Location getMiddle() {
        return middle;
    }

    public int getAchievement() {
        return achievement;
    }

    public IslandDimension getIsDimension() {
        return isDimension;
    }

    public void createISSpawn() {
        //TODO Generate Island Terrein
    }

    public Location getSpawnLocation() {
        Location location = middle.clone();
        location.add(0.5,0,0.5);
        double y = 256;
        for(double i = 0.0; i < 256.0; i-=1.0){
            Block b = new Location(location.getWorld(), location.getX(), i, location.getZ()).getBlock();
            Block b1 = new Location(location.getWorld(), location.getX(), i+1, location.getZ()).getBlock();
            Block b2 = new Location(location.getWorld(), location.getX(), i+2, location.getZ()).getBlock();
            if(!b.getType().equals(Material.AIR) && b1.getType().equals(Material.AIR) && b2.getType().equals(Material.AIR)){
                y = i+1;
                break;
            }
        }
        location.setY(y);
        return location;
    }
}
