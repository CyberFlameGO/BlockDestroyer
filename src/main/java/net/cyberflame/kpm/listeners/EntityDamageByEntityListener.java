package net.cyberflame.kpm.listeners;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.entity.Snowball;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.projectiles.ProjectileSource;

public class EntityDamageByEntityListener implements Listener
{
    @EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
    public void onEntityDamageByEntity(EntityDamageByEntityEvent e)
    {
        if (e.getDamager() instanceof Player && e.getEntity() instanceof Player)
        {
            Player attacker = (Player) e.getDamager();
            Material attackItem = attacker.getItemInHand().getType();
            if (attacker.hasPotionEffect(PotionEffectType.INVISIBILITY))
            {
                attacker.removePotionEffect(PotionEffectType.INVISIBILITY);
            }
            else if (attackItem == Material.WOOD_HOE || attackItem == Material.GOLD_HOE
                    || attackItem == Material.STONE_HOE || attackItem == Material.IRON_HOE
                    || attackItem == Material.DIAMOND_HOE)
            {

            }
        } else if (e.getDamager() instanceof Snowball)
            if (e.getEntity() instanceof Player)
            {
                Player victim = (Player) e.getEntity();
                ProjectileSource ps = ((Snowball) e.getDamager()).getShooter();

                if (ps instanceof Player)
                {
                    victim.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, 20 * 10, 1));
                }
            }
    }
}