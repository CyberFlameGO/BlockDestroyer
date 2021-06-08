package net.cyberflame.kpm;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import net.cyberflame.kpm.listeners.PlayerListener;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import net.cyberflame.kpm.command.BuildModeCommand;

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
        this.getCommand("buildmode").setExecutor(new BuildModeCommand());
        enabledBuild = new HashMap<UUID, Boolean>();
        disabledworlds = this.getConfig().getStringList("disabled-worlds");
        Bukkit.getPluginManager().registerEvents(new PlayerListener(this), this);
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
