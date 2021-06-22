package net.cyberflame.kpm.utils;

public class ZoneVector
{
    private final int x;

    private final int z;

    public ZoneVector(int x, int z)
    {
        this.x = x;
        this.z = z;
    }

    public boolean isInAABB(ZoneVector min, ZoneVector max)
    {
        return (this.x <= max.x && this.x >= min.x && this.z <= max.z && this.z >= min.z);
    }
}