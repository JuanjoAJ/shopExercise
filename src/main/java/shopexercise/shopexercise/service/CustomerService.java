package shopexercise.shopexercise.service;

import javafx.scene.control.Alert;
import shopexercise.shopexercise.model.Customer;
import shopexercise.shopexercise.repositories.CustomerRepository;
import shopexercise.shopexercise.utils.CustomerValidator;
import shopexercise.shopexercise.utils.PassHasher;

import java.util.ArrayList;

public class CustomerService {
    private final ArrayList<Customer> customers;
    private final CustomerValidator customerValidator;
    private final CustomerRepository customerRepository;


    public CustomerService() {
        customers = new ArrayList<>();
        customerValidator = new CustomerValidator();
        customerRepository = new CustomerRepository();
    }

    public void createCustomer(Customer customer) {
        if (customerValidator.validCustomer(customer)) {
            if (customerValidator.customerExist(customerRepository.getCustomers(), customer.getEmail()) == null) {
                String passHash = PassHasher.hashPassword(customer.getPassword());
                customerRepository.registerCustomer(customer, passHash);
                customers.add(customer);
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("REGISTERED");
                alert.setHeaderText(null);
                alert.setContentText("Thank you! Welcome to the family of \"Shopping with Juanjo\"");
                alert.showAndWait();
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("ERROR");
                alert.setHeaderText(null);
                alert.setContentText("The email you have provided is already associated with an account");
                alert.showAndWait();
            }
        }

    }

    public Customer getCustomer(String mail, String passPlain) {
        Customer customer;
        if ((customer = customerRepository.getCustomer(mail)) != null) {
            if (customerValidator.checkPass(passPlain, customer.getPassword())) {
                return customer;
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("ERROR");
                alert.setHeaderText(null);
                alert.setContentText("The password you have provided is not correct");
                alert.showAndWait();

            }
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERROR");
            alert.setHeaderText(null);
            alert.setContentText("The email you have provided doesn't exist");
            alert.showAndWait();
        }
        return null;
    }


}
