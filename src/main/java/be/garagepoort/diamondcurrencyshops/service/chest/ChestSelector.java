package be.garagepoort.diamondcurrencyshops.service.chest;

import be.garagepoort.diamondcurrencyshops.common.BusinessException;
import be.garagepoort.diamondcurrencyshops.database.ShopChestRepository;
import be.garagepoort.diamondcurrencyshops.service.shop.Shop;
import be.garagepoort.diamondcurrencyshops.service.shop.ShopService;
import com.google.common.collect.Lists;
import org.bukkit.Location;
import org.bukkit.block.Chest;
import org.bukkit.entity.Player;

import java.util.*;

public class ChestSelector {

    private static ChestSelector instance;
    private static HashMap<UUID, List<Location>> selectedChests = new HashMap<>();

    private ChestSelector() {
    }

    public static ChestSelector getInstance() {
        if (instance == null) {
            instance = new ChestSelector();
        }
        return instance;
    }

    public void addOrRemoveChest(Player player, Chest chest) {
        if (!selectedChests.containsKey(player.getUniqueId())) {
            selectedChests.put(player.getUniqueId(), Lists.newArrayList());
        }
        validateChest(player, chest);

        List<Location> chests = selectedChests.get(player.getUniqueId());
        if (chests.stream().anyMatch(c -> c.equals(chest.getLocation()))) {
            chests.remove(chest.getLocation());
            player.sendMessage("Chest unselected for shop.");
        } else {
            chests.add(chest.getLocation());
            player.sendMessage("Chest selected for shop.");
        }
    }

    public static List<Location> getSelectedChestLocations(Player player) {
        if (!selectedChests.containsKey(player.getUniqueId())) {
            return Collections.emptyList();
        }
        return selectedChests.get(player.getUniqueId());
    }

    public static void clearSelection(Player player) {
        selectedChests.remove(player.getUniqueId());
    }

    private void validateChest(Player player, Chest chest) {
        Optional<ShopChest> shopChest = ShopChestRepository.getInstance().getChestByLocation(chest.getLocation());
        if (shopChest.isPresent()) {
            Shop shop = ShopService.getInstance().getShop(shopChest.get().getShopId());
            if (!shop.getOwnerId().equals(player.getUniqueId().toString())) {
                throw new BusinessException("This chest belongs to another shop");
            }
        }
    }

}
