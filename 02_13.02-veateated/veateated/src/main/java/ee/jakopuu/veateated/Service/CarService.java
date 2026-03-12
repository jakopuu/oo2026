package ee.jakopuu.veateated.Service;

import ee.jakopuu.veateated.entity.Car;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Data
@Service
@RequiredArgsConstructor
public class CarService {

    private final String regex = "^[A-HJ-NPR-Za-hj-npr-z\\\\d]{8}[\\\\dX][A-HJ-NPR-Za-hj-npr-z\\\\d]{2}\\\\d{6}$";
    private final Pattern pattern = Pattern.compile(regex);

    public boolean isValid(String vin) {
        Matcher matcher = pattern.matcher(vin);
        return matcher.matches();
    }

    public void validate(Car car){
        if(car.getBrand() == null){
            throw new RuntimeException("Cannot sign up without brand");
        }
        if(car.getModel() == null){
            throw new RuntimeException("Cannot sign up without Model");
        }
        if(car.getYear() <= 1886){
            throw new RuntimeException("Car is way too old");
        }
        if(car.getYear() > 2026 ){
            throw new RuntimeException("Car not manufactured yet");
        }
        if(!isValid(car.getVin())){
            throw new RuntimeException("Invalid Vin");
        }
    }
}
