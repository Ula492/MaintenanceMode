package com.ula.mm;

import com.ula.mm.GUI.mmgui;
import org.bukkit.Bukkit;
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

    public void onEnable(){
        Bukkit.getConsoleSender().sendMessage("§ePlugin MaintenanceMode Enabled v1.0");


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
