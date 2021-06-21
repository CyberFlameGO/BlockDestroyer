package net.cyberflame.kpm.listeners;

import net.cyberflame.kpm.KPM;
import net.cyberflame.kpm.utils.BlockChecker;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.Plugin;

public class PlayerQuitListener implements Listener
{
    @EventHandler
    public void onQuit(PlayerQuitEvent e)
    {
        BlockChecker checker = BlockChecker.getBlockChecker(e.getPlayer());
        checker.cancelBlockChecker();
        e.getPlayer().removeMetadata("CharacterFixer", KPM.getPlugin());
    }
}
