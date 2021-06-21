package net.cyberflame.kpm.listeners;

import net.cyberflame.kpm.utils.BlockChecker;
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
        BlockChecker checker = BlockChecker.getBlockChecker(e.getPlayer());
        checker.runBlockChecker();
        e.getPlayer().getInventory().setHelmet(new ItemStack(Material.GLASS));
    }
}