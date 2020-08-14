package be.garagepoort.diamondcurrencyshops.service.chest;

import org.bukkit.Location;

public class ShopChest {

    private int id;
    private int shopId;
    private Location location;

    public ShopChest(int id, int shopId, Location location) {
        this.id = id;
        this.shopId = shopId;
        this.location = location;
    }

    public int getId() {
        return id;
    }

    public int getShopId() {
        return shopId;
    }

    public Location getLocation() {
        return location;
    }
}
