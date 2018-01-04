package com.ula.mm.GUI;

import com.ula.mm.main;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class mmgui implements Listener{

    private final main plugin;

    private final String Prefix = "§b§lMaintenance Mode §m> §r ";
    public mmgui (main plugin){
        this.plugin = plugin;


    }

    public static void MMGUI(Player p){

        Inventory inv = Bukkit.createInventory(null, 9, "      §0§o§nMaintenance Mode Menu");

        ItemStack Emerald = new ItemStack(Material.EMERALD_BLOCK);
        ItemMeta EmeraldMeta = Emerald.getItemMeta();
        EmeraldMeta.setDisplayName("§a§lStart Maintenance Mode");
        Emerald.setItemMeta(EmeraldMeta);

        ItemStack RC = new ItemStack(Material.STAINED_CLAY, 1, (short)14);
        ItemMeta RCMeta = RC.getItemMeta();
        RCMeta.setDisplayName("§c§lStop Maintenance Mode");
        RC.setItemMeta(RCMeta);

        inv.setItem(0, Emerald);
        inv.setItem(2, RC);

        p.openInventory(inv);
    }

    @EventHandler
    public void ItemClick(InventoryClickEvent e){
        Player p = (Player)e.getWhoClicked();

        if(e.getInventory().getName() != "      §0§o§nMaintenance Mode Menu"){
            return;
        }
        if (e.getCurrentItem() == null || e.getCurrentItem().getType() == Material.AIR || !e.getCurrentItem().hasItemMeta()) {
            return;
        }

        if(e.getCurrentItem().getItemMeta().getDisplayName().contains("§a§lStart Maintenance Mode")){
            e.setCancelled(true);
            p.sendMessage(Prefix + "Start Button Works!");
        }
        if(e.getCurrentItem().getItemMeta().getDisplayName().contains("§c§lStop Maintenance Mode")){
            e.setCancelled(true);
            p.sendMessage(Prefix + "Stop Button Works!");
        }
    }


}
