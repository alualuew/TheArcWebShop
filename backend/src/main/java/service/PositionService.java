package service;

import java.util.Optional;

import com.model.Cart;
import com.model.Position;
import com.model.Product;
import com.model.User;
import repository.PositionRepository;
import repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class PositionService {

    private final PositionRepository positionRepository;
    private final UserRepository userRepository;

    private final CartService cartService;
    private final ProductService productService;

    // /////////////////////////////////////////////////////////////////////////
    // Init
    // /////////////////////////////////////////////////////////////////////////

    public PositionService(PositionRepository positionRepository,
                           UserRepository userRepository,
                           CartService cartService,
                           ProductService productService) {
        this.positionRepository = positionRepository;
        this.userRepository = userRepository;
        this.cartService = cartService;
        this.productService = productService;
    }

    // /////////////////////////////////////////////////////////////////////////
    // Methods
    // /////////////////////////////////////////////////////////////////////////

    public Optional<Position> findById(Long id) {
        return positionRepository.findById(id);
    }

    public Position save(Position position, Long userId, Long productId) {
        Cart cart = cartService.findByUserId(userId);

        if (cart == null) {
            Optional<User> user = userRepository.findById(userId);

            if (user.isPresent()) {
                cart = cartService.save(new Cart(user.get()));
            } else {
                // TODO: throw 403 error, user does not exist
                throw new RuntimeException("User does not exist");
            }
        }

        Optional<Product> product = productService.findById(productId);

        if (product.isEmpty()) {
            // TODO: throw 400 bad request, product does not exist
            throw new RuntimeException("Product does not exist");
        }

        position.setCart(cart);
        position.setProduct(product.get());

        return positionRepository.save(position);
    }
}
