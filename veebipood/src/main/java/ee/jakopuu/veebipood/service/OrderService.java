package ee.jakopuu.veebipood.service;

import ee.jakopuu.veebipood.Repository.OrderRepository;
import ee.jakopuu.veebipood.Repository.PersonRepository;
import ee.jakopuu.veebipood.Repository.ProductRepository;
import ee.jakopuu.veebipood.dto.EveryPayBody;
import ee.jakopuu.veebipood.dto.EveryPayResponse;
import ee.jakopuu.veebipood.dto.OrderRowDto;
import ee.jakopuu.veebipood.dto.PaymentUrl;
import ee.jakopuu.veebipood.entity.Order;
import ee.jakopuu.veebipood.entity.OrderRow;
import ee.jakopuu.veebipood.entity.Person;
import ee.jakopuu.veebipood.entity.Product;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@AllArgsConstructor
public class OrderService {
    private OrderRepository orderRepository;
    private PersonRepository personRepository;
    private ProductRepository productRepository;
    private Environment environment;
    private final RestTemplate restTemplate = new RestTemplate();

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

    public PaymentUrl makePayment(Long orderId, double sum) {
        String everyPayApiUsername = environment.getProperty("everypay.api-username", "");
        String everyPayApiSecret = environment.getProperty("everypay.api-secret", "");
        String everyPayAccountName = environment.getProperty("everypay.account-name", "");
        String everyPayCustomerUrl = environment.getProperty("everypay.customer-url", "http://err.ee");

        if (everyPayApiUsername.isBlank()) {
            throw new RuntimeException("EveryPay seaded puuduvad application.properties failist (everypay.*)");
        }

        EveryPayBody body = new EveryPayBody();
        body.setAccount_name(everyPayAccountName);
        body.setNonce("jakopuu" + ZonedDateTime.now() + Math.random()); // turvaelement, et ei läheks topeltpäring
        body.setTimestamp(ZonedDateTime.now().toString()); // pluss-miinus 5 minutit lubatud
        body.setAmount(sum);
        body.setOrder_reference("jakopuu" + orderId); // makstud tellimust ei saa teist korda maksta
        body.setCustomer_url(everyPayCustomerUrl); // kuhu makse järel tagasi suunatakse, localhosti ei saa kasutada
        body.setApi_username(everyPayApiUsername);

        HttpHeaders headers = new HttpHeaders();
        headers.setBasicAuth(everyPayApiUsername, everyPayApiSecret);
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<EveryPayBody> entity = new HttpEntity<>(body, headers);

        String url = "https://igw-demo.every-pay.com/api/v4/payments/oneoff";
        EveryPayResponse response = restTemplate.exchange(url, HttpMethod.POST, entity, EveryPayResponse.class).getBody();

        PaymentUrl paymentUrl = new PaymentUrl();
        paymentUrl.setUrl(response.getPayment_link());
        return paymentUrl;
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
