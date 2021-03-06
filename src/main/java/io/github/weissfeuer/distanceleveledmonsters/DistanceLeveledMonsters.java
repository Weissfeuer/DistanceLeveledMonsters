
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
        // put config into server folder, if it does not already exist
        saveDefaultConfig();
    }

    @EventHandler
    // On Spawn: Scale Health
    public void onMonsterSpawn(EntitySpawnEvent event) {
        if (!(event.getEntity() instanceof Monster)) return;
        Monster mob = (Monster) event.getEntity();
        
        double monsterHealthScaling = 0.0;
        // get multiplier if monster configured
        if (getConfig().getInt(mob.getWorld().getName() + ".LevelDistance") != 0) {
	        if (mob instanceof Blaze)           monsterHealthScaling = (getConfig().getDouble(mob.getWorld().getName() + ".Scaling.Blaze.Health"));
	        if (mob instanceof Spider)          monsterHealthScaling = (getConfig().getDouble(mob.getWorld().getName() + ".Scaling.Spider.Health"));
	        if (mob instanceof CaveSpider)      monsterHealthScaling = (getConfig().getDouble(mob.getWorld().getName() + ".Scaling.CaveSpider.Health"));
	        if (mob instanceof Enderman)        monsterHealthScaling = (getConfig().getDouble(mob.getWorld().getName() + ".Scaling.Enderman.Health"));
	        if (mob instanceof Zombie)          monsterHealthScaling = (getConfig().getDouble(mob.getWorld().getName() + ".Scaling.Zombie.Health"));
	        if (mob instanceof PigZombie)       monsterHealthScaling = (getConfig().getDouble(mob.getWorld().getName() + ".Scaling.PigZombie.Health"));
	        if (mob instanceof ZombieVillager)  monsterHealthScaling = (getConfig().getDouble(mob.getWorld().getName() + ".Scaling.ZombieVillager.Health"));
	        if (mob instanceof Husk)            monsterHealthScaling = (getConfig().getDouble(mob.getWorld().getName() + ".Scaling.Husk.Health"));
	        if (mob instanceof Drowned)         monsterHealthScaling = (getConfig().getDouble(mob.getWorld().getName() + ".Scaling.Drowned.Health"));
	        if (mob instanceof Silverfish)      monsterHealthScaling = (getConfig().getDouble(mob.getWorld().getName() + ".Scaling.Silverfish.Health"));
	        if (mob instanceof Skeleton)        monsterHealthScaling = (getConfig().getDouble(mob.getWorld().getName() + ".Scaling.Skeleton.Health"));
	        if (mob instanceof WitherSkeleton)  monsterHealthScaling = (getConfig().getDouble(mob.getWorld().getName() + ".Scaling.WitherSkeleton.Health"));
	        if (mob instanceof Stray)           monsterHealthScaling = (getConfig().getDouble(mob.getWorld().getName() + ".Scaling.Stray.Health"));
	        if (mob instanceof Witch)           monsterHealthScaling = (getConfig().getDouble(mob.getWorld().getName() + ".Scaling.Witch.Health"));
	        if (mob instanceof Wither)          monsterHealthScaling = (getConfig().getDouble(mob.getWorld().getName() + ".Scaling.Wither.Health"));
	        if (mob instanceof Creeper)         monsterHealthScaling = (getConfig().getDouble(mob.getWorld().getName() + ".Scaling.Creeper.Health"));
	        if (mob instanceof Evoker)          monsterHealthScaling = (getConfig().getDouble(mob.getWorld().getName() + ".Scaling.Evoker.Health"));
	        if (mob instanceof Vindicator)      monsterHealthScaling = (getConfig().getDouble(mob.getWorld().getName() + ".Scaling.Vindicator.Health"));
	        if (mob instanceof Vex)             monsterHealthScaling = (getConfig().getDouble(mob.getWorld().getName() + ".Scaling.Vex.Health"));
	        if (mob instanceof Guardian)        monsterHealthScaling = (getConfig().getDouble(mob.getWorld().getName() + ".Scaling.Guardian.Health"));
	        if (mob instanceof ElderGuardian)   monsterHealthScaling = (getConfig().getDouble(mob.getWorld().getName() + ".Scaling.ElderGuardian.Health"));
        }
        
        if (monsterHealthScaling > 0.0) {
        	// LevelMultiplier = distance from spawn / Level per Blocks
        	double distance = mob.getLocation().distance(mob.getWorld().getSpawnLocation()) / (getConfig().getInt(mob.getWorld().getName() + ".LevelDistance"));
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
        // get multiplier if monster configured
        if (getConfig().getInt(mob.getWorld().getName() + ".LevelDistance") != 0) {
	        if (mob instanceof Blaze)           monsterAttackScaling = (getConfig().getDouble(mob.getWorld().getName() + ".Scaling.Blaze.Attack"));
	        if (mob instanceof Spider)          monsterAttackScaling = (getConfig().getDouble(mob.getWorld().getName() + ".Scaling.Spider.Attack"));
	        if (mob instanceof CaveSpider)      monsterAttackScaling = (getConfig().getDouble(mob.getWorld().getName() + ".Scaling.CaveSpider.Attack"));
	        if (mob instanceof Enderman)        monsterAttackScaling = (getConfig().getDouble(mob.getWorld().getName() + ".Scaling.Enderman.Attack"));
	        if (mob instanceof Zombie)          monsterAttackScaling = (getConfig().getDouble(mob.getWorld().getName() + ".Scaling.Zombie.Attack"));
	        if (mob instanceof PigZombie)       monsterAttackScaling = (getConfig().getDouble(mob.getWorld().getName() + ".Scaling.PigZombie.Attack"));
	        if (mob instanceof ZombieVillager)  monsterAttackScaling = (getConfig().getDouble(mob.getWorld().getName() + ".Scaling.ZombieVillager.Attack"));
	        if (mob instanceof Husk)            monsterAttackScaling = (getConfig().getDouble(mob.getWorld().getName() + ".Scaling.Husk.Attack"));
	        if (mob instanceof Drowned)         monsterAttackScaling = (getConfig().getDouble(mob.getWorld().getName() + ".Scaling.Drowned.Attack"));
	        if (mob instanceof Silverfish)      monsterAttackScaling = (getConfig().getDouble(mob.getWorld().getName() + ".Scaling.Silverfish.Attack"));
	        if (mob instanceof Skeleton)        monsterAttackScaling = (getConfig().getDouble(mob.getWorld().getName() + ".Scaling.Skeleton.Attack"));
	        if (mob instanceof WitherSkeleton)  monsterAttackScaling = (getConfig().getDouble(mob.getWorld().getName() + ".Scaling.WitherSkeleton.Attack"));
	        if (mob instanceof Stray)           monsterAttackScaling = (getConfig().getDouble(mob.getWorld().getName() + ".Scaling.Stray.Attack"));
	        if (mob instanceof Witch)           monsterAttackScaling = (getConfig().getDouble(mob.getWorld().getName() + ".Scaling.Witch.Attack"));
	        if (mob instanceof Wither)          monsterAttackScaling = (getConfig().getDouble(mob.getWorld().getName() + ".Scaling.Wither.Attack"));
	        if (mob instanceof Creeper)         monsterAttackScaling = (getConfig().getDouble(mob.getWorld().getName() + ".Scaling.Creeper.Attack"));
	        if (mob instanceof Evoker)          monsterAttackScaling = (getConfig().getDouble(mob.getWorld().getName() + ".Scaling.Evoker.Attack"));
	        if (mob instanceof Vindicator)      monsterAttackScaling = (getConfig().getDouble(mob.getWorld().getName() + ".Scaling.Vindicator.Attack"));
	        if (mob instanceof Vex)             monsterAttackScaling = (getConfig().getDouble(mob.getWorld().getName() + ".Scaling.Vex.Attack"));
	        if (mob instanceof Guardian)        monsterAttackScaling = (getConfig().getDouble(mob.getWorld().getName() + ".Scaling.Guardian.Attack"));
	        if (mob instanceof ElderGuardian)   monsterAttackScaling = (getConfig().getDouble(mob.getWorld().getName() + ".Scaling.ElderGuardian.Attack"));
        }
        
        if (monsterAttackScaling > 0.0) {
        	// LevelMultiplier = distance from spawn / Level per Blocks
        	double distance = mob.getLocation().distance(mob.getWorld().getSpawnLocation()) / (getConfig().getInt(mob.getWorld().getName() + ".LevelDistance"));
        	// Damage = Default Damage + ( LevelMultiplier * Attack Mulitplier )
        	event.setDamage(event.getDamage() + ( distance * monsterAttackScaling));
        }
    }

    @EventHandler
    // Scale EXP, Item-Drops
    public void monsterDeath(EntityDeathEvent event) {
        if (!(event.getEntity() instanceof Monster)) return;
        Monster mob = (Monster) event.getEntity();
        
        if (getConfig().getInt(mob.getWorld().getName() + ".LevelDistance") != 0) {
	        // LevelMultiplier = distance from spawn / Level per Blocks
	    	double distance = mob.getLocation().distance(mob.getWorld().getSpawnLocation()) / (getConfig().getInt(mob.getWorld().getName() + ".LevelDistance"));
	    	
	    	if (getConfig().getDouble("ExpMultiplier") > 0.0) {
	    		// EXP = Default EXP + ( Default EXP * LevelMultiplier * EXP Multiplier )
	    		event.setDroppedExp((int) (event.getDroppedExp() + ( event.getDroppedExp() * distance * (getConfig().getDouble(mob.getWorld().getName() + ".Scaling.ExpMultiplier")))));
	    	}
	        
	        if (getConfig().getDouble("DropMultiplier") > 0.0) {
	        	// for each item drop
	            for (ItemStack drop : event.getDrops()) {
	            	// don't increase unstackable items
	                if (drop.getMaxStackSize() == 1) continue;
	                // Drop = Default Drop + ( Default Drop * LevelMulitplier * Drop Multiplier )
	                drop.setAmount((int) (drop.getAmount() + ( drop.getAmount() * distance * (getConfig().getDouble(mob.getWorld().getName() + ".Scaling.DropMultiplier")))));
	            }
	        }
        }
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
    	// default command /DistanceLevel to display monster level around the player
    	if (cmd.getName().equalsIgnoreCase("DistanceLevel") && args.length == 0) {
    		if (sender instanceof Player) {
    			Player player = (Player) sender;
    			if (getConfig().getInt(player.getWorld().getName() + ".LevelDistance") != 0) {
    				sender.sendMessage("Current Monsterlevel: " + Math.round((int) player.getLocation().distance(player.getWorld().getSpawnLocation()) / (getConfig().getInt(player.getWorld().getName() + ".LevelDistance"))));
    			}
    			else {
    				sender.sendMessage("This world is not scaled");
    			}
    		} else {
    			sender.sendMessage("This command can only be run by a player.");
    		}
    		return true;
    	}
    	// manual coordinate option of the command /DistanceLevel
    	if (cmd.getName().equalsIgnoreCase("DistanceLevel") && args.length == 2) {
    		if (sender instanceof Player) {
    			Player player = (Player) sender;
    			if (getConfig().getInt(player.getWorld().getName() + ".LevelDistance") != 0) {
	    			try {
	    				double X = Double.parseDouble(args[0]);
	    				double Z = Double.parseDouble(args[1]);
	        			Location testLocation = new Location (player.getWorld(), X, Z, 0.0);
	        			sender.sendMessage("Monsterlevel at position " + X + " / " + Z + " : " + Math.round((int) testLocation.distance(player.getWorld().getSpawnLocation()) / (getConfig().getInt(player.getWorld().getName() + ".LevelDistance"))));
	        		} catch (NumberFormatException e) {
	        			return false;
	        		}
    			}
    			else {
    				sender.sendMessage("This world is not scaled");
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
    				double Z = Double.parseDouble(args[1]);
    				World world = sender.getServer().getWorld(args[2]);
    				if (world == null) {
    					sender.sendMessage("World does not exist");
    				}
    				else if (getConfig().getInt(world.getName() + ".LevelDistance") != 0) {
    					Location testLocation = new Location (world, X, Z, 0.0);
    					sender.sendMessage("Monsterlevel at position " + X + " / " + Z + " : " + Math.round((int) testLocation.distance(world.getSpawnLocation()) / (getConfig().getInt(world.getName() + ".LevelDistance"))));
    				}
    				else {
    					sender.sendMessage("World is not managed");
    					return true;
    				}
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