package com.model;
import javax.validation.constraints.NotBlank;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;


@Entity
public class User{

@Id //für Primärschlüsselfeld
@GeneratedValue //vergibt automatisch Identifikation
@Column(name = "id")
private  Long id;

@Column(name = "username", nullable = false)
private String username;

@Column(name = "password", nullable = false)
private String password;

@Column(name = "admin", nullable = false)
private boolean admin;

@NotBlank
@Column(name = "email")
private String email;

@NotBlank
@Column(name = "firstname")
private String firstname;

@NotBlank
@Column(name =  "lastname") 
private String lastname;

@NotBlank
@Column(name = "postalcode")
private Long postalcode;

@NotBlank
@Column(name = "city")
private String city;

@NotBlank
@Column(name = "street")
private String street;

@NotBlank
@Column(name = "streetnumber")
private Long streetnumber;


//constructor
public User(Long id, String email, String firstname, String lastname, Long postalcode, String city, String street,
        Long streetnumber) {
    this.id = id;
    this.email = email;
    this.firstname = firstname;
    this.lastname = lastname;
    this.postalcode = postalcode;
    this.city = city;
    this.street = street;
    this.streetnumber = streetnumber;
}


//getters and setters

public Long getId() {
    return id;
}

public String getUsername() {
    return username;
}

public void setUsername(String username) {
    this.username = username;
}

public String getPassword() {
    return password;
}

public void setPassword(String password) {
    this.password = password;
}

public boolean isAdmin() {
    return admin;
}

public void setAdmin(boolean admin) {
    this.admin = admin;
}

public String getEmail() {
    return email;
}

public void setEmail(String email) {
    this.email = email;
}

public String getFirstname() {
    return firstname;
}

public void setFirstname(String firstname) {
    this.firstname = firstname;
}

public String getLastname() {
    return lastname;
}

public void setLastname(String lastname) {
    this.lastname = lastname;
}

public Long getPostalcode() {
    return postalcode;
}

public void setPostalcode(Long postalcode) {
    this.postalcode = postalcode;
}

public String getCity() {
    return city;
}

public void setCity(String city) {
    this.city = city;
}

public String getStreetaddress() {
    return street;
}

public void setStreetaddress(String street) {
    this.street = street;
}

public Long getStreetnumber() {
    return streetnumber;
}

public void setStreetnumber(Long streetnumber) {
    this.streetnumber = streetnumber;
}

}