package net.cyberflame.kpm.listeners;

import net.cyberflame.kpm.KPM;
import org.bukkit.event.entity.*;
import org.bukkit.entity.*;
import org.bukkit.event.*;
import org.bukkit.util.Vector;

public class ProjectileLaunchListener implements Listener
{

    private Double splashPotSpeed = KPM.getPlugin().getConfig().getDouble("splashPotSpeed");

    @SuppressWarnings("deprecation")
    @EventHandler
    public void playerRod(final ProjectileLaunchEvent e)
    {
        final double v = 1.1; //<--To make the rod even faster just change the value right here. ex. 2.0
        if (e.getEntityType().equals((Object) EntityType.FISHING_HOOK))
            {
                e.getEntity().setVelocity(e.getEntity().getVelocity().multiply(v));
            }
        if (e.getEntityType() == EntityType.SPLASH_POTION)
            {
                final Projectile projectile = e.getEntity();

                if (projectile.getShooter() instanceof Player && ((Player) projectile.getShooter()).isSprinting())
                    {
                        final Vector velocity = projectile.getVelocity();

                        velocity.setY(velocity.getY() - splashPotSpeed);
                        projectile.setVelocity(velocity);
                    }
            }
    }
}