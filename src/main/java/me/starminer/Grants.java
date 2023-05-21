package me.starminer;

import net.md_5.bungee.api.chat.ClickEvent;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.*;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.Plugin;

import java.util.*;

import static me.starminer.Roles.startRoles;
import static org.bukkit.Bukkit.getServer;

public class Grants implements Listener {

    private Plugin plugin;

    public Grants(Plugin plugin){
        getServer().getPluginManager().registerEvents(this, plugin);
    }

    public static Map<Player, Role> roles = new HashMap<>();

    //BUILD, CRAFT, FIGHT, BREAK

    //MAKE ENUM
//    private static final int BUILD = 0;
//    private static final int CRAFT = 1;
//    private static final int FIGHT = 2;
//    private static final int BREAK = 3;


    public enum Role {
        BUILD,
        CRAFT,
        FIGHT,
        BREAK
    }

//    public enum BlockMaterial {
//        CRAFTING_TABLE,
//        FURNACE,
//        BLAST_FURNACE,
//        SMOKER,
//        ANVIL,
//        CHIPPED_ANVIL,
//        DAMAGED_ANVIL,
//        BARREL,
//        SHULKER_BOX,
//        WHITE_SHULKER_BOX,
//        ORANGE_SHULKER_BOX,
//        MAGENTA_SHULKER_BOX,
//        LIGHT_BLUE_SHULKER_BOX,
//        YELLOW_SHULKER_BOX,
//        LIME_SHULKER_BOX,
//        PINK_SHULKER_BOX,
//        GRAY_SHULKER_BOX,
//        LIGHT_GRAY_SHULKER_BOX,
//        CYAN_SHULKER_BOX,
//        PURPLE_SHULKER_BOX,
//        BLUE_SHULKER_BOX,
//        BROWN_SHULKER_BOX,
//        GREEN_SHULKER_BOX,
//        RED_SHULKER_BOX,
//        BLACK_SHULKER_BOX,
//        HOPPER,
//        OAK_DOOR,
//        SPRUCE_DOOR,
//        BIRCH_DOOR,
//        JUNGLE_DOOR,
//        ACACIA_DOOR,
//        DARK_OAK_DOOR
//    }

    public static EnumSet<Material> craftMaterials = EnumSet.of(
            Material.CRAFTING_TABLE, Material.FURNACE, Material.BLAST_FURNACE, Material.SMOKER,
            Material.ANVIL, Material.CHIPPED_ANVIL, Material.DAMAGED_ANVIL, Material.BARREL,
            Material.SHULKER_BOX, Material.WHITE_SHULKER_BOX, Material.ORANGE_SHULKER_BOX,
            Material.MAGENTA_SHULKER_BOX, Material.LIGHT_BLUE_SHULKER_BOX, Material.YELLOW_SHULKER_BOX,
            Material.LIME_SHULKER_BOX, Material.PINK_SHULKER_BOX, Material.GRAY_SHULKER_BOX,
            Material.LIGHT_GRAY_SHULKER_BOX, Material.CYAN_SHULKER_BOX, Material.PURPLE_SHULKER_BOX,
            Material.BLUE_SHULKER_BOX, Material.BROWN_SHULKER_BOX, Material.GREEN_SHULKER_BOX,
            Material.RED_SHULKER_BOX, Material.BLACK_SHULKER_BOX, Material.HOPPER,
            Material.OAK_DOOR, Material.SPRUCE_DOOR, Material.BIRCH_DOOR,
            Material.JUNGLE_DOOR, Material.ACACIA_DOOR, Material.DARK_OAK_DOOR, Material.BELL
    );

    public static void swapRoles() { //Class only for timing (put methods with more detail in this one)
//        System.out.println("Test");
        roles.clear();
        Random random = new Random();
        List<Player> players = new ArrayList<>(Bukkit.getOnlinePlayers());

        for (int i = 0; i < 4; i++) {
            if (players.size() != 0) {
                Player p = players.get(random.nextInt(players.size()));

                //Make this player enum type BUILD
//                    p.setMetadata("playerType", new FixedMetadataValue(Roles.instanceClass(), PlayerType.values()[i]));

                roles.put(p, Role.values()[i]);
                players.remove(p);
                onDamage(p);
                System.out.println("Number of players: " + players.size());


            }
        }
    }


    public static void onDamage(Player p) { //for testing, want to detect if specific player is type (enum)

        Role role = roles.get(p);
        if (role == Role.BUILD) {
            p.sendMessage("BUILD"); // THIS IS BY DEFAULT
        }

        if (role == Role.CRAFT) {
            p.sendMessage("CRAFT");
        }

        if (role == Role.FIGHT) {
            p.sendMessage("FIGHT");
        }

        if (role == Role.BREAK) {
            p.sendMessage("BREAK");
        }
    }



    //SPECIFIC

    @EventHandler(priority = EventPriority.HIGHEST)
    public static void playerPlaceBlock(BlockPlaceEvent e){
        if(startRoles) {
            if (roles.get(e.getPlayer()) != Role.BUILD) {
                e.setCancelled(true);
            }
        }
    }

    @EventHandler
    public static void playerItemInteractions(PlayerInteractEvent e){
        if(startRoles) {
            Player p = e.getPlayer();
            Role role = roles.get(p);


            if (e.getAction() == Action.RIGHT_CLICK_BLOCK) { //BUILD
                p.sendMessage("Right-Clicked");
                Material blockMaterialCRAFT = Objects.requireNonNull(e.getClickedBlock()).getType();

//            for(BlockMaterial craftMaterial : BlockMaterial.values()) {
                //if (blockMaterialCRAFT == Material.valueOf(craftMaterial.name())) { //MAKE ENUM

                if (craftMaterials.contains(blockMaterialCRAFT)) { //Only Crafter can right-click interactive blocks
                    p.sendMessage("Interactible Block"); //CRAFTING SOLO DONE Pt.1 (ALSO SNEAKING FOR BUILD)
                    if (role != Role.CRAFT && (!(role == Role.BUILD && p.isSneaking()))) {
                        e.setCancelled(true);
                    }
                } else if (role != Role.BUILD) { //BUILD SOLO DONE
                    e.setCancelled(true);
                }
            } else if (e.getAction() == Action.LEFT_CLICK_BLOCK) {
                if (role != Role.BREAK) {
                    e.setCancelled(true); //BREAK SOLO DONE
                }
            }
        }
    }

    @EventHandler
    public static void playerLeftClickMob(EntityDamageByEntityEvent e){
        if(startRoles) {
            if (e.getDamager() instanceof Player) {
                Player p = (Player) e.getDamager();
                Role role = roles.get(p);
                if (role != Role.FIGHT) {
                    e.setCancelled(true); //FIGHT SOLO DONE
                }
            }
        }
    }

    @EventHandler
    public static void playerRightClickMob(PlayerInteractEntityEvent e){
        if(startRoles) {
            Player p = e.getPlayer();

            p.sendMessage("Mob interaction!");

            Role role = roles.get(p);
            EntityType entityType = e.getRightClicked().getType();

            if (entityType == EntityType.VILLAGER || entityType == EntityType.HORSE
                    || entityType == EntityType.DONKEY || entityType == EntityType.MULE
                    || entityType == EntityType.LLAMA || entityType == EntityType.CAT
                    || entityType == EntityType.WOLF || entityType == EntityType.PARROT
                    || entityType == EntityType.IRON_GOLEM || entityType == EntityType.SNOWMAN
                    || entityType == EntityType.TURTLE) {
                if (role != Role.CRAFT) { // CRAFT SOLO DONE Pt.2
                    e.setCancelled(true);
                }
            } else if (role != Role.FIGHT) { // FIGHT SOLO DONE
                p.sendMessage("Mob interaction!");

                e.setCancelled(true);
            }
            p.sendMessage("Mob interaction!");
        }

    }



//ALL PLAYERS (OTHER)

    @EventHandler
    public static void preventSleep(PlayerBedEnterEvent e){
        if(startRoles) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public static void playerShearInteraction(PlayerShearEntityEvent e){
        if(startRoles) {
            Player p = e.getPlayer();
            Role role = roles.get(p);
            if (role != Role.BREAK) { //Only BREAK can shear sheep
                e.setCancelled(true);
            }
        }
    }

}