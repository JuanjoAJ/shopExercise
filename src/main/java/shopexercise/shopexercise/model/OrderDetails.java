package shopexercise.shopexercise.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderDetails {
    private int id;
    private int idOrderFK;
    private int idProductFK;
    private int amount;
    private double unitPrice;


    public OrderDetails(int idOrderFK, int idProductFK, int amount, double unitPrice) {
        this.idOrderFK = idOrderFK;
        this.idProductFK = idProductFK;
        this.amount = amount;
        this.unitPrice = unitPrice;
    }

    public OrderDetails(int idOrderFK, int idProductFK, double unitPrice) {
        this.idOrderFK = idOrderFK;
        this.idProductFK = idProductFK;
        this.amount = 1;
        this.unitPrice = unitPrice;
    }
}
