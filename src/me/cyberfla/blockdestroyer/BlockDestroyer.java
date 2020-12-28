package me.cyberfla.blockdestroyer;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.plugin.java.JavaPlugin;

import me.cyberfla.blockdestroyer.command.BuildModeCommand;
import net.minecraft.server.v1_8_R3.MinecraftServer;

public class BlockDestroyer extends JavaPlugin {
	
	private static BlockDestroyer plugin;
	private static List<String> disabledworlds;
	private static Map<UUID, Boolean> enabledBuild;

    @Override
    public void onEnable() {
    	this.plugin = this;
    	this.getConfig().options().copyDefaults(true);
        this.saveConfig();
        this.registerCommands();
        enabledBuild = new HashMap<UUID, Boolean>();
        disabledworlds = this.getConfig().getStringList("disabled-worlds");
        Bukkit.getPluginManager().registerEvents(new PlayerListener(this), this);
        System.out.println("[BlockDestroyer] Loaded");
    }
    
    public static BlockDestroyer getInstance() {
    	return plugin;
    }
    
    public static List<String> getDisabledWorlds() {
    	return disabledworlds;
    }
    
    public static Map<UUID, Boolean> getEnabledBuild() {
    	return enabledBuild;
    }
    
    private void registerCommands() {
		Arrays.asList(new BuildModeCommand(this)).forEach(command -> registerCommand(command, this.getName()));
	}
	
	public void registerCommand(Command cmd, String fallbackPrefix) {
		MinecraftServer.getServer().server.getCommandMap().register(cmd.getName(), fallbackPrefix, cmd);
	}
	
	public void setBuildEnabled(UUID uuid) {
		if (enabledBuild.get(uuid) != null) {
			enabledBuild.remove(uuid);
			return;
		}
		enabledBuild.put(uuid, true);
		return;
	}
	
	public boolean getBuildEnabled(UUID uuid) {
		if (enabledBuild.get(uuid) != null) {
			return true;
		}
		return false;
	}
}
