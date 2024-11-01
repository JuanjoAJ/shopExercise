package shopexercise.shopexercise.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import lombok.Getter;
import lombok.Setter;
import shopexercise.shopexercise.MainApp;
import shopexercise.shopexercise.model.Customer;
import shopexercise.shopexercise.service.CustomerService;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

@Getter
@Setter
public class SignInController implements Initializable {
    @FXML
    private TextField txtEmail;
    @FXML
    private PasswordField txtPassword;

    @FXML
    private Button inputButton;
    @FXML
    private Button inputSignUp;

    private Customer customer;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        inputButton.setOnMouseEntered(event -> inputButton.setStyle("-fx-background-color: #333333; -fx-text-fill: #f3ca4c;"));
        inputButton.setOnMouseExited(event -> inputButton.setStyle("-fx-background-color: #000000; -fx-text-fill: #ffffff;"));
        inputSignUp.setOnMouseEntered(event -> inputSignUp.setStyle("-fx-text-fill:#ba2b2b; -fx-background-color: #faf0e6; -fx-underline: true;"));
        inputSignUp.setOnMouseExited(event -> inputSignUp.setStyle("-fx-text-fill:#be5050; -fx-background-color: #faf0e6;"));
        this.customer = new Customer();
    }

    @FXML
    private void signIn() {
        String email = txtEmail.getText();
        String password = txtPassword.getText();
        CustomerService customerService = new CustomerService();
        if ((this.customer = customerService.getCustomer(email, password)) != null) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Welcome");
            alert.setHeaderText(null);
            alert.setContentText(String.format("Welcome %s", this.customer.getName()));
            alert.showAndWait();

            try {
                FXMLLoader loader = new FXMLLoader(MainApp.class.getResource("shopping-view.fxml"));
                Parent root = loader.load();
                ShoppingController shoppingController = loader.getController();
                shoppingController.setCustomer(this.customer);

                Stage stage = (Stage) this.inputButton.getScene().getWindow();
                stage.close();

                stage = new Stage();
                Scene scene = new Scene(root, 894, 581);
                stage.setScene(scene);
                stage.show();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }


    @FXML
    private void signUp(ActionEvent event) {
        Stage currenStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Stage stage = new Stage();
        FXMLLoader loader = new FXMLLoader(MainApp.class.getResource("signUp-view.fxml"));
        Parent root = null;
        try {

            root = loader.load();
            Scene scene = new Scene(root, 402, 496);
            stage.setScene(scene);
            stage.show();
            currenStage.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}