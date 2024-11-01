package shopexercise.shopexercise.utils;

import javafx.scene.control.Alert;
import lombok.NoArgsConstructor;
import org.mindrot.jbcrypt.BCrypt;
import shopexercise.shopexercise.model.Customer;

import java.util.ArrayList;

@NoArgsConstructor
public class CustomerValidator {


    private boolean validPass(String pass) {
        return pass.length() > 6;
    }

    private boolean validEmail(String email) {
        return email.contains("@") && email.lastIndexOf('.') > email.lastIndexOf('@') + 3 && email.substring(email.lastIndexOf('.') + 1).length() >= 2;
    }

    private boolean validPhone(String phone) {
        if (phone.length() != 9) return false;

        for (Character c : phone.toCharArray()) {
            if (!Character.isDigit(c)) return false;
        }
        return true;
    }

    private boolean validName(String nameSurname) {
        if (nameSurname.length() <= 0) return false;
        for (Character c : nameSurname.toCharArray()) {
            if (Character.isDigit(c)) return false;
        }
        return true;
    }

    public boolean validCustomer(Customer customer) {
        if (validEmail(customer.getEmail())) {
            if (validPhone(customer.getPhone())) {
                if (validName(customer.getName())) {
                    if (validName(customer.getSurname())) {
                        if (validPass(customer.getPassword())) {
                            return true;
                        } else {
                            Alert alert = new Alert(Alert.AlertType.ERROR);
                            alert.setTitle("ERROR");
                            alert.setHeaderText(null);
                            alert.setContentText("You should write your password (min 9 characters)");
                            alert.showAndWait();
                        }
                    } else {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("ERROR");
                        alert.setHeaderText(null);
                        alert.setContentText("You should write your surname correctly");
                        alert.showAndWait();
                    }


                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("ERROR");
                    alert.setHeaderText(null);
                    alert.setContentText("You should write your name correctly");
                    alert.showAndWait();
                }
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("ERROR");
                alert.setHeaderText(null);
                alert.setContentText("You should write the phone correctly");
                alert.showAndWait();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERROR");
            alert.setHeaderText(null);
            alert.setContentText("You should write the email correctly");
            alert.showAndWait();
        }

        return false;
    }

    public Customer customerExist(ArrayList<Customer> customers, String email) {
        for (Customer customer : customers) {
            if (customer.getEmail().equalsIgnoreCase(email)) {
                return customer;
            }
        }
        return null;
    }

    public boolean checkPass(String pass, String hashPass) {
        return BCrypt.checkpw(pass, hashPass);
    }


}
