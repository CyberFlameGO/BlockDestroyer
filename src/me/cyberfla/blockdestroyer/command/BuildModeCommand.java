package me.cyberfla.blockdestroyer.command;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.cyberfla.blockdestroyer.BlockDestroyer;
import me.cyberfla.blockdestroyer.Utils;
import net.md_5.bungee.api.ChatColor;

public class BuildModeCommand extends Command {
	
	private BlockDestroyer plugin;

	public BuildModeCommand(BlockDestroyer plugin) {
		super("buildmode");
		this.plugin = plugin;
		this.setUsage(ChatColor.YELLOW + "Usage: " + ChatColor.GOLD + "/buildmode " + ChatColor.WHITE + "[player]");
		this.setPermissionMessage(Utils.NO_PERMISSION);
	}
	
	public boolean execute(CommandSender sender, String alias, String[] args) {
		if (!sender.hasPermission("buildmode.use")) {
			Utils.sendMessage(sender, this.getPermissionMessage());
			return true;
		}
		if (args.length == 1) {
			if (Bukkit.getPlayer(args[0]) != null) {
				Player target = Bukkit.getPlayer(args[0]);
				String targetname = Bukkit.getPlayer(args[0]).getName();
				plugin.setBuildEnabled(Bukkit.getPlayer(args[0]).getUniqueId());
				Utils.sendMessage(sender, (plugin.getBuildEnabled(target.getUniqueId()) ? Utils.BUILD_OTHER_TOGGLE_ON.replaceAll("%player%", targetname) : Utils.BUILD_OTHER_TOGGLE_OFF.replaceAll("%player%", targetname)));
				Utils.sendMessage(target, (plugin.getBuildEnabled(target.getUniqueId()) ? Utils.BUILD_TOGGLE_ON.replaceAll("%player%", targetname) : Utils.BUILD_TOGGLE_OFF.replaceAll("%player%", targetname)));
				return true;
			}
			else {
				Utils.sendMessage(sender, Utils.UNKNOWN_PLAYER.replaceAll("%player%", args[0]));
				return true;
			}
		}
		Player player = (Player) sender;
		plugin.setBuildEnabled(player.getUniqueId());
		Utils.sendMessage(player, (plugin.getBuildEnabled(player.getUniqueId()) ? Utils.BUILD_TOGGLE_ON.replaceAll("%player%", player.getName()) : Utils.BUILD_TOGGLE_OFF.replaceAll("%player%", player.getName())));
		return true;
	}
	

}
