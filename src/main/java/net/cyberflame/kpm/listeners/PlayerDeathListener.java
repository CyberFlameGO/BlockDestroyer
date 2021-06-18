package net.cyberflame.kpm.listeners;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.Potion;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;


import java.util.Collection;
import java.util.HashMap;

public class PlayerDeathListener implements Listener
{
    private HashMap<String, Collection<PotionEffect>> playerMap = new HashMap<>();

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent e)
    {
        Player p = e.getEntity();
        World w = p.getWorld();
        Location l = p.getLocation();
        this.playerMap.put(p.getName(), p.getActivePotionEffects());
        ItemStack potion = new Potion(null).toItemStack(1);
        PotionMeta potionMeta = (PotionMeta) potion.getItemMeta();
        potionMeta.setMainEffect(null);
        for (PotionEffect pe : this.playerMap.get(p.getName()))
            potionMeta.addCustomEffect(pe, true);
        potion.setItemMeta(potionMeta);
        w.dropItemNaturally(l, potion);
    }
}