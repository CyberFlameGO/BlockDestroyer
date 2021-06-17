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
        if (e.getDamager() instanceof Player)
        {
            Player attacker = (Player) e.getDamager();
            ItemStack attackItem = attacker.getItemInHand();
            Material attackItemMaterial = attackItem.getType();
            if (attacker.hasPotionEffect(PotionEffectType.INVISIBILITY) && e.getEntity() instanceof Player)
            {
                attacker.removePotionEffect(PotionEffectType.INVISIBILITY);
            }
            if (attackItemMaterial == Material.WOOD_HOE || attackItemMaterial == Material.GOLD_HOE
                    || attackItemMaterial == Material.STONE_HOE || attackItemMaterial == Material.IRON_HOE
                    || attackItemMaterial == Material.DIAMOND_HOE)
            {
                int attackItemDurability = attacker.getItemInHand().getDurability();
                if (attackItemDurability != 0)
                {
                    attacker.getItemInHand().setDurability((short) (attackItemDurability - 1));
                }
                else
                {
                    attacker.setItemInHand(new ItemStack(Material.AIR));
                }
            }
        }
        if (e.getDamager() instanceof Snowball)
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