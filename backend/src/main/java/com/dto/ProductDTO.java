package com.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;

public class ProductDTO {

    private Long id;

    @NotBlank
    @Length(min = 4, max = 100)
    private String name;

    private String description;
    private String imageUrl;

    @DecimalMin("0.01")
    private double price;

    @Min(1)
    private int quantity;

    private String type;
    
    private boolean active;


    // /////////////////////////////////////////////////////////////////////////
    // Getters and Setters
    // /////////////////////////////////////////////////////////////////////////

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
