package shopexercise.shopexercise.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Product {

    private int id;
    private String title;
    private String description;
    private String category;
    private Double price;
    private int stock;

    public Product(int id, String title, String description, String category, Double price, int stock) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.category = category;
        this.price = price;
        this.stock = stock;
    }
}
