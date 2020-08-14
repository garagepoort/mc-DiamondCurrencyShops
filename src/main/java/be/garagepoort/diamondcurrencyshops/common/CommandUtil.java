package be.garagepoort.diamondcurrencyshops.common;

import org.bukkit.command.CommandSender;

public class CommandUtil {

    public static boolean executeCommand(CommandSender sender, CommandInterface commandInterface) {
        try {
            return commandInterface.execute();
        } catch (BusinessException e) {
            sender.sendMessage(e.getMessage());
            return false;
        }
    }
}
