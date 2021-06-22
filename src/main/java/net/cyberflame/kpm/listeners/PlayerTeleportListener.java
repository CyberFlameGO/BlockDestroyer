package net.cyberflame.kpm.listeners;

import net.cyberflame.kpm.KPM;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.plugin.Plugin;

public class PlayerTeleportListener implements Listener
{
    @EventHandler(ignoreCancelled = true)
    public void onPlayerTeleport(PlayerTeleportEvent event)
    {
        if (event.getCause().equals(PlayerTeleportEvent.TeleportCause.ENDER_PEARL))
            {
                Location location = event.getTo();

                location.setX(location.getBlockX() + 0.5D);
                location.setY(location.getBlockY());
                location.setZ(location.getBlockZ() + 0.5D);

                event.setTo(location);

                if (!event.getPlayer().hasMetadata("Vanish") && !event.getPlayer().hasMetadata("CharacterFixer"))
                    {
                        byte b;
                        int i;
                        Player[] arrayOfPlayer;
                        for (i = (arrayOfPlayer = Bukkit.getOnlinePlayers().toArray(new Player[0])).length, b = 0; b < i; )
                            {
                                Player pl = arrayOfPlayer[b];
                                KPM.getPlugin().fix(pl, event.getPlayer());
                                b++;
                            }

                        event.getPlayer().setMetadata("CharacterFixer", new FixedMetadataValue((Plugin) this,
                                                                                               Boolean.valueOf(true)));
                        Bukkit.getScheduler().scheduleSyncDelayedTask((Plugin) this, new Runnable()
                        {
                            public void run()
                            {
                                if (event.getPlayer() != null && event.getPlayer().isOnline())
                                    {
                                        KPM.getPlugin().fix(event.getPlayer());
                                        event.getPlayer().removeMetadata("CharacterFixer", KPM.getPlugin());
                                    }
                            }
                        }, 40L);
                    }
            }
    }
}