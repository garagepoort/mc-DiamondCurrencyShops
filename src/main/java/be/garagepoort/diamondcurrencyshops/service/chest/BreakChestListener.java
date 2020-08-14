package be.garagepoort.diamondcurrencyshops.service.chest;

import be.garagepoort.diamondcurrencyshops.database.ShopChestRepository;
import be.garagepoort.diamondcurrencyshops.service.shop.Shop;
import be.garagepoort.diamondcurrencyshops.service.shop.ShopService;
import org.bukkit.Material;
import org.bukkit.block.Chest;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

import java.util.Optional;

import static be.garagepoort.diamondcurrencyshops.common.DLogger.logger;

public class BreakChestListener implements Listener {

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onChestBreak(BlockBreakEvent event) {
        logger.info("On inventory click shop listener fired");
        if (event.getBlock().getType() == Material.CHEST) {
            Chest chest = (Chest) event.getBlock().getState();
            Optional<ShopChest> shopChest = ShopChestRepository.getInstance().getChestByLocation(chest.getLocation());
            if (shopChest.isPresent()) {
                Shop shop = ShopService.getInstance().getShop(shopChest.get().getShopId());
                if (!shop.getOwnerId().equals(event.getPlayer().getUniqueId().toString())) {
                    event.getPlayer().sendMessage("You are not allowed to break this chest. It belongs to a shop");
                    event.setCancelled(true);
                }
            }
        } else {
            logger.info("Not a chest, break event can continue");
        }
    }
}
