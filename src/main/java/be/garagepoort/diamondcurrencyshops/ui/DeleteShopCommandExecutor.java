package be.garagepoort.diamondcurrencyshops.ui;

import be.garagepoort.diamondcurrencyshops.MainJavaPlugin;
import be.garagepoort.diamondcurrencyshops.common.BusinessException;
import be.garagepoort.diamondcurrencyshops.service.shop.ShopService;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import static be.garagepoort.diamondcurrencyshops.common.CommandUtil.executeCommand;

public class DeleteShopCommandExecutor implements CommandExecutor {

    private MainJavaPlugin mainJavaPlugin;

    public DeleteShopCommandExecutor(MainJavaPlugin mainJavaPlugin) {
        this.mainJavaPlugin = mainJavaPlugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        return executeCommand(sender, () -> {
            if(args.length < 1) {
                throw new BusinessException("Shopname must be provided");
            }

            if (sender instanceof Player) {
                ShopService.getInstance().deleteShop(args[0], (Player) sender);
                sender.sendMessage("Shop deleted");
                return true;
            }
            sender.sendMessage("You must be a player!");
            return false;
        });
    }
}
