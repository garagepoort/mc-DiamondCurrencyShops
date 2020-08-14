package be.garagepoort.diamondcurrencyshops.service.chest;

import com.google.common.collect.Lists;
import org.bukkit.Location;
import org.bukkit.block.Chest;
import org.bukkit.entity.Player;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

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
        if(!selectedChests.containsKey(player.getUniqueId())) {
            return Collections.emptyList();
        }
        return selectedChests.get(player.getUniqueId());
    }

    public static void clearSelection(Player player) {
        selectedChests.remove(player.getUniqueId());
    }


}
