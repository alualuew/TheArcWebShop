package com.controller;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import com.dto.ProductDTO;
import com.model.Product;
import com.service.ProductService;

import jakarta.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/products")
public class ProductController {

    /////
    // Init
    /////

    private ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    /////
    // Methods
    /////

    @GetMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public List<Product> getAllProducts(
            @RequestParam(name = "searchterm", required = false) String searchterm,
            @RequestParam(name = "activeFilter", required = false) Boolean activeFilter) {

        List<Product> filteredProducts = productService.getAllFilteredProdcuts(searchterm, activeFilter);

        return filteredProducts;
    }

    @PostMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Product> createProduct(@RequestBody @Valid ProductDTO productDTO) {
        Product product = productService.save(fromDTO(productDTO));
        return ResponseEntity.created(URI.create("http://localhost:8080/products")).body(product);
    }
    
    @PutMapping("/update")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Product> updateProduct(@RequestBody @Valid ProductDTO productDTO) {
        Optional<Product> optionalProduct = productService.getProductById(productDTO.getId());
        if (optionalProduct.isPresent()) {
            Product existingProduct = optionalProduct.get();
            existingProduct.setName(productDTO.getName());
            existingProduct.setDescription(productDTO.getDescription());
            existingProduct.setImageUrl(productDTO.getImageUrl());
            existingProduct.setPrice(productDTO.getPrice());
            existingProduct.setQuantity(productDTO.getQuantity());
            existingProduct.setType(productDTO.getType());
            existingProduct.setActive(productDTO.isActive());
            Product updatedProduct = productService.updateProduct(existingProduct);
            return ResponseEntity.ok(updatedProduct);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        Optional<Product> optionalProduct = productService.getProductById(id);
        if (optionalProduct.isPresent()) {
            productService.deleteProductAndFile(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /////
    // ProductDTO-Object in Product-Object
    /////

    private static Product fromDTO(ProductDTO productDTO) {
        return new Product(productDTO.getName(),
                productDTO.getDescription(),
                productDTO.getImageUrl(),
                productDTO.getPrice(),
                productDTO.getQuantity(),
                productDTO.getType(),
                productDTO.isActive());
    }
}
