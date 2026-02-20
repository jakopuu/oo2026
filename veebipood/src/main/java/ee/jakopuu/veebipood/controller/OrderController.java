package ee.jakopuu.veebipood.controller;

import ee.jakopuu.veebipood.Repository.OrderRepository;
import ee.jakopuu.veebipood.dto.OrderRowDto;
import ee.jakopuu.veebipood.entity.Order;
import ee.jakopuu.veebipood.entity.OrderRow;
import ee.jakopuu.veebipood.service.OrderService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
public class OrderController {

    private OrderService orderService;
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
    public Order addOrder(@RequestParam Long personid,
                                @RequestParam(required = false) String parcelMachine,
                                @RequestBody List<OrderRowDto> orderRows){
        return orderService.saveOrder(personid,parcelMachine,orderRows);
        //return orderRepository.findAll();
    }

}