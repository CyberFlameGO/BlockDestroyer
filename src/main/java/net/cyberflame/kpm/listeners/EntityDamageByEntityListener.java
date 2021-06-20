package net.cyberflame.kpm.listeners;

import net.cyberflame.kpm.KPM;
import net.cyberflame.kpm.utils.Zone;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.projectiles.ProjectileSource;
import org.bukkit.util.Vector;

public class EntityDamageByEntityListener implements Listener {
    private KPM KPM;

    public EntityDamageByEntityListener(KPM KPM) {
        this.KPM = KPM;
    }

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

                short attackItemDurability = attackItem.getDurability();
                int maxUses = attackItemMaterial.getMaxDurability();
                int durability = maxUses + 1 - attackItemDurability;

                attacker.getInventory().getItemInHand().setDurability((short) (attackItemDurability + 1));
                if (attackItemDurability == maxUses)
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

        if (e.getDamager() instanceof Projectile && e.getEntity() instanceof Player)
        {
            Player p = (Player) e.getEntity();
            Projectile projectile = (Projectile) e.getDamager();
            if (projectile.getType() == EntityType.ARROW && projectile.getShooter() instanceof Player &&
                    ((Player) projectile.getShooter()).getUniqueId().equals(p.getUniqueId()))
            {
                ItemStack item = p.getItemInHand();
                if (this.KPM.getConfig().getBoolean("settings.punch-only", false)
                        && !item.containsEnchantment(Enchantment.ARROW_KNOCKBACK))
                {
                    return;
                }
                if (this.KPM.getConfig().getBoolean("settings.punch-only", true)
                        && item.containsEnchantment(Enchantment.ARROW_KNOCKBACK))
                {
                    int enchLevel = item.getEnchantmentLevel(Enchantment.ARROW_KNOCKBACK);
                    if (enchLevel != 2)
                    {
                        return;
                    }
                }

                if (e.getEntity().getWorld().getName().startsWith("PlayerWarp") &&
                        Zone.contains(e.getEntity().getLocation(), 21, 27, -6, 0)) {
                    e.setCancelled(true);
                    sendMessage((CommandSender) e.getEntity(), "&cYou cannot use this in a safezone.");

                    return;
                }

                e.setDamage(1.0D);
                Bukkit.getServer().getScheduler().runTaskLater((Plugin) this.KPM, () ->
                {
                    Vector velocity = p.getEyeLocation().getDirection().multiply(this.KPM.getConfig().getDouble("settings.velocity-multiplier", 2.5D));
                    velocity.setY(0.33D);
                    p.setVelocity(velocity);
                }, 0L);
            }
        }
    }

    public static String replace(String text) {
        return ChatColor.translateAlternateColorCodes('&', text);
    }

    public static void sendMessage(CommandSender sender, String message) {
        sender.sendMessage(replace(message));
    }
}