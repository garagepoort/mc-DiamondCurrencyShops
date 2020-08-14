package be.garagepoort.diamondcurrencyshops.database;

import be.garagepoort.diamondcurrencyshops.common.DLogger;
import be.garagepoort.diamondcurrencyshops.service.item.ShopItem;
import be.garagepoort.diamondcurrencyshops.service.shop.Shop;
import org.bukkit.Material;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ShopItemRepository {

    private static ShopItemRepository instance;

    private ShopItemRepository() {
    }

    public static ShopItemRepository getInstance() {
        if (instance == null) {
            instance = new ShopItemRepository();
        }
        return instance;
    }

    public void saveItem(Shop shop, ShopItem shopItem) {
        String sql = "INSERT INTO shop_items(shop_id, material, cost, amount_of_items) VALUES(?,?,?,?)";

        try (Connection conn = SqlLiteConnection.connect()) {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, shop.getId());
            pstmt.setString(2, shopItem.getMaterial().name());
            pstmt.setInt(3, shopItem.getCost());
            pstmt.setInt(4, shopItem.getAmountOfItems());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            DLogger.logger.severe(e.getMessage());
        }
    }

    public Optional<ShopItem> getItemFromShop(Shop shop, Material material) {
        String sql = "SELECT id, material, cost, amount_of_items FROM shop_items WHERE shop_id = ? AND material = ?;";

        try (Connection conn = SqlLiteConnection.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, shop.getId());
            pstmt.setString(2, material.name());

            ResultSet rs = pstmt.executeQuery();

            boolean first = rs.next();
            if(!first) {
                return Optional.empty();
            }
            return Optional.of(new ShopItem(rs.getInt(3), Material.getMaterial(rs.getString(2)), rs.getInt(3), rs.getInt(4)));
        } catch (SQLException e) {
            DLogger.logger.severe(e.getMessage());
        }
        return null;
    }

    public void deleteItems(Shop shop) {
        String sql = "DELETE FROM shop_items WHERE shop_id = ?;";

        try (Connection conn = SqlLiteConnection.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, shop.getId());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            DLogger.logger.severe(e.getMessage());
        }
    }

    public List<ShopItem> getItemsForShop(Shop shop) {
        String sql = "SELECT id, material, cost, amount_of_items FROM shop_items WHERE shop_id = ?;";

        try (Connection conn = SqlLiteConnection.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, shop.getId());

            ResultSet rs = pstmt.executeQuery();

            List<ShopItem> result = new ArrayList<>();
            while(rs.next()) {
                result.add(new ShopItem(rs.getInt(3), Material.getMaterial(rs.getString(2)), rs.getInt(3), rs.getInt(4)));
            }
            return result;
        } catch (SQLException e) {
            DLogger.logger.severe(e.getMessage());
        }
        return null;
    }
}
