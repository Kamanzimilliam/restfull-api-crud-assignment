package auca.ac.rw.restfullApiAssignment.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import auca.ac.rw.restfullApiAssignment.modal.ecommerce.Product;
import auca.ac.rw.restfullApiAssignment.service.ProductService;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    // ─────────────────────────────────────────────
    // CREATE
    // ─────────────────────────────────────────────

    // POST /api/products/addProduct
    @PostMapping(value = "/addProduct",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> addNewProduct(@RequestBody Product product) {
        String result = productService.addNewProduct(product);
        if (result.equals("Product added successfully")) {
            return new ResponseEntity<>(result, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(result, HttpStatus.CONFLICT);
        }
    }

    // ─────────────────────────────────────────────
    // READ
    // ─────────────────────────────────────────────

    // GET /api/products/getAllProducts
    @GetMapping(value = "/getAllProducts", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Product>> getAllProducts() {
        List<Product> products = productService.getAllProducts();
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    // GET /api/products/getProduct/{id}
    @GetMapping(value = "/getProduct/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getProductById(@PathVariable Long id) {
        Optional<Product> product = productService.getProductById(id);
        if (product.isPresent()) {
            return new ResponseEntity<>(product.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Product with id " + id + " not found", HttpStatus.NOT_FOUND);
        }
    }

    // GET /api/products/getProductsByCategory/{category}
    @GetMapping(value = "/getProductsByCategory/{category}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getProductsByCategory(@PathVariable String category) {
        List<Product> products = productService.searchByCategory(category);
        if (products != null) {
            return new ResponseEntity<>(products, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("No products found for category: " + category, HttpStatus.NOT_FOUND);
        }
    }

    // GET /api/products/getProductsByBrand/{brand}
    @GetMapping(value = "/getProductsByBrand/{brand}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getProductsByBrand(@PathVariable String brand) {
        List<Product> products = productService.searchByBrand(brand);
        if (products != null) {
            return new ResponseEntity<>(products, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("No products found for brand: " + brand, HttpStatus.NOT_FOUND);
        }
    }

    // GET /api/products/getProductsByBrandAndCategory?brand=Apple&category=Electronics
    @GetMapping(value = "/getProductsByBrandAndCategory", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getProductsByBrandAndCategory(@RequestParam String brand,
                                                            @RequestParam String category) {
        List<Product> products = productService.searchByBrandAndCategory(brand, category);
        if (products != null) {
            return new ResponseEntity<>(products, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(
                "No products found for brand: " + brand + " and category: " + category,
                HttpStatus.NOT_FOUND
            );
        }
    }

    // GET /api/products/getProductsByPriceAndBrand?price=999.99&brand=Apple
    @GetMapping(value = "/getProductsByPriceAndBrand", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getProductsByPriceAndBrand(@RequestParam Double price,
                                                         @RequestParam String brand) {
        List<Product> products = productService.searchByPriceAndBrand(price, brand);
        if (products != null) {
            return new ResponseEntity<>(products, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(
                "No products found for price: " + price + " and brand: " + brand,
                HttpStatus.NOT_FOUND
            );
        }
    }

    // ─────────────────────────────────────────────
    // UPDATE
    // ─────────────────────────────────────────────

    // PUT /api/products/updateProduct/{id}
    @PutMapping(value = "/updateProduct/{id}",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> updateProduct(@PathVariable Long id, @RequestBody Product updatedProduct) {
        String result = productService.updateProduct(id, updatedProduct);
        if (result.equals("Product updated successfully")) {
            return new ResponseEntity<>(result, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
        }
    }

    // PATCH /api/products/updateStock/{id}?newStockQuantity=100
    @PatchMapping(value = "/updateStock/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> updateStock(@PathVariable Long id,
                                         @RequestParam int newStockQuantity) {
        String result = productService.updateStock(id, newStockQuantity);
        if (result.equals("Product stock updated successfully")) {
            return new ResponseEntity<>(result, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
        }
    }

    // ─────────────────────────────────────────────
    // DELETE
    // ─────────────────────────────────────────────

    // DELETE /api/products/deleteProduct/{id}
    @DeleteMapping(value = "/deleteProduct/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> deleteProduct(@PathVariable Long id) {
        String result = productService.deleteProduct(id);
        if (result.equals("Product deleted successfully")) {
            return new ResponseEntity<>(result, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
        }
    }
}