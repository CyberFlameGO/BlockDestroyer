package net.cyberflame.kpm.commands;

import net.cyberflame.kpm.KPM;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class KPMCommand implements CommandExecutor
{
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
    {
        if (sender.hasPermission("kpm.root"))
            {
                if (args.length == 0)
                    {
                        sender.sendMessage("§eUsage: /kpm <reload>");
                    }
                else if (args.length >= 1)
                    {
                        if (args[0].equals("reload") && sender.hasPermission("kpm.reload"))
                            {
                                KPM.get().reloadConfig();
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
