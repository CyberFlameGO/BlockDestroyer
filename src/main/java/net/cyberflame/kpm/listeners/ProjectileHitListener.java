package net.cyberflame.kpm.listeners;

import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.util.BlockIterator;

public class ProjectileHitListener implements Listener
{
    @EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
    public void ProjectileHit(ProjectileHitEvent event)
    {
        Projectile projectile = event.getEntity();
        if (!(projectile instanceof Arrow))
            return;
        Arrow arrow = (Arrow) projectile;
        if (!(arrow.getShooter() instanceof Player))
            return;
        World world = arrow.getWorld();
        BlockIterator iterator = new BlockIterator(world, arrow.getLocation().toVector(),
                arrow.getVelocity().normalize(), 0.0D, 4);
        Block hitBlock = null;
        while (iterator.hasNext())
            {
                hitBlock = iterator.next();
                if (hitBlock.getType() != Material.AIR)
                    break;
            }
        if (hitBlock.getType() == Material.GLASS)
            {
                hitBlock.breakNaturally();
                arrow.remove();
            }
    }
}
