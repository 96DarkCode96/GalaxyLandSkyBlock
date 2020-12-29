package me.darkcode.galaxylandskyblock.objects;

import me.darkcode.galaxylandskyblock.Core;
import me.darkcode.galaxylandskyblock.managers.IslandManager;
import net.luckperms.api.LuckPerms;
import net.luckperms.api.LuckPermsProvider;
import net.luckperms.api.model.group.Group;
import net.luckperms.api.node.Node;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.UUID;

public class User {

    private final UUID uuid;
    private final String name;
    private final Island island;
    private ArrayList<Long> coopIslandsIDs;

    public User(UUID uuid){
        Player player = Bukkit.getPlayer(uuid);
        this.uuid = uuid;
        assert player != null;
        this.name = player.getName();
        boolean b = Core.getIslandManager().loadIsland(this);
        island = Core.getIslandManager().getIsland(uuid);
        player.teleport(island.getSpawnLocation());
        if(b){
            //TODO Give items!
            //TODO Start guide!
        }else{
            //TODO Send updates!
        }
    }

    public void kick(String msg){
        Player player = Bukkit.getPlayer(getName());
        if(player != null){
            player.kickPlayer(msg.replaceAll("&", "§"));
        }
    }

    public UUID getUuid() {
        return uuid;
    }

    public String getName() {
        return name;
    }

    public Island getIsland() {
        return island;
    }

    public String getGroupName(){
        LuckPerms lp = LuckPermsProvider.get();
        net.luckperms.api.model.user.User user = lp.getUserManager().getUser(getUuid());
        assert user != null;
        return user.getPrimaryGroup();
    }

    public boolean hasPermission(String perm) {
        LuckPerms lp = LuckPermsProvider.get();
        net.luckperms.api.model.user.User user = lp.getUserManager().getUser(getUuid());
        Group group = lp.getGroupManager().getGroup(getGroupName());
        Node node = Node.builder(perm).value(true).build();
        assert user != null;
        if (user.getNodes().contains(node)) return true;
        assert group != null;
        return group.getNodes().contains(node);
    }

}
