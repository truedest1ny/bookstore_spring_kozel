package com.kozel.bookstore.data.dao.impl;

import com.kozel.bookstore.data.dao.UserDao;
import com.kozel.bookstore.data.entity.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

@Repository
@Slf4j
@RequiredArgsConstructor
public class UserDaoImpl implements UserDao {

    private static final String ADD_SQL_NP =
            "INSERT INTO users (first_name, last_name, email, login, password, role_id) " +
                    "VALUES (:firstName, :lastName, :email, :login, :password, (SELECT id FROM roles WHERE enum_value = :role))";

    private static final String GET_ALL_SQL =
            "SELECT us.id, us.first_name, us.last_name, us.email, us.login, us.password, rl.enum_value FROM users us" +
                    " JOIN roles rl ON us.role_id = rl.id" +
                    " WHERE us.is_deleted = false" +
                    " ORDER BY us.id";

    private static final String GET_BY_ID_SQL_NP =
            "SELECT us.id, us.first_name, us.last_name, us.email, us.login, us.password, rl.enum_value FROM users us" +
                    " JOIN roles rl ON us.role_id = rl.id" +
                    " WHERE us.id = :id AND us.is_deleted = false";

    private static final String GET_BY_EMAIL_SQL_NP =
            "SELECT us.id, us.first_name, us.last_name, us.email, us.login, us.password, rl.enum_value FROM users us" +
                    " JOIN roles rl ON us.role_id = rl.id" +
                    " WHERE us.email = :email AND us.is_deleted = false";

    private static final String GET_BY_LOGIN_SQL_NP =
            "SELECT us.id, us.first_name, us.last_name, us.email, us.login, us.password, rl.enum_value FROM users us" +
                    " JOIN roles rl ON us.role_id = rl.id" +
                    " WHERE us.login = :login AND us.is_deleted = false";

    private static final String GET_BY_LAST_NAME_SQL_NP =
            "SELECT us.id, us.first_name, us.last_name, us.email, us.login, us.password, rl.enum_value FROM users us" +
                    " JOIN roles rl ON us.role_id = rl.id" +
                    " WHERE us.last_name = :lastName AND us.is_deleted = false";

    private static final String UPDATE_SQL_NP =
            "UPDATE users SET " +
                    "first_name = :firstName," +
                    "last_name = :lastName, " +
                    "email = :email, " +
                    "password = :password, " +
                    "role_id = (SELECT id FROM roles WHERE enum_value = :role), " +
                    "is_deleted = :isDeleted " +
                    "WHERE id = :id";

    private static final String CLEAR_DELETED_ROWS_SQL_NP =
            "DELETE FROM users WHERE is_deleted = :isDeleted";

    private static final String COUNT_ALL_SQL =
            "SELECT COUNT (id) FROM users";

    private final NamedParameterJdbcTemplate template;


    @Override
    public Long save(User user) {
        MapSqlParameterSource parameterSource = new MapSqlParameterSource();
        parameterSource.addValue("firstName", user.getFirstName())
                .addValue("lastName", user.getLastName())
                .addValue("email", user.getEmail())
                .addValue("login", user.getLogin())
                .addValue("password", user.getPassword())
                .addValue("role", user.getRole().toString());

        KeyHolder keyHolder = new GeneratedKeyHolder();

        template.update(ADD_SQL_NP, parameterSource, keyHolder);

        return (Long) Objects.requireNonNull(keyHolder.getKeys()).get("id");
    }

    @Override
    public User findById(Long id) {
        MapSqlParameterSource parameterSource = new MapSqlParameterSource();
        parameterSource.addValue("id", id);

        return template.queryForObject(GET_BY_ID_SQL_NP, parameterSource, this::mapRow);
    }

    @Override
    public List<User> findAll() {
        return template.query(GET_ALL_SQL, this::mapRow);
    }

    @Override
    public User update(User user) {
        MapSqlUpdate(user);
        return findById(user.getId());
    }


    @Override
    public void delete(User user) {
        MapSqlUpdate(user);
    }

    @Override
    public long clearDeletedRows() {
        MapSqlParameterSource parameterSource = new MapSqlParameterSource();
        parameterSource.addValue("isDeleted", true);

        return template.update(CLEAR_DELETED_ROWS_SQL_NP, parameterSource);
    }

    @Override
    public User findByEmail(String email) {
        MapSqlParameterSource parameterSource = new MapSqlParameterSource();
        parameterSource.addValue("email", email);

        return template.queryForObject(GET_BY_EMAIL_SQL_NP, parameterSource, this::mapRow);
    }

    @Override
    public User findByLogin(String login) {
        MapSqlParameterSource parameterSource = new MapSqlParameterSource();
        parameterSource.addValue("login", login);

        return template.queryForObject(GET_BY_LOGIN_SQL_NP, parameterSource, this::mapRow);
    }

    @Override
    public List<User> findByLastName(String lastName) {
        MapSqlParameterSource parameterSource = new MapSqlParameterSource();
        parameterSource.addValue("lastName", lastName);

        return template.query(GET_BY_LAST_NAME_SQL_NP, parameterSource, this::mapRow);
    }

    @Override
    public long countAll() {
        return template.queryForObject(COUNT_ALL_SQL, new HashMap<>(), Long.class);
    }

    private User mapRow(ResultSet resultSet, int rowNum) throws SQLException {
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


    private void MapSqlUpdate(User user) {
        MapSqlParameterSource parameterSource = new MapSqlParameterSource();
        parameterSource
                .addValue("id", user.getId())
                .addValue("firstName", user.getFirstName())
                .addValue("lastName", user.getLastName())
                .addValue("email", user.getEmail())
                .addValue("password", user.getPassword())
                .addValue("role", user.getRole().toString())
                .addValue("isDeleted", user.isDeleted());

        template.update(UPDATE_SQL_NP, parameterSource);
    }
}