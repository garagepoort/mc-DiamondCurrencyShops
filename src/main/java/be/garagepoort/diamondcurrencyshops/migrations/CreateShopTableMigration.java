package be.garagepoort.diamondcurrencyshops.migrations;

public class CreateShopTableMigration implements Migration {
    @Override
    public String getStatement() {
        return "CREATE TABLE IF NOT EXISTS shops (\n"
                + "	id integer PRIMARY KEY,\n"
                + "	name varchar NOT NULL,\n"
                + "	owner_name varchar NOT NULL\n"
                + ");";
    }

    @Override
    public int getVersion() {
        return 1;
    }
}
