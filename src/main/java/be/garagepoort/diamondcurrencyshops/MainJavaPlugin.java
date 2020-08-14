package be.garagepoort.diamondcurrencyshops;


import be.garagepoort.diamondcurrencyshops.common.DLogger;
import be.garagepoort.diamondcurrencyshops.database.migrations.SqlMigrations;
import be.garagepoort.diamondcurrencyshops.service.chest.BreakChestListener;
import be.garagepoort.diamondcurrencyshops.service.chest.CancelChestSelectionListener;
import be.garagepoort.diamondcurrencyshops.service.chest.BuyItemChestListener;
import be.garagepoort.diamondcurrencyshops.service.chest.SelectChestListener;
import be.garagepoort.diamondcurrencyshops.ui.*;
import org.bukkit.plugin.java.JavaPlugin;

public class MainJavaPlugin extends JavaPlugin {

    @Override
    public void onEnable() {
        DLogger.initialize(getLogger());
        SqlMigrations.getInstance().createMigrationTable();
        SqlMigrations.getInstance().runMigrations();

        getLogger().info("DiamondsCurrencyShops plugin enabled");

        getServer().getPluginManager().registerEvents(new SelectChestListener(), this);
        getServer().getPluginManager().registerEvents(new CancelChestSelectionListener(), this);
        getServer().getPluginManager().registerEvents(new BuyItemChestListener(), this);
        getServer().getPluginManager().registerEvents(new BreakChestListener(), this);

        this.getCommand("listDshops").setExecutor(new ListShopsCommandExecutor(this));
        this.getCommand("createDshop").setExecutor(new CreateShopCommandExecutor(this));
        this.getCommand("addDchests").setExecutor(new AddChestsCommandExecutor(this));
        this.getCommand("addDitem").setExecutor(new AddItemsCommandExecutor(this));
        this.getCommand("removeDshop").setExecutor(new DeleteShopCommandExecutor(this));
        this.getCommand("listDshopItems").setExecutor(new ListShopItemsCommandExecutor(this));
    }

    @Override
    public void onDisable() {
        getLogger().info("DiamondsCurrencyShops plugin disabled");
    }
}
