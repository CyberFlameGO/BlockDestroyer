package net.cyberflame.kpm.listeners;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;


import java.util.Collection;
import java.util.HashMap;

public class PlayerDeathListener implements Listener
{
    private HashMap<String, Collection<PotionEffect>> playerMap = new HashMap<>();

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent e)
    {
        Player p = e.getEntity();
        Location l = p.getLocation();
        this.playerMap.put(p.getName(), p.getActivePotionEffects());
        ItemStack potion = new ItemStack(Material.POTION);
        for (PotionEffect pe : this.playerMap.get(p.getName()))
            p.addPotionEffect(pe);
        // world.dropItemNaturally(player.getLocation(), soupItem);
    }
}