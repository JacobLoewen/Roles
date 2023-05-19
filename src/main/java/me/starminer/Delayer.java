package me.starminer;

import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;

public class Delayer implements Listener {
    private static Plugin plugin = null;
    private int id = -1;

    public Delayer(Plugin instance){
        plugin = instance;
    }

    public Delayer(Runnable runnable){
        this(runnable, 0);
    }

    public Delayer(Runnable runnable, long delay){
        if(plugin.isEnabled()) {
            id = Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, runnable, delay);
        }
        else{
            runnable.run();
        }
    }
}