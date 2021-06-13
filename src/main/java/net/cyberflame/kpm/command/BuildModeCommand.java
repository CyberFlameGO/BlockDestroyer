package net.cyberflame.kpm.command;

import net.cyberflame.kpm.utils.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.cyberflame.kpm.KPM;
import net.cyberflame.kpm.utils.StringUtils;

public class BuildModeCommand implements CommandExecutor
{

	private final KPM plugin;
	public String permission = "kpm.buildmode";

	public BuildModeCommand(KPM plugin) {
		this.plugin = plugin;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
	{
		if (!sender.hasPermission(permission))
		{
			StringUtils.sendMessage(sender, StringUtils.NO_PERMISSION);
			return true;
		}
		if (args.length == 1)
		{
			if (Bukkit.getPlayer(args[0]) != null)
			{
				Player target = Bukkit.getPlayer(args[0]);
				String targetname = Bukkit.getPlayer(args[0]).getName();
				plugin.setBuildEnabled(Bukkit.getPlayer(args[0]).getUniqueId());
				StringUtils.sendMessage(sender, (plugin.getBuildEnabled(target.getUniqueId()) ? StringUtils.BUILD_OTHER_TOGGLE_ON.replaceAll("%player%", targetname) : StringUtils.BUILD_OTHER_TOGGLE_OFF.replaceAll("%player%", targetname)));
				StringUtils.sendMessage(target, (plugin.getBuildEnabled(target.getUniqueId()) ? StringUtils.BUILD_TOGGLE_ON.replaceAll("%player%", targetname) : StringUtils.BUILD_TOGGLE_OFF.replaceAll("%player%", targetname)));
				return true;
			}
			else
			{
				StringUtils.sendMessage(sender, StringUtils.UNKNOWN_PLAYER.replaceAll("%player%", args[0]));
				return true;
			}
		}
		Player player = (Player) sender;
		plugin.setBuildEnabled(player.getUniqueId());
		StringUtils.sendMessage(player, (plugin.getBuildEnabled(player.getUniqueId()) ? StringUtils.BUILD_TOGGLE_ON.replaceAll("%player%", player.getName()) : StringUtils.BUILD_TOGGLE_OFF.replaceAll("%player%", player.getName())));
		return true;
	}
}