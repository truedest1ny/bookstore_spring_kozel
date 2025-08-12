package com.kozel.bookstore.web.controller;

import com.kozel.bookstore.service.BookService;
import com.kozel.bookstore.service.CartService;
import com.kozel.bookstore.service.dto.book.BookDto;
import com.kozel.bookstore.service.dto.cart.CartDto;
import com.kozel.bookstore.service.dto.cart.CartItemDto;
import com.kozel.bookstore.service.dto.user.UserSessionDto;
import com.kozel.bookstore.web.pagination.InMemoryPaginationHandler;
import com.kozel.bookstore.web.pagination.PaginationValidator;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.kozel.bookstore.util.WebConstants.*;

/**
 * Controller for managing the user's shopping cart.
 * This class handles all cart-related actions, including displaying the cart,
 * adding items, removing items, and clearing the cart. It seamlessly manages
 * both authenticated user carts (stored in the database) and guest session carts.
 */
@Controller
@RequestMapping("/cart")
@RequiredArgsConstructor
public class CartController implements PaginationValidator, InMemoryPaginationHandler {

    private final CartService cartService;
    private final BookService bookService;

    /**
     * Displays the contents of the shopping cart.
     * This method retrieves the cart from either the user's account or the session.
     * It then applies in-memory pagination and sorting to the cart items before
     * displaying them.
     *
     * @param pageable The pagination and sorting parameters for the cart items.
     * @param model The Spring Model to add view attributes.
     * @param attributes RedirectAttributes for redirection handling.
     * @param session The HttpSession to retrieve or store cart and user information.
     * @return The view name for the cart page.
     */
    @GetMapping
    public String getCart(@PageableDefault(
                        size = 20,
                        sort = "quantity",
                        direction = Sort.Direction.DESC) Pageable pageable,
                                Model model,
                                RedirectAttributes attributes,
                                HttpSession session) {

        Pageable correctedPageable = correctPageableParams(pageable,CART_ITEMS_SORT_PROPERTIES);

        UserSessionDto user = (UserSessionDto) session.getAttribute(USER_ATTRIBUTE_KEY);
        CartDto cart;
        if (user != null) {
            cart = cartService.findOrCreateByUserId(user.getId());
        } else {
            cart = (CartDto) session.getAttribute(CART_ATTRIBUTE_KEY);
            if (cart == null) {
                cart = new CartDto();
            }
        }

        List<CartItemDto> itemsList = new ArrayList<>(cart.getItems());
        applySortToList(itemsList, correctedPageable.getSort());

        Page<CartItemDto> itemsPage = createPageFromList(itemsList, correctedPageable);

        Optional<String> redirectUrl = validateAndRedirectPage(
                itemsPage, correctedPageable, "/cart", attributes);

        if (redirectUrl.isPresent()) {
            return redirectUrl.get();
        }

        model.addAttribute(PAGE_ATTRIBUTE_KEY, itemsPage);
        session.setAttribute(CART_ATTRIBUTE_KEY, cart);
        addSortParamsToModel(model, itemsPage);
        return "cart/cart";
    }

    /**
     * Adds a specified quantity of a book to the shopping cart.
     * The method distinguishes between authenticated and guest users, handling
     * cart updates accordingly. After adding, the user is redirected to the
     * main books page with a success message.
     *
     * @param bookId The ID of the book to add.
     * @param quantity The number of books to add.
     * @param page The current page number for redirection.
     * @param size The current page size for redirection.
     * @param sort The current sort parameters for redirection.
     * @param session The HttpSession to manage cart and user information.
     * @param attributes RedirectAttributes for flash messages and pagination parameters.
     * @return A redirect string to the books list page.
     */
    @PostMapping("/add")
    public String addToCart(@RequestParam Long bookId,
                            @RequestParam(defaultValue = "1") int quantity,
                            @RequestParam(required = false) Integer page,
                            @RequestParam(required = false) Integer size,
                            @RequestParam(required = false) List<String> sort,
                            HttpSession session,
                            RedirectAttributes attributes) {

        UserSessionDto user = (UserSessionDto) session.getAttribute(USER_ATTRIBUTE_KEY);
        CartDto cart;

        if (user != null) {
            cart = cartService.addItemToUserCart(user.getId(), bookId, quantity);
        } else {
            BookDto book = bookService.getById(bookId);
            cart = (CartDto) session.getAttribute(CART_ATTRIBUTE_KEY);
            if (cart == null) {
                cart = new CartDto();
            }
            cartService.addItemToCart(cart, book, quantity);
        }

        session.setAttribute(CART_ATTRIBUTE_KEY, cart);
        attributes.addFlashAttribute(SUCCESS_MESSAGE_KEY,
                "Item was successfully added.");

        addPaginationAttributes(attributes, page, size, sort);
        return "redirect:/books";
    }

    /**
     * Removes a single item from the shopping cart.
     * This method removes the specified book from the cart, updates the total price,
     * and redirects the user back to the cart page with a success message.
     *
     * @param bookId The ID of the book to remove.
     * @param session The HttpSession to manage cart and user information.
     * @param attributes RedirectAttributes to add a flash message.
     * @return A redirect string to the cart page.
     */
    @PostMapping("/remove")
    public String removeFromCart(@RequestParam Long bookId,
                                 HttpSession session,
                                 RedirectAttributes attributes) {
        UserSessionDto user = (UserSessionDto) session.getAttribute(USER_ATTRIBUTE_KEY);
        CartDto cart;
        if (user != null) {
            cart = cartService.removeItemFromUserCart(user.getId(), bookId);
        } else {
            cart = (CartDto) session.getAttribute(CART_ATTRIBUTE_KEY);
            if (cart != null) {
                cart.getItems().removeIf(item -> item.getBook().getId().equals(bookId));
                cartService.updateCartTotalPrice(cart);
            }
        }
        session.setAttribute(CART_ATTRIBUTE_KEY, cart);
        attributes.addFlashAttribute(SUCCESS_MESSAGE_KEY,
                "Item was successfully removed.");
        return "redirect:/cart";
    }

    /**
     * Clears all items from the shopping cart.
     * The method clears the cart for either an authenticated user or a guest session
     * and redirects the user to the books page with a success message.
     *
     * @param session The HttpSession to manage cart and user information.
     * @param attributes RedirectAttributes to add a flash message.
     * @return A redirect string to the books list page.
     */
    @PostMapping("/clear")
    public String clearCart(HttpSession session, RedirectAttributes attributes) {

        UserSessionDto user = (UserSessionDto) session.getAttribute(USER_ATTRIBUTE_KEY);

        if (user != null) {
            CartDto updatedCart = cartService.clearCartByUserId(user.getId());
            session.setAttribute(CART_ATTRIBUTE_KEY, updatedCart);
        } else {
            session.setAttribute(CART_ATTRIBUTE_KEY, new CartDto());
        }

        attributes.addFlashAttribute(SUCCESS_MESSAGE_KEY,
                "Cart was successfully cleared.");
        return "redirect:/books";
    }
}
