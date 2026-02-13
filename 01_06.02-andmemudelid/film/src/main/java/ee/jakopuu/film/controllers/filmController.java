package ee.jakopuu.film.controllers;

import ee.jakopuu.film.Repository.FilmRepository;
import ee.jakopuu.film.entity.Movie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class filmController{

    @Autowired
    private FilmRepository filmRepository;

    @GetMapping("Movies")
    public List<Movie> getMovies(){
        return filmRepository.findAll();
    }

    @DeleteMapping("Movies/{id}")
    public List<Movie> deleteMovies(@PathVariable Long id) {
        filmRepository.deleteById(id);
        return filmRepository.findAll();
    }
    @PostMapping("Movies")
    public List<Movie> addMovies(@RequestBody Movie movie){
        filmRepository.save(movie);
        return filmRepository.findAll();
    }
}
