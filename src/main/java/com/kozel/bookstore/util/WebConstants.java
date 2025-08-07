package com.kozel.bookstore.util;

import java.util.Set;

public final class WebConstants {

    private WebConstants() {
        throw new UnsupportedOperationException("Utility class cannot be instantiated");
    }

    /*Pagination constants*/
    public static final int MAX_PAGE_SIZE = 50;
    public static final String SORT_PARAMETERS_ATTRIBUTE_KEY = "sortParams";
    public static final Set<String> BOOK_SORT_PROPERTIES = Set.of(
            "name", "author", "publishedYear", "price"
    );
    public static final Set<String> ORDER_SORT_PROPERTIES = Set.of(
            "date", "userLogin", "totalPrice", "status"
    );
    public static final Set<String> USER_SORT_PROPERTIES = Set.of(
            "email", "login", "role"
    );
    public static final Set<String> CART_ITEMS_SORT_PROPERTIES = Set.of(
            "book", "author", "quantity", "price", "subtotal"
    );
    public static final Set<String> ORDER_ITEMS_SORT_PROPERTIES = Set.of(
            "book", "author", "quantity", "price", "subtotal"
    );

    public static final String PAGE_ATTRIBUTE_KEY = "page";
    public static final String SIZE_ATTRIBUTE_KEY = "size";
    public static final String SORT_ATTRIBUTE_KEY = "sort";

    /*Request constants*/
    public static final String URL_KEY = "url";

    /*Error constants*/
    public static final String ERROR_STATUS_KEY = "status";
    public static final String ERROR_REASON_KEY = "reason";

    /*Notification constants*/
    public static final String ERROR_MESSAGE_KEY = "error";
    public static final String SUCCESS_MESSAGE_KEY = "success";
    public static final String WARN_MESSAGE_KEY = "warn";

    /*Order controller constants*/
    public static final String ORDER_ATTRIBUTE_KEY = "order";
    public static final String IS_EMPLOYEE_ATTRIBUTE_KEY = "isEmployee";

    /*User controller constants*/
    public static final String USER_ATTRIBUTE_KEY = "user";
    public static final String USER_ROLES_ATTRIBUTE_KEY = "roles";

    /*Cart controller constants*/
    public static final String CART_ATTRIBUTE_KEY = "sessionCart";

    /*Book controller constants*/
    public static final String BOOK_ATTRIBUTE_KEY = "book";
    public static final String BOOK_COVERS_ATTRIBUTE_KEY = "covers";

    /*Other constants*/
    public static final String IS_OWNER_PROFILE_ATTRIBUTE_KEY = "isOwnerProfile";

}
