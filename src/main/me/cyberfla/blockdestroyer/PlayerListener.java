package me.cyberfla.blockdestroyer;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

public class PlayerListener implements Listener {

	private BlockDestroyer plugin;

	public PlayerListener(BlockDestroyer plugin) {
		this.plugin = plugin;
	}

	@EventHandler
	public void onBlockPlace(BlockPlaceEvent e) {
		//Get the block placed, who placed it and the world,
		Player player = e.getPlayer();
		Block block = e.getBlock();
		World world = block.getWorld();
		//check if player has buildmode enabled,
		if (plugin.getBuildEnabled(player.getUniqueId())) {
			return;
		}
		//else check if it was in one of the disabled worlds,
		for (int i = 0; i < plugin.getDisabledWorlds().size(); i++) {
			String worldname = plugin.getDisabledWorlds().get(i);
			if (world.getName().equalsIgnoreCase(worldname)) {
				//return on same name as the world is in disabled-worlds.
				return;
			}
		}
		//else, schedule a delayed-task to run after x amount of ticks,
		Bukkit.getScheduler().scheduleSyncDelayedTask(this.plugin, new Runnable() {
			public void run() {
				//remove the block placed.
				block.setType(Material.AIR);
			}
		}, 20L * 2L * 3l);
	}
}
