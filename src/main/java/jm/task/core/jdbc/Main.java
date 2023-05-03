package jm.task.core.jdbc;
import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.transaction.UserTransaction;
import java.sql.SQLException;

public class Main {
    private static final UserService userService = new UserServiceImpl();
    public static void main(String[] args) {

        userService.createUsersTable();

        userService.saveUser("Alyona", "Pestrikova", (byte) 20);
        userService.saveUser("Anna", "Ivanova", (byte) 30);
        userService.saveUser("Polya", "Sergeeva", (byte) 12);
        userService.saveUser("Irina", "Soboleva", (byte) 26);

        System.out.println(userService.getAllUsers().toString());

        userService.cleanUsersTable();
        userService.dropUsersTable();
    }
}
