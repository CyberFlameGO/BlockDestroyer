package net.cyberflame.kpm;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import net.cyberflame.kpm.listeners.*;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import net.cyberflame.kpm.commands.BuildModeCommand;

public class KPM extends JavaPlugin
{
	
	private static KPM plugin;
	private static List<String> disabledworlds;
	private static Map<UUID, Boolean> enabledBuild;

    @Override
    public void onEnable()
	{
    	this.plugin = this;
    	this.getConfig().options().copyDefaults(true);
        this.saveConfig();
        this.getCommand("buildmode").setExecutor(new BuildModeCommand(plugin));
        enabledBuild = new HashMap<UUID, Boolean>();
        disabledworlds = this.getConfig().getStringList("disabled-worlds");
		saveDefaultConfig();
		Bukkit.getServer().getPluginManager().registerEvents(new ArmorListener(getConfig().getStringList("blocked")), this);
        Bukkit.getPluginManager().registerEvents(new PlayerBlockPlaceListener(this), this);
		Bukkit.getPluginManager().registerEvents(new PlayerDeathListener(), this);
		Bukkit.getPluginManager().registerEvents(new PlayerProjectileLandListener(), this);
		Bukkit.getPluginManager().registerEvents(new PlayerUnequipListener(), this);
		Bukkit.getPluginManager().registerEvents(new SoupListener(), this);
        System.out.println("[KPM] Loaded");
    }
    
    public static KPM getInstance()
	{
    	return plugin;
    }
    
    public static List<String> getDisabledWorlds()
	{
    	return disabledworlds;
    }
    
    public static Map<UUID, Boolean> getEnabledBuild()
	{
    	return enabledBuild;
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
		if (enabledBuild.get(uuid) != null)
		{
			return true;
		}
		return false;
	}
}
