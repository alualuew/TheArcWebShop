package com.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.model.Product;

import service.ProductService;

@RestController
@RequestMapping("/api/products")

public class ProductController {
    
    private final ProductService productService;

    //constructor that injects the Product Service
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

@GetMapping
    public List<Product> findAllProducts() {
        return productService.findAll();
    }

    @GetMapping("/{id}")
public Optional<Product> findProductById(@PathVariable Long id) {
    return productService.findById(id);
}
@DeleteMapping("/{id}")
public void deleteProduct(@PathVariable Long id) {
    productService.deleteProduct(id);
}
@GetMapping("/search/{name}")
public List<Product> searchProducts(@PathVariable String name) {
    return productService.searchProducts(name);
}
}
