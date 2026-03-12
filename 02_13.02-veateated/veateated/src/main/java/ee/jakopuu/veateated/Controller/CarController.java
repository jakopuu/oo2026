package ee.jakopuu.veateated.Controller;

import ee.jakopuu.veateated.Repository.CarRepository;
import ee.jakopuu.veateated.Service.CarService;
import ee.jakopuu.veateated.entity.Car;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping
public class CarController {

    @Autowired
    private CarRepository carRepository;

    @GetMapping("cars")
    public List<Car> getCars() {
        return carRepository.findAll();
    }

    @PostMapping("cars")
    public List<Car> addCar(@RequestBody Car car) {
        if (car.getVin() == null) {
            throw new RuntimeException("Cannot add without Vin");
        }
        carRepository.save(car);
        return car.findAll();
    }
    @PutMapping("cars")
    public List<Car> editCar(@RequestBody Car car){
        if (car.getVin()==null){
            throw new RuntimeException("Cannot edit without VIN");
        }
        if (!carRepository.existsById(Long.valueOf(car.getVin()))){
            throw new RuntimeException("car VIN does not exit");
        }
        carRepository.save(car);
        return carRepository.findAll();
    }

}

