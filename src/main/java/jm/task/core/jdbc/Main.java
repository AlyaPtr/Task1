package jm.task.core.jdbc;
import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;

import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws SQLException {
        UserService userService = new UserServiceImpl();
        userService.saveUser("Alyona", "Pestrikova", (byte) 20);
        userService.saveUser("Anna", "Ivanova", (byte) 30);
        userService.saveUser("Polya", "Sergeeva", (byte) 12);
        userService.getAllUsers().forEach(a -> System.out.println(a.getName() + " " + a.getLastName()));

    }
}
