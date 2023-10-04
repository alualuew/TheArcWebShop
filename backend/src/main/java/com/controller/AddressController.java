package com.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.model.Address;
import com.model.User;
import com.service.AddressService;
import com.service.UserService;

@RestController
@RequestMapping("/addresses")
public class AddressController {

    /////
    //Init
    /////

    private final AddressService addressService;
    private final UserService userService;

    @Autowired
    public AddressController(AddressService addressService, UserService userService) {
        this.addressService = addressService;
        this.userService = userService;
    }

    /////
    //Methods
    /////

    @GetMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public List<Address> getAllAddresses() {
        return addressService.getAllAddresses();
    }

    @PostMapping("/users/{id}")
    public ResponseEntity<Address> createAddress(@PathVariable("id") Long id, @RequestBody Address address) {
        Optional<User> userOptional = userService.getUserById(id);
        if (!userOptional.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        User user = userOptional.get();
        Address createdAddress = addressService.createAddress(address);
        user.setAddress(createdAddress);
        userService.updateUser(user);

        return ResponseEntity.ok(createdAddress);
    }
}
