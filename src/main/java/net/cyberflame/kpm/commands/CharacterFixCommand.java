package net.cyberflame.kpm.commands;

import net.cyberflame.kpm.KPM;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CharacterFixCommand implements CommandExecutor
{
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
    {
        if (args.length > 0 && sender.hasPermission("kpm.reload") && (args[0].equalsIgnoreCase("rl") || args[0].equalsIgnoreCase("reload")))
            {
                KPM.getPlugin().reloadConfig();
                if (KPM.getPlugin().autoFix != null)
                    {
                        KPM.getPlugin().autoFix.cancel();
                    }
                if (KPM.getPlugin().getConfig().getBoolean("auto-fix"))
                    {
                        KPM.getPlugin().autoFix();
                    }
                sender.sendMessage(ChatColor.GREEN + "The plugin has been reloaded!");
                return true;
            }
        if (sender instanceof Player)
            {
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', KPM.getPlugin().getConfig().getString("fix" +
                                                                                                                     "-message")));
                byte b;
                int i;
                Player[] arrayOfPlayer;
                for (i = (arrayOfPlayer = Bukkit.getOnlinePlayers().toArray(new Player[0])).length, b = 0; b < i; )
                    {
                        Player pl = arrayOfPlayer[b];
                        KPM.getPlugin().fix(pl, (Player) sender);
                        b++;
                    }

            }
        return true;
    }
}
