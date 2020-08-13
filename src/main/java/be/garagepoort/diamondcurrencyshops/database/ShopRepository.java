package be.garagepoort.diamondcurrencyshops.database;

import be.garagepoort.diamondcurrencyshops.DLogger;
import be.garagepoort.diamondcurrencyshops.service.Shop;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
        String sql = "INSERT INTO shops(name,owner_name) VALUES(?,?)";

        try (Connection conn = SqlLiteConnection.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, shop.getName());
            pstmt.setString(2, shop.getOwner());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            DLogger.logger.severe(e.getMessage());
        }
    }

    public Optional<Shop> findShop(String name) {
        String sql = "SELECT id, name, owner_name FROM shops where name=?";

        try (Connection conn = SqlLiteConnection.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, name);
            ResultSet rs  = pstmt.executeQuery();

            boolean first = rs.next();
            if(!first) {
                return Optional.empty();
            }
            return Optional.of(new Shop(rs.getInt(1), rs.getString(2), rs.getString(3)));
        } catch (SQLException e) {
            DLogger.logger.severe(e.getMessage());
        }
        return null;
    }
}
