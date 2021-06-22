package net.cyberflame.kpm;

import net.cyberflame.kpm.commands.BuildModeCommand;
import net.cyberflame.kpm.commands.KPMCommand;
import net.cyberflame.kpm.commands.PingCommand;
import net.cyberflame.kpm.listeners.*;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.*;

public class KPM extends JavaPlugin
{

    private static KPM plugin;
    private static List<String> disabledWorlds;
    private static Map<UUID, Boolean> enabledBuild;
    private String POTION_NAME;
    private List<String> BLOCKED_EFFECTS;

    public Field fieldPlayerConnection;
    public Method sendPacket;
    public Constructor<?> packetVelocity;
    public String craftBukkitVersion;
    public double horMultiplier = 1D;
    public double verMultiplier = 1D;

    public static KPM getInstance()
    {
        return plugin;
    }

    public static KPM getPlugin()
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
        getConfig().options().copyDefaults(true);
        saveConfig();
        this.getConfig().options().copyDefaults(true);
        this.saveConfig();

        this.craftBukkitVersion = Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3];
        this.horMultiplier = getConfig().getDouble("knockback-multiplier.horizontal");
        this.verMultiplier = getConfig().getDouble("knockback-multiplier.vertical");
        if (getConfig().getString("potion-name") == null) {
            this.POTION_NAME = "&b%player%'s potion";
        } else {
            this.POTION_NAME = getConfig().getString("potion-name");
        }

        if (getConfig().getStringList("blocked-effects") == null) {
            this.BLOCKED_EFFECTS = new ArrayList<>();
        } else {
            this.BLOCKED_EFFECTS = getConfig().getStringList("blocked-effects");
        }
        getServer().getPluginManager().registerEvents(new EntityDeathListener(), this);

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
        pm.registerEvents(new PlayerDropItemListener(), this);
        pm.registerEvents(new PlayerInteractAtEntityListener(), this);
        pm.registerEvents(new PlayerInteractListener(), this);
        pm.registerEvents(new PlayerItemConsumeListener(), this);
        pm.registerEvents(new PlayerJoinListener(), this);
        pm.registerEvents(new PlayerUnequipListener(), this);

        if (KPM.getPlugin().getConfig().getBoolean("experimental-features"))
            {
                pm.registerEvents(new EntityDeathListener(), this);
                pm.registerEvents(new ProjectileHitListener(), this);
            }

        System.out.println("[KPM] Registered events successfully.");
    }

    private void registerCommands()
    {
        this.getCommand("kpm").setExecutor(new KPMCommand());
        this.getCommand("buildmode").setExecutor(new BuildModeCommand(plugin));
        getCommand("ping").setExecutor(new PingCommand());
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

    public String getPotionName() {
        return this.POTION_NAME;
    }

    public List<String> getBlockedEffects() {
        return this.BLOCKED_EFFECTS;
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
