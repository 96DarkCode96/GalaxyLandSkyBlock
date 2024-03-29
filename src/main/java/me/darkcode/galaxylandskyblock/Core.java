package me.darkcode.galaxylandskyblock;

import com.sk89q.worldedit.WorldEdit;
import me.darkcode.galaxylandskyblock.events.EListener;
import me.darkcode.galaxylandskyblock.managers.IslandManager;
import me.darkcode.galaxylandskyblock.managers.UserManager;
import me.darkcode.galaxylandskyblock.objects.User;
import me.darkcode.galaxylandskyblock.objects.VoidGenerator;
import me.darkcode.galaxylandskyblock.utils.Log;
import org.bukkit.Bukkit;
import org.bukkit.WorldCreator;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.Objects;

public final class Core extends JavaPlugin {

    private static Core instance;
    private static Log log;
    private static UserManager userManager;
    private static IslandManager islandManager;
    private static InputStream updatesIS;
    private static WorldEdit worldEdit;

    @Override
    public void onEnable() {
        log = new Log();
        log.info("Starting...");
        log.info("Loading instances...");
        instance = this;
        userManager = new UserManager();
        islandManager = new IslandManager();
        worldEdit = WorldEdit.getInstance();
        if(Bukkit.getServer().getPluginManager().getPlugin("WorldEdit") == null){
            log.error("This plugin needs WorldEdit to be installed on your server!");
        }
        log.info("Instances loaded!");
        log.info("Registering events...");
        Bukkit.getPluginManager().registerEvents(new EListener(), this);
        log.info("Events registered!");
        if (!new File("plugins/GalaxyLandSkyblock/").exists()) {
            new File("plugins/GalaxyLandSkyblock/").mkdir();
            log.info("Plugin dir created!");
        }
        InputStream in = this.getResource("config.yml");
        updatesIS = this.getResource("updates.txt");
        File cf = new File("plugins/GalaxyLandSkyblock/config.yml");
        if (!cf.exists()) {
            try {
                Files.copy(in, cf.toPath());
                log.info("Config created!");
            } catch (IOException ignored) {
            }
        }
        in = this.getResource("island.schem");
        File schematic = new File("plugins/GalaxyLandSkyblock/island.schem");
        if (!cf.exists()) {
            try {
                Files.copy(in, schematic.toPath());
                log.info("Schematic copied!");
            } catch (IOException ignored) {
            }
        }
        File df = new File("plugins/GalaxyLandSkyblock/data.yml");
        if (!df.exists()) {
            try {
                df.createNewFile();
                log.info("Data file created!");
            } catch (IOException ignored) {}
        }
        if(Bukkit.getWorld(Objects.requireNonNull(YamlConfiguration.loadConfiguration(new File("plugins/GalaxyLandSkyblock/config.yml")).getString("worldName"))) == null){
            WorldCreator wc = new WorldCreator(Objects.requireNonNull(YamlConfiguration.loadConfiguration(new File("plugins/GalaxyLandSkyblock/config.yml")).getString("worldName")));
            wc.generateStructures(false);
            wc.generator(new VoidGenerator());
            Bukkit.createWorld(wc);
        }
        log.info("Started!");
    }

    @Override
    public void onDisable() {
        for(User user : userManager.getUsersList()){
            user.kick("Sorry, but SkyBlock is currently offline!");
        }
        Bukkit.setWhitelist(true);
        Bukkit.getWhitelistedPlayers().clear();
    }

    public static WorldEdit getWorldEdit() {
        return worldEdit;
    }

    public static InputStream getUpdatesIS() {
        return updatesIS;
    }

    public static Log getLog() {
        return log;
    }

    public static Core getInstance() {
        return instance;
    }

    public static UserManager getUserManager() {
        return userManager;
    }

    public static IslandManager getIslandManager() {
        return islandManager;
    }
}
