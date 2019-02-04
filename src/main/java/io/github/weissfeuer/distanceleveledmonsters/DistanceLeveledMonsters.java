
package io.github.weissfeuer.distanceleveledmonsters;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.attribute.Attribute;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Blaze;
import org.bukkit.entity.CaveSpider;
import org.bukkit.entity.Creeper;
import org.bukkit.entity.Drowned;
import org.bukkit.entity.ElderGuardian;
import org.bukkit.entity.Enderman;
import org.bukkit.entity.Evoker;
import org.bukkit.entity.Guardian;
import org.bukkit.entity.Husk;
import org.bukkit.entity.Monster;
import org.bukkit.entity.PigZombie;
import org.bukkit.entity.Player;
import org.bukkit.entity.Silverfish;
import org.bukkit.entity.Skeleton;
import org.bukkit.entity.Spider;
import org.bukkit.entity.Stray;
import org.bukkit.entity.Vex;
import org.bukkit.entity.Vindicator;
import org.bukkit.entity.Witch;
import org.bukkit.entity.Wither;
import org.bukkit.entity.WitherSkeleton;
import org.bukkit.entity.Zombie;
import org.bukkit.entity.ZombieVillager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntitySpawnEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

public class DistanceLeveledMonsters extends JavaPlugin implements Listener {

    @Override
    public void onEnable() {
        Bukkit.getPluginManager().registerEvents(this, this);
        loadConfig();
    }

    private void loadConfig() {
        if (getConfig().get("ResetToDefaults") == null || getConfig().getBoolean("ResetToDefaults") == true) {
        	getConfig().set("ResetToDefaults", false);

            getConfig().set("LevelDistance", 1000);				// Adds a level every X blocks, partial levels apply
            
            													// Set any to 0.0 to disable
            
            getConfig().set("ExpMultiplier", 0.05);				// Additional EXP per level, 1.0 meaning for each level you get the full EXP amount again
            getConfig().set("DropMultiplier", 0.05);				// Additional Drops per level, 1.0 meaning for each level you get the full Drop amount again
            													// only works for stackables, rounds down
            												
            getConfig().set("BlazeHealthScaling", 0.5);			// Adds 1 health every 1/x level - the calculation is a reverse, a higher number means more health each level
            getConfig().set("BlazeAttackScaling", 0.3);			// Same for damage
            getConfig().set("CaveSpiderHealthScaling", 0.25);	// set any to 0.0 to disable scaling
            getConfig().set("CaveSpiderAttackScaling", 0.1);
            getConfig().set("CreeperHealthScaling", 0.25);
            getConfig().set("CreeperAttackScaling", 0.75);
            getConfig().set("DrownedHealthScaling", 0.33);
            getConfig().set("DrownedAttackScaling", 0.33);
            getConfig().set("ElderGuardianHealthScaling", 0.15);
            getConfig().set("ElderGuardianAttackScaling", 0.15);
            getConfig().set("EndermanHealthScaling", 0.25);
            getConfig().set("EndermanAttackScaling", 0.5);
            // Endermite
            getConfig().set("EvokerHealthScaling", 0.1);
            getConfig().set("EvokerAttackScaling", 0.1);
            getConfig().set("GuardianHealthScaling", 0.25);
            getConfig().set("GuardianAttackScaling", 0.25);
            getConfig().set("HuskHealthScaling", 0.25);
            getConfig().set("HuskAttackScaling", 0.50);
            getConfig().set("PigZombieHealthScaling", 0.33);
            getConfig().set("PigZombieAttackScaling", 0.5);
            getConfig().set("SilverfishHealthScaling", 0.1);
            getConfig().set("SilverfishAttackScaling", 0.25);
            getConfig().set("SkeletonHealthScaling", 0.25);
            getConfig().set("SkeletonAttackScaling", 0.25);
            getConfig().set("SpiderHealthScaling", 0.33);
            getConfig().set("SpiderAttackScaling", 0.33);
            getConfig().set("StrayHealthScaling", 0.15);
            getConfig().set("StrayAttackScaling", 0.25);
            getConfig().set("VexHealthScaling", 0.0);
            getConfig().set("VexAttackScaling", 0.15);
            getConfig().set("VindicatorHealthScaling", 0.20);
            getConfig().set("VindicatorAttackScaling", 0.20);
            getConfig().set("WitchHealthScaling", 0.25);
            getConfig().set("WitchAttackScaling", 0.5);
            getConfig().set("WitherSkeletonHealthScaling", 0.15);
            getConfig().set("WitherSkeletonAttackScaling", 0.2);
            getConfig().set("WitherHealthScaling", 0.1);
            getConfig().set("WitherAttackScaling", 0.1);
            getConfig().set("ZombieHealthScaling", 0.33);
            getConfig().set("ZombieAttackScaling", 0.33); 
            getConfig().set("ZombieVillagerHealthScaling", 0.40);
            getConfig().set("ZombieVillagerAttackScaling", 0.50);
            
        } else getConfig().options().copyDefaults(true);
        saveConfig();
    }

    @EventHandler
    // On Spawn: Scale Health, Gear Zombies
    public void onMonsterSpawn(EntitySpawnEvent event) {
        if (!(event.getEntity() instanceof Monster)) return;
        Monster mob = (Monster) event.getEntity();
        
        double monsterHealthScaling = 0.0;
        if (mob instanceof Blaze) 			monsterHealthScaling = (getConfig().getDouble("BlazeHealthScaling"));
        if (mob instanceof Spider)			monsterHealthScaling = (getConfig().getDouble("SpiderHealthScaling"));
        if (mob instanceof CaveSpider)		monsterHealthScaling = (getConfig().getDouble("CaveSpiderHealthScaling"));
        if (mob instanceof Enderman) 		monsterHealthScaling = (getConfig().getDouble("EndermanHealthScaling"));
        if (mob instanceof Zombie) 			monsterHealthScaling = (getConfig().getDouble("ZombieHealthScaling"));
        if (mob instanceof PigZombie) 		monsterHealthScaling = (getConfig().getDouble("PigZombieHealthScaling"));
        if (mob instanceof ZombieVillager) 	monsterHealthScaling = (getConfig().getDouble("ZombieVillagerHealthScaling"));
        if (mob instanceof Husk) 			monsterHealthScaling = (getConfig().getDouble("HuskHealthScaling"));
        if (mob instanceof Drowned) 		monsterHealthScaling = (getConfig().getDouble("DrownedHealthScaling"));
        if (mob instanceof Silverfish) 		monsterHealthScaling = (getConfig().getDouble("SilverfishHealthScaling"));
        if (mob instanceof Skeleton) 		monsterHealthScaling = (getConfig().getDouble("SkeletonHealthScaling"));
        if (mob instanceof WitherSkeleton) 	monsterHealthScaling = (getConfig().getDouble("WitherSkeletonHealthScaling"));
        if (mob instanceof Stray) 			monsterHealthScaling = (getConfig().getDouble("StrayHealthScaling"));
        if (mob instanceof Witch) 			monsterHealthScaling = (getConfig().getDouble("WitchHealthScaling"));
        if (mob instanceof Wither) 			monsterHealthScaling = (getConfig().getDouble("WitherHealthScaling"));
        if (mob instanceof Creeper) 		monsterHealthScaling = (getConfig().getDouble("CreeperHealthScaling"));
        if (mob instanceof Evoker) 			monsterHealthScaling = (getConfig().getDouble("EvokerHealthScaling"));
        if (mob instanceof Vindicator) 		monsterHealthScaling = (getConfig().getDouble("VindicatorHealthScaling"));
        if (mob instanceof Vex) 			monsterHealthScaling = (getConfig().getDouble("VexHealthScaling"));
        if (mob instanceof Guardian) 		monsterHealthScaling = (getConfig().getDouble("GuardianHealthScaling"));
        if (mob instanceof ElderGuardian) 	monsterHealthScaling = (getConfig().getDouble("ElderGuardianHealthScaling"));
        	
        if (monsterHealthScaling > 0.0) {
        	// LevelMultiplier = distance from spawn / Level per Blocks
        	double distance = mob.getLocation().distance(mob.getWorld().getSpawnLocation()) / (getConfig().getInt("LevelDistance"));
        	// MaxHealth = Base Health + ( LevelMultiplier * HealthScalingMultiplier )
        	mob.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(mob.getHealth() + (distance  * monsterHealthScaling));
        	// Heal mob on spawn
        	mob.setHealth(mob.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue());
        }
    }

    @EventHandler
    // Scale Damage
    public void monsterDamagePlayer(EntityDamageByEntityEvent event) {
        if (!(event.getDamager() instanceof Monster)) return;
        Monster mob = (Monster) event.getDamager();
        
        double monsterAttackScaling = 0.0;
        if (mob instanceof Blaze) 			monsterAttackScaling = (getConfig().getDouble("BlazeAttackScaling"));
        if (mob instanceof Spider)			monsterAttackScaling = (getConfig().getDouble("SpiderAttackScaling"));
        if (mob instanceof CaveSpider)		monsterAttackScaling = (getConfig().getDouble("CaveSpiderAttackScaling"));
        if (mob instanceof Enderman) 		monsterAttackScaling = (getConfig().getDouble("EndermanAttackScaling"));
        if (mob instanceof Zombie) 			monsterAttackScaling = (getConfig().getDouble("ZombieAttackScaling"));
        if (mob instanceof PigZombie) 		monsterAttackScaling = (getConfig().getDouble("PigZombieAttackScaling"));
        if (mob instanceof ZombieVillager) 	monsterAttackScaling = (getConfig().getDouble("ZombieVillagerAttackScaling"));
        if (mob instanceof Husk) 			monsterAttackScaling = (getConfig().getDouble("HuskAttackScaling"));
        if (mob instanceof Drowned) 		monsterAttackScaling = (getConfig().getDouble("DrownedAttackScaling"));
        if (mob instanceof Silverfish) 		monsterAttackScaling = (getConfig().getDouble("SilverfishAttackScaling"));
        if (mob instanceof Skeleton) 		monsterAttackScaling = (getConfig().getDouble("SkeletonAttackScaling"));
        if (mob instanceof WitherSkeleton) 	monsterAttackScaling = (getConfig().getDouble("WitherSkeletonAttackScaling"));
        if (mob instanceof Stray) 			monsterAttackScaling = (getConfig().getDouble("StrayAttackScaling"));
        if (mob instanceof Witch) 			monsterAttackScaling = (getConfig().getDouble("WitchAttackScaling"));
        if (mob instanceof Wither) 			monsterAttackScaling = (getConfig().getDouble("WitherAttackScaling"));
        if (mob instanceof Creeper) 		monsterAttackScaling = (getConfig().getDouble("CreeperAttackScaling"));
        if (mob instanceof Evoker) 			monsterAttackScaling = (getConfig().getDouble("EvokerAttackScaling"));
        if (mob instanceof Vindicator) 		monsterAttackScaling = (getConfig().getDouble("VindicatorAttackScaling"));
        if (mob instanceof Vex) 			monsterAttackScaling = (getConfig().getDouble("VexAttackScaling"));
        if (mob instanceof Guardian) 		monsterAttackScaling = (getConfig().getDouble("GuardianAttackScaling"));
        if (mob instanceof ElderGuardian) 	monsterAttackScaling = (getConfig().getDouble("ElderGuardianAttackScaling"));
        
        if (monsterAttackScaling > 0.0) {
        	// LevelMultiplier = distance from spawn / Level per Blocks
        	double distance = mob.getLocation().distance(mob.getWorld().getSpawnLocation()) / (getConfig().getInt("LevelDistance"));
        	// Damage = Default Damage + ( LevelMultiplier * Attack Mulitplier )
        	event.setDamage(event.getDamage() + ( distance * monsterAttackScaling));
        }
    }

    @EventHandler
    // Scale EXP, Item-Drops
    public void monsterDeath(EntityDeathEvent event) {
        if (!(event.getEntity() instanceof Monster)) return;
        Monster mob = (Monster) event.getEntity();
        
        // LevelMultiplier = distance from spawn / Level per Blocks
    	double distance = mob.getLocation().distance(mob.getWorld().getSpawnLocation()) / (getConfig().getInt("LevelDistance"));
    	
    	if (getConfig().getDouble("ExpMultiplier") > 0.0) {
    		// EXP = Default EXP + ( Default EXP * LevelMultiplier * EXP Multiplier )
    		event.setDroppedExp((int) (event.getDroppedExp() + ( event.getDroppedExp() * distance * (getConfig().getDouble("ExpMultiplier")))));
    	}
        
        if (getConfig().getDouble("DropMultiplier") > 0.0) {
        	// for each item drop
            for (ItemStack drop : event.getDrops()) {
            	// don't increase unstackable items
                if (drop.getMaxStackSize() == 1) continue;
                // Drop = Default Drop + ( Default Drop * LevelMulitplier * Drop Multiplier )
                drop.setAmount((int) (drop.getAmount() + ( drop.getAmount() * distance * (getConfig().getDouble("DropMultiplier")))));
            }
        }
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
    	// default command /DistanceLevel to display monster level around the player
    	if (cmd.getName().equalsIgnoreCase("DistanceLevel") && args.length == 0) {
    		if (sender instanceof Player) {
    			Player player = (Player) sender;
    			sender.sendMessage("Current Monsterlevel: " + Math.round((int) player.getLocation().distance(player.getWorld().getSpawnLocation()) / (getConfig().getInt("LevelDistance"))));
    		} else {
    			sender.sendMessage("This command can only be run by a player.");
    		}
    		return true;
    	}
    	// manual coordinate option of the command /DistanceLevel
    	if (cmd.getName().equalsIgnoreCase("DistanceLevel") && args.length == 2) {
    		if (sender instanceof Player) {
    			Player player = (Player) sender;
    			try {
    				double X = Double.parseDouble(args[0]);
    				double Y = Double.parseDouble(args[1]);
        			Location testLocation = new Location (player.getWorld(), X, Y, 0.0);
        			sender.sendMessage("Monsterlevel at position " + X + " / " + Y + " : " + Math.round((int) testLocation.distance(player.getWorld().getSpawnLocation()) / (getConfig().getInt("LevelDistance"))));
        		} catch (NumberFormatException e) {
        			return false;
        		}
    		}
    		else {
    			sender.sendMessage("This command can only be run by a player.");
    		}
    		return true;
    	}
    	// manual coordinate option of the command /DistanceLevel + manual world
    	if (cmd.getName().equalsIgnoreCase("DistanceLevel") && args.length == 3) {
    		if (sender instanceof ConsoleCommandSender) {
    			try {
    				double X = Double.parseDouble(args[0]);
    				double Y = Double.parseDouble(args[1]);
    				World world = sender.getServer().getWorld(args[2]);
    				if (world == null) {
    					sender.sendMessage("World does not exist");
    					return true;
    				}
        			Location testLocation = new Location (world, X, Y, 0.0);
        			sender.sendMessage("Monsterlevel at position " + X + " / " + Y + " : " + Math.round((int) testLocation.distance(world.getSpawnLocation()) / (getConfig().getInt("LevelDistance"))));
        		} catch (NumberFormatException e) {
        			return false;
        		}
    		}
    		else {
    			return false;
    		}
    		return true;
    	}
    	else {
    		return false;
    	}
    }
}