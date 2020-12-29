package me.darkcode.galaxylandskyblock.objects;

import me.darkcode.galaxylandskyblock.Core;
import me.darkcode.galaxylandskyblock.managers.IslandManager;
import net.luckperms.api.LuckPerms;
import net.luckperms.api.LuckPermsProvider;
import net.luckperms.api.model.group.Group;
import net.luckperms.api.node.Node;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
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
            player.getInventory().setItem(8, new Item().setMaterial(Material.NETHER_STAR).setAmount(1).setDisplayName("§c§lMenu").setItemFlags(ItemFlag.values()).setLore(Arrays.asList("", "§2Right click to", "§2join to SkyBlock Hub!", "")).build());
            for(int i = 0; i < 100; i++){
                player.sendMessage("");
            }
            //TODO Start guide!
        }else{
            for(int i = 0; i < 100; i++){
                player.sendMessage("");
            }
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(Core.getUpdatesIS()));
            String line = null;
            try{
                while((line = bufferedReader.readLine()) != null){
                    player.sendMessage(line.replaceAll("&", "§"));
                }
            }catch(Exception ignored){}
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
