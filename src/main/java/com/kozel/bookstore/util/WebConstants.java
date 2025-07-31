package com.kozel.bookstore.util;

public final class WebConstants {

    private WebConstants() {
        throw new UnsupportedOperationException("Utility class cannot be instantiated");
    }

    public static final String URL_KEY = "url";

    public static final String ERROR_STATUS_KEY = "status";
    public static final String ERROR_REASON_KEY = "reason";

    public static final String ERROR_MESSAGE_KEY = "error";
    public static final String SUCCESS_MESSAGE_KEY = "success";
    public static final String WARN_MESSAGE_KEY = "warn";

    public static final String ORDER_ATTRIBUTE_KEY = "order";
    public static final String ORDERS_ATTRIBUTE_KEY = "orders";
    public static final String IS_EMPLOYEE_ATTRIBUTE_KEY = "isEmployee";

    public static final String USERS_ATTRIBUTE_KEY = "users";
    public static final String USER_ATTRIBUTE_KEY = "user";
    public static final String USER_ROLES_ATTRIBUTE_KEY = "roles";

    public static final String CART_ATTRIBUTE_KEY = "sessionCart";

    public static final String BOOK_ATTRIBUTE_KEY = "book";
    public static final String BOOKS_ATTRIBUTE_KEY = "books";
    public static final String BOOK_COVERS_ATTRIBUTE_KEY = "covers";

    public static final String IS_OWNER_PROFILE_ATTRIBUTE_KEY = "isOwnerProfile";

}
