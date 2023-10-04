package com.repository;

import com.model.Cart;
import com.model.Position;
import com.model.Product;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PositionRepository extends JpaRepository<Position, Long> {
   
    void deleteByIdAndCartId(Long id, Long cartId);

    Position findByCartAndProduct(Cart cart, Product product);

}
