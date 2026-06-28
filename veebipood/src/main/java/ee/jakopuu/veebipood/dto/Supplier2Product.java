package ee.jakopuu.veebipood.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

@Getter
@Setter
public class Supplier2Product {
    private int id;
    private String title;
    private int price;
    private String description;
    private Supplier2Category category;
    private ArrayList<String> images;
}
