package net.cyberflame.kpm.listeners;

import net.cyberflame.kpm.KPM;
import net.cyberflame.kpm.utils.BlockChecker;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

public class BlockPlaceListener implements Listener
{

    private final KPM plugin;

    public BlockPlaceListener(KPM plugin)
    {
        this.plugin = plugin;
    }

    @EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
    public void onBlockPlace(BlockPlaceEvent e)
    {
        //Get the block placed, who placed it and the world,
        Player p = e.getPlayer();
        Block block = e.getBlock();
        World world = block.getWorld();
        Location location = e.getBlockPlaced().getLocation();
        Material blockMaterial = e.getBlockPlaced().getType();

        if (p.hasPermission("kpm.override"))
            return;
        Location loc = e.getBlockPlaced().getLocation();
        boolean cancel = false;
        if (cancel)
            {
                e.setCancelled(true);
                e.getBlockPlaced().setType(Material.AIR);
                BlockChecker checker = BlockChecker.getBlockChecker(p);
                p.sendMessage(ChatColor.RED + "" + ChatColor.BOLD + "(!) You are not allowed to place blocks here!");
                Location loc2 = new Location(loc.getWorld(), loc.getX(), loc.getY(), loc.getZ());
                Location lastBlock = new Location(checker.getLastStoodBlock().getWorld(), checker.getLastStoodBlock().getX(), checker.getLastStoodBlock().getY(), checker.getLastStoodBlock().getZ());
                if (loc2 == lastBlock)
                    return;
                p.teleport(checker.getLastStoodBlock().add(0.0D, 1.0D, 0.0D));
            }

        //check if p has buildmode enabled,
        if (plugin.getBuildEnabled(p.getUniqueId()))
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
        if (!(blockMaterial == Material.STONE_SLAB2 || blockMaterial == Material.DOUBLE_STONE_SLAB2
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
                        world.playEffect(location, Effect.TILE_BREAK, block.getType());
                    }
                }, 20L * 2L * 3l);
            }
        else
            p.sendMessage(ChatColor.RED + "Sorry, but you cannot place this material.");
    }
}
