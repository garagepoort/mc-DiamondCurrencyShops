package be.garagepoort.diamondcurrencyshops.service.item;

import org.bukkit.Material;

public class ShopItem {

    public int id;
    public Material material;
    public int cost;
    public int amountOfItems;

    public ShopItem(Material material, int cost, int amountOfItems) {
        this.material = material;
        this.cost = cost;
        this.amountOfItems = amountOfItems;
    }

    public ShopItem(int id, Material material, int cost, int amountOfItems) {
        this.id = id;
        this.material = material;
        this.cost = cost;
        this.amountOfItems = amountOfItems;
    }

    public Material getMaterial() {
        return material;
    }

    public int getCost() {
        return cost;
    }

    public int getAmountOfItems() {
        return amountOfItems;
    }
}
