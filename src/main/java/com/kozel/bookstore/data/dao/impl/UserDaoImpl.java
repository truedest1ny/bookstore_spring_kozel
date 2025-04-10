package com.kozel.bookstore.data.dao.impl;

import com.kozel.bookstore.data.connection.DataSource;
import com.kozel.bookstore.data.dao.UserDao;
import com.kozel.bookstore.data.entity.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
public class UserDaoImpl implements UserDao {

    private static final String ADD_USER_SQL =
            "INSERT INTO users (first_name, last_name, email, login, password, role_id) " +
                    "VALUES (?, ?, ?, ?, ?, (SELECT id FROM roles WHERE enum_value = ?))";
    private static final String GET_ID_ADD_SQL =
            "SELECT id FROM users WHERE login = ?";

    private static final String GET_ALL_SQL =
            "SELECT us.id, us.first_name, us.last_name, us.email, us.login, us.password, rl.enum_value FROM users us" +
                    " JOIN roles rl ON us.role_id = rl.id ORDER BY us.id";

    private static final String GET_BY_ID_SQL =
            "SELECT us.id, us.first_name, us.last_name, us.email, us.login, us.password, rl.enum_value FROM users us" +
                    " JOIN roles rl ON us.role_id = rl.id WHERE us.id = ?";

    private static final String GET_BY_EMAIL_SQL =
            "SELECT us.id, us.first_name, us.last_name, us.email, us.login, us.password, rl.enum_value FROM users us" +
                    " JOIN roles rl ON us.role_id = rl.id WHERE us.email = ?";

    private static final String GET_BY_LOGIN_SQL =
            "SELECT us.id, us.first_name, us.last_name, us.email, us.login, us.password, rl.enum_value FROM users us" +
                    " JOIN roles rl ON us.role_id = rl.id WHERE us.login = ?";

    private static final String GET_BY_LAST_NAME_SQL =
            "SELECT us.id, us.first_name, us.last_name, us.email, us.login, us.password, rl.enum_value FROM users us" +
                    " JOIN roles rl ON us.role_id = rl.id WHERE us.last_name = ?";

    private static final String UPDATE_SQL =
            "UPDATE users SET " +
                    "first_name = ?, " +
                    "last_name = ?, " +
                    "email = ?, " +
                    "login = ?, " +
                    "password = ?, " +
                    "role_id = (SELECT id FROM roles WHERE enum_value = ?) " +
                    "WHERE id = ?";
    private static final String DELETE_BY_ID_SQL =
            "DELETE FROM users WHERE id = ?";

    private static final String COUNT_ALL_SQL =
            "SELECT COUNT (id) FROM users";


    private final DataSource dataSource;


    @Override
    public Long save(User user) {
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(ADD_USER_SQL);

            statement.setString(1, user.getFirstName());
            statement.setString(2, user.getLastName());
            statement.setString(3, user.getEmail());
            statement.setString(4, user.getLogin());
            statement.setString(5, user.getPassword());
            statement.setString(6, user.getRole().toString());

            statement.executeUpdate();

            log.debug("Query to database completed");

            statement = connection.prepareStatement(GET_ID_ADD_SQL);

            statement.setString(1, user.getLogin());
            ResultSet resultSet = statement.executeQuery();


            if (resultSet.next()) {
                user.setId(resultSet.getLong("id"));
                return user.getId();
            }
            return null;
        }
            catch (SQLException exception){
                throw new RuntimeException(exception);
            }
    }

    @Override
    public User findById(Long id) {
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(GET_BY_ID_SQL);
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();

            log.debug("Query to database completed");

            if (resultSet.next())
            {
                return process(resultSet);
            }
            return null;
        } catch (SQLException exception){
            throw new RuntimeException(exception);
        }

    }

    @Override
    public List<User> findAll() {
        List<User> users = new ArrayList<>();

        try (Connection connection = dataSource.getConnection()) {
            Statement statement = connection.createStatement();

            ResultSet resultSet = statement.executeQuery(GET_ALL_SQL);

            log.debug("Query to database completed");

            while (resultSet.next())
            {
                User user = process(resultSet);
                users.add(user);
            }


        } catch (SQLException exception){
            throw new RuntimeException(exception);
        }

        return users;

    }

    @Override
    public User update(User object) {
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(UPDATE_SQL);

            statement.setString(1, object.getFirstName());
            statement.setString(2, object.getLastName());
            statement.setString(3, object.getEmail());
            statement.setString(4, object.getLogin());
            statement.setString(5, object.getPassword());
            statement.setString(6, object.getRole().toString());
            statement.setLong(7, object.getId());

            log.debug("Query to database completed");

            if (statement.executeUpdate() > 0)
            {
                return object;
            }
            return null;

        } catch (SQLException exception){
            throw new RuntimeException(exception);
        }

    }

    @Override
    public boolean deleteById(Long id) {
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(DELETE_BY_ID_SQL);

            statement.setLong(1, id);

            log.debug("Query to database completed");

            return (statement.executeUpdate() > 0);

        } catch (SQLException exception){
            throw new RuntimeException(exception);
        }
    }

    @Override
    public User findByEmail(String email) {
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(GET_BY_EMAIL_SQL);
            statement.setString(1, email);
            ResultSet resultSet = statement.executeQuery();

            log.debug("Query to database completed");

            if (resultSet.next())
            {
                return process(resultSet);
            }
            return null;
        } catch (SQLException exception){
            throw new RuntimeException(exception);
        }
    }

    @Override
    public User findByLogin(String login) {
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(GET_BY_LOGIN_SQL);
            statement.setString(1, login);
            ResultSet resultSet = statement.executeQuery();

            log.debug("Query to database completed");

            if (resultSet.next())
            {
                return process(resultSet);
            }
            return null;
        } catch (SQLException exception){
            throw new RuntimeException(exception);
        }
    }

    @Override
    public List<User> findByLastName(String lastName) {
        List<User> users = new ArrayList<>();

        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(GET_BY_LAST_NAME_SQL);
            statement.setString(1, lastName);
            ResultSet resultSet = statement.executeQuery();

            log.debug("Query to database completed");

            while (resultSet.next())
            {
                User user = process(resultSet);
                users.add(user);
            }

        } catch (SQLException exception){
            throw new RuntimeException(exception);
        }
        return users;
    }

    @Override
    public long countAll() {
        try (Connection connection = dataSource.getConnection()) {
            Statement statement = connection.createStatement();

            ResultSet resultSet = statement.executeQuery(COUNT_ALL_SQL);

            log.debug("Query to database completed");

            if (resultSet.next())
            {
                return resultSet.getLong(1);
            }

            return 0L;

        } catch (SQLException exception){
            throw new RuntimeException(exception);
        }
    }

    private User process (ResultSet resultSet) throws SQLException {
        User user = new User();
        user.setId(resultSet.getLong("id"));
        user.setFirstName(resultSet.getString("first_name"));
        user.setLastName(resultSet.getString("last_name"));
        user.setEmail(resultSet.getString("email"));
        user.setLogin(resultSet.getString("login"));
        user.setPassword(resultSet.getString("password"));
        user.setRole(User.Role.valueOf(resultSet.getString("enum_value")));
        return user;
    }
}
