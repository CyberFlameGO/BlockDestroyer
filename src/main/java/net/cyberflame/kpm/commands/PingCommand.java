package net.cyberflame.kpm.commands;

import net.cyberflame.kpm.KPM;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class PingCommand implements CommandExecutor
{

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args)
    {
        if (args.length == 1 && args[0].equalsIgnoreCase("help"))
            {
                sender.sendMessage(ChatColor.AQUA + "KPM Version: " + KPM.getPlugin().getDescription().getVersion());
                sender.sendMessage(ChatColor.AQUA + "/ping help - View this menu");
                if (sender.hasPermission("kpm.ping"))
                    sender.sendMessage(ChatColor.AQUA + "/ping - View your ping");
                if (sender.hasPermission("kpm.ping.others"))
                    sender.sendMessage(ChatColor.AQUA + "/ping <player> - Get a players ping");
                return true;
            }

        if (args.length == 1)
            {

                if (sender instanceof Player && !sender.hasPermission("kpm.ping.others"))
                    {
                        sender.sendMessage(ChatColor.RED + "You do not have permission to run this command");
                        return false;
                    }

                Player player = Bukkit.getPlayer(args[0]);
                if (player == null)
                    {
                        sender.sendMessage(ChatColor.RED + "The player \"" + args[0] + "\" could not be found");
                        return false;
                    }

                try
                    {
                        sender.sendMessage(ChatColor.GREEN + player.getName() + "'s ping is " + KPM.getPingReflection(player) + "ms");
                    }
                catch (Exception e)
                    {
                        sender.sendMessage(ChatColor.RED + "Failed to get ping :(");
                        e.printStackTrace();
                    }
                return true;
            }

        if (sender instanceof Player)
            {
                Player player = (Player) sender;
                if (!player.hasPermission("kpm.ping"))
                    {
                        player.sendMessage(ChatColor.RED + "You do not have permission to run this command");
                        return false;
                    }

                try
                    {
                        sender.sendMessage(ChatColor.GREEN + "Your ping is " + KPM.getPingReflection(player) + "ms");
                    }
                catch (Exception e)
                    {
                        sender.sendMessage(ChatColor.RED + "Failed to get ping :(");
                        e.printStackTrace();
                    }
            }
        else
            {
                sender.sendMessage(ChatColor.RED + "Command must be ran as a player");
            }

        return true;
    }

}