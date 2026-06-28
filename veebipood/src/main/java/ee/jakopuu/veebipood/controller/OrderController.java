package ee.jakopuu.veebipood.controller;

import ee.jakopuu.veebipood.Repository.OrderRepository;
import ee.jakopuu.veebipood.dto.OrderRowDto;
import ee.jakopuu.veebipood.dto.ParcelMachine;
import ee.jakopuu.veebipood.dto.PaymentUrl;
import ee.jakopuu.veebipood.entity.Order;
import ee.jakopuu.veebipood.entity.OrderRow;
import ee.jakopuu.veebipood.service.OrderService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@AllArgsConstructor
public class OrderController {

    private OrderService orderService;
    private OrderRepository orderRepository;
    private final RestTemplate restTemplate = new RestTemplate();

    @GetMapping("orders")
    public List<Order> getOrders(){
        return orderRepository.findAll();
    }

    @DeleteMapping("orders/{id}")
    public List<Order> deleteOrder(@PathVariable Long id){
        orderRepository.deleteById(id);
        return orderRepository.findAll();
    }

    // Omniva pakiautomaatide nimekiri, riigi järgi filtreeritud (nt EE, LV, LT)
    @GetMapping("orders/parcelmachines")
    public List<ParcelMachine> getParcelMachines(@RequestParam String country) {
        String url = "https://www.omniva.ee/locations.json";
        ParcelMachine[] response = restTemplate.exchange(url, HttpMethod.GET, null, ParcelMachine[].class).getBody();
        return Arrays.stream(response)
                .filter(e -> e.getA0_name().equals(country.toUpperCase()))
                .toList();
    }

    @PostMapping("orders")
    public PaymentUrl addOrder(@RequestParam Long personid,
                                @RequestParam(required = false) String parcelMachine,
                                @RequestBody List<OrderRowDto> orderRows){
        Order order = orderService.saveOrder(personid, parcelMachine, orderRows);
        return orderService.makePayment(order.getId(), order.getTotal());
    }

}