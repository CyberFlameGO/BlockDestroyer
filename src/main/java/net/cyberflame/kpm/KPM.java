package net.cyberflame.kpm;

import net.cyberflame.kpm.commands.*;
import net.cyberflame.kpm.listeners.*;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Pattern;

public class KPM extends JavaPlugin
{

    private static KPM plugin;
    private static List<String> disabledWorlds;
    private static Map<UUID, Boolean> enabledBuild;
    public BukkitTask autoFix;

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

    public static KPM getPlugin() {
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
        getConfig().addDefault("knockback-multiplier.horizontal", 1D);
        getConfig().addDefault("knockback-multiplier.vertical", 1D);
        saveConfig();
        this.getConfig().options().copyDefaults(true);
        this.saveConfig();

        this.craftBukkitVersion = Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3];
        this.horMultiplier = getConfig().getDouble("knockback-multiplier.horizontal");
        this.verMultiplier = getConfig().getDouble("knockback-multiplier.vertical");

        enabledBuild = new HashMap<UUID, Boolean>();
        disabledWorlds = this.getConfig().getStringList("disabled-worlds");
        saveDefaultConfig();
        saveResource("config.yml", false);

        reloadConfig();

        if (getConfig().getBoolean("auto-fix"))
            {
                autoFix();
            }

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
        pm.registerEvents(new PlayerInteractAtEntityListener(), this);
        pm.registerEvents(new PlayerInteractListener(), this);
        pm.registerEvents(new PlayerItemConsumeListener(), this);;
        pm.registerEvents(new PlayerJoinListener(), this);
        pm.registerEvents(new PlayerQuitListener(), this);
        pm.registerEvents(new PlayerTeleportListener(), this);
        pm.registerEvents(new PlayerUnequipListener(), this);
        pm.registerEvents(new PlayerVelocityListener(), this);
        pm.registerEvents(new PotionSplashListener(), this);
        pm.registerEvents(new ProjectileHitListener(), this);
        pm.registerEvents(new ProjectileLaunchListener(), this);

        System.out.println("[KPM] Registered events successfully.");
    }

    private void registerCommands()
    {
        this.getCommand("kpm").setExecutor(new KPMCommand());
        this.getCommand("buildmode").setExecutor(new BuildModeCommand(plugin));
        getCommand("splashpotionspeed").setExecutor(new SplashPotionSpeedCommand());
        getCommand("ping").setExecutor(new PingCommand());
        getCommand("setknockback").setExecutor(new SetKnockbackCommand());
        getCommand("characterfix").setExecutor(new CharacterFixCommand());
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

    public static int getPingReflection(Player player) throws Exception {
        int ping = 0;
        Class<?> craftPlayer = Class.forName("org.bukkit.craftbukkit." + getServerVersion() + "entity.CraftPlayer");
        Object converted = craftPlayer.cast(player);
        Method handle = converted.getClass().getMethod("getHandle");
        Object entityPlayer = handle.invoke(converted);
        Field pingField = entityPlayer.getClass().getField("ping");
        ping = pingField.getInt(entityPlayer);
        return ping;
    }

    public static String getServerVersion() {
        Pattern brand = Pattern.compile("(v|)[0-9][_.][0-9][_.][R0-9]*");
        String version = null;
        String pkg = Bukkit.getServer().getClass().getPackage().getName();
        String version0 = pkg.substring(pkg.lastIndexOf('.') + 1);
        if (!brand.matcher(version0).matches()) {
            version0 = "";
        }
        version = version0;
        return !"".equals(version) ? version + "." : "";
    }

    public String getCraftBukkitVersion() {
        return craftBukkitVersion;
    }

    public double getHorMultiplier() {
        return horMultiplier;
    }

    public void setHorMultiplier(double horMultiplier) {
        this.horMultiplier = horMultiplier;
    }

    public double getVerMultiplier() {
        return verMultiplier;
    }

    public void setVerMultiplier(double verMultiplier) {
        this.verMultiplier = verMultiplier;
    }

    public void damageListener() {
        try {
            Class<?> entityPlayerClass =
                    Class.forName("net.minecraft.server." + KPM.getInstance().getCraftBukkitVersion() +
                                  ".EntityPlayer");
            Class<?> packetVelocityClass =
                    Class.forName("net.minecraft.server." + KPM.getInstance().getCraftBukkitVersion() +
                                  ".PacketPlayOutEntityVelocity");
            Class<?> playerConnectionClass =
                    Class.forName("net.minecraft.server." + KPM.getInstance().getCraftBukkitVersion() +
                                  ".PlayerConnection");

            // Get the fields here to improve performance later on
            this.fieldPlayerConnection = entityPlayerClass.getField("playerConnection");
            this.sendPacket = playerConnectionClass.getMethod("sendPacket", packetVelocityClass.getSuperclass());
            this.packetVelocity = packetVelocityClass.getConstructor(int.class, double.class, double.class, double.class);
        } catch (ClassNotFoundException | NoSuchFieldException | SecurityException | NoSuchMethodException e) {
            e.printStackTrace();
        }
    }

    public void autoFix() {
        this.autoFix = (new BukkitRunnable() { public void run() { byte b; int i;
            Player[] arrayOfPlayer;
            for (i = (arrayOfPlayer = Bukkit.getOnlinePlayers().toArray(new Player[0])).length, b = 0; b < i; ) { Player pl = arrayOfPlayer[b];
                KPM.this.fix(pl);
                b++; }
        } }
        ).runTaskTimer(this, 0L, (getConfig().getInt("auto-fix-time") * 20));
    }


    public void fix(Player pl, Player fixer) {
        if (pl.hasMetadata("Vanish"))
            return;  fixer.hidePlayer(pl);
        fixer.showPlayer(pl);
    }


    public void fix(Player p) {
        if (p.hasMetadata("Vanish"))
            return;  byte b; int i; Player[] arrayOfPlayer; for (i = (arrayOfPlayer = Bukkit.getOnlinePlayers().toArray(new Player[0])).length, b = 0; b < i; ) { Player pl = arrayOfPlayer[b];
            pl.hidePlayer(p);
            pl.showPlayer(p);
            b++; }

    }


}
