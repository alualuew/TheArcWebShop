package com.service;

import com.model.Cart;
import com.model.Position;
import com.repository.CartRepository;

import java.util.Optional;
import java.util.Set;

import org.springframework.stereotype.Service;

@Service
public class CartService {
    
    private CartRepository cartRepository;
    private final TokenService tokenService;

    public CartService(CartRepository cartRepository, TokenService tokenService) {
        this.cartRepository = cartRepository;
        this.tokenService = tokenService;
    }

    /////
    //Methods
    /////

    public Cart save(Cart cart) {
        return cartRepository.save(cart);
    }

    public Cart findByUserId(Long userId) {
        return cartRepository.findByUserId(userId);
    }
    
    public Set<Position> getPositionsInCart(String token) {

        Long userId = tokenService.getUserIdFromToken(token);
        Optional<Cart> cartOptional = cartRepository.findCartByUserId(userId);

        if (cartOptional.isPresent()) {
            Cart cart = cartOptional.get();
            return cart.getPositions(); // Hiermit werden alle Positionen für den angegebenen Warenkorb zurückgegeben
        } else {
            throw new EntityNotFoundException();
        }
    }
}
