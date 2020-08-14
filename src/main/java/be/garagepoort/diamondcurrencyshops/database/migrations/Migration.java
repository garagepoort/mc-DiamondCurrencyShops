package be.garagepoort.diamondcurrencyshops.database.migrations;

public interface Migration {

    String getStatement();

    int getVersion();
}
