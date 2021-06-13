package net.cyberflame.kpm.listeners;

import net.cyberflame.kpm.events.ArmorEquipEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class PlayerUnequipListener implements Listener
{
    @EventHandler
    public void playerUnequip(ArmorEquipEvent event)
    {
        event.setCancelled(true);
    }
}
