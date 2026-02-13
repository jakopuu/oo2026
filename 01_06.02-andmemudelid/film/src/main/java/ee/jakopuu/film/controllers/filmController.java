package ee.jakopuu.film.controllers;

import ee.jakopuu.film.Repository.FilmRepository;
import ee.jakopuu.film.entity.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class filmController{

    @Autowired
    private FilmRepository filmRepository;

    @GetMapping("products")
    public List<Product> getProducts(){
        return filmRepository.findAll();
    }

    @GetMapping("products/{id}")
    public List<Product> deleteProduct(@PathVariable Long id) {
        filmRepository.deleteById(id);
        return filmRepository.findAll();
    }
    @PostMapping("products")
    public List<Product> addProduct(@RequestBody Product product){
        filmRepository.save(product);
        return filmRepository.findAll();
    }
}
