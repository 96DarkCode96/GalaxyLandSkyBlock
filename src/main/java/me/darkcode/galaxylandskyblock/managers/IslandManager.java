package me.darkcode.galaxylandskyblock.managers;

import me.darkcode.galaxylandskyblock.events.IslandCreatedEvent;
import me.darkcode.galaxylandskyblock.objects.Island;
import me.darkcode.galaxylandskyblock.objects.IslandDimension;
import me.darkcode.galaxylandskyblock.objects.User;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;
import java.util.UUID;

public class IslandManager {

    private final ArrayList<Island> listOfIslands;

    public IslandManager(){
        listOfIslands = new ArrayList<>();
    }

    /**
     *
     * @param user User of island
     * @return Returns true if created new island;
     */
    public boolean loadIsland(User user) {
        YamlConfiguration config = YamlConfiguration.loadConfiguration(new File("plugins/GalaxyLandSkyblock/data.yml"));
        if(!config.getBoolean(user.getUuid() + ".hasIsland")){
            Player p = Bukkit.getPlayer(user.getUuid());
            for(int i = 0; i < 100; i++){
                p.sendMessage("");
            }
            p.sendMessage("§2Creating island...");
            IslandDimension isDimension = getDimension(user);
            long id = generateId();
            Location middle = new Location(Bukkit.getWorld(Objects.requireNonNull(YamlConfiguration.loadConfiguration(new File("plugins/GalaxyLandSkyblock/config.yml")).getString("worldName"))),
                    getLastXOfDimension(isDimension), 0, getZOfDimension(isDimension));
            Island island = new Island(user.getUuid(), id, middle, isDimension,1);
            island.createISSpawn();
            listOfIslands.add(island);
            config.set(user.getUuid() + ".hasIsland", true);
            config.set(user.getUuid() + ".islandId", id);
            config.set("islands." + id + ".middle.x", middle.getX());
            config.set("islands." + id + ".middle.z", middle.getZ());
            config.set("islands." + id + ".dimension", isDimension.name());
            config.set("islands." + id + ".achievements", 1);
            try { config.save(new File("plugins/GalaxyLandSkyblock/data.yml")); } catch (IOException ignored) {}
            Bukkit.getPluginManager().callEvent(new IslandCreatedEvent(user, island));
            return true;
        }else{
            long id = config.getLong(user.getUuid() + ".islandId");
            IslandDimension isDimension = IslandDimension.valueOf(config.getString("islands." + id + ".dimension"));
            Location middle = new Location(Bukkit.getWorld(Objects.requireNonNull(YamlConfiguration.loadConfiguration(new File("plugins/GalaxyLandSkyblock/config.yml")).getString("worldName"))),
                    config.getDouble("islands." + id + ".middle.x"), 0, config.getDouble("islands." + id + ".middle.z"));
            Island island = new Island(user.getUuid(), id, middle, isDimension,config.getInt("islands." + id + ".achievements"));
            listOfIslands.add(island);
            return false;
        }
    }

    private double getZOfDimension(IslandDimension isDimension) {
        if(isDimension.equals(IslandDimension.D150x150)){
            return 75.0;
        }else if(isDimension.equals(IslandDimension.D250x250)){
            return 375.0;
        }else if(isDimension.equals(IslandDimension.D300x300)){
            return 750.0;
        }else if(isDimension.equals(IslandDimension.D400x400)){
            return 1200.0;
        }else{
            return 75.0;
        }
    }

    private long generateId() {
        StringBuilder generatedString = new StringBuilder();
        for(int i = 0; i < 10; i++){
            generatedString.append(new Random().nextInt(10));
        }
        YamlConfiguration config = YamlConfiguration.loadConfiguration(new File("plugins/GalaxyLandSkyblock/data.yml"));
        if(config.getString("islands." + generatedString.toString() + ".dimension") != null){
            return generateId();
        }
        return Long.parseLong(generatedString.toString());
    }

    public ArrayList<Island> getListOfIslands() {
        return listOfIslands;
    }

    public Island getIsland(UUID uuid){
        for(Island island : getListOfIslands()){
            if(island.getPlayerUUID().equals(uuid)){
                return island;
            }
        }
        return null;
    }

    private double getLastXOfDimension(IslandDimension isDimension){
        double xa = Double.parseDouble(isDimension.name().substring(5));
        double x = 75.0-xa-100;
        YamlConfiguration config = YamlConfiguration.loadConfiguration(new File("plugins/GalaxyLandSkyblock/data.yml"));
        if(config.getConfigurationSection("islands") == null){
            return x;
        }
        for(String key : Objects.requireNonNull(config.getConfigurationSection("islands")).getKeys(false)){
            if(Objects.requireNonNull(config.getString("islands." + key + ".dimension")).equalsIgnoreCase(isDimension.name())){
                if(config.getDouble("islands." + key + ".middle.x") > x){
                    x = config.getDouble("islands." + key + ".middle.x");
                }
            }
        }
        return x+100+xa;
    }

    private IslandDimension getDimension(User user){
        YamlConfiguration config = YamlConfiguration.loadConfiguration(new File("plugins/GalaxyLandSkyblock/config.yml"));
        if(user.hasPermission(config.getString("permissions.island.dimension400"))){
            return IslandDimension.D400x400;
        }else if(user.hasPermission(config.getString("permissions.island.dimension300"))){
            return IslandDimension.D300x300;
        }else if(user.hasPermission(config.getString("permissions.island.dimension250"))){
            return IslandDimension.D250x250;
        }else{
            return IslandDimension.D150x150;
        }
    }

}
