package be.garagepoort.diamondcurrencyshops.database;

import be.garagepoort.diamondcurrencyshops.common.DLogger;
import be.garagepoort.diamondcurrencyshops.service.chest.ShopChest;
import be.garagepoort.diamondcurrencyshops.service.shop.Shop;
import org.bukkit.Bukkit;
import org.bukkit.Location;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class ShopChestRepository {

    private static ShopChestRepository instance;

    private ShopChestRepository() {}

    public static ShopChestRepository getInstance() {
        if(instance == null) {
            instance = new ShopChestRepository();
        }
        return instance;
    }

    public void saveChests(Shop shop, List<Location> chests) {
        String sql = "INSERT INTO shop_chests(shop_id, chest_x, chest_y, chest_z, chest_world) VALUES(?,?,?,?,?)";

        try (Connection conn = SqlLiteConnection.connect()) {
            conn.setAutoCommit(false);
            for (Location chest : chests) {
                PreparedStatement pstmt = conn.prepareStatement(sql);
                pstmt.setInt(1, shop.getId());
                pstmt.setInt(2, chest.getBlockX());
                pstmt.setInt(3, chest.getBlockY());
                pstmt.setInt(4, chest.getBlockZ());
                pstmt.setString(5, chest.getWorld().getUID().toString());
                pstmt.executeUpdate();
            }
            conn.commit();
        } catch (SQLException e) {
            DLogger.logger.severe(e.getMessage());
        }
    }

    public List<Location> getAllChests(Shop shop) {
        String sql = "SELECT chest_x, chest_y, chest_z, chest_world FROM shop_chests WHERE shop_id = ?;";

        try (Connection conn = SqlLiteConnection.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, shop.getId());

            ResultSet rs  = pstmt.executeQuery();
            List<Location> result = new ArrayList<>();
            while(rs.next()) {
                result.add(new Location(Bukkit.getWorld(UUID.fromString(rs.getString(4))), rs.getDouble(1), rs.getDouble(2), rs.getDouble(3)));
            }
            return result;
        } catch (SQLException e) {
            DLogger.logger.severe(e.getMessage());
        }
        return null;
    }

    public Optional<ShopChest> getChestByLocation(Location location) {
        String sql = "SELECT id, shop_id, chest_x, chest_y, chest_z, chest_world FROM shop_chests WHERE chest_x = ? AND chest_y = ? AND chest_z = ? AND chest_world = ?;";

        try (Connection conn = SqlLiteConnection.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, location.getBlockX());
            pstmt.setInt(2, location.getBlockY());
            pstmt.setInt(3, location.getBlockZ());
            pstmt.setString(4, location.getWorld().getUID().toString());

            ResultSet rs  = pstmt.executeQuery();

            boolean first = rs.next();
            if(!first) {
                return Optional.empty();
            }
            Location chestLocation = new Location(Bukkit.getWorld(UUID.fromString(rs.getString(6))), rs.getDouble(3), rs.getDouble(4), rs.getDouble(5));

            return Optional.of(new ShopChest(rs.getInt(1), rs.getInt(2), chestLocation));
        } catch (SQLException e) {
            DLogger.logger.severe(e.getMessage());
        }
        return null;
    }
}
