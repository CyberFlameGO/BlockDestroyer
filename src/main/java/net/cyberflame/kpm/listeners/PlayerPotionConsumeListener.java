package net.cyberflame.kpm.listeners;

import net.cyberflame.kpm.KPM;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.inventory.ItemStack;

public class PlayerPotionConsumeListener implements Listener
{
    private KPM plugin;

    public PlayerPotionConsumeListener()
    {
        plugin = KPM.getPlugin(KPM.class);
    }

    @EventHandler
    public void onConsume(final PlayerItemConsumeEvent e) {
        if (e.getItem().getType().equals(Material.POTION))
            Bukkit.getServer().getScheduler().runTaskLaterAsynchronously(plugin, new Runnable() {
                public void run() {
                    e.getPlayer().setItemInHand(new ItemStack(Material.AIR));
                }
            },  1L);
    }
}
