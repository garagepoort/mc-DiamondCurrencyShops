package be.garagepoort.diamondcurrencyshops.database;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import org.bukkit.plugin.PluginLogger;

public class SqlMigrations {

    private static SqlMigrations instance;

    private SqlMigrations() {
    }

    public static SqlMigrations getInstance() {
        if (instance == null) {
            instance = new SqlMigrations();
        }
        return instance;
    }

    public void createMigrationTable(PluginLogger pluginLogger) {
        Connection connect = SqlLiteConnection.connect(pluginLogger);

        // SQL statement for creating a new table
        String sql = "CREATE TABLE IF NOT EXISTS shops (\n"
                + "	name varchar PRIMARY KEY,\n"
                + "	player_name varchar NOT NULL,\n"
                + "	x integer, \n"
                + "	y integer, \n"
                + "	z integer \n"
                + ");";

        try {
            Statement stmt = connect.createStatement();
            stmt.execute(sql);
        } catch (SQLException e) {
            pluginLogger.severe("Failure creating migration table: " + e.getMessage());
        }

    }
