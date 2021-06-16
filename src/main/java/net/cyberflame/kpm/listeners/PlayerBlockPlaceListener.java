package net.cyberflame.kpm.listeners;

import net.cyberflame.kpm.KPM;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.Effect;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

public class PlayerBlockPlaceListener implements Listener
{

    private KPM plugin;

    public PlayerBlockPlaceListener(KPM plugin)
    {
        this.plugin = plugin;
    }

    @EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
    public void onBlockPlace(BlockPlaceEvent e)
    {
        //Get the block placed, who placed it and the world,
        Player player = e.getPlayer();
        Block block = e.getBlock();
        World world = block.getWorld();
        Location location = e.getBlockPlaced().getLocation();
        Material blockMaterial = e.getBlockPlaced().getType();
        //check if player has buildmode enabled,
        if (plugin.getBuildEnabled(player.getUniqueId()))
            {
                return;
            }
        //else check if it was in one of the disabled worlds,
        for (int i = 0; i < KPM.getDisabledWorlds().size(); i++)
            {
                String worldname = KPM.getDisabledWorlds().get(i);
                if (world.getName().equalsIgnoreCase(worldname))
                    {
                        //return on same name as the world is in disabled-worlds.
                        return;
                    }
            }
        //else, schedule a delayed-task to run after x amount of ticks,
        if(!(blockMaterial == Material.STONE_SLAB2 || blockMaterial == Material.DOUBLE_STONE_SLAB2
            || blockMaterial == Material.STEP || blockMaterial == Material.DOUBLE_STEP
            || blockMaterial == Material.WOOD_STEP || blockMaterial == Material.WOOD_DOUBLE_STEP
            || blockMaterial == Material.SNOW))
        {
            Bukkit.getScheduler().scheduleSyncDelayedTask(this.plugin, new Runnable()
            {
                public void run()
                {
                    //remove the block placed.
                    block.setType(Material.AIR);
                    world.playSound(location, Sound.DIG_STONE, 1F, 1F);

                }
            }, 20L * 2L * 3l);
        }
        else
            player.sendMessage(ChatColor.RED + "Sorry, but you cannot place this material.");
    }
}
