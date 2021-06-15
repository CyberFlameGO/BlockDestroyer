package net.cyberflame.kpm.listeners;

import org.bukkit.entity.Player;
import org.bukkit.entity.Snowball;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.projectiles.ProjectileSource;

public class EntityDamageByEntityListener implements Listener
{
    @EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
    public void onPlayerAttack(EntityDamageByEntityEvent e)
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
    @EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
    public void onSnowballHit(EntityDamageByEntityEvent e) {
        if ((e.getDamager() instanceof Snowball))
            if ((e.getEntity() instanceof Player))
            {
                Player victim = (Player) e.getEntity();
                ProjectileSource ps = ((Snowball) e.getDamager()).getShooter();

                if (ps instanceof Player)
                {
                    victim.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, 20 * 5, 1));
                }
            }
    }
}
