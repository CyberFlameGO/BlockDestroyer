package net.cyberflame.kpm.utils;

import java.util.HashMap;

import net.cyberflame.kpm.KPM;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

public class BlockChecker {
    private static KPM pl = KPM.getInstance();

    private static HashMap<Player, BlockChecker> blockCheckers = new HashMap<>();

    private Player player;

    private BukkitRunnable runnable;

    private Location lastBlock;

    private BlockChecker(Player p) {
        this.player = p;
    }

    public static BlockChecker getBlockChecker(Player p) {
        if (blockCheckers.containsKey(p))
            return blockCheckers.get(p);
        BlockChecker checker = new BlockChecker(p);
        blockCheckers.put(p, checker);
        return checker;
    }

    public void runBlockChecker() {
        this.lastBlock = this.player.getLocation();
        this.runnable = new BukkitRunnable() {
            public void run() {
                Location loc = BlockChecker.this.player.getPlayer().getLocation();
                loc.setY((int)loc.getY());
                if (loc.subtract(0.0D, 1.0D, 0.0D).getBlock().getType().equals(Material.AIR))
                    return;
                BlockChecker.this.setLastStoodBlock(loc);
            }
        };
        this.runnable.runTaskTimer((Plugin)pl, 0L, 1L);
    }

    public void cancelBlockChecker() {
        this.runnable.cancel();
    }

    public Location getLastStoodBlock() {
        return this.lastBlock;
    }

    public void setLastStoodBlock(Location loc) {
        this.lastBlock = loc;
    }
}
