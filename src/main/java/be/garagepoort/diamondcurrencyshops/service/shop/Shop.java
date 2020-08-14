package be.garagepoort.diamondcurrencyshops.service.shop;

import org.bukkit.block.Chest;

import java.util.List;

public class Shop {

    private int id;
    private String name;
    private String owner;
    private String ownerId;
    private List<Chest> chests;

    public Shop(int id, String name, String ownerId, String owner) {
        this.id = id;
        this.name = name;
        this.ownerId = ownerId;
        this.owner = owner;
    }

    public Shop(String name, String ownerId, String owner) {
        this.name = name;
        this.owner = owner;
        this.ownerId = ownerId;
    }

    public String getName() {
        return name;
    }

    public String getOwner() {
        return owner;
    }

    public int getId() {
        return id;
    }

    public String getOwnerId() {
        return ownerId;
    }

    public List<Chest> getChests() {
        return chests;
    }

    public void setChests(List<Chest> chests) {
        this.chests = chests;
    }
}
