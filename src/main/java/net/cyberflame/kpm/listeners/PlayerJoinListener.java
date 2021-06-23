package net.cyberflame.kpm.listeners;

import net.cyberflame.kpm.KPM;
import net.cyberflame.kpm.utils.BlockChecker;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;

public class PlayerJoinListener implements Listener
{
    @EventHandler
    public void onJoin(PlayerJoinEvent event)
    {
        Player player = event.getPlayer();
        BlockChecker checker = BlockChecker.getBlockChecker(player);
        checker.runBlockChecker();
        if (KPM.getPlugin().getConfig().getBoolean("glass-head"))
            {
                player.getInventory().setHelmet(new ItemStack(Material.GLASS));
            }
    }
}