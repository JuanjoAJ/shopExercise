package shopexercise.shopexercise.service;

import lombok.Getter;
import shopexercise.shopexercise.model.*;
import shopexercise.shopexercise.repositories.CustomerRepository;
import shopexercise.shopexercise.repositories.OrderDetailsRepository;
import shopexercise.shopexercise.repositories.OrderRepository;
import shopexercise.shopexercise.utils.MailSender;

import java.util.ArrayList;

@Getter
public class OrderService {
    private final ArrayList<Order> orders;
    private final ArrayList<OrderDetails> orderDetails;
    private final OrderRepository orderRepository;
    private final OrderDetailsRepository orderDetailsRepository;
    private final ProductService productService;


    public OrderService() {
        orders = new ArrayList<>();
        orderDetails = new ArrayList<>();
        orderRepository = new OrderRepository();
        orderDetailsRepository = new OrderDetailsRepository();
        productService = new ProductService();
    }


    public Order getCreateOrder(Order order) {
        ArrayList<Order> orders = orderRepository.getOrdersUser(order.getIdCustomerFK());
        if (orders.isEmpty() || orders.get(orders.size() - 1).isFinished()) {
            orderRepository.createOrder(order);
            return order;
        } else {
            return orderRepository.getOrder(orders.get(orders.size() - 1).getId());
        }
    }

    public void completeOrder(int idOrder) {
        orderRepository.finishedOrder(idOrder);
        ArrayList<OrderDetailWithProduct> orderDetailWithProducts=orderDetailWithProducts(idOrder);
        sendMail(orderDetailWithProducts);
    }

    private void sendMail(ArrayList<OrderDetailWithProduct> orderDetailWithProduct){
        CustomerRepository customerRepository=new CustomerRepository();
        Customer customer= customerRepository.getCustomer(orderRepository.getOrder(orderDetailWithProduct.getFirst().getIDOrder()).getIdCustomerFK());
        MailSender mailSender=new MailSender(orderDetailWithProduct, customer);
        mailSender.sendMail();
    }


    public void addProduct(OrderDetails orderDetails) {
        OrderDetails orderDetails1 = orderDetails;
        if ((orderDetails1 = orderDetailsRepository.getOrderDetails(orderDetails1.getIdOrderFK(), orderDetails1.getIdProductFK())) != null) {
            orderDetailsRepository.updateAmount(orderDetails1.getIdProductFK(),
                    (orderDetails1.getAmount() + 1));
        } else {
            orderDetailsRepository.createOrderDetails(orderDetails);
        }
        orderRepository.updatePrice(orderDetails.getIdOrderFK(),
                (getPriceOrderDetails(orderDetails) + orderRepository.getOrder(orderDetails.getIdOrderFK()).getTotalPrice()));
        productService.removeStock(orderDetails.getIdProductFK());
        this.orderDetails.add(orderDetails);
    }

    public void deleteProduct(int idOrder, int idProduct) {
        OrderDetails orderDetails = orderDetailsRepository.getOrderDetails(idOrder, idProduct);
        if (orderDetails.getAmount() <= 1) {
            orderDetailsRepository.deleteOrderDetails(orderDetails.getId());
        } else {
            orderDetailsRepository.updateAmount(orderDetails.getIdProductFK(),
                    (orderDetails.getAmount() - 1));
        }
        orderRepository.updatePrice(orderDetails.getIdOrderFK(),
                (orderRepository.getOrder(orderDetails.getIdOrderFK()).getTotalPrice() - orderDetails.getUnitPrice()));
        productService.addStock(idProduct);
    }

    public double getPriceOrderDetails(OrderDetails order) {
        return order.getAmount() * order.getUnitPrice();
    }

    public ArrayList<OrderDetailWithProduct> orderDetailWithProducts(int idOrder) {
        ArrayList<OrderDetailWithProduct> orderDetailWithProducts = new ArrayList<>();
        ArrayList<OrderDetails> orderDetails = orderDetailsRepository.getArrayOrderDetails(idOrder);
        for (OrderDetails item : orderDetails) {
            Product product = productService.getProductRepository().getProduct(item.getIdProductFK());
            OrderDetailWithProduct orderDetailWithProduct = new OrderDetailWithProduct(product, item);
            orderDetailWithProducts.add(orderDetailWithProduct);
        }
        return orderDetailWithProducts;
    }
}
