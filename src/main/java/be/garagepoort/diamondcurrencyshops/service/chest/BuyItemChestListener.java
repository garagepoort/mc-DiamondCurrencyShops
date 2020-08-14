package be.garagepoort.diamondcurrencyshops.service.chest;

import be.garagepoort.diamondcurrencyshops.common.BusinessException;
import be.garagepoort.diamondcurrencyshops.database.ShopChestRepository;
import be.garagepoort.diamondcurrencyshops.service.item.ItemService;
import be.garagepoort.diamondcurrencyshops.service.shop.Shop;
import be.garagepoort.diamondcurrencyshops.service.shop.ShopService;
import org.bukkit.block.Chest;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.InventoryHolder;

import java.util.Optional;

import static be.garagepoort.diamondcurrencyshops.common.DLogger.logger;

public class BuyItemChestListener implements Listener {

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onInventoryClick(InventoryClickEvent event) {
        InventoryHolder holder = event.getClickedInventory().getHolder();
        logger.info("On inventory click shop listener fired");
        if (holder instanceof Chest) {
            Chest chest = (Chest) holder;
            Optional<ShopChest> shopChest = ShopChestRepository.getInstance().getChestByLocation(chest.getLocation());
            if (!shopChest.isPresent()) {
                logger.info("Opened chest is not a shop chest, event can continue");
                return;
            }

            handleShopChestEvent(event, chest, shopChest.get());
        } else {
            logger.info("Not a chest, event can continue");
        }
    }

    private void handleShopChestEvent(InventoryClickEvent event, Chest chest, ShopChest shopChest) {
        try {
            logger.info("Moving items from shopchest");
            Shop shop = ShopService.getInstance().getShop(shopChest.getShopId());

            Player initiatingPlayer = (Player) event.getWhoClicked();
            if (initiatingPlayer.getUniqueId().toString().equals(shop.getOwnerId())) {
                logger.info("Shop owner moving items. Event can continue");
            } else {
                logger.info("Another player moving items. Initiating BUY sequence");
                if(!event.getClick().isLeftClick()) {
                    event.setCancelled(true);
                    return;
                }
                if(event.getCurrentItem()==null) {
                    return;
                }

                ItemService.getInstance().buyItemFromShop(shop, chest, initiatingPlayer, event.getCurrentItem());
            }
        } catch (BusinessException e) {
            event.getWhoClicked().sendMessage(e.getMessage());
            logger.info("Buying failed with message: [" + e.getMessage() + "]");
            event.setCancelled(true);
        }
    }
}
