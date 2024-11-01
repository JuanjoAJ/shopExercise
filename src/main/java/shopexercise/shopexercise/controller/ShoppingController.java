package shopexercise.shopexercise.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import lombok.Getter;
import lombok.Setter;
import shopexercise.shopexercise.MainApp;
import shopexercise.shopexercise.model.Customer;
import shopexercise.shopexercise.model.Order;
import shopexercise.shopexercise.model.OrderDetails;
import shopexercise.shopexercise.model.Product;
import shopexercise.shopexercise.service.OrderService;
import shopexercise.shopexercise.service.ProductService;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

@Getter
@Setter
public class ShoppingController implements Initializable {

    public Button btnLogOut;
    @FXML
    private TableView<Product> tblProduct;

    @FXML
    private TableColumn colName;

    @FXML
    public TableColumn colID;

    @FXML
    private TableColumn colPrice;

    @FXML
    private TableColumn colCategory;

    @FXML
    private Button btnAdd;

    @FXML
    private Button btnViewCart;

    private ProductService productService;

    private ObservableList<Product> productObservableList;

    private Customer customer;

    private OrderService orderService;

    private Product product;

    private Order order;

    private Stage viewCartStage;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        btnLogOut.setOnMouseEntered(event -> btnLogOut.setStyle("-fx-underline: true; -fx-background-color: #faf0e6;"));
        btnLogOut.setOnMouseExited(event -> btnLogOut.setStyle("-fx-underline: false; -fx-background-color: #faf0e6;"));

        productService = new ProductService();
        productObservableList = FXCollections.observableList(productService.getProducts());
        this.colName.setCellValueFactory(new PropertyValueFactory<>("title"));
        this.colID.setCellValueFactory(new PropertyValueFactory<>("id"));
        this.colPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        this.colCategory.setCellValueFactory(new PropertyValueFactory<>("category"));
        tblProduct.setItems(productObservableList);
        this.order = new Order();
        this.orderService = new OrderService();
    }


    @FXML
    private void logOut() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Thank you");
        alert.setHeaderText(null);
        alert.setContentText("See you soon!");
        alert.showAndWait();

        Stage stage = (Stage) this.btnLogOut.getScene().getWindow();
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

    @FXML
    public void select(javafx.scene.input.MouseEvent mouseEvent) {
        this.product = this.tblProduct.getSelectionModel().getSelectedItem();

    }


    @FXML
    private void addCart() {
        Alert alert;
        this.order = orderService.getCreateOrder(new Order(customer.getId()));
        if (product != null) {
            if (productService.isStock(this.product.getId())) {
                this.order = orderService.getCreateOrder(new Order(customer.getId()));
                orderService.addProduct(new OrderDetails(this.order.getId(), product.getId(), product.getPrice()));
                alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("ADDED");
                alert.setHeaderText(null);
                alert.setContentText("Added to cart");
                alert.showAndWait();
                if(viewCartStage!=null && viewCartStage.isShowing()){
                    viewCart();
                }
            }
        } else {
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERROR");
            alert.setHeaderText(null);
            alert.setContentText("You must select a product");
            alert.showAndWait();
        }
    }

    @FXML
    private void viewCart() {
        this.order = orderService.getCreateOrder(new Order(customer.getId()));
        if(viewCartStage!=null && viewCartStage.isShowing()){
            viewCartStage.close();
        }
        viewCartStage = new Stage();
        FXMLLoader loader = new FXMLLoader(MainApp.class.getResource("cart-view.fxml"));
        Parent root = null;
        try {
            root = loader.load();
            OrderController orderController = loader.getController();
            orderController.setOrder(this.order);
            Scene scene = new Scene(root, 894, 437.0);
            viewCartStage.setScene(scene);
            viewCartStage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


}
