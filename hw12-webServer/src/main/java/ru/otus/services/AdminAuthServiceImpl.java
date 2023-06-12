package ru.otus.services;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class AdminAuthServiceImpl implements AdminAuthService {
    private final String ADMIN_PROPERTIES = "/admin.properties";

    public AdminAuthServiceImpl() {
    }

    @Override
    public boolean authenticate(String login, String password) {
        try (InputStream is = AdminAuthServiceImpl.class.getResourceAsStream(ADMIN_PROPERTIES)) {
            Properties props = new Properties();
            props.load(is);
            String pass = props.getProperty(String.format("%s.password", login));
            return password.equals(pass);
        } catch (IOException e) {
            throw new IllegalStateException("Invalid config file " + ADMIN_PROPERTIES);
        }
    }
}
