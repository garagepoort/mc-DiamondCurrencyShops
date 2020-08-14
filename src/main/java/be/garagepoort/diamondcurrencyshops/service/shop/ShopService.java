package be.garagepoort.diamondcurrencyshops.service.shop;

import be.garagepoort.diamondcurrencyshops.common.BusinessException;
import be.garagepoort.diamondcurrencyshops.database.ShopChestRepository;
import be.garagepoort.diamondcurrencyshops.database.ShopItemRepository;
import be.garagepoort.diamondcurrencyshops.database.ShopRepository;
import be.garagepoort.diamondcurrencyshops.service.chest.ChestSelector;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.Optional;

public class ShopService {

    private static ShopService instance;

    private ShopService() {
    }

    public static ShopService getInstance() {
        if (instance == null) {
            instance = new ShopService();
        }
        return instance;
    }

    public Shop getShop(String name, Player player) {
        return ShopRepository.getInstance().findShopForOwner(name, player)
                .orElseThrow(() -> new BusinessException("You have no shop named: " + name));
    }

    public void createShop(String name, Player owner) {
        Optional<Shop> existingShop = ShopRepository.getInstance().findShop(name);
        if (existingShop.isPresent()) {
            throw new BusinessException("Cannot create the shop. A shop with this name already exists. Please choose another name.");
        }
        Shop shop = new Shop(name, owner.getUniqueId().toString(), owner.getName());
        ShopRepository.getInstance().saveShop(shop);
    }

    public void deleteShop(String name, Player owner) {
        Shop existingShop = ShopRepository.getInstance().findShopForOwner(name, owner)
                .orElseThrow(() -> new BusinessException("Cannot delete the shop. A shop with this name does not exist."));
        ShopItemRepository.getInstance().deleteItems(existingShop);
        ShopChestRepository.getInstance().deleteChests(existingShop);
        ShopRepository.getInstance().deleteShop(existingShop);
    }

    public void addChestsToShop(String name, Player owner) {
        Shop shop = getShop(name, owner);
        ShopChestRepository.getInstance().saveChests(shop, ChestSelector.getSelectedChestLocations(owner));
    }

    public List<Shop> getShops() {
        return ShopRepository.getInstance().getAllShops();
    }

    public Shop getShop(int id) {
        return ShopRepository.getInstance().findShopById(id)
                .orElseThrow(() -> new RuntimeException("No shop with id found: " + id));
    }
}
