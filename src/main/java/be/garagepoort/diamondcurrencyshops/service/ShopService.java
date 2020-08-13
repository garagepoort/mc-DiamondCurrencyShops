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

    public String createShop(Player player, String name) {
        Optional<Shop> existingShop = ShopRepository.getInstance().findShop(name);
        if(existingShop.isPresent()) {
            return "Cannot create the shop. A shop with this name already exists. Please choose another name.";
        }
        Shop shop = new Shop();

        return null;
    }
}
