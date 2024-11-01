package shopexercise.shopexercise.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import shopexercise.shopexercise.MainApp;
import shopexercise.shopexercise.model.Customer;
import shopexercise.shopexercise.service.CustomerService;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class signUpController implements Initializable {


    @FXML
    private TextField txtName;
    @FXML
    private TextField txtSurname;
    @FXML
    private TextField txtAddress;
    @FXML
    private TextField txtEmail;
    @FXML
    private TextField txtPassword;
    @FXML
    private TextField txtPhone;
    @FXML
    private Button inputButton;

    private CustomerService customerService;


    @FXML
    private void signUp() {

        String name = txtName.getText();
        String surname = txtSurname.getText();
        String address = txtAddress.getText();
        String email = txtEmail.getText();
        String password = txtPassword.getText();
        String phone = txtPhone.getText();


        customerService.createCustomer(new Customer(name, surname, address, password, email, phone));
        Stage stage = (Stage) this.inputButton.getScene().getWindow();
        stage.close();

        stage = new Stage();
        FXMLLoader loader = new FXMLLoader(MainApp.class.getResource("signIn-view.fxml"));
        Parent root = null;
        try {
            root = loader.load();
            Scene scene = new Scene(root, 402, 496);
            stage.setScene(scene);
            stage.show();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        customerService = new CustomerService();
        inputButton.setOnMouseEntered(event -> inputButton.setStyle("-fx-background-color: #333333; -fx-text-fill: #f3ca4c;"));
        inputButton.setOnMouseExited(event -> inputButton.setStyle("-fx-background-color: #000000; -fx-text-fill: #ffffff;"));
    }
}


