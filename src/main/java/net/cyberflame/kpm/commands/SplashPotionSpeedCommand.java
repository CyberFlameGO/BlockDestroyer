package net.cyberflame.kpm.commands;

import org.apache.commons.lang.math.NumberUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import net.cyberflame.kpm.KPM;


public class SplashPotionSpeedCommand implements CommandExecutor
{

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
    {
        if (sender.hasPermission(KPM.get().getConfig().get("permission").toString()))
            {
                if (args.length == 0)
                    {
                        sender.sendMessage("§cCorrect usage: /splashpotionspeed <get/set> <values>");
                    }
                else if (args.length >= 1)
                    {
                        if (args[0].equals("get"))
                            {
                                sender.sendMessage("§aSplashPotionSpeed values: " + KPM.get().getSPSValue());
                            }
                        else if (args[0].equals("set"))
                            {
                                if (args.length >= 2)
                                    {
                                        Double arg = NumberUtils.toDouble(args[1], -1D);

                                        if (arg < 0D)
                                            {
                                                sender.sendMessage("§cInvalid SplashPotionSpeed value.");
                                                return true;
                                            }

                                        sender.sendMessage("§aSplashPotionSpeed of " + Bukkit.getServerName() + " set to " + arg +
                                                           ".");
                                        KPM.get().setSPSTo(arg);
                                    }
                                else
                                    {
                                        sender.sendMessage("§cCorrect usage: /splashpotionspeed <set> <values>");
                                    }
                            }
                        else
                            {
                                sender.sendMessage("§cCorrect usage: /splashpotionspeed <get/set> <values>");
                            }
                    }
                else
                    {
                        sender.sendMessage("§cNo permission.");
                    }
            }
        return false;
    }
}