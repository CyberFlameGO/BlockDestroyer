package net.cyberflame.kpm.commands;

import org.bukkit.command.CommandExecutor;

public class SetKnockbackCommand implements CommandExecutor
{
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!sender.hasPermission("kbfix.setknockback")) {
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

        this.horMultiplier = horMultiplier;
        this.verMultiplier = verMultiplier;

        getConfig().set("knockback-multiplier.horizontal", horMultiplier);
        getConfig().set("knockback-multiplier.vertical", verMultiplier);
        saveConfig();

        sender.sendMessage(ChatColor.GREEN + "Successfully updated the knockback multipliers!");
        return true;
    }
}
