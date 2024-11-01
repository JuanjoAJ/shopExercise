package shopexercise.shopexercise.utils;

import shopexercise.shopexercise.model.Customer;
import shopexercise.shopexercise.model.OrderDetailWithProduct;
import shopexercise.shopexercise.repositories.OrderRepository;

import javax.crypto.spec.SecretKeySpec;
import javax.mail.*;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Properties;

public class MailSender {

    private final ArrayList<OrderDetailWithProduct> orderDetails;
    private final Customer customer;

    public MailSender(ArrayList<OrderDetailWithProduct> orderDetails, Customer customer) {
        this.orderDetails = orderDetails;
        this.customer = customer;
    }

    public String textMail(){
        StringBuilder text= new StringBuilder();
        OrderRepository orderRepository=new OrderRepository();

        for (OrderDetailWithProduct item:orderDetails) {
            text.append(item).append("\n");
        }
        text.append(String.format("\nTOTAL PRICE: %.2f \n Thank you for choosing to shop with us.", orderRepository.getOrder(orderDetails.getFirst().getIDOrder()).getTotalPrice()));
        return text.toString();
    }

    public void sendMail() {
        SecretKeySpec keySpec=ConfigLoader.loadKey();

        Properties properties = ConfigLoader.loadConfig();
        properties.setProperty("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", properties.getProperty("HOST"));
        properties.put("mail.smtp.ssl.trust", properties.getProperty("HOST"));
        properties.setProperty("mail.smtp.port", properties.getProperty("PORT"));
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.ssl.protocols", "TLSv1.2");
        String user=ConfigLoader.decrypt(keySpec, properties.getProperty("ENCRYPTED_USER"));
        String pass=ConfigLoader.decrypt(keySpec, properties.getProperty("ENCRYPTED_PASSWORD"));
         Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(user, pass);
            }
        });
        try {
            MimeMessage msg = new MimeMessage(session);
            msg.setFrom(new InternetAddress(user));
            msg.setRecipient(Message.RecipientType.TO, new InternetAddress(customer.getEmail()));
            msg.setSubject(String.format("Your order %d", orderDetails.getFirst().getIDOrder()));
            msg.setSentDate(new Date());
            msg.setText(textMail());
            Transport.send(msg, user, pass);

        } catch (MessagingException e) {
            System.out.println("Send failed, exception: " + e);
        }

    }




}
