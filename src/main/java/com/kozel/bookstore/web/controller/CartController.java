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


@Controller
@RequestMapping("/cart")
@RequiredArgsConstructor
public class CartController implements PaginationValidator, InMemoryPaginationHandler {


    private final CartService cartService;
    private final BookService bookService;

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
