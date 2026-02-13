package ee.jakopuu.veebipood.controller;

import ee.jakopuu.veebipood.Repository.ProductRepository;
import ee.jakopuu.veebipood.entity.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ProductController {

    @Autowired
    private ProductRepository productRepository;

    @GetMapping("products")
    public List<Product> getProducts(){
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