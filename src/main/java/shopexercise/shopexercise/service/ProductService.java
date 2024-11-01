package shopexercise.shopexercise.service;

import javafx.scene.control.Alert;
import lombok.Getter;
import org.json.JSONArray;
import org.json.JSONObject;
import shopexercise.shopexercise.model.Product;
import shopexercise.shopexercise.repositories.ProductRepository;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

@Getter
public class ProductService {
    private final ArrayList<Product> products;
    private final ProductRepository productRepository;


    public ProductService() {
        productRepository = new ProductRepository();
        saveProductsJSON();
        products = productRepository.getProducts();

    }

    private void saveProductsJSON() {
        BufferedReader br = null;

        try {
            URL url = new URL("https://dummyjson.com/products");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String read = br.readLine();
            JSONObject answer = new JSONObject(read);
            JSONArray productsJSON = answer.getJSONArray("products");
            for (int i = 0; i < productsJSON.length(); i++) {

                JSONObject productJSON = productsJSON.getJSONObject(i);
                int id = productJSON.getInt("id");
                String title = productJSON.getString("title");
                String description = productJSON.getString("description");
                String category = productJSON.getString("category");
                double price = productJSON.getDouble("price");
                int stock = productJSON.getInt("stock");
                Product product = new Product(id, title, description, category, price, stock);
                if (productRepository.getProduct(id) == null) {
                    productRepository.saveProduct(product);
                }
            }
        } catch (MalformedURLException e) {
            System.out.println("Error en la codificación del URL");
        } catch (IOException e) {
            System.out.println("Error de internet");
        }
    }

    public void addStock(int id) {
        productRepository.updateStock(id, productRepository.getProduct(id).getStock() + 1);
    }

    public void removeStock(int id) {
        if (isStock(id)) productRepository.updateStock(id, productRepository.getProduct(id).getStock() - 1);
    }

    public boolean isStock(int id) {
        if (productRepository.getProduct(id).getStock() < 1) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Out of Stock");
            alert.showAndWait();
            return false;
        }
        return true;
    }


}
