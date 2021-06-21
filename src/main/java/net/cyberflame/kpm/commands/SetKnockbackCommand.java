package net.cyberflame.kpm.commands;

import net.cyberflame.kpm.KPM;
import org.apache.commons.lang3.math.NumberUtils;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class SetKnockbackCommand implements CommandExecutor
{
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!sender.hasPermission("kpm.setknockback")) {
            sender.sendMessage(ChatColor.RED + "No permission.");
            return true;
        }

        if (args.length < 2){
            sender.sendMessage(ChatColor.RED + "Usage: /" + label + " <horizontal multiplier> <vertical multiplier>.");
            return true;
        }

        double horMultiplier = NumberUtils.toDouble(args[0], -1D);
        double verMultiplier = NumberUtils.toDouble(args[1], -1D);

        if (horMultiplier < 0D || verMultiplier < 0D) {
            sender.sendMessage(ChatColor.RED + "Invalid horizontal/vertical multiplier!");
            return true;
        }

        KPM.getInstance().horMultiplier = horMultiplier;
        KPM.getInstance().verMultiplier = verMultiplier;

        KPM.getInstance().getConfig().set("knockback-multiplier.horizontal", horMultiplier);
        KPM.getInstance().getConfig().set("knockback-multiplier.vertical", verMultiplier);
        KPM.getInstance().saveConfig();

        sender.sendMessage(ChatColor.GREEN + "Successfully updated the knockback multipliers!");
        return true;
    }
}
