package ee.jakopuu.veebipood.service;

import ee.jakopuu.veebipood.Repository.OrderRepository;
import ee.jakopuu.veebipood.Repository.PersonRepository;
import ee.jakopuu.veebipood.Repository.ProductRepository;
import ee.jakopuu.veebipood.dto.OrderRowDto;
import ee.jakopuu.veebipood.entity.Order;
import ee.jakopuu.veebipood.entity.OrderRow;
import ee.jakopuu.veebipood.entity.Person;
import ee.jakopuu.veebipood.entity.Product;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@AllArgsConstructor
public class OrderService {
    private OrderRepository orderRepository;
    private PersonRepository personRepository;
    private ProductRepository productRepository;

    public Order saveOrder(Long personId, String parcelMachine, List<OrderRowDto> orderRows){
        Order order = new Order();
        order.setCreated(new Date());
        order.setParcelMachine(parcelMachine);
       // order.setOrderRows(orderRows);
        Person person = personRepository.findById(personId).orElseThrow();
        order.setPerson(person);
        order.setTotal(calculateOrderTotal(orderRows, order));
        return orderRepository.save(order);
    }

    private double calculateOrderTotal(List<OrderRowDto> orderRows, Order order) {
        double total = 0;
        List<OrderRow> orderRowsInOrder = new ArrayList<>();
        for (OrderRowDto orderRowDto : orderRows) {
            Product product = productRepository.findById(orderRowDto.productId()).orElseThrow();
            total += product.getPrice() * orderRowDto.quantity();

            OrderRow orderRowInOrder = new OrderRow();
            orderRowInOrder.setProduct(product);
            orderRowInOrder.setQuantity(orderRowDto.quantity());
            orderRowsInOrder.add(orderRowInOrder);
        }
        order.setOrderRows(orderRowsInOrder);
        return total;
    }
}
