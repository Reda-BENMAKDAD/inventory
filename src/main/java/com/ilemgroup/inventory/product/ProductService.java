package com.ilemgroup.inventory.product;
import java.util.List;
import org.springframework.stereotype.Service;



@Service
public class ProductService {
    private final ProductDao productDao;

    public ProductService(ProductDao productDao) {
        this.productDao = productDao;
    }  

    public Product getProductById(Long id) {
        return productDao.findById(id);

    }

    public List<Product> getAllProducts() {
        return this.productDao.findAll();
    }

    public void createProduct(Product product) {
        productDao.save(product);
    }

    public void updateProduct(Product product) {
        productDao.update(product);
    }

    public void deleteProduct(Long id) {
        productDao.delete(id);
    }
    
}