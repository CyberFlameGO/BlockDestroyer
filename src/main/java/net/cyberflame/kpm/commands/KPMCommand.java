package net.cyberflame.kpm.commands;

import net.cyberflame.kpm.KPM;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.permissions.Permission;

public class KPMCommand implements CommandExecutor
{
    String kpmreload = "kpm.reload";
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
    {
        if (sender.hasPermission("kpm.root"))
            {
                if (args.length == 0)
                    {
                        sender.sendMessage("§aPlugin created by CyberFlame (https://cyberfla.me");
                        if (sender.hasPermission(kpmreload))
                            {
                                sender.sendMessage("§eUsage: /kpm [reload]");
                            }
                    }
                else if (args.length >= 1)
                    {
                        if (args[0].equals("reload") && sender.hasPermission(kpmreload))
                            {
                                KPM.getPlugin().reloadConfig();
                                sender.sendMessage("§aThe KPM configuration has been reloaded.");
                            }
                        else
                            {
                                sender.sendMessage("§cCorrect usage: /kpm <reload>");
                            }
                    }
                return true;
            }
        else
            {
                return false;
            }
    }
}
