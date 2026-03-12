package ee.jakopuu.rentalstore.controller;

import ee.jakopuu.rentalstore.dto.FilmSaveDto;
import ee.jakopuu.rentalstore.entity.Film;
import ee.jakopuu.rentalstore.entity.FilmType;
import ee.jakopuu.rentalstore.repository.FilmRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class FilmController {

    private final FilmRepository filmRepository;

    @PostMapping("films")
    public Film saveFilm(@RequestBody FilmSaveDto filmSaveDto){
        Film film = new Film();
        film.setTitle(filmSaveDto.title());
        film.setType(filmSaveDto.type());
        film.setDays(0);
        return filmRepository.save(film);
    }
    @DeleteMapping("films/{id}")
    public void deleteFilm(@PathVariable Long id){
        filmRepository.deleteById(id);
    }
    @PatchMapping("films/type")
    public Film changeFilmType(@RequestParam Long id, @RequestParam FilmType filmType){
        Film film = filmRepository.findById(id).orElseThrow();
        film.setType(filmType);
        return filmRepository.save(film);
    }

    @GetMapping("films")
    public List<Film> findAll(){
        return filmRepository.findAll();
    }

    @GetMapping("films/available")
    public List<Film> findAllAvailable(){
//        List<Film> films = findAll();
//        List<Film> availableFilms = new ArrayList<>();
//        for(Film film : films){
//            if (film.getDays() == 0) {
//                availableFilms.add(film);
//            }
//        }
//        return availableFilms;
        return filmRepository.findByDays(0);
    }
}
