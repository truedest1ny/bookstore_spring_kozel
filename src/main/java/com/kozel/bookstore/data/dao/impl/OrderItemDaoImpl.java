package com.kozel.bookstore.data.dao.impl;

import com.kozel.bookstore.data.dao.OrderItemDao;
import com.kozel.bookstore.data.dto.OrderItemDto;
import lombok.RequiredArgsConstructor;
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
@RequiredArgsConstructor
public class OrderItemDaoImpl implements OrderItemDao {

    private static final String ADD_SQL_NP =
            "INSERT INTO order_items (book_id, quantity, price, order_id) " +
                    "VALUES (:bookId, :quantity, :price, :orderId)";

    private static final String GET_BY_ID_SQL_NP =
            "SELECT id, book_id, quantity, price, order_id FROM order_items" +
                    " WHERE id = :id";

    private static final String GET_BY_ORDER_ID_SQL_NP =
            "SELECT id, book_id, quantity, price, order_id FROM order_items" +
                    " WHERE order_id = :orderId";

    private static final String GET_ALL_SQL =
            "SELECT id, book_id, quantity, price, order_id FROM order_items" +
                    " ORDER BY id";

    private static final String UPDATE_SQL_NP =
            "UPDATE order_items SET " +
                    "book_id = :bookId, " +
                    "quantity = :quantity, " +
                    "price = :price " +
                    "WHERE id = :id";

    private static final String DELETE_SQL_NP =
            "DELETE FROM order_items WHERE id = :id";

    private static final String COUNT_ALL_SQL =
            "SELECT COUNT (id) FROM order_items";

    private final NamedParameterJdbcTemplate template;

    @Override
    public List<OrderItemDto> findByOrderId(Long orderId) {
        MapSqlParameterSource parameterSource = new MapSqlParameterSource();
        parameterSource.addValue("orderId", orderId);

        return template.query(GET_BY_ORDER_ID_SQL_NP, parameterSource, this::mapRow);
    }

    @Override
    public Long save(OrderItemDto orderItem) {
        MapSqlParameterSource parameterSource = new MapSqlParameterSource();
        parameterSource
                .addValue("bookId", orderItem.getBookId())
                .addValue("quantity", orderItem.getQuantity())
                .addValue("price", orderItem.getPrice())
                .addValue("orderId", orderItem.getOrderId());

        KeyHolder keyHolder = new GeneratedKeyHolder();

        template.update(ADD_SQL_NP, parameterSource, keyHolder);

        return (Long) Objects.requireNonNull(keyHolder.getKeys()).get("id");
    }

    @Override
    public OrderItemDto findById(Long id) {
        MapSqlParameterSource parameterSource = new MapSqlParameterSource();
        parameterSource.addValue("id", id);

        return template.queryForObject(GET_BY_ID_SQL_NP, parameterSource, this::mapRow);
    }

    @Override
    public List<OrderItemDto> findAll() {
        return template.query(GET_ALL_SQL, this::mapRow);
    }

    @Override
    public OrderItemDto update(OrderItemDto orderItem) {
        MapSqlUpdate(orderItem);
        return findById(orderItem.getId());
    }

    @Override
    public void delete(OrderItemDto orderItem) {
        MapSqlParameterSource parameterSource = new MapSqlParameterSource();
        parameterSource.addValue("id", orderItem.getId());

        template.update(DELETE_SQL_NP, parameterSource);
    }

    @Override
    public long countAll() {
        return template.queryForObject(COUNT_ALL_SQL, new HashMap<>(), Long.class);
    }

    private OrderItemDto mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        OrderItemDto orderItem = new OrderItemDto();

        orderItem.setId(resultSet.getLong("id"));
        orderItem.setBookId(resultSet.getLong("book_id"));
        orderItem.setQuantity(resultSet.getLong("quantity"));
        orderItem.setPrice(resultSet.getBigDecimal("price"));
        orderItem.setOrderId(resultSet.getLong("order_id"));

        return orderItem;
    }


    private void MapSqlUpdate(OrderItemDto orderItem) {
        MapSqlParameterSource parameterSource = new MapSqlParameterSource();
        parameterSource
                .addValue("id", orderItem.getId())
                .addValue("bookId", orderItem.getBookId())
                .addValue("quantity", orderItem.getQuantity())
                .addValue("price", orderItem.getPrice())
                .addValue("orderId", orderItem.getOrderId());

        template.update(UPDATE_SQL_NP, parameterSource);
    }
}
