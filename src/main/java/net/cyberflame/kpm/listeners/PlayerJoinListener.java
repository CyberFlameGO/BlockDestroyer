package net.cyberflame.kpm.listeners;

import net.cyberflame.kpm.utils.BlockChecker;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;

import static net.cyberflame.kpm.KPM.getPingReflection;

public class PlayerJoinListener implements Listener
{
    @EventHandler
    public void onJoin(PlayerJoinEvent event)
    {
        Player player = event.getPlayer();

        BlockChecker checker = BlockChecker.getBlockChecker(player);
        checker.runBlockChecker();

        try {
            player.setPlayerListName(player.getDisplayName() + ChatColor.GOLD + " [" + getPingReflection(player) + "]");
        } catch (Exception e) {
            e.printStackTrace();
        }

        player.getInventory().setHelmet(new ItemStack(Material.GLASS));
    }
}