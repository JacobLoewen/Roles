package me.starminer;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import static me.starminer.Grants.swapRoles;
import static me.starminer.Roles.startRoles;

public class Commands implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(sender instanceof Player){
            Player player = (Player) sender;

            if(cmd.getName().equalsIgnoreCase("start")){
                startRoles = true;
                player.sendMessage("READY PLAYER ONE?");
                swapRoles();
                System.out.println("Test2");
            }

            if(cmd.getName().equalsIgnoreCase("stop")){
                startRoles = false;
            }

        }
        return true;
    }
}
