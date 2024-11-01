package shopexercise.shopexercise.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import lombok.Getter;
import lombok.Setter;
import shopexercise.shopexercise.model.Customer;
import shopexercise.shopexercise.model.Order;
import shopexercise.shopexercise.model.OrderDetailWithProduct;
import shopexercise.shopexercise.model.OrderDetails;
import shopexercise.shopexercise.repositories.CustomerRepository;
import shopexercise.shopexercise.repositories.OrderRepository;
import shopexercise.shopexercise.service.OrderService;
import shopexercise.shopexercise.utils.MailSender;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

@Setter
@Getter
public class OrderController implements Initializable {
    @FXML
    private TableView<OrderDetailWithProduct> tblProduct;

    @FXML
    private TableColumn<OrderDetailWithProduct, String> colTitle;

    @FXML
    private TableColumn<OrderDetailWithProduct, String> colCategory;

    @FXML
    private TableColumn<OrderDetailWithProduct, Double> colPrice;

    @FXML
    private TableColumn<OrderDetailWithProduct, Integer> colAmount;

    @FXML
    private ObservableList<OrderDetailWithProduct> orderServiceObservableList;


    @FXML
    private Label totalPrice;

    @FXML
    private Button btnBackShop;

    private Order order;
    private OrderDetails orderDetails;
    private OrderDetailWithProduct orderDetailWithProduct;
    private OrderService orderService;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        orderService = new OrderService();
        colTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
        colCategory.setCellValueFactory(new PropertyValueFactory<>("category"));
        colPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        colAmount.setCellValueFactory(new PropertyValueFactory<>("amount"));

    }

    private void setupTable() {
        if (this.order != null) {
            ArrayList<OrderDetailWithProduct> orderDetailWithProduct = orderService.orderDetailWithProducts(order.getId());
            orderServiceObservableList = FXCollections.observableList(orderDetailWithProduct);
            tblProduct.setItems(orderServiceObservableList);
            totalPrice.setText(order.getTotalPrice() + "");
        }
    }

    public void setOrder(Order order) {
        this.order = order;
        setupTable();
    }

    @FXML
    private void deleteItem(ActionEvent event) {
        OrderDetailWithProduct orderDetailWithProduct = tblProduct.getSelectionModel().getSelectedItem();
        Alert alert;
        if (orderDetailWithProduct == null) {
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERROR");
            alert.setHeaderText(null);
            alert.setContentText("You must select a product");
            alert.showAndWait();
        } else {
            if (orderDetailWithProduct.getAmount() == 1) {
                this.orderServiceObservableList.remove(orderDetailWithProduct);
            }
            orderService.deleteProduct(this.order.getId(), orderDetailWithProduct.getProduct().getId());
            this.order = orderService.getCreateOrder(order);
            totalPrice.setText(order.getTotalPrice() + "");
            setupTable();

        }
    }

    @FXML
    private void completeOrder(ActionEvent event) {

        orderService.completeOrder(this.order.getId());

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Order complete");
        alert.setHeaderText(null);
        alert.setContentText(String.format("Your order %d has been completed correctly", order.getId()));
        alert.showAndWait();

        backShop(event);
    }

    @FXML
    private void backShop(ActionEvent event) {
        Stage stage = (Stage) this.btnBackShop.getScene().getWindow();
        stage.close();
    }


    public void select(MouseEvent mouseEvent) {
        orderDetailWithProduct = tblProduct.getSelectionModel().getSelectedItem();
    }
}
