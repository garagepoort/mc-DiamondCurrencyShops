package be.garagepoort.diamondcurrencyshops.ui;

import be.garagepoort.diamondcurrencyshops.MainJavaPlugin;
import be.garagepoort.diamondcurrencyshops.common.BusinessException;
import be.garagepoort.diamondcurrencyshops.service.item.ItemService;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import static be.garagepoort.diamondcurrencyshops.common.CommandUtil.executeCommand;

public class AddItemsCommandExecutor implements CommandExecutor {

    private MainJavaPlugin mainJavaPlugin;

    public AddItemsCommandExecutor(MainJavaPlugin mainJavaPlugin) {
        this.mainJavaPlugin = mainJavaPlugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        return executeCommand(sender, () -> {
            if(args.length != 4) {
                throw new BusinessException("Incorrect use of command");
            }

            if(sender instanceof Player){
                ItemService.getInstance().addItemToShop(args[0], (Player) sender, Material.getMaterial(args[1]), Integer.parseInt(args[2]), Integer.parseInt(args[3]));
                sender.sendMessage("Item successfully added to shop [" + args[0] + "]");
                return true;
            }
            sender.sendMessage("You must be a player!");
            return false;
        });
    }
}
