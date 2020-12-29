package me.darkcode.galaxylandskyblock.objects;

import com.sk89q.worldedit.EditSession;
import com.sk89q.worldedit.LocalSession;
import com.sk89q.worldedit.WorldEdit;
import com.sk89q.worldedit.WorldEditException;
import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldedit.extent.clipboard.Clipboard;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardFormat;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardFormats;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardReader;
import com.sk89q.worldedit.function.operation.Operation;
import com.sk89q.worldedit.function.operation.Operations;
import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldedit.session.ClipboardHolder;
import com.sk89q.worldedit.session.SessionOwner;
import me.darkcode.galaxylandskyblock.Core;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;

import java.io.File;
import java.io.FileInputStream;
import java.util.Objects;
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
        try{
            File file = new File("plugins/GalaxyLandSkyblock/island.schem");
            ClipboardFormat format = ClipboardFormats.findByFile(file);
            assert format != null;
            ClipboardReader reader = format.getReader(new FileInputStream(file));

            Clipboard clipboard = reader.read();
            com.sk89q.worldedit.world.World adaptedWorld = BukkitAdapter.adapt(Objects.requireNonNull(getSpawnLocation().getWorld()));
            EditSession editSession = WorldEdit.getInstance().getEditSessionFactory().getEditSession(adaptedWorld, -1);

            Operation operation = new ClipboardHolder(clipboard).createPaste(editSession)
                    .to(BlockVector3.at(getSpawnLocation().getBlockX(), 74, getSpawnLocation().getBlockZ())).ignoreAirBlocks(false).build();

            Operations.complete(operation);
            editSession.flushSession();
        }catch(Exception ignored){}
        //TODO Spawn Guide Entity
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
