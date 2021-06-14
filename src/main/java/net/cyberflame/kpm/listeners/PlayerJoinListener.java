package net.cyberflame.kpm.listeners;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;

public class PlayerJoinListener implements Listener
{
    @EventHandler
    public void onJoin(PlayerJoinEvent e)
    {
        e.getPlayer().getInventory().setHelmet(new ItemStack(Material.GLASS));
    }
}
