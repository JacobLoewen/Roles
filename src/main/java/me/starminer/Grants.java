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

    public static EnumSet<Material> weaponMaterials = EnumSet.of(
            Material.BOW, Material.CROSSBOW, Material.TRIDENT,
            Material.SNOWBALL, Material.EGG, Material.FIRE_CHARGE,
            Material.SPLASH_POTION, Material.TIPPED_ARROW,
            Material.POTION, Material.LINGERING_POTION
    );

    public static EnumSet<Material> foodMaterials = EnumSet.of(
            Material.APPLE, Material.BAKED_POTATO, Material.BEETROOT,
            Material.BEETROOT_SOUP, Material.BREAD, Material.CAKE, Material.CARROT,
            Material.CARROT_ON_A_STICK, Material.CHORUS_FRUIT, Material.COOKED_BEEF,
            Material.COOKED_CHICKEN, Material.COOKED_COD, Material.COOKED_MUTTON,
            Material.COOKED_PORKCHOP, Material.COOKED_RABBIT, Material.COOKED_SALMON,
            Material.COOKIE, Material.DRIED_KELP, Material.ENCHANTED_GOLDEN_APPLE,
            Material.GOLDEN_APPLE, Material.GOLDEN_CARROT, Material.HONEY_BOTTLE,
            Material.MELON, Material.MUSHROOM_STEW, Material.POISONOUS_POTATO,
            Material.PORKCHOP, Material.POTATO, Material.PUMPKIN_PIE,
            Material.RABBIT, Material.RABBIT_STEW, Material.ROTTEN_FLESH,
            Material.SPIDER_EYE, Material.SUSPICIOUS_STEW, Material.SWEET_BERRIES,
            Material.TROPICAL_FISH, Material.COD, Material.SALMON,
            Material.MUTTON, Material.CHICKEN, Material.BEEF, Material.PUFFERFISH
    );

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
            Material.JUNGLE_DOOR, Material.ACACIA_DOOR, Material.DARK_OAK_DOOR, Material.BELL, Material.CHEST,
            Material.CHEST_MINECART, Material.TRAPPED_CHEST, Material.ENDER_CHEST,
            Material.ACACIA_BUTTON, Material.STONE_BUTTON, Material.OAK_BUTTON,
            Material.SPRUCE_BUTTON, Material.BIRCH_BUTTON, Material.JUNGLE_BUTTON,
            Material.DARK_OAK_BUTTON, Material.CRIMSON_BUTTON, Material.WARPED_BUTTON,
            Material.CARTOGRAPHY_TABLE, Material.BREWING_STAND, Material.ENCHANTING_TABLE,
            Material.BEACON, Material.DROPPER, Material.DISPENSER, Material.JUKEBOX,
            Material.NOTE_BLOCK, Material.LECTERN, Material.OAK_FENCE_GATE,
            Material.SPRUCE_FENCE_GATE, Material.BIRCH_FENCE_GATE, Material.JUNGLE_FENCE_GATE,
            Material.ACACIA_FENCE_GATE, Material.DARK_OAK_FENCE_GATE, Material.OAK_TRAPDOOR,
            Material.SPRUCE_TRAPDOOR, Material.BIRCH_TRAPDOOR, Material.JUNGLE_TRAPDOOR,
            Material.ACACIA_TRAPDOOR, Material.DARK_OAK_TRAPDOOR, Material.LEVER,
            Material.OAK_PRESSURE_PLATE, Material.SPRUCE_PRESSURE_PLATE, Material.BIRCH_PRESSURE_PLATE,
            Material.JUNGLE_PRESSURE_PLATE,Material.ACACIA_PRESSURE_PLATE, Material.DARK_OAK_PRESSURE_PLATE,
            Material.STONE_PRESSURE_PLATE, Material.HEAVY_WEIGHTED_PRESSURE_PLATE, Material.LIGHT_WEIGHTED_PRESSURE_PLATE,
            Material.DAYLIGHT_DETECTOR, Material.COMPARATOR, Material.REPEATER,
            Material.REDSTONE_TORCH, Material.REDSTONE_LAMP, Material.TARGET,
            Material.LOOM, Material.STONECUTTER, Material.SMITHING_TABLE
    );

    public static void swapRoles() { //Class only for timing (put methods with more detail in this one)
//        System.out.println("Test");
        roles.clear();
        Random random = new Random();
        List<Player> players = new ArrayList<>(Bukkit.getOnlinePlayers());

        for (int i = 0; i < 4; i++) {
            if (players.size() != 0) {
                Player p = players.get(random.nextInt(players.size()));

//                roles.put(p, Role.values()[i]);
                roles.put(p, Role.values()[random.nextInt(4)]); //TODO TEMPORARY
                //TODO: CLOSE THE INVENTORY OF CHESTS!

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
                if(e.getItem() != null && foodMaterials.contains(e.getItem().getType())){
                    p.sendMessage("FOOOOD!");
                }

                if(e.getItem() != null && weaponMaterials.contains(e.getItem().getType())){
                    p.sendMessage("WEAPONS!");
                    if(role != Role.FIGHT){
                        e.setCancelled(true);
                    }
                }

                p.sendMessage("Test1");
                if (craftMaterials.contains(blockMaterialCRAFT)) { //Only Crafter can right-click interactive blocks
                    p.sendMessage("Interactible Block"); //CRAFTING SOLO DONE Pt.1 (ALSO SNEAKING FOR BUILD)
                    if (role != Role.CRAFT && (!(role == Role.BUILD && p.isSneaking()))) {
                        e.setCancelled(true);
                    }
                }
                else if (role != Role.BUILD) { //BUILD SOLO DONE
                    e.setCancelled(true);
                }
            }
            else if(e.getAction() == Action.RIGHT_CLICK_AIR){
                if(e.getItem() != null && weaponMaterials.contains(e.getItem().getType())){
                    p.sendMessage("WEAPONS!");
                    if(role != Role.FIGHT){
                        e.setCancelled(true);
                    }
                }
            }
            else if (e.getAction() == Action.LEFT_CLICK_BLOCK) {
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