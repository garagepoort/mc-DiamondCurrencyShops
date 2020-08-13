package be.garagepoort.diamondcurrencyshops.service;

import java.util.List;
import org.bukkit.Location;
import org.bukkit.block.Chest;
import org.bukkit.block.DoubleChest;
import org.bukkit.entity.Player;

public class Shop {

    private int id;
    private String name;
    private String owner;
    private List<Chest> chests;

    public Shop(int id, String name, String owner) {
        this.id = id;
        this.name = name;
        this.owner = owner;
    }

    public Shop(String name, String owner) {
        this.name = name;
        this.owner = owner;
    }

    public String getName() {
        return name;
    }

    public String getOwner() {
        return owner;
    }

}
