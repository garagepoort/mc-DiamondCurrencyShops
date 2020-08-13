package be.garagepoort.diamondcurrencyshops.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import org.bukkit.plugin.PluginLogger;

public class SqlLiteConnection {

    /**
     * Connect to a sample database
     * @return Connection conn
     */
    public static Connection connect(PluginLogger pluginLogger) {
        Connection conn = null;
        try {
            String url = "jdbc:sqlite:C:/sqlite/db/chinook.db";
            conn = DriverManager.getConnection(url);
        } catch (SQLException e) {
            pluginLogger.severe("An exception occurred while connecting sqlite: " + e.getMessage());
            System.out.println(e.getMessage());
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException ex) {
                pluginLogger.severe("An exception occurred while connecting sqlite: " + ex.getMessage());
            }
        }
        return conn;
    }
}
