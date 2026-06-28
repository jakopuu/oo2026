package ee.jakopuu.veebipood.controller;

import ee.jakopuu.veebipood.Repository.ProductRepository;
import ee.jakopuu.veebipood.entity.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
public class ProductController {

    @Autowired
    private ProductRepository productRepository;

    // localhost:8080/products?page=0&size=4&sort=price,asc&activeCategoryId=0
    @GetMapping("products")
    public Page<Product> getProducts(Pageable pageable, @RequestParam(defaultValue = "0") Long activeCategoryId) {
        if (activeCategoryId > 0) {
            return productRepository.findByCategoryId(activeCategoryId, pageable);
        }
        return productRepository.findAll(pageable);
    }

    @GetMapping("products/admin")
    public List<Product> getAdminProducts(){
        return productRepository.findAll();
    }

    @GetMapping("products/{id}")
    public Product getOneProducts(@PathVariable Long id){
        return productRepository.findById(id).orElseThrow( );
    }

    @DeleteMapping("products/{id}")
    public List<Product> deleteProduct(@PathVariable Long id){
        productRepository.deleteById(id);
        return productRepository.findAll();
    }

    @PostMapping("products")
    public List<Product> addProduct(@RequestBody Product product){
        if (product.getId()!=null){
            throw new RuntimeException("Cannot add with ID");
        }
        productRepository.save(product);
        return productRepository.findAll();
    }
    @PutMapping("products")
    public List<Product> editProduct(@RequestBody Product product){
        if (product.getId()==null){
            throw new RuntimeException("Cannot edit without ID");
        }
        if (!productRepository.existsById(product.getId())){
            throw new RuntimeException("Product ID does not exit");
        }
        productRepository.save(product);
        return productRepository.findAll();
    }
}