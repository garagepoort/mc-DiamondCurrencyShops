package be.garagepoort.diamondcurrencyshops.ui;

import be.garagepoort.diamondcurrencyshops.MainJavaPlugin;
import be.garagepoort.diamondcurrencyshops.service.shop.ShopService;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import static be.garagepoort.diamondcurrencyshops.common.CommandUtil.executeCommand;
import static java.util.stream.Collectors.joining;

public class ListShopsCommandExecutor implements CommandExecutor {

    private MainJavaPlugin mainJavaPlugin;

    public ListShopsCommandExecutor(MainJavaPlugin mainJavaPlugin) {
        this.mainJavaPlugin = mainJavaPlugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        return executeCommand(sender, () -> {
            if(sender instanceof Player){
                String shopList = ShopService.getInstance().getShops().stream()
                        .map(s -> "Shop: " + s.getName() + " - Owner: " + s.getOwner())
                        .collect(joining("\n"));
                sender.sendMessage(shopList);
                return true;
            }
            sender.sendMessage("You must be a player!");
            return false;
        });
    }
}
