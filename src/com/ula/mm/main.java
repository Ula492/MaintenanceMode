package com.ula.mm;

import com.ula.mm.GUI.mmgui;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.FileConfigurationOptions;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class main extends JavaPlugin implements Listener{

    private static Plugin plugin;
    private static main instance;
    public static main getInstance() {
        return instance;
    }

   public  FileConfiguration config = getConfig();

    public void onEnable(){
        Bukkit.getConsoleSender().sendMessage("§ePlugin MaintenanceMode Enabled v1.0");


        config.addDefault("Countdown", 5);
        config.addDefault("MOTDOn", " - MaintenanceMode ON  -");
        config.addDefault("MOTDOff", " - MaintenanceMode OFF  -");
        config.addDefault("KickMessage", " §4§lServer has begun its Maintenance Mode, try re-joining later!");
        config.addDefault("FakePlayersON", 0);
        config.addDefault("FakePlayersOFF", 10000);
        config.options().copyDefaults(true);
        saveConfig();

        this.getServer().getMessenger().registerOutgoingPluginChannel((Plugin)this, "BungeeCord");
        plugin = this;
        instance = this;
        PluginManager pm = this.getServer().getPluginManager();
        pm.registerEvents(this, this);
        pm.registerEvents(new mmgui(this), this);
        this.getCommand("mm").setExecutor(new Commands());
    }

    public void onDisable(){

        Bukkit.getConsoleSender().sendMessage("§4Plugin MaintenanceMode Disabled v1.0");



    }

}
