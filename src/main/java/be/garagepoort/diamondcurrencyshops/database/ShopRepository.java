package be.garagepoort.diamondcurrencyshops.database;

import be.garagepoort.diamondcurrencyshops.common.DLogger;
import be.garagepoort.diamondcurrencyshops.service.shop.Shop;
import org.bukkit.entity.Player;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ShopRepository {

    private static ShopRepository instance;

    private ShopRepository() {}

    public static ShopRepository getInstance() {
        if(instance == null) {
            instance = new ShopRepository();
        }
        return instance;
    }

    public void saveShop(Shop shop) {
        String sql = "INSERT INTO shops(name, owner_id, owner_name) VALUES(?,?,?)";

        try (Connection conn = SqlLiteConnection.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, shop.getName());
            pstmt.setString(2, shop.getOwnerId());
            pstmt.setString(3, shop.getOwner());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            DLogger.logger.severe(e.getMessage());
        }
    }

    public Optional<Shop> findShop(String name) {
        String sql = "SELECT id, name, owner_id, owner_name FROM shops where name=?";

        try (Connection conn = SqlLiteConnection.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, name);
            ResultSet rs  = pstmt.executeQuery();

            boolean first = rs.next();
            if(!first) {
                return Optional.empty();
            }
            return Optional.of(new Shop(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4)));
        } catch (SQLException e) {
            DLogger.logger.severe(e.getMessage());
        }
        return null;
    }


    public Optional<Shop> findShopById(int id) {
        String sql = "SELECT id, name, owner_id, owner_name FROM shops where id=?";

        try (Connection conn = SqlLiteConnection.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            ResultSet rs  = pstmt.executeQuery();

            boolean first = rs.next();
            if(!first) {
                return Optional.empty();
            }
            return Optional.of(new Shop(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4)));
        } catch (SQLException e) {
            DLogger.logger.severe(e.getMessage());
        }
        return null;

    }

    public Optional<Shop> findShopForOwner(String name, Player owner) {
        String sql = "SELECT id, name, owner_id, owner_name FROM shops where name=? and owner_id = ?";

        try (Connection conn = SqlLiteConnection.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, name);
            pstmt.setString(2, owner.getUniqueId().toString());
            ResultSet rs  = pstmt.executeQuery();

            boolean first = rs.next();
            if(!first) {
                return Optional.empty();
            }
            return Optional.of(new Shop(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4)));
        } catch (SQLException e) {
            DLogger.logger.severe(e.getMessage());
        }
        return null;
    }

    public List<Shop> getAllShops() {
        String sql = "SELECT id, name, owner_id, owner_name FROM shops;";

        try (Connection conn = SqlLiteConnection.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            ResultSet rs  = pstmt.executeQuery();

            List<Shop> result = new ArrayList<>();
            while(rs.next()) {
                result.add(new Shop(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4)));
            }
            return result;
        } catch (SQLException e) {
            DLogger.logger.severe(e.getMessage());
        }
        return null;
    }

    public void deleteShop(Shop shop) {
        String sql = "DELETE FROM shops WHERE id = ?;";

        try (Connection conn = SqlLiteConnection.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, shop.getId());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            DLogger.logger.severe(e.getMessage());
        }
    }
}
