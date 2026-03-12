package ee.jakopuu.veateated.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity

public class Car {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String brand;
    private String model;
    private int year;
    private double price;
    private String vin;
    private String regNumber;

    public List<Car> findAll() {
        return null;
    }
}
