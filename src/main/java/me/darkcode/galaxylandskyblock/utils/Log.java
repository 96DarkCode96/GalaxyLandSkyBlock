package me.darkcode.galaxylandskyblock.utils;

import org.bukkit.Bukkit;

public class Log {
    public void info(String msg){ Bukkit.getConsoleSender().sendMessage("[GLSkyBlock] » §f" + msg.replaceAll("&", "§")); }
    public void warn(String msg){ Bukkit.getConsoleSender().sendMessage("[GLSkyBlock] » §e" + msg.replaceAll("&", "§")); }
    public void debug(String msg){ Bukkit.getConsoleSender().sendMessage("[GLSkyBlock] » §2" + msg.replaceAll("&", "§")); }
    public void error(String msg){ Bukkit.getConsoleSender().sendMessage("[GLSkyBlock] » §4" + msg.replaceAll("&", "§")); }
}
