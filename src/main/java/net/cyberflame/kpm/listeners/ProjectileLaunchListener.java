package net.cyberflame.kpm.listeners;

import org.bukkit.event.entity.*;
import org.bukkit.entity.*;
import org.bukkit.event.*;

public class ProjectileLaunchListener implements Listener
{

    @EventHandler
    public void playerRod(final ProjectileLaunchEvent e)
    {
        final double v = 1.1; //<--To make the rod even faster just change the value right here. ex. 2.0
        if (e.getEntityType().equals((Object)EntityType.FISHING_HOOK)) {
            e.getEntity().setVelocity(e.getEntity().getVelocity().multiply(v));
        }
    }
}