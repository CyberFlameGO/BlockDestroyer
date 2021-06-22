package net.cyberflame.kpm.listeners;

import net.cyberflame.kpm.KPM;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;

public class PlayerDropItemListener implements Listener
{

    private final KPM plugin;

    public PlayerDropItemListener()
    {
        plugin = KPM.getPlugin(KPM.class);
    }

    // This logic is pretty much pointless but I'm keeping it here regardless
    @EventHandler
    public void dropItem(PlayerDropItemEvent event)
    {
        Player player = event.getPlayer();
        Item item = event.getItemDrop();
        Material type = item.getItemStack().getType();
        if (type == Material.MUSHROOM_SOUP || type == Material.POTION)
            {
                Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, () ->
                    {
                    item.remove();
                    player.updateInventory();
                    }, 20 * 10);
            }
        else
            {
                //event.setCancelled(true);
                player.updateInventory();
                return;
            }
        if (type == Material.BOWL)
            {
                switch (type)
                    {
                        case BOWL:
                        {
                            Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, () ->
                                {
                                item.remove();
                                player.updateInventory();
                                }, 0);
                            return;
                        }
                    }
            }
    }
}