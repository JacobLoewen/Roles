package me.starminer;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Objects;

public final class Roles extends JavaPlugin {

    private static Roles instance;
    static boolean startRoles = false;
    private int taskId; // Used to store the task ID

    @Override
    public void onEnable() {
        // Plugin startup logic
        System.out.println("ROLES!!!");
//        Objects.requireNonNull(getCommand("start")).setExecutor(new Commands());
        new Grants(this);
        Commands commands = new Commands();
        Objects.requireNonNull(getCommand("start")).setExecutor(commands);
        Objects.requireNonNull(getCommand("begin")).setExecutor(commands);
        Objects.requireNonNull(getCommand("stop")).setExecutor(commands);
        Objects.requireNonNull(getCommand("halt")).setExecutor(commands);

        BukkitRunnable role = new Role();
        role.runTaskTimer(this, 0, 20 * 2 * 5);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public static class Role extends BukkitRunnable {
        @Override
        public void run() {
            if(startRoles) {
                Grants.swapRoles();
                // Put your task logic here
                // This code will run every 5 minutes
                // For example:
                Bukkit.broadcastMessage("Task executed!");
            }
        }
    }

//    public static Roles instanceClass(){
//        if (instance == null){
//            instance = new Roles();
//        }
//        return instance;
//    }
}
