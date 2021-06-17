package net.cyberflame.kpm.utils;

import org.bukkit.Location;

public class Zone
{
    public static boolean contains(Location loc, int x1, int x2, int z1, int z2) {
        ZoneVector curr = new ZoneVector(loc.getBlockX(), loc.getBlockZ());
        ZoneVector min = new ZoneVector(Math.min(x1, x2), Math.min(z1, z2));
        ZoneVector max = new ZoneVector(Math.max(x1, x2), Math.max(z1, z2));
        return curr.isInAABB(min, max);
    }
}