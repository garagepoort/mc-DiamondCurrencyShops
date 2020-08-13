package be.garagepoort.diamondcurrencyshops.ui;

import be.garagepoort.diamondcurrencyshops.MainJavaPlugin;
import be.garagepoort.diamondcurrencyshops.database.ShopRepository;
import be.garagepoort.diamondcurrencyshops.service.Shop;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CreateShopCommandExecutor implements CommandExecutor {

    private MainJavaPlugin mainJavaPlugin;

    public CreateShopCommandExecutor(MainJavaPlugin mainJavaPlugin) {
        this.mainJavaPlugin = mainJavaPlugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender instanceof Player){
            ShopRepository.getInstance().saveShop(new Shop(args[0], ((Player) sender).getLocation(), (Player) sender));
        }
        return false;
    }
}
