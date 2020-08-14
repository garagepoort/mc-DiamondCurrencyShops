package be.garagepoort.diamondcurrencyshops.service.chest;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemHeldEvent;

public class CancelChestSelectionListener implements Listener {

    @EventHandler(priority = EventPriority.MONITOR)
    public void selectChestForShop(PlayerItemHeldEvent event) {
        Player player = event.getPlayer();
        if(ChestSelector.getSelectedChestLocations(player).isEmpty()) {
            return;
        }

        player.sendMessage("Cancelled selecting chests for shop");
        ChestSelector.clearSelection(player);
    }

}
