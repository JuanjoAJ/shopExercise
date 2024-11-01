package shopexercise.shopexercise.repositories;

import shopexercise.shopexercise.database.DBConnection;
import shopexercise.shopexercise.database.SchemeDB;
import shopexercise.shopexercise.model.Order;

import java.sql.*;
import java.util.ArrayList;

public class OrderRepository {
    private Connection connection;

    public void createOrder(Order order) {
        connection = DBConnection.getConnection();
        Statement statement = null;
        try {

            statement = connection.createStatement();
            String query = String.format("INSERT INTO %s(%s, %s, %s) VALUES (?,?,?)",
                    SchemeDB.TAB_ORDER,
                    SchemeDB.COLUMN_DATE, SchemeDB.COLUMN_ID_CUSTOMER, SchemeDB.COLUMN_PRICE);
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, order.getDateOrder());
            preparedStatement.setInt(2, order.getIdCustomerFK());
            preparedStatement.setDouble(3, order.getTotalPrice());
            preparedStatement.execute();
            preparedStatement.close();
        } catch (SQLException e) {
            System.out.println("Failed connection to the database");
        } finally {
            DBConnection.closeConnection();
            connection = null;
        }
    }

    public void finishedOrder(int id) {
        connection = DBConnection.getConnection();
        Statement statement = null;

        try {
            statement = connection.createStatement();
            String query = String.format("UPDATE %s SET %s=%s WHERE %s=%s",
                    SchemeDB.TAB_ORDER,
                    SchemeDB.COLUMN_FINISHED, 1,
                    SchemeDB.COLUMN_ID, id);
            statement.execute(query);
            statement.close();
        } catch (SQLException e) {
            System.out.println("Failed connection to the database");
        } finally {
            DBConnection.closeConnection();
            connection = null;
        }
    }

    public void updatePrice(int id, double price) {
        connection = DBConnection.getConnection();
        Statement statement = null;

        try {
            statement = connection.createStatement();
            String query = String.format("UPDATE %s SET %s=%s WHERE %s=%s",
                    SchemeDB.TAB_ORDER,
                    SchemeDB.COLUMN_PRICE, price,
                    SchemeDB.COLUMN_ID, id);
            statement.execute(query);
            statement.close();
        } catch (SQLException e) {
            System.out.println("Failed connection to the database");
        } finally {
            DBConnection.closeConnection();
            connection = null;
        }
    }

    public Order getOrder(int id) {
        connection = DBConnection.getConnection();
        Statement statement = null;
        Order order = null;
        try {
            statement = connection.createStatement();
            String query = String.format("SELECT * FROM %s WHERE %s=%s",
                    SchemeDB.TAB_ORDER,
                    SchemeDB.COLUMN_ID, id);
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                order = new Order(
                        resultSet.getInt(SchemeDB.COLUMN_ID),
                        resultSet.getString(SchemeDB.COLUMN_DATE),
                        resultSet.getInt(SchemeDB.COLUMN_ID_CUSTOMER),
                        resultSet.getInt(SchemeDB.COLUMN_PRICE),
                        resultSet.getBoolean(SchemeDB.COLUMN_FINISHED));
            }
            statement.close();
        } catch (SQLException e) {
            System.out.println("Failed connection to the database");
        } finally {
            DBConnection.closeConnection();
            connection = null;
        }
        return order;
    }

    public ArrayList<Order> getOrdersUser(int id) {
        ArrayList<Order> orders = new ArrayList<>();
        connection = DBConnection.getConnection();
        Statement statement = null;

        try {
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(String.format("Select * FROM %s WHERE %s=%s",
                    SchemeDB.TAB_ORDER,
                    SchemeDB.COLUMN_ID_CUSTOMER, id));
            while (resultSet.next()) {
                Order order = new Order(
                        resultSet.getInt(SchemeDB.COLUMN_ID),
                        resultSet.getString(SchemeDB.COLUMN_DATE),
                        resultSet.getInt(SchemeDB.COLUMN_ID_CUSTOMER),
                        resultSet.getInt(SchemeDB.COLUMN_PRICE),
                        resultSet.getBoolean(SchemeDB.COLUMN_FINISHED));
                orders.add(order);
            }
            statement.close();
        } catch (SQLException e) {
            System.out.println("Failed connection to the database");
        } finally {
            DBConnection.closeConnection();
            connection = null;
        }
        return orders;

    }

    public ArrayList<Order> getOrders() {
        ArrayList<Order> orders = new ArrayList<>();
        connection = DBConnection.getConnection();
        Statement statement = null;

        try {
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(String.format("Select * FROM %s", SchemeDB.TAB_ORDER));
            while (resultSet.next()) {
                Order order = new Order(
                        resultSet.getInt(SchemeDB.COLUMN_ID),
                        resultSet.getString(SchemeDB.COLUMN_DATE),
                        resultSet.getInt(SchemeDB.COLUMN_ID_CUSTOMER),
                        resultSet.getInt(SchemeDB.COLUMN_PRICE),
                        resultSet.getBoolean(SchemeDB.COLUMN_FINISHED));
                orders.add(order);
            }
            statement.close();
        } catch (SQLException e) {
            System.out.println("Failed connection to the database");
        } finally {
            DBConnection.closeConnection();
            connection = null;
        }
        return orders;
    }

}



