
package io.github.weissfeuer.DistanceLeveledMonsters;

import org.bukkit.Bukkit;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntitySpawnEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

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
            
            getConfig().set("ExpMultiplier", 0.1);				// Additional EXP per level, 1.0 meaning for each level you get the full EXP amount again
            getConfig().set("DropMultiplier", 0.1);				// Additional Drops per level, 1.0 meaning for each level you get the full Drop amount again
            													// only works for stackables, rounds down
            												
            getConfig().set("BlazeHealthScaling", 1.0);			// Adds 1 health every 1/x level - the calculation is a reverse, a higher number means more health each level
            getConfig().set("BlazeAttackScaling", 1.0);			// Same for damage
            getConfig().set("CaveSpiderHealthScaling", 1.0);	// set any to 0.0 to disable scaling
            getConfig().set("CaveSpiderAttackScaling", 1.0);
            getConfig().set("CreeperHealthScaling", 1.0);
            getConfig().set("CreeperAttackScaling", 1.0);
            getConfig().set("EndermanHealthScaling", 1.0);
            getConfig().set("EndermanAttackScaling", 1.0);
            getConfig().set("PigZombieHealthScaling", 1.0);
            getConfig().set("PigZombieAttackScaling", 1.0);
            getConfig().set("SilverfishHealthScaling", 1.0);
            getConfig().set("SilverfishAttackScaling", 1.0);
            getConfig().set("SkeletonHealthScaling", 1.0);
            getConfig().set("SkeletonAttackScaling", 1.0);
            getConfig().set("SpiderHealthScaling", 1.0);
            getConfig().set("SpiderAttackScaling", 1.0);
            getConfig().set("WitchHealthScaling", 1.0);
            getConfig().set("WitchAttackScaling", 1.0);
            getConfig().set("WitherHealthScaling", 1.0);
            getConfig().set("WitherAttackScaling", 1.0);
            getConfig().set("ZombieHealthScaling", 1.0);
            getConfig().set("ZombieAttackScaling", 1.0); 
            
        } else getConfig().options().copyDefaults(true);
        saveConfig();
    }

    @EventHandler
    // On Spawn: Scale Health, Gear Zombies
    public void onMonsterSpawn(EntitySpawnEvent event) {
        if (!(event.getEntity() instanceof Monster)) return;
        Monster mob = (Monster) event.getEntity();
        
        double monsterHealthScaling = 1.0;
        if (mob instanceof Blaze) 		monsterHealthScaling = (getConfig().getDouble("BlazeHealthScaling"));
        if (mob instanceof Spider)		monsterHealthScaling = (getConfig().getDouble("SpiderHealthScaling"));
        if (mob instanceof CaveSpider)	monsterHealthScaling = (getConfig().getDouble("CaveSpiderHealthScaling"));
        if (mob instanceof Enderman) 	monsterHealthScaling = (getConfig().getDouble("EndermanHealthScaling"));
        if (mob instanceof Zombie) 		monsterHealthScaling = (getConfig().getDouble("ZombieHealthScaling"));
        if (mob instanceof PigZombie) 	monsterHealthScaling = (getConfig().getDouble("PigZombieHealthScaling"));
        if (mob instanceof Silverfish) 	monsterHealthScaling = (getConfig().getDouble("SilverfishHealthScaling"));
        if (mob instanceof Skeleton) 	monsterHealthScaling = (getConfig().getDouble("SkeletonHealthScaling"));
        if (mob instanceof Witch) 		monsterHealthScaling = (getConfig().getDouble("WitchHealthScaling"));
        if (mob instanceof Wither) 		monsterHealthScaling = (getConfig().getDouble("WitherHealthScaling"));
        if (mob instanceof Creeper) 	monsterHealthScaling = (getConfig().getDouble("CreeperHealthScaling"));
        	
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
        
        double monsterAttackScaling = 1.0;
        if (mob instanceof Blaze) 		monsterAttackScaling = (getConfig().getDouble("BlazeAttackScaling"));
        if (mob instanceof Spider)		monsterAttackScaling = (getConfig().getDouble("SpiderAttackScaling"));
        if (mob instanceof CaveSpider)	monsterAttackScaling = (getConfig().getDouble("CaveSpiderAttackScaling"));
        if (mob instanceof Enderman) 	monsterAttackScaling = (getConfig().getDouble("EndermanAttackScaling"));
        if (mob instanceof Zombie) 		monsterAttackScaling = (getConfig().getDouble("ZombieAttackScaling"));
        if (mob instanceof PigZombie) 	monsterAttackScaling = (getConfig().getDouble("PigZombieAttackScaling"));
        if (mob instanceof Silverfish) 	monsterAttackScaling = (getConfig().getDouble("SilverfishAttackScaling"));
        if (mob instanceof Skeleton) 	monsterAttackScaling = (getConfig().getDouble("SkeletonAttackScaling"));
        if (mob instanceof Witch) 		monsterAttackScaling = (getConfig().getDouble("WitchAttackScaling"));
        if (mob instanceof Wither) 		monsterAttackScaling = (getConfig().getDouble("WitherAttackScaling"));
        if (mob instanceof Creeper) 	monsterAttackScaling = (getConfig().getDouble("CreeperAttackScaling"));
        
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
    	if (cmd.getName().equalsIgnoreCase("DistanceLevel") && args.length == 0) {
    		if (!(sender instanceof Player)) {
    			sender.sendMessage("This command can only be run by a player.");
    		} else {
    			Player player = (Player) sender;
    			sender.sendMessage("Current Monsterlevel: " + Math.round((int) player.getLocation().distance(player.getWorld().getSpawnLocation()) / (getConfig().getInt("LevelDistance"))));
    		}
    		return true;
    	}
    	else {
    		return false;
    	}
    }
}