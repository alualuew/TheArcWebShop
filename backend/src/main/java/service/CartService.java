package service;

import com.model.Cart;
import repository.CartRepository;
import org.springframework.stereotype.Service;

@Service
public class CartService {
    
    private CartRepository cartRepository;

    public CartService(CartRepository cartRepository) {
        this.cartRepository = cartRepository;
    }

    // /////////////////////////////////////////////////////////////////////////
    // Methods
    // /////////////////////////////////////////////////////////////////////////

    public Cart save(Cart cart) {
        return cartRepository.save(cart);
    }

    public Cart findByUserId(Long userId) {
        return cartRepository.findByUserId(userId);
    }
}
