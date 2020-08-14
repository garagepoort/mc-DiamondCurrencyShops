package be.garagepoort.diamondcurrencyshops.service.item;

import be.garagepoort.diamondcurrencyshops.database.ShopItemRepository;
import be.garagepoort.diamondcurrencyshops.common.BusinessException;
import be.garagepoort.diamondcurrencyshops.service.shop.Shop;
import be.garagepoort.diamondcurrencyshops.service.shop.ShopService;
import org.bukkit.Material;
import org.bukkit.block.Chest;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Optional;

public class ItemService {

    private static ItemService instance;

    private ItemService() {
    }

    public static ItemService getInstance() {
        if (instance == null) {
            instance = new ItemService();
        }
        return instance;
    }

    public void addItemToShop(String name, Player player, Material material, int cost, int amountOfItems) {
        if (cost < 1) {
            throw new BusinessException("Cost of the items must be at least 1 diamond");
        }
        if (amountOfItems < 1) {
            throw new BusinessException("The amount of items per sale must be at least 1");
        }

        Shop shop = ShopService.getInstance().getShop(name, player);
        Optional<ShopItem> itemFromShop = ShopItemRepository.getInstance().getItemFromShop(shop, material);
        if (itemFromShop.isPresent()) {
            throw new BusinessException("Unable to add item [" + material + "]. Item is already configured in shop. Try updating the item price using /itemDupdate");
        }

        ShopItemRepository.getInstance().saveItem(shop, new ShopItem(material, cost, amountOfItems));
    }

    public void buyItemFromShop(Shop shop, Chest chest, Player player, ItemStack itemStack) {
        ShopItem itemFromShop = ShopItemRepository.getInstance().getItemFromShop(shop, itemStack.getType()).orElseThrow(() -> new BusinessException("This shop is not selling: [" + itemStack.getType() + "]"));

        validateDiamonds(player, itemFromShop);
        ItemStack diamonds = player.getInventory().getItemInMainHand();
        diamonds.setAmount(diamonds.getAmount() - itemFromShop.getCost());
        chest.getInventory().addItem(new ItemStack(Material.DIAMOND, itemFromShop.getCost()));
    }

    private void validateDiamonds(Player player, ShopItem itemFromShop) {
        ItemStack itemInMainHand = player.getInventory().getItemInMainHand();
        if (itemInMainHand.getType() != Material.DIAMOND) {
            throw new BusinessException("You must be holding diamonds to buy at the shop");
        }

        if (itemFromShop.getCost() > itemInMainHand.getAmount()) {
            throw new BusinessException("You are not holding enough diamonds to buy this amount of items. Total cost of diamonds is [" + itemFromShop.getCost() + "]");
        }
    }
}
