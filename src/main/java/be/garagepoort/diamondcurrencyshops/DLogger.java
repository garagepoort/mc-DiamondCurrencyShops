package be.garagepoort.diamondcurrencyshops;


import java.util.logging.Logger;

public class DLogger {

    public static Logger logger;

    public static void initialize(java.util.logging.Logger logger) {
        DLogger.logger = logger;
    }
}
