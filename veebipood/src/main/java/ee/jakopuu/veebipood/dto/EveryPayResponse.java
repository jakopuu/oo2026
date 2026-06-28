package ee.jakopuu.veebipood.dto;

import lombok.Data;

@Data
public class EveryPayResponse {
    private String account_name;
    private String order_reference;
    private double initial_amount;
    private String payment_reference;
    private String payment_link;
    private String payment_state;
    private String currency;
}
