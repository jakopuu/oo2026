package ee.jakopuu.veebipood.controller;

import ee.jakopuu.veebipood.Repository.OrderRepository;
import ee.jakopuu.veebipood.entity.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class OrderController {
    @Autowired
    private OrderRepository orderRepository;

    @GetMapping("orders")
    public List<Order> getOrders(){
        return orderRepository.findAll();
    }

    @GetMapping("orders/{id}")
    public List<Order> deleteOrder(@PathVariable Long id){
        orderRepository.deleteById(id);
        return orderRepository.findAll();
    }

    @PostMapping("orders")
    public List<Order> addOrder(@RequestBody Order Order){
        orderRepository.save(Order);
        return orderRepository.findAll();
    }

}