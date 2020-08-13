package be.garagepoort.diamondcurrencyshops.ui;

import be.garagepoort.diamondcurrencyshops.MainJavaPlugin;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class ListShopsCommandExecutor implements CommandExecutor {

    private MainJavaPlugin mainJavaPlugin;

    public ListShopsCommandExecutor(MainJavaPlugin mainJavaPlugin) {
        this.mainJavaPlugin = mainJavaPlugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        return false;
    }
}
