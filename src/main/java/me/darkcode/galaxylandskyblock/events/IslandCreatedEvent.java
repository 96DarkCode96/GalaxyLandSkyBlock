package me.darkcode.galaxylandskyblock.events;

import me.darkcode.galaxylandskyblock.objects.Island;
import me.darkcode.galaxylandskyblock.objects.User;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class IslandCreatedEvent extends Event {

    private static final HandlerList HANDLERS = new HandlerList();
    private final User user;
    private final Island island;

    public IslandCreatedEvent(User user, Island island){
        this.user = user;
        this.island = island;
    }

    public User getUser() {
        return user;
    }

    public Island getIsland() {
        return island;
    }

    public HandlerList getHandlers() {
        return HANDLERS;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS;
    }

}
