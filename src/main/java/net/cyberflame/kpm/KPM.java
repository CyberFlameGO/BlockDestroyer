package net.cyberflame.kpm;

import net.cyberflame.kpm.commands.BuildModeCommand;
import net.cyberflame.kpm.listeners.*;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class KPM extends JavaPlugin
{

    private static KPM plugin;
    private static List<String> disabledWorlds;
    private static Map<UUID, Boolean> enabledBuild;

    public static KPM getInstance()
    {
        return plugin;
    }

    public static List<String> getDisabledWorlds()
    {
        return disabledWorlds;
    }

    /**
     * @return
     */
    public static Map<UUID, Boolean> getEnabledBuild()
    {
        return enabledBuild;
    }

    @Override
    public void onEnable()
    {
        plugin = this;
        this.getConfig().options().copyDefaults(true);
        this.saveConfig();
        this.getCommand("buildmode").setExecutor(new BuildModeCommand(plugin));
        enabledBuild = new HashMap<UUID, Boolean>();
        disabledWorlds = this.getConfig().getStringList("disabled-worlds");
        saveDefaultConfig();
        saveResource("config.yml", false);

        PluginManager pm = Bukkit.getPluginManager();

        Bukkit.getServer().getPluginManager().registerEvents(new ArmorListener(getConfig().getStringList("blocked")), this);
        pm.registerEvents(new BlockPlaceListener(this), this);
        pm.registerEvents(new EntityDamageByEntityListener(this), this);
        //pm.registerEvents(new PlayerDeathListener(), this);
        pm.registerEvents(new PlayerDropItemListener(), this);
        pm.registerEvents(new PlayerItemConsumeListener(), this);
        pm.registerEvents(new PlayerInteractAtEntityListener(), this);
        pm.registerEvents(new PlayerInteractListener(), this);
        pm.registerEvents(new PlayerJoinListener(), this);
        pm.registerEvents(new PlayerTeleportListener(), this);
        pm.registerEvents(new PlayerUnequipListener(), this);
        pm.registerEvents(new ProjectileHitListener(), this);
        pm.registerEvents(new ProjectileLaunchListener(), this);
        System.out.println("[KPM] Registered events successfully.");

        final CommandSender console = this.getServer().getConsoleSender();
        console.sendMessage("Plugin enabled successfully!");
        console.sendMessage("Version: " + this.getDescription().getVersion());
    }

    public void setBuildEnabled(UUID uuid)
    {
        if (enabledBuild.get(uuid) != null)
            {
                enabledBuild.remove(uuid);
                return;
            }
        enabledBuild.put(uuid, true);
        return;
    }

    public boolean getBuildEnabled(UUID uuid)
    {
        return enabledBuild.get(uuid) != null;
    }

    @Override
    public void onDisable()
    {
        final CommandSender console = this.getServer().getConsoleSender();
        console.sendMessage("KPM disabled.");
        System.out.println(getEnabledBuild());
    }
}
