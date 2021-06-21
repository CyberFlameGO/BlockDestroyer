package net.cyberflame.kpm;

import net.cyberflame.kpm.commands.BuildModeCommand;
import net.cyberflame.kpm.commands.KPMCommand;
import net.cyberflame.kpm.commands.SplashPotionSpeedCommand;
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

    public static KPM get() {
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
        getConfig().options().copyDefaults(true);
        getConfig().addDefault("potionfall", 1.0);
        getConfig().addDefault("permission", "potionfall.command");
        saveConfig();
        this.getConfig().options().copyDefaults(true);
        this.saveConfig();
        enabledBuild = new HashMap<UUID, Boolean>();
        disabledWorlds = this.getConfig().getStringList("disabled-worlds");
        saveDefaultConfig();
        saveResource("config.yml", false);

        reloadConfig();

        registerListeners();
        registerCommands();

        final CommandSender console = this.getServer().getConsoleSender();
        console.sendMessage("Plugin enabled successfully!");
        console.sendMessage("Version: " + this.getDescription().getVersion());
    }

    private void registerListeners()
    {
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
        pm.registerEvents(new PlayerQuitListener(), this);
        pm.registerEvents(new PlayerTeleportListener(), this);
        pm.registerEvents(new PlayerUnequipListener(), this);
        pm.registerEvents(new ProjectileHitListener(), this);
        pm.registerEvents(new ProjectileLaunchListener(), this);

        System.out.println("[KPM] Registered events successfully.");
    }

    private void registerCommands()
    {
        this.getCommand("kpm").setExecutor(new KPMCommand());
        this.getCommand("buildmode").setExecutor(new BuildModeCommand(plugin));
        getCommand("splashpotionspeed").setExecutor(new SplashPotionSpeedCommand());
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

    public void setSPSTo(Double values) {
        getConfig().set("splashPotSpeed", values);
        saveConfig();
        reloadConfig();
    }

    public Double getSPSValue() {
        return getConfig().getDouble("splashPotSpeed");
    }

    @Override
    public void onDisable()
    {
        final CommandSender console = this.getServer().getConsoleSender();

        System.out.println(getEnabledBuild());
        console.sendMessage("KPM disabled.");
        plugin = null;
    }
}
