package com.kozel.bookstore.util;

import java.util.Set;

/**
 * A utility class that defines various constants used throughout the web application.
 * <p>This class provides centralized access to constants related to pagination,
 * request parameters, error handling, session attributes, and view model keys.
 * It is a final class with a private constructor to prevent instantiation,
 * as it only contains static members.</p>
 */
public final class WebConstants {

    private WebConstants() {
        throw new UnsupportedOperationException("Utility class cannot be instantiated");
    }

    /*Pagination constants*/

    /**
     * The maximum allowed page size for pagination.
     */
    public static final int MAX_PAGE_SIZE = 50;

    /**
     * The key for the model attribute that stores the sorting parameters.
     */
    public static final String SORT_PARAMETERS_ATTRIBUTE_KEY = "sortParams";

    /**
     * A set of valid properties that can be used for sorting books.
     */
    public static final Set<String> BOOK_SORT_PROPERTIES = Set.of(
            "name", "author", "publishedYear", "price"
    );

    /**
     * A set of valid properties that can be used for sorting orders.
     */
    public static final Set<String> ORDER_SORT_PROPERTIES = Set.of(
            "date", "userLogin", "totalPrice", "status"
    );

    /**
     * A set of valid properties that can be used for sorting users.
     */
    public static final Set<String> USER_SORT_PROPERTIES = Set.of(
            "email", "login", "role"
    );

    /**
     * A set of valid properties that can be used for sorting cart items.
     */
    public static final Set<String> CART_ITEMS_SORT_PROPERTIES = Set.of(
            "book", "author", "quantity", "price", "subtotal"
    );

    /**
     * A set of valid properties that can be used for sorting order items.
     */
    public static final Set<String> ORDER_ITEMS_SORT_PROPERTIES = Set.of(
            "book", "author", "quantity", "price", "subtotal"
    );

    /**
     * The key for the 'page' parameter in pagination requests and model attributes.
     */
    public static final String PAGE_ATTRIBUTE_KEY = "page";

    /**
     * The key for the 'size' parameter in pagination requests and model attributes.
     */
    public static final String SIZE_ATTRIBUTE_KEY = "size";

    /**
     * The key for the 'sort' parameter in pagination requests and model attributes.
     */
    public static final String SORT_ATTRIBUTE_KEY = "sort";

    /*Request constants*/

    /**
     * The key for the model attribute that stores the current URL.
     */
    public static final String URL_KEY = "url";

    /*Error constants*/

    /**
     * The key for the error status in error messages.
     */
    public static final String ERROR_STATUS_KEY = "status";

    /**
     * The key for the error reason or message in error messages.
     */
    public static final String ERROR_REASON_KEY = "reason";

    /*Notification constants*/

    /**
     * The key for storing an error message to be displayed to the user.
     */
    public static final String ERROR_MESSAGE_KEY = "error";

    /**
     * The key for storing a success message to be displayed to the user.
     */
    public static final String SUCCESS_MESSAGE_KEY = "success";

    /**
     * The key for storing a warning message to be displayed to the user.
     */
    public static final String WARN_MESSAGE_KEY = "warn";

    /*Order controller constants*/

    /**
     * The key for the model attribute that stores an order object.
     */
    public static final String ORDER_ATTRIBUTE_KEY = "order";

    /**
     * The key for the model attribute that indicates if the current user is an employee.
     */
    public static final String IS_EMPLOYEE_ATTRIBUTE_KEY = "isEmployee";

    /*User controller constants*/

    /**
     * The key for the model attribute or session attribute that stores a user object.
     */
    public static final String USER_ATTRIBUTE_KEY = "user";

    /**
     * The key for the model attribute that stores a list of user roles.
     */
    public static final String USER_ROLES_ATTRIBUTE_KEY = "roles";

    /*Cart controller constants*/

    /**
     * The key for the session attribute that stores the shopping cart.
     */
    public static final String CART_ATTRIBUTE_KEY = "sessionCart";

    /*Book controller constants*/

    /**
     * The key for the model attribute that stores a book object.
     */
    public static final String BOOK_ATTRIBUTE_KEY = "book";

    /**
     * The key for the model attribute that stores a list of available book covers.
     */
    public static final String BOOK_COVERS_ATTRIBUTE_KEY = "covers";

    /*Other constants*/

    /**
     * The key for the model attribute that indicates if the user is viewing their own profile.
     */
    public static final String IS_OWNER_PROFILE_ATTRIBUTE_KEY = "isOwnerProfile";

}
