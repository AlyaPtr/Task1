package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.Transaction;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class UserDaoHibernateImpl implements UserDao {

    public UserDaoHibernateImpl() {}

    @FunctionalInterface
    interface SessionFun {
        void fun(Session s);
    }

    private void createQuery(SessionFun sessionFun) {
        Transaction transaction;
        try (Session session = Util.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            sessionFun.fun(session);
            transaction.commit();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void createUsersTable() {
        createQuery(session -> session.createSQLQuery("CREATE TABLE Users (\n" +
                "`id` INT NOT NULL AUTO_INCREMENT,\n" +
                "`name` VARCHAR(45) NOT NULL,\n" +
                "`lastname` VARCHAR(45) NOT NULL,\n" +
                "`age` INT(3) NOT NULL,\n" +
                "PRIMARY KEY (`id`));").executeUpdate());
    }

    @Override
    public void dropUsersTable() {
        createQuery(session -> session.createSQLQuery("DROP TABLE Users").executeUpdate());
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        createQuery(session -> session.persist(new User(name, lastName, age)));
    }

    @Override
    public void removeUserById(long id) {
        createQuery(session -> session.remove(session.find(User.class, id)));
    }

    @Override
    public List<User> getAllUsers() {
        AtomicReference<List<User>> list = new AtomicReference<>(new ArrayList<>());
        createQuery(session -> list.set(session.createCriteria(User.class).list()));
        return list.get();
    }

    @Override
    public void cleanUsersTable() {
        createQuery(session -> session.createSQLQuery("truncate table Users").executeUpdate());
    }
}