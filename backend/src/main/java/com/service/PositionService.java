package com.service;

import java.util.Optional;

import com.model.Cart;
import com.model.Position;
import com.model.Product;
import com.model.User;
import com.repository.PositionRepository;
import com.repository.UserRepository;

import org.springframework.stereotype.Service;

@Service
public class PositionService {

    private final PositionRepository positionRepository;
    private final UserRepository userRepository;

    private final CartService cartService;
    private final ProductService productService;

    private final TokenService tokenService;

    // /////////////////////////////////////////////////////////////////////////
    // Init
    // /////////////////////////////////////////////////////////////////////////

    public PositionService(PositionRepository positionRepository,
            UserRepository userRepository,
            CartService cartService,
            ProductService productService,
            TokenService tokenService) {
        this.positionRepository = positionRepository;
        this.userRepository = userRepository;
        this.cartService = cartService;
        this.productService = productService;
        this.tokenService = tokenService;
    }

    /////
    // Methods
    /////

    public Optional<Position> findById(Long id) {
        return positionRepository.findById(id);
    }

    public Position save(Position position, Long productId, String token) {

        Long userId = tokenService.getUserIdFromToken(token);

        Cart cart = cartService.findByUserId(userId);

        if (cart == null) {
            Optional<User> user = userRepository.findById(userId);

            if (user.isPresent()) {
                cart = cartService.save(new Cart(user.get()));
            } else {
                throw new RuntimeException("User does not exist");
            }
        }

        Optional<Product> product = productService.getProductById(productId);

        if (product.isEmpty()) {
            throw new RuntimeException("Product does not exist");
        }

        Position existingPosition = positionRepository.findByCartAndProduct(cart, product.get());

        if (existingPosition != null) {
            existingPosition.setQuantity(existingPosition.getQuantity() + 1);
            return positionRepository.save(existingPosition);
        } else {
            position.setCart(cart);
            position.setProduct(product.get());
            return positionRepository.save(position);
        }
    }

    public void deletePosition(Long id, String token) {
        Long userId = tokenService.getUserIdFromToken(token);
        Long cartId = cartService.findByUserId(userId).getId();
        positionRepository.deleteByIdAndCartId(id, cartId);
    }
}
