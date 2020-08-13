package be.garagepoort.diamondcurrencyshops.migrations;

import java.sql.*;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import be.garagepoort.diamondcurrencyshops.DLogger;
import be.garagepoort.diamondcurrencyshops.database.SqlLiteConnection;

public class SqlMigrations {

    private static SqlMigrations instance;
    private static List<Migration> migrations = Arrays.asList(new CreateShopTableMigration());

    private SqlMigrations() {
    }

    public static SqlMigrations getInstance() {
        if (instance == null) {
            instance = new SqlMigrations();
        }
        return instance;
    }

    public void createMigrationTable() {
        try (Connection connect = SqlLiteConnection.connect()) {
            DLogger.logger.info("Creating migration table");

            // SQL statement for creating a new table
            String sql = "CREATE TABLE IF NOT EXISTS migrations (\n"
                    + "	id integer PRIMARY KEY,\n"
                    + "	version integer NOT NULL\n"
                    + ");";
            Statement stmt = connect.createStatement();
            stmt.execute(sql);
        } catch (SQLException e) {
            DLogger.logger.severe("Failure creating migration table: " + e.getMessage());
        }

    }

    public void runMigrations() {
        try (Connection connect = SqlLiteConnection.connect()) {
            DLogger.logger.info("Starting migrations");
            connect.setAutoCommit(false);
            int maxVersion = getMaxVersion();

            List<Migration> validMigrations = migrations.stream().filter(m -> m.getVersion() > maxVersion)
                    .sorted(Comparator.comparingInt(Migration::getVersion))
                    .collect(Collectors.toList());

            for (Migration migration : validMigrations) {
                String sql = migration.getStatement();
                Statement stmt = connect.createStatement();
                stmt.execute(sql);

                PreparedStatement migrationStatement = connect.prepareStatement("INSERT INTO migrations (version) VALUES (?);");
                migrationStatement.setInt(1, migration.getVersion());
                migrationStatement.execute();

                connect.commit();
            }
            // SQL statement for creating a new table
        } catch (SQLException e) {
            DLogger.logger.severe("Failure executing migrations: " + e.getMessage());
        }
    }

    private int getMaxVersion() {
        try (Connection connect = SqlLiteConnection.connect()) {
            Statement stmt = connect.createStatement();
            ResultSet resultSet = stmt.executeQuery("SELECT max(version) as max from migrations");
            int max = resultSet.next() ? resultSet.getInt("max") : 0;
            DLogger.logger.info("Latest migration version = " + max);
            return max;

            // SQL statement for creating a new table
        } catch (SQLException e) {
            DLogger.logger.severe("Failure retrieving max migration version: " + e.getMessage());
        }
        return 0;
    }
}
