package service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.model.Product;

import repository.ProductRepository;

@Service
public class ProductService {

private ProductRepository productRepository;

public ProductService(ProductRepository productRepository) {
    this.productRepository = productRepository;
}

public List<Product> findAll() {  //findAll() ruft alle Produkte aus dem Datenbank-Repository ab und gibt sie als Liste zurück
    return productRepository.findAll();
}

public Product save(Product product) { // speichert ein Produkt in der Datenbank und gibt das gespeicherte Produkt zurück
    return productRepository.save(product);
}
@PostMapping
public Product createProduct (@RequestBody Product product) { //neues Produkt in der Datenbank hinzu und gibt erstellte Produkt zurück. Diese Methode wird durch eine POST-Anfrage vom Frontend aufgerufen und die Request-Body-Daten werden als Produkt-Objekt bereitgestellt.
    return productRepository.save(product);
}

public Optional<Product> findById(Long id) {
    return productRepository.findById(id);
}

public void deleteProduct(Long id) {
    productRepository.deleteById(id);
}

public List<Product> searchProducts(String name) {
    return productRepository.searchByName(name);
}
}