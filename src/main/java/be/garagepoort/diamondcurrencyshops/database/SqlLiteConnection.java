package be.garagepoort.diamondcurrencyshops.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import be.garagepoort.diamondcurrencyshops.DLogger;
import org.bukkit.plugin.PluginLogger;

import static be.garagepoort.diamondcurrencyshops.DLogger.logger;

public class SqlLiteConnection {

    /**
     * Connect to a sample database
     * @return Connection conn
     */
    public static Connection connect() throws SQLException {
        String url = "jdbc:sqlite:be.garagepoort.dshops.db";
        return DriverManager.getConnection(url);
    }
}
