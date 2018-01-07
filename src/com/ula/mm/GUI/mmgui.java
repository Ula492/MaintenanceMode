package com.ula.mm.GUI;

import com.ula.mm.Utils.PacketUtils;
import com.ula.mm.main;
import net.minecraft.server.v1_12_R1.MinecraftServer;
import org.bukkit.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.server.BroadcastMessageEvent;
import org.bukkit.event.server.ServerListPingEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;

public class mmgui implements Listener{

    private final main plugin;

    private final String Prefix = "§b§lMaintenance Mode §m> §r ";
    public mmgui (main plugin){
        this.plugin = plugin;


    }

    private boolean sList = false;



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
    public void ItemClick(InventoryClickEvent e) {
        Player p = (Player) e.getWhoClicked();

        if (e.getInventory().getName() != "      §0§o§nMaintenance Mode Menu") {
            return;
        }
        if (e.getCurrentItem() == null || e.getCurrentItem().getType() == Material.AIR || !e.getCurrentItem().hasItemMeta()) {
            return;
        }

        if (e.getCurrentItem().getItemMeta().getDisplayName().contains("§a§lStart Maintenance Mode")) {
            e.setCancelled(true);
            p.closeInventory();
            if(!sList) {

                new BukkitRunnable() {
                    int countdown = main.getInstance().config.getInt("Countdown"); //Seconds to countDown from

                    @Override
                    public void run() {
                        if (countdown <= 0) { //Lets make sure the player is online just in-case this causes lag!
                            ///////////////////////////////////////
                            //Now we add everything that will run after the timer is done;
                            // - Whitelist - Done 1/06/18
                            // - MOTD - Done 1/07/18
                            // - Whitelist Join Message - Done 1/06/18
                            // - Player Count set to 0 - Done 1/07/18
                            // - Config/Custom Messages.
                            ///////////////////////////////////////

                            Bukkit.setWhitelist(true);

                            if (p.isWhitelisted()) {
                                Bukkit.broadcastMessage(Prefix + " " + Bukkit.getWhitelistedPlayers().size() + " whitelisted players are allowed to join the server while in Maintenance Mode, make sure that you check the list of players you want to allow to join during this session!");
                            } else {
                                p.kickPlayer(main.getInstance().config.getString("KickMessage"));
                            }

                            sList = true;

                            this.cancel();//cancel the task cus 1) The player left or 2) countdown has = 0 and some players were not kicked because they are whitelisted.
                            return;//Always exit the method :p

                        } else if (countdown > 0) {
                            p.playSound(p.getLocation(), Sound.BLOCK_ANVIL_LAND, 0.1F, 0.1F);
                            p.sendTitle("§4MaintenanceMode", "§f§l" + countdown + "s" + " §a§lUntil Maintenance starts", 0, 50, 20);
                            countdown--;
                        }
                    }
                }.runTaskTimer(plugin, 0, 20);//Repeating task with 0 ticks initial delay, run once per 20 ticks (one second).
            }else{
                p.sendMessage("Sir, MaintenanceMode is already enabled!");
            }
        }

        if(e.getCurrentItem().getItemMeta().getDisplayName().contains("§c§lStop Maintenance Mode")){
            e.setCancelled(true);
            p.closeInventory();
            if(sList) {
                p.playSound(p.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 0.1F, 0.1F);
                p.sendTitle("§4MaintenanceMode", " §a§lEnded!", 0, 50, 20);
                Bukkit.setWhitelist(false);
                sList = false;
            }else{
                p.sendMessage("Sir, MaintenanceMode is already disabled!");
            }
        }
    }

    @EventHandler
    public void List(ServerListPingEvent ev){
        if(sList == true){

            ev.setMotd(main.getInstance().config.getString("MOTDOn"));
            ev.setMaxPlayers(main.getInstance().config.getInt("FakePlayersON"));

        }else{
            ev.setMaxPlayers(main.getInstance().config.getInt("FakePlayersOFF"));
            ev.setMotd(main.getInstance().config.getString("MOTDOff"));

        }
    }


}
