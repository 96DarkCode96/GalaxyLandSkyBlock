package me.darkcode.galaxylandskyblock.events;

import me.darkcode.galaxycore.GalaxyCoreSpigot;
import me.darkcode.galaxylandskyblock.Core;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.UUID;

public class EListener implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent e){
        Player player = e.getPlayer();
        UUID uuid = player.getUniqueId();
        String name = player.getName();
        Core.getUserManager().loadUser(uuid);
        Core.getLog().debug("User " + name + " joined the SkyBlock!");
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e){
        Player player = e.getPlayer();
        UUID uuid = player.getUniqueId();
        String name = player.getName();
        Core.getUserManager().unloadUser(uuid);
        Core.getLog().debug("User " + name + " quit the SkyBlock!");
    }

    @EventHandler
    public void onIslandCreatedEvent(IslandCreatedEvent event) {
        Core.getLog().info("User " + event.getUser().getName() + " created island! (Dim: " + event.getIsland().getIsDimension().name().substring(5) + "; x: " + event.getIsland().getMiddle().getX() + "; z: " + event.getIsland().getMiddle().getZ() + ";)");
        try {
            GalaxyCoreSpigot.getInstance().getStatistic("skyblock-islands", s -> {
                try {
                    GalaxyCoreSpigot.getInstance().setStatistic("skyblock-islands", "" + (Integer.parseInt(s) + 1));
                } catch (Exception ignored) {
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
