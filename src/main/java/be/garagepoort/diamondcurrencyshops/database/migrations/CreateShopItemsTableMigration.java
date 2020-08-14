package be.garagepoort.diamondcurrencyshops.database.migrations;

public class CreateShopItemsTableMigration implements Migration {
    @Override
    public String getStatement() {
        return "CREATE TABLE IF NOT EXISTS shop_items (\n"
                + "	id integer PRIMARY KEY,\n"
                + "	shop_id integer NOT NULL,\n"
                + "	material varchar NOT NULL,\n"
                + "	cost integer NOT NULL,\n"
                + "	amount_of_items integer NOT NULL,\n"
                + "FOREIGN KEY(shop_id) REFERENCES shops(id)\n"
                + ");";
    }

    @Override
    public int getVersion() {
        return 3;
    }
}
