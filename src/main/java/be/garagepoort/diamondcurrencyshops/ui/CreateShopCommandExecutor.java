package be.garagepoort.diamondcurrencyshops.ui;

import be.garagepoort.diamondcurrencyshops.MainJavaPlugin;
import be.garagepoort.diamondcurrencyshops.database.ShopRepository;
import be.garagepoort.diamondcurrencyshops.service.Shop;
import be.garagepoort.diamondcurrencyshops.service.ShopService;
import org.apache.commons.lang.StringUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.util.StringUtil;

public class CreateShopCommandExecutor implements CommandExecutor {

    private MainJavaPlugin mainJavaPlugin;

    public CreateShopCommandExecutor(MainJavaPlugin mainJavaPlugin) {
        this.mainJavaPlugin = mainJavaPlugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender instanceof Player){
            String errorMessage = ShopService.getInstance().createShop(args[0], (Player) sender);
            if(StringUtils.isNotEmpty(errorMessage)) {
                sender.sendMessage(errorMessage);
                return false;
            }
            sender.sendMessage("Shop created");
            return true;
        }
        sender.sendMessage("You must be a player!");
        return false;
    }
}
