package be.garagepoort.diamondcurrencyshops.service.chest;

import be.garagepoort.diamondcurrencyshops.common.BusinessException;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;

import java.util.Objects;

public class SelectChestListener implements Listener {

    @EventHandler(priority = EventPriority.NORMAL)
    public void selectChestForShop(PlayerInteractEvent event) {
        try {
            EquipmentSlot e = event.getHand();
            if (Objects.equals(e, EquipmentSlot.HAND)) {
                Block clickedBlock = event.getClickedBlock();
                if (clickedBlock == null) {
                    return;
                }

                Player player = event.getPlayer();
                ItemStack itemInMainHand = event.getPlayer().getInventory().getItemInMainHand();
                if (itemInMainHand.getType() == Material.GOLDEN_HOE && clickedBlock.getType() == Material.CHEST) {
                    Chest chest = (Chest) clickedBlock.getState();
                    ChestSelector.getInstance().addOrRemoveChest(player, chest);
                    event.getPlayer().sendMessage("To finish adding chests to shop execute the command: \"/addDchests [shopname]\"");
                }
            }
        } catch (BusinessException e) {
            event.getPlayer().sendMessage(e.getMessage());
        }
    }

}
