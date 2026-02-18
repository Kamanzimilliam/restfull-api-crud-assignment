package auca.ac.rw.restfullApiAssignment.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import auca.ac.rw.restfullApiAssignment.modal.ecommerce.Product;
import auca.ac.rw.restfullApiAssignment.repository.ProductRepository;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    // CREATE
    public String addNewProduct(Product product) {
        Optional<Product> existProduct = productRepository.findById(product.getId());
        if (existProduct.isPresent()) {
            return "Product with id " + product.getId() + " already exists";
        } else {
            productRepository.save(product);
            return "Product added successfully";
        }
    }
// READ - Get by Brand AND Category
public List<Product> searchByBrandAndCategory(String brand, String category) {
    List<Product> products = productRepository.findByBrandAndCategory(brand, category);
    return (products != null && !products.isEmpty()) ? products : null;
}
    // READ - Get All
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    // READ - Get by ID
    public Optional<Product> getProductById(Long id) {
        return productRepository.findById(id);
    }

    // READ - Get by Category
    public List<Product> searchByCategory(String categoryName) {
        List<Product> products = productRepository.findByCategory(categoryName);
        return (products != null && !products.isEmpty()) ? products : null;
    }

    // READ - Get by Brand
    public List<Product> searchByBrand(String brandName) {
        List<Product> products = productRepository.findByBrand(brandName);
        return (products != null && !products.isEmpty()) ? products : null;
    }

    // READ - Get by Price and Brand
    public List<Product> searchByPriceAndBrand(Double price, String brand) {
        List<Product> products = productRepository.findByPriceAndBrand(price, brand);
        return (products != null && !products.isEmpty()) ? products : null;
    }

    // UPDATE - Full update
    public String updateProduct(Long id, Product updatedProduct) {
        Optional<Product> existingProduct = productRepository.findById(id);
        if (existingProduct.isPresent()) {
            Product product = existingProduct.get();
            product.setName(updatedProduct.getName());
            product.setDescription(updatedProduct.getDescription());
            product.setPrice(updatedProduct.getPrice());
            product.setCategory(updatedProduct.getCategory());
            product.setStockQuantity(updatedProduct.getStockQuantity());
            product.setBrand(updatedProduct.getBrand());
            productRepository.save(product);
            return "Product updated successfully";
        } else {
            return "Product with id " + id + " not found";
        }
    }

    // UPDATE - Partial stock update
    public String updateStock(Long id, int newStockQuantity) {
        Optional<Product> existingProduct = productRepository.findById(id);
        if (existingProduct.isPresent()) {
            Product product = existingProduct.get();
            product.setStockQuantity(newStockQuantity);
            productRepository.save(product);
            return "Product stock updated successfully";
        } else {
            return "Product with id " + id + " not found";
        }
    }

    // DELETE
    public String deleteProduct(Long id) {
        Optional<Product> existingProduct = productRepository.findById(id);
        if (existingProduct.isPresent()) {
            productRepository.deleteById(id);
            return "Product deleted successfully";
        } else {
            return "Product with id " + id + " not found";
        }
    }
}
    
