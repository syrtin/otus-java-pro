package ru.otus.crm;

import org.hibernate.cfg.Configuration;
import ru.otus.crm.dbmigrations.MigrationsExecutorFlyway;

public class DbInit {
    public static final String HIBERNATE_CFG_FILE = "hibernate.cfg.xml";

    public static Configuration getConfiguration() {
        var configuration = new Configuration().configure(HIBERNATE_CFG_FILE);

        var dbUrl = configuration.getProperty("hibernate.connection.url");
        var dbUserName = configuration.getProperty("hibernate.connection.username");
        var dbPassword = configuration.getProperty("hibernate.connection.password");

        new MigrationsExecutorFlyway(dbUrl, dbUserName, dbPassword).executeMigrations();

        return configuration;
    }
}
