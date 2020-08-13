package be.garagepoort.diamondcurrencyshops.service;

import java.util.List;
import org.bukkit.Location;
import org.bukkit.block.Chest;
import org.bukkit.block.DoubleChest;
import org.bukkit.entity.Player;

public class Shop {

    private String name;
    private Location location;
    private Player player;
    private List<Chest> chests;

    public Shop(String name, Location location, Player player) {
        this.name = name;
        this.location = location;
        this.player = player;
    }
}
