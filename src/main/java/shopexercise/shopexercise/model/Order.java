package shopexercise.shopexercise.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Order {
    private int id;
    private String dateOrder;
    private int idCustomerFK;
    private double totalPrice;
    private boolean finished;

    public Order(int idCustomerFK) {

        this.dateOrder = String.valueOf(LocalDate.now());
        this.idCustomerFK = idCustomerFK;
        this.totalPrice = 0;
    }
}
