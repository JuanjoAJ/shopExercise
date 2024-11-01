package shopexercise.shopexercise.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Customer {


    private int id;
    private String name;
    private String surname;
    private String address;
    private String password;
    private String email;
    private String phone;


    public Customer(String name, String surname, String address, String password, String email, String phone) {
        this.name = name;
        this.surname = surname;
        this.address = address;
        this.password = password;
        this.email = email;
        this.phone = phone;
    }

    public Customer(int id, String name, String surname, String address, String password, String email, String phone) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.address = address;
        this.password = password;
        this.email = email;
        this.phone = phone;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
