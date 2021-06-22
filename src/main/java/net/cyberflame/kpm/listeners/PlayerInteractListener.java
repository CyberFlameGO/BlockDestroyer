package net.cyberflame.kpm.listeners;

import net.cyberflame.kpm.KPM;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class PlayerInteractListener implements Listener
{
    private final KPM plugin;

    public PlayerInteractListener()
    {
        plugin = KPM.getPlugin(KPM.class);
    }

    @EventHandler
    public void playerInteract(PlayerInteractEvent event)
    {
        Player player = event.getPlayer();
        if (player.getItemInHand().getType() == Material.MUSHROOM_SOUP)
            {
                if (player.getHealth() == 20.0)
                    {
                        return;
                    }
                else
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