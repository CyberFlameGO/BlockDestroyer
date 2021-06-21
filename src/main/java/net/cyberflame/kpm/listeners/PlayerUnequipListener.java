package net.cyberflame.kpm.listeners;

import net.cyberflame.kpm.KPM;
import net.cyberflame.kpm.events.ArmorEquipEvent;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;

public class PlayerUnequipListener implements Listener
{
    @EventHandler
    public void playerUnequip(ArmorEquipEvent event)
    {
        if (KPM.getPlugin().getConfig().getBoolean("glass-head")) {
            event.setCancelled(true);
        }
    }
}
