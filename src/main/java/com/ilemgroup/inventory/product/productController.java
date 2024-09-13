package com.ilemgroup.inventory.product;

import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/v1/product")
public class productController {
    
    private ProductService productService;

    public productController(ProductService productService) {
        this.productService = productService;
    }


    @GetMapping
    public List<Product> getAllProducts() {
        return productService.getAllProducts();
    }

    @GetMapping("/{id}")
    public Optional<Product> getProductById(@PathVariable Long id) {
        return Optional.ofNullable(productService.getProductById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Ce produit n'existe pas")));
    } 
    
    @PostMapping
    public HttpStatus createProduct(@RequestBody Product product){
        productService.createProduct(product);
        return HttpStatus.CREATED;
    }

    @PutMapping("/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable Long id, @RequestBody Product product) {
        System.out.println("id : " + id);
        Product updatedproduct =  productService.updateProduct(id, product);
        if (updatedproduct != null) {
            return ResponseEntity.ok(updatedproduct);
        } else {
            return ResponseEntity.notFound().build();
        }
    } 


    @DeleteMapping
    public void deleteProduct(@PathVariable Long id){
        productService.deleteProduct(id);
    }

}
