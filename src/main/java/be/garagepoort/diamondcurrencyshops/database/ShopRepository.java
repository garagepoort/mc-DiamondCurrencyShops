package be.garagepoort.diamondcurrencyshops.database;

import be.garagepoort.diamondcurrencyshops.service.Shop;
import java.util.Optional;

public class ShopRepository {

    private static ShopRepository instance;

    private ShopRepository() {}

    public static ShopRepository getInstance() {
        if(instance == null) {
            instance = new ShopRepository();
        }
        return instance;
    }

    public void saveShop(Shop shop) {

    }

    public Optional<Shop> findShop(String name) {

    }
}
