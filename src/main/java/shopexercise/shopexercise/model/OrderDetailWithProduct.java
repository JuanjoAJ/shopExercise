package shopexercise.shopexercise.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderDetailWithProduct {
    private Product product;
    private OrderDetails orderDetails;

    public int getIDOrder(){return orderDetails.getIdOrderFK();}

    public String getTitle() {
        return product.getTitle();
    }

    public String getCategory() {
        return product.getCategory();
    }

    public Double getPrice() {
        return product.getPrice();
    }

    public Integer getAmount() {
        return orderDetails.getAmount();
    }

    @Override
    public String toString() {
        return String.format("Product: %s Amount: %s Price: %.2f TOTAL:%.2f", product.getTitle(), orderDetails.getAmount(), orderDetails.getUnitPrice(), (orderDetails.getAmount()*orderDetails.getUnitPrice()));
    }
}