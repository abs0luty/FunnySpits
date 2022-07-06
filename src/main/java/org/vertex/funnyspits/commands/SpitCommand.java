package org.vertex.funnyspits.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.vertex.funnyspits.FunnySpits;

public class SpitCommand implements CommandExecutor {
    public SpitCommand(FunnySpits plugin) {
        plugin.getCommand("spit").setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command,
                             String label, String[] args) {
        if (!(sender instanceof Player)) return false;

        Player player = (Player) sender;
        return Spit.spit(player);
    }
}
