package be.garagepoort.diamondcurrencyshops.service;

import be.garagepoort.diamondcurrencyshops.database.ShopRepository;
import java.util.Optional;
import org.bukkit.entity.Player;

public class ShopService {

    private static ShopService instance;

    private ShopService() {}

    public static ShopService getInstance() {
        if(instance == null) {
            instance = new ShopService();
        }
        return instance;
    }

    public String createShop(String name, Player owner) {
        Optional<Shop> existingShop = ShopRepository.getInstance().findShop(name);
        if(existingShop.isPresent()) {
            return "Cannot create the shop. A shop with this name already exists. Please choose another name.";
        }
        Shop shop = new Shop(name, owner.getName());
        ShopRepository.getInstance().saveShop(shop);
        return null;
    }
}
