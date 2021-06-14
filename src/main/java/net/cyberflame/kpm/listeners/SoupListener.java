package net.cyberflame.kpm.listeners;

import net.cyberflame.kpm.KPM;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class SoupListener implements Listener
{
    private KPM plugin;

    public SoupListener() {
        plugin = KPM.getPlugin(KPM.class);
    }

    @EventHandler
    public void soupClick(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        if (player.getItemInHand().getType() == Material.MUSHROOM_SOUP)
            {
                if (player.getHealth() == 20.0)
                    {
                        return;
                    } else
                    {
                        event.setCancelled(true);
                        double newHealth = Math.min(player.getHealth() + 7.0, player.getMaxHealth());
                        player.setHealth(newHealth);
                        int slot = player.getInventory().getHeldItemSlot();
                        player.getInventory().setItem(slot, new ItemStack(Material.AIR));
                    }
            }
    }

    @EventHandler
    public void playerClick(PlayerInteractAtEntityEvent event) {
        Player player = event.getPlayer();
        if (player.getItemInHand().getType() == Material.MUSHROOM_SOUP)
            {
                if (player.getHealth() == 20.0)
                    {
                        return;
                    } else
                    {
                        event.setCancelled(true);
                        double newHealth = Math.min(player.getHealth() + 7.0, player.getMaxHealth());
                        player.setHealth(newHealth);
                        int slot = player.getInventory().getHeldItemSlot();
                        player.getInventory().setItem(slot, new ItemStack(Material.AIR));
                    }
            }
    }

    @EventHandler
    public void onClick(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        if (player.getItemInHand().getType() == Material.MUSHROOM_SOUP)
            {
                if (event.getAction() == Action.LEFT_CLICK_AIR)
                    {
                        if (player.getHealth() == 20.0)
                            {
                                return;
                            } else
                            {
                                event.setCancelled(true);
                                double newHealth = Math.min(player.getHealth() + 7.0, player.getMaxHealth());
                                player.setHealth(newHealth);
                                int slot = player.getInventory().getHeldItemSlot();
                                player.getInventory().setItem(slot, new ItemStack(Material.AIR));
                            }
                    }
            }
    }

    // This logic is pretty much pointless but I'm keeping it here regardless
    @EventHandler
    public void dropItem(PlayerDropItemEvent event) {
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
            } else
            {
                event.setCancelled(true);
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