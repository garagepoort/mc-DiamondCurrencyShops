package be.garagepoort.diamondcurrencyshops.migrations;

public interface Migration {

    String getStatement();

    int getVersion();
}
