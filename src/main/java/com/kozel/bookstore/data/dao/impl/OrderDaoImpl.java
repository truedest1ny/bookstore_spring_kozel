package com.kozel.bookstore.data.dao.impl;

import com.kozel.bookstore.data.dao.OrderDao;
import com.kozel.bookstore.data.dto.OrderDto;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

@Repository
@RequiredArgsConstructor
public class OrderDaoImpl implements OrderDao {
    private static final String ADD_SQL_NP =
            "INSERT INTO orders (date, user_id, status_id, price) " +
                    "VALUES (:date, :userId, (SELECT id FROM statuses WHERE enum_value = :status), :price)";

    private static final String GET_BY_ID_SQL_NP =
            "SELECT o.id, o.date, o.user_id, st.enum_value, o.price FROM orders o" +
                    " JOIN statuses st ON o.status_id = st.id" +
                    " WHERE o.id = :id";

    private static final String GET_ALL_SQL =
            "SELECT o.id, o.date, o.user_id, st.enum_value, o.price FROM orders o" +
                    " JOIN statuses st ON o.status_id = st.id" +
                    " ORDER BY o.id";

    private static final String GET_BY_USER_ID_SQL_NP =
            "SELECT o.id, o.date, o.user_id, st.enum_value, o.price FROM orders o" +
                    " JOIN statuses st ON o.status_id = st.id" +
                    " WHERE o.user_id = :userId";

    private static final String GET_BY_STATUS_SQL_NP =
            "SELECT o.id, o.date, o.user_id, st.enum_value, o.price FROM orders o" +
                    " JOIN statuses st ON o.status_id = st.id" +
                    " WHERE o.status_id = (SELECT id FROM statuses WHERE enum_value = :status)";

    private static final String UPDATE_SQL_NP =
            "UPDATE orders SET " +
                    "date = :date, " +
                    "user_id = :userId, " +
                    "status_id = (SELECT id FROM covers WHERE enum_value = :cover),  " +
                    "price = :price " +
                    "WHERE id = :id";
    

    private static final String COUNT_ALL_SQL =
            "SELECT COUNT (id) FROM orders";

    private final NamedParameterJdbcTemplate template;
    @Override
    public List<OrderDto> findByUserId(Long userId) {
        MapSqlParameterSource parameterSource = new MapSqlParameterSource();
        parameterSource.addValue("userId", userId);

        return template.query(GET_BY_USER_ID_SQL_NP, parameterSource, this::mapRow);
    }

    @Override
    public List<OrderDto> findByStatus(OrderDto.Status status) {
        MapSqlParameterSource parameterSource = new MapSqlParameterSource();
        parameterSource.addValue("status", status);

        return template.query(GET_BY_STATUS_SQL_NP, parameterSource, this::mapRow);
    }

    @Override
    public Long save(OrderDto order) {
        MapSqlParameterSource parameterSource = new MapSqlParameterSource();
        parameterSource
                .addValue("date", order.getDate().toString())
                .addValue("userId", order.getUserId())
                .addValue("status", order.getStatus().toString())
                .addValue("price", order.getPrice());

        KeyHolder keyHolder = new GeneratedKeyHolder();

        template.update(ADD_SQL_NP, parameterSource, keyHolder);

        return (Long) Objects.requireNonNull(keyHolder.getKeys()).get("id");
    }

    @Override
    public OrderDto findById(Long id) {
        MapSqlParameterSource parameterSource = new MapSqlParameterSource();
        parameterSource.addValue("id", id);

        return template.queryForObject(GET_BY_ID_SQL_NP, parameterSource, this::mapRow);
    }

    @Override
    public List<OrderDto> findAll() {
        return template.query(GET_ALL_SQL, this::mapRow);
    }

    @Override
    public OrderDto update(OrderDto order) {
        MapSqlUpdate(order);
        return findById(order.getId());
    }

    @Override
    public void delete(OrderDto order) {
        MapSqlUpdate(order);
    }

    @Override
    public long countAll() {
        return template.queryForObject(COUNT_ALL_SQL, new HashMap<>(), Long.class);
    }

    private OrderDto mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        OrderDto order = new OrderDto();

        order.setId(resultSet.getLong("id"));
        order.setDate(resultSet.getObject("date", LocalDateTime.class));
        order.setUserId(resultSet.getLong("user_id"));
        order.setStatus(OrderDto.Status.valueOf(resultSet.getString("enum_value")));
        order.setPrice(resultSet.getBigDecimal("price"));

        return order;
    }


    private void MapSqlUpdate(OrderDto order) {
        MapSqlParameterSource parameterSource = new MapSqlParameterSource();
        parameterSource
                .addValue("id", order.getId())
                .addValue("date", order.getDate().toString())
                .addValue("userId", order.getUserId())
                .addValue("status", order.getStatus().toString())
                .addValue("price", order.getPrice());

        template.update(UPDATE_SQL_NP, parameterSource);
    }
}
