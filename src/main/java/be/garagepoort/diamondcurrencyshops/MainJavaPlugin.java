package be.garagepoort.diamondcurrencyshops;


import be.garagepoort.diamondcurrencyshops.migrations.SqlMigrations;
import be.garagepoort.diamondcurrencyshops.ui.CreateShopCommandExecutor;
import be.garagepoort.diamondcurrencyshops.ui.ListShopsCommandExecutor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

public class MainJavaPlugin extends JavaPlugin {

    @Override
    public void onEnable() {
        DLogger.initialize(getLogger());
        SqlMigrations.getInstance().createMigrationTable();
        SqlMigrations.getInstance().runMigrations();
        getLogger().info("DiamondsCurrencyShops plugin enabled");
        this.getCommand("listDshops").setExecutor(new ListShopsCommandExecutor(this));
        this.getCommand("createDshop").setExecutor(new CreateShopCommandExecutor(this));
    }

    @Override
    public void onDisable() {
        getLogger().info("DiamondsCurrencyShops plugin disabled");
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if (cmd.getName().equalsIgnoreCase("basic")) { // If the player typed /basic then do the following, note: If you only registered this executor for one command, you don't need this
            // doSomething
            return true;
        } //If this has happened the function will return true.

        // If this hasn't happened the value of false will be returned.
        return false;
    }
}
