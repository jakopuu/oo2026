package ee.jakopuu.veebipood.controller;

import ee.jakopuu.veebipood.dto.Supplier1Product;
import ee.jakopuu.veebipood.dto.Supplier2Product;
import org.springframework.http.HttpMethod;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

// Mõte: e-poel on mitu tarnijat, kellelt tooteid sisse tuua. Siin 2 näidet erinevate kujudega API-dest.
@RestController
@CrossOrigin(origins = "*")
public class SupplierController {

    private final RestTemplate restTemplate = new RestTemplate();

    // Ainult üle 4-tärnilise hinnanguga tooted
    @GetMapping("supplier1")
    public List<Supplier1Product> getProductsSupplier1() {
        String url = "https://fakestoreapi.com/products";
        Supplier1Product[] response = restTemplate.exchange(url, HttpMethod.GET, null, Supplier1Product[].class).getBody();
        return Arrays.stream(response)
                .filter(p -> p.getRating().getRate() > 4.0)
                .toList();
    }

    // Hinna järgi sorteeritud
    @GetMapping("supplier2")
    public List<Supplier2Product> getProductsSupplier2() {
        String url = "https://api.escuelajs.co/api/v1/products";
        Supplier2Product[] response = restTemplate.exchange(url, HttpMethod.GET, null, Supplier2Product[].class).getBody();
        return Arrays.stream(response)
                .sorted(Comparator.comparing(Supplier2Product::getPrice))
                .toList();
    }
}
