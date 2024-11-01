package shopexercise.shopexercise.repositories;

import shopexercise.shopexercise.database.DBConnection;
import shopexercise.shopexercise.database.SchemeDB;
import shopexercise.shopexercise.model.Product;

import java.sql.*;
import java.util.ArrayList;

public class ProductRepository implements SchemeDB {

    private Connection connection;

    public void saveProduct(Product product) {
        connection = DBConnection.getConnection();
        PreparedStatement preparedStatement = null;

        try {
            String query = String.format("INSERT INTO %s(%s,%s,%s,%s,%s,%s ) VALUES (?,?,?,?,?,?)",
                    SchemeDB.TAB_PRODUCTS,
                    SchemeDB.COLUMN_ID, SchemeDB.COLUMN_TITLE, SchemeDB.COLUMN_DESCRIPTION,
                    SchemeDB.COLUMN_CATEGORY, SchemeDB.COLUMN_PRICE, SchemeDB.COLUMN_STOCK);
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, product.getId());
            preparedStatement.setString(2, product.getTitle());
            preparedStatement.setString(3, product.getDescription());
            preparedStatement.setString(4, product.getCategory());
            preparedStatement.setDouble(5, product.getPrice());
            preparedStatement.setInt(6, product.getStock());
            preparedStatement.execute();
            preparedStatement.close();
        } catch (SQLException e) {
            System.out.println("Failed connection to the database");
        } finally {
            DBConnection.closeConnection();
            connection = null;
        }
    }

    public Product getProduct(int id) {
        Product product = null;
        connection = DBConnection.getConnection();
        Statement statement = null;
        String query = String.format("Select * FROM %s WHERE %s=%s",
                SchemeDB.TAB_PRODUCTS,
                SchemeDB.COLUMN_ID,
                id);
        try {
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                product = new Product(
                        resultSet.getInt(SchemeDB.COLUMN_ID),
                        resultSet.getString(SchemeDB.COLUMN_TITLE),
                        resultSet.getString(SchemeDB.COLUMN_DESCRIPTION),
                        resultSet.getString(SchemeDB.COLUMN_CATEGORY),
                        resultSet.getDouble(SchemeDB.COLUMN_PRICE),
                        resultSet.getInt(SchemeDB.COLUMN_STOCK)
                );
            }
        } catch (SQLException e) {
            System.out.println("Failed connection to the database");
        } finally {
            DBConnection.closeConnection();
            connection = null;
        }
        return product;
    }

    public void updateStock(int id, int stock) {
        connection = DBConnection.getConnection();
        String query = String.format("UPDATE %s SET %s=? WHERE %s=?",
                SchemeDB.TAB_PRODUCTS,
                SchemeDB.COLUMN_STOCK,
                SchemeDB.COLUMN_ID
        );
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(2, id);
            preparedStatement.setInt(1, stock);
            preparedStatement.execute();
            preparedStatement.close();
        } catch (SQLException e) {
            System.out.println("Failed connection to the database");
        } finally {
            DBConnection.closeConnection();
            connection = null;

        }
    }

    public ArrayList<Product> getProducts() {
        ArrayList<Product> products = new ArrayList<Product>();
        connection = DBConnection.getConnection();
        Statement statement = null;
        String query = String.format("Select * FROM %s", SchemeDB.TAB_PRODUCTS);

        try {
            statement = connection.createStatement();
            ResultSet result = statement.executeQuery(query);
            while (result.next()) {
                Product product = new Product(
                        result.getInt(SchemeDB.COLUMN_ID),
                        result.getString(SchemeDB.COLUMN_TITLE),
                        result.getString(SchemeDB.COLUMN_DESCRIPTION),
                        result.getString(SchemeDB.COLUMN_CATEGORY),
                        result.getDouble(SchemeDB.COLUMN_PRICE),
                        result.getInt(SchemeDB.COLUMN_STOCK)
                );
                products.add(product);
            }
            statement.close();
        } catch (SQLException e) {
            System.out.println("Failed connection to the database");
        } finally {
            DBConnection.closeConnection();
            connection = null;
        }

        return products;
    }


}
