package net.cyberflame.kpm.listeners;

import net.cyberflame.kpm.KPM;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.Potion;
import org.bukkit.potion.PotionEffect;

public class EntityDeathListener implements Listener {

    @EventHandler
    public void onPlayerDeath(EntityDeathEvent event) {
        if (!(event.getEntity() instanceof Player)) {
            return;
        }
        Player victim = (Player) event.getEntity();
        if (victim.getActivePotionEffects() == null)
            return;

        ItemStack item = new ItemStack(Material.POTION, 1);
        PotionMeta im = (PotionMeta) item.getItemMeta();

        for (PotionEffect effect : victim.getActivePotionEffects()) {
            if (KPM.getPlugin().getBlockedEffects().contains(effect.getType().getName().toUpperCase())) {
                continue;
            }
            im.addCustomEffect(effect, true);
        }
        im.setDisplayName(ChatColor.translateAlternateColorCodes('&',
                                                                 KPM.getPlugin().getPotionName().replace("%player%",
                                                                                                         victim.getName())));
        item.setItemMeta(im);

        Potion potion = new Potion(im.getCustomEffects().get(0).getType().hashCode());
        potion.apply(item);
        event.getDrops().add(item);
    }
}
