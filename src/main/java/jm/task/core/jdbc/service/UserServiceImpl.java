package jm.task.core.jdbc.service;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class UserServiceImpl implements UserService {

    private void createQuery(String query) {
        try (Statement statement = Util.getConnection().createStatement()) {
            statement.executeUpdate(query);
            statement.executeQuery("SELECT * FROM dbtest.Users");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    public void createUsersTable() {
        createQuery("CREATE TABLE dbtest.Users (\n" +
                "`id` INT NOT NULL AUTO_INCREMENT,\n" +
                "`name` VARCHAR(45) NOT NULL,\n" +
                "`lastname` VARCHAR(45) NOT NULL,\n" +
                "`age` INT(3) NOT NULL,\n" +
                "PRIMARY KEY (`id`));");
    }

    public void dropUsersTable() {
        createQuery("DROP TABLE dbtest.Users;");
    }

    public void saveUser(String name, String lastName, byte age) {
        createQuery("INSERT INTO dbtest.Users (name, lastname, age) VALUES " +
                "('" + name + "', '" + lastName + "', " + age + ");");
    }

    public void removeUserById(long id) {
        createQuery("DELETE FROM dbtest.Users WHERE id=" + id + ";");
    }

    public List<User> getAllUsers() {
        List<User> list = new ArrayList<>();
        String sql = "SELECT * FROM dbtest.Users";
        try(Statement statement = Util.getConnection().createStatement()) {
            ResultSet res = statement.executeQuery(sql);
            while(res.next()) {
                list.add(new User(res.getString("name"),
                        res.getString("lastname"), res.getByte("age")));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return list;
    }

    public void cleanUsersTable() {
        createQuery("TRUNCATE TABLE dbtest.Users;");
    }
}
