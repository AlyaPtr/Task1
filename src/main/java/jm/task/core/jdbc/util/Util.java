package jm.task.core.jdbc.util;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Util {
    private static final Connection connection;
    private static final String url = "jdbc:mysql://localhost:3306/dbtest";
    private static final String user = "root";
    private static final String password = "root";


    static {
        try {
            connection = DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static SessionFactory getSessionFactory() {

        Properties prop = new Properties();
        prop.setProperty("connection.driver_class", "com.mysql.cj.jdbc.Driver");
        prop.setProperty("hibernate.connection.url", url);
        prop.setProperty("hibernate.connection.username", user);
        prop.setProperty("hibernate.connection.password", password);
        prop.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQLDialect");
        prop.setProperty("hibernate.use_sql_comments", "true");
        prop.setProperty("hibernate.show_sql", "true");
        prop.setProperty("hibernate.format_sql", "true");
        prop.setProperty("hibernate.hbm2ddl.auto", "update");

        Configuration cfg = new Configuration();
        cfg.addAnnotatedClass(jm.task.core.jdbc.model.User.class);
        cfg.setProperties(prop);

        ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                .applySettings(cfg.getProperties()).build();
        return cfg.buildSessionFactory(serviceRegistry);
    }

    public static Connection getConnection() {
        return connection;
    }
}
