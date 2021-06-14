package net.cyberflame.kpm.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.potion.PotionEffectType;

public class PlayerHitListener implements Listener
{
    @EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
    public void onHit(EntityDamageByEntityEvent e)
    {
        if (e.getDamager() instanceof Player && e.getEntity() instanceof Player)
            {
                Player attacker = (Player) e.getDamager();
                if (attacker.getActivePotionEffects().contains(PotionEffectType.INVISIBILITY))
                    {
                        attacker.removePotionEffect(PotionEffectType.INVISIBILITY);
                    }
            }
    }
}
