package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {

    public UserDaoJDBCImpl(){
    }
    private void createQuery(String query) {
        try (Statement statement = Util.getConnection().createStatement()) {
            statement.executeUpdate(query);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void createUsersTable() {
        createQuery("CREATE TABLE Users (\n" +
                "`id` INT NOT NULL AUTO_INCREMENT,\n" +
                "`name` VARCHAR(45) NOT NULL,\n" +
                "`lastname` VARCHAR(45) NOT NULL,\n" +
                "`age` INT(3) NOT NULL,\n" +
                "PRIMARY KEY (`id`));");
    }

    public void dropUsersTable() {
        createQuery("DROP TABLE Users;");
    }

    public void saveUser(String name, String lastName, byte age) {
        createQuery("INSERT INTO Users (name, lastname, age) VALUES " +
                "('" + name + "', '" + lastName + "', " + age + ");");
        System.out.println("User с именем - " + name + " добавлен в базу данных.");
    }

    public void removeUserById(long id) {
        createQuery("DELETE FROM Users WHERE id=" + id + ";");
    }

    public List<User> getAllUsers() {
        List<User> list = new ArrayList<>();
        String sql = "SELECT * FROM Users";
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
        createQuery("TRUNCATE TABLE Users;");
    }
}
