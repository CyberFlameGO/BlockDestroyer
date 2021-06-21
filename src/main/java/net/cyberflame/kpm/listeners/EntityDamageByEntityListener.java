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

import java.lang.reflect.InvocationTargetException;

public class EntityDamageByEntityListener implements Listener
{
    private KPM KPM;

    public EntityDamageByEntityListener(KPM KPM)
    {
        this.KPM = KPM;
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onEntityDamageByEntity(EntityDamageByEntityEvent event)
    {
        if (event.getDamager() instanceof Player)
            {
                Player attacker = (Player) event.getDamager();
                ItemStack attackItem = attacker.getItemInHand();
                Material attackItemMaterial = attackItem.getType();
                if (attacker.hasPotionEffect(PotionEffectType.INVISIBILITY) && event.getEntity() instanceof Player)
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
                        if (attackItemDurability >= maxUses)
                            {
                                attacker.setItemInHand(new ItemStack(Material.AIR));
                            }
                    }
            }

        if (event.getDamager() instanceof Snowball)
            if (event.getEntity() instanceof Player)
                {
                    Player victim = (Player) event.getEntity();
                    ProjectileSource ps = ((Snowball) event.getDamager()).getShooter();

                    if (ps instanceof Player)
                        {
                            victim.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, 20 * 10, 1));
                        }
                }

        if (event.getDamager() instanceof Projectile && event.getEntity() instanceof Player)
            {
                Player p = (Player) event.getEntity();
                Projectile projectile = (Projectile) event.getDamager();
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

                        if (event.getEntity().getWorld().getName().startsWith("PlayerWarp") &&
                            Zone.contains(event.getEntity().getLocation(), 21, 27, -6, 0))
                            {
                                event.setCancelled(true);
                                sendMessage((CommandSender) event.getEntity(), "&cYou cannot use this in a safezone.");

                                return;
                            }

                        event.setDamage(1.0D);
                        Bukkit.getServer().getScheduler().runTaskLater((Plugin) this.KPM, () ->
                        {
                            Vector velocity = p.getEyeLocation().getDirection().multiply(this.KPM.getConfig().getDouble("settings.velocity-multiplier", 2.5D));
                            velocity.setY(0.33D);
                            p.setVelocity(velocity);
                        }, 0L);
                    }
            }

        if (event.isCancelled())
            {
                return;
            }

        Player damaged = (Player) event.getEntity();
        Player damager = (Player) event.getDamager();

        if (damaged.getNoDamageTicks() > damaged.getMaximumNoDamageTicks() / 2D)
            {
                return;
            }

        double horMultiplier = KPM.getInstance().getHorMultiplier();
        double verMultiplier = KPM.getInstance().getVerMultiplier();
        double sprintMultiplier = damager.isSprinting() ? 0.8D : 0.5D;
        double kbMultiplier = damager.getItemInHand() == null ? 0 : damager.getItemInHand().getEnchantmentLevel(Enchantment.KNOCKBACK) * 0.2D;
        @SuppressWarnings("deprecation")
        double airMultiplier = damaged.isOnGround() ? 1 : 0.5;

        Vector knockback = damaged.getLocation().toVector().subtract(damager.getLocation().toVector()).normalize();
        knockback.setX((knockback.getX() * sprintMultiplier + kbMultiplier) * horMultiplier);
        knockback.setY(0.35D * airMultiplier * verMultiplier);
        knockback.setZ((knockback.getZ() * sprintMultiplier + kbMultiplier) * horMultiplier);

        KPM.damageListener();

        try
            {
                // Send the velocity packet immediately instead of using setVelocity, which fixes the 'relog bug'
                Object entityPlayer = damaged.getClass().getMethod("getHandle").invoke(damaged);
                Object playerConnection = KPM.fieldPlayerConnection.get(entityPlayer);
                Object packet = KPM.packetVelocity.newInstance(damaged.getEntityId(), knockback.getX(), knockback.getY(),
                                                               knockback.getZ());
                KPM.sendPacket.invoke(playerConnection, packet);
            }
        catch (SecurityException | IllegalArgumentException | IllegalAccessException | InvocationTargetException | NoSuchMethodException | InstantiationException e)
            {
                e.printStackTrace();
            }
    }

    public static String replace(String text) {
        return ChatColor.translateAlternateColorCodes('&', text);
    }

    public static void sendMessage(CommandSender sender, String message) {
        sender.sendMessage(replace(message));
    }
}