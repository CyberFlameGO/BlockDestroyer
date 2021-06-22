package net.cyberflame.kpm.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PotionSplashEvent;

public class PotionSplashListener implements Listener
{
    @SuppressWarnings("deprecation")
    @EventHandler
    void onPotionSplash(final PotionSplashEvent event)
    {
        if (event.getEntity().getShooter() instanceof Player)
            {
                final Player shooter = (Player) event.getEntity().getShooter();

                if (shooter.isSprinting() && event.getIntensity(shooter) > 0.6D)
                    {
                        event.setIntensity(shooter, 1.0D);
                    }
            }
    }
}
