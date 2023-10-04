package com.controller;

import com.model.Cart;
import com.model.Position;
import com.service.CartService;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.HttpStatus.CREATED;

import java.util.Set;

@RestController
@RequestMapping("/carts")
public class CartController {

    /////
    //Init
    /////
    
    private CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    /////
    //Methods
    /////

    @ResponseStatus(code = CREATED)
    @PostMapping
    public Cart create(@RequestBody Cart cart) {
        return cartService.save(cart);
    }

    @GetMapping("/{userId}/positions")
    public Set<Position> getPositionsInCart(HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        Set<Position> positions = cartService.getPositionsInCart(token);
        return positions;
    }
}
