package models;

import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
public class Product {
    private int  id;
    private int price;
    private Map<String, String> metadata;
}
