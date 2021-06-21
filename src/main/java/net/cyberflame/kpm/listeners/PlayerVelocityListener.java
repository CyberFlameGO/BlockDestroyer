package net.cyberflame.kpm.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerVelocityEvent;

public class PlayerVelocityListener implements Listener
{
    @EventHandler
    public void onPlayerVelocity(PlayerVelocityEvent event)
    {
        Player player = event.getPlayer();
        EntityDamageEvent lastDamage = player.getLastDamageCause();

        if (lastDamage == null || !(lastDamage instanceof EntityDamageByEntityEvent))
            {
                return;
            }

        // Cancel the vanilla knockback
        if (((EntityDamageByEntityEvent) lastDamage).getDamager() instanceof Player)
            {
                event.setCancelled(true);
            }
    }
}
