package me.starminer;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerGameModeChangeEvent;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static me.starminer.Roles.startRoles;

public class Grants implements Listener {

    public static List<Player> roles = new ArrayList<>(Bukkit.getOnlinePlayers());

    public Grants(Roles plugin){
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    //BUILD, CRAFT, FIGHT, BREAK

//    public enum PlayerType {
//        BUILD,
//        CRAFT,
//        FIGHT,
//        BREAK,
//        DEFAULT
//    }

    public static void swapRoles() { //Class only for timing (put methods with more detail in this one)
        System.out.println("Test");
        roles.clear();
        Random random = new Random();
        List<Player> players = new ArrayList<>(Bukkit.getOnlinePlayers());

        for (int i = 0; i < 4; i++) {
            if (players.size() != 0) {
                Player p = players.get(random.nextInt(players.size()));

                //Make this player enum type BUILD
//                    p.setMetadata("playerType", new FixedMetadataValue(Roles.instanceClass(), PlayerType.values()[i]));

                roles.add(p);
                players.remove(p);
                onDamage(p);
                System.out.println("Number of players: " + players.size());
            }
        }
    }


    public static void onDamage(Player p) { //for testing, want to detect if specific player is type (enum)

        int index = roles.indexOf(p);
        if(index == 0) {
            p.sendMessage("BUILD");
        }

        if(index == 1) {
            p.sendMessage("CRAFT");
        }

        if(index == 2) {
            p.sendMessage("FIGHT");
        }

        if(index == 3) {
            p.sendMessage("BREAK");
        }
    }

//    public static PlayerType checkType(Player p){
//        for(MetadataValue metadata : p.getMetadata("playerType")){
//            if (metadata.value() instanceof PlayerType){
//                return (PlayerType) metadata.value();
//            }
//        }
//        return PlayerType.DEFAULT;
//    }
}
