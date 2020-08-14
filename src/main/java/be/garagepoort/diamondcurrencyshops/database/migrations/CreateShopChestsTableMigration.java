package be.garagepoort.diamondcurrencyshops.database.migrations;

public class CreateShopChestsTableMigration implements Migration {
    @Override
    public String getStatement() {
        return "CREATE TABLE IF NOT EXISTS shop_chests (\n"
                + "	id integer PRIMARY KEY,\n"
                + "	shop_id integer NOT NULL,\n"
                + "	chest_x integer NOT NULL,\n"
                + "	chest_y integer NOT NULL,\n"
                + "	chest_z integer NOT NULL,\n"
                + "	chest_world varchar NOT NULL,\n"
                + "FOREIGN KEY(shop_id) REFERENCES shops(id)\n"
                + ");";
    }

    @Override
    public int getVersion() {
        return 2;
    }
}
