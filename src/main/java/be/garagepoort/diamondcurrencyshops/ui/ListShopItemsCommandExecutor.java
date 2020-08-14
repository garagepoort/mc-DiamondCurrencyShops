package be.garagepoort.diamondcurrencyshops.ui;

import be.garagepoort.diamondcurrencyshops.MainJavaPlugin;
import be.garagepoort.diamondcurrencyshops.common.BusinessException;
import be.garagepoort.diamondcurrencyshops.database.ShopItemRepository;
import be.garagepoort.diamondcurrencyshops.database.ShopRepository;
import be.garagepoort.diamondcurrencyshops.service.shop.Shop;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import static be.garagepoort.diamondcurrencyshops.common.CommandUtil.executeCommand;
import static java.util.stream.Collectors.joining;

public class ListShopItemsCommandExecutor implements CommandExecutor {

    private MainJavaPlugin mainJavaPlugin;

    public ListShopItemsCommandExecutor(MainJavaPlugin mainJavaPlugin) {
        this.mainJavaPlugin = mainJavaPlugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        return executeCommand(sender, () -> {
            if(args.length < 1) {
                throw new BusinessException("Shopname must be provided");
            }

            if(sender instanceof Player){
                Shop shop = ShopRepository.getInstance().findShop(args[0]).orElseThrow(() -> new BusinessException("No Shop found with this name"));

                sender.sendMessage("This shop is selling:\n");
                String shopList = ShopItemRepository.getInstance().getItemsForShop(shop).stream()
                        .map(s -> s.getAmountOfItems() + "x of "+ s.getMaterial() +" for " + s.getCost() + " Diamonds")
                        .collect(joining("\n"));
                sender.sendMessage(shopList);
                return true;
            }
            sender.sendMessage("You must be a player!");
            return false;
        });
    }
}
