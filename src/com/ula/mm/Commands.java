package com.ula.mm;

import com.ula.mm.GUI.mmgui;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Commands implements CommandExecutor {

    public boolean onCommand(final CommandSender cs, Command cmd, String label, final String[] args) {
        if (cs instanceof Player) {
            Player p = (Player) cs;
            if (cmd.getName().equalsIgnoreCase("mm")) {
                if (p.hasPermission("mm.menu")) {
                    mmgui.MMGUI(p);
                }
            }
        }
        return true;
    }
}
