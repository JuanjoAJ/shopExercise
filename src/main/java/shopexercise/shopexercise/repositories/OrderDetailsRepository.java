package shopexercise.shopexercise.repositories;

import shopexercise.shopexercise.database.DBConnection;
import shopexercise.shopexercise.database.SchemeDB;
import shopexercise.shopexercise.model.OrderDetails;

import java.sql.*;
import java.util.ArrayList;

public class OrderDetailsRepository {
    Connection connection;

    public void createOrderDetails(OrderDetails orderDetails) {
        connection = DBConnection.getConnection();

        Statement statement = null;

        try {

            statement = connection.createStatement();
            String query = String.format("INSERT INTO %S(%s, %s, %s, %s) VALUES (?, ?, ?, ?);",
                    SchemeDB.TAB_ORDER_DETAILS,
                    SchemeDB.COLUMN_ID_ORDER, SchemeDB.COLUMN_ID_PRODUCT, SchemeDB.COLUMN_AMOUNT, SchemeDB.COLUMN_UNIT_PRICE);
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, orderDetails.getIdOrderFK());
            preparedStatement.setInt(2, orderDetails.getIdProductFK());
            preparedStatement.setInt(3, orderDetails.getAmount());
            preparedStatement.setDouble(4, orderDetails.getUnitPrice());
            preparedStatement.execute();
            preparedStatement.close();
        } catch (SQLException e) {
            System.out.println("Failed connection to the database");
        } finally {
            DBConnection.closeConnection();
            connection = null;
        }
    }

    public void deleteOrderDetails(int id) {
        connection = DBConnection.getConnection();
        Statement statement = null;

        try {
            statement = connection.createStatement();
            statement.execute(String.format("DELETE FROM %s WHERE %s=%s",
                    SchemeDB.TAB_ORDER_DETAILS,
                    SchemeDB.COLUMN_ID, id));
            statement.close();
        } catch (SQLException e) {
            System.out.println("Failed connection to the database");
        } finally {
            DBConnection.closeConnection();
            connection = null;
        }
    }

    public ArrayList<OrderDetails> getArrayOrderDetails(int idOrder) {
        connection = DBConnection.getConnection();
        Statement statement = null;
        ArrayList<OrderDetails> orderDetails = new ArrayList<>();
        try {
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(String.format("SELECT * FROM %s WHERE %s=%s",
                    SchemeDB.TAB_ORDER_DETAILS,
                    SchemeDB.COLUMN_ID_ORDER, idOrder));
            while (resultSet.next()) {
                OrderDetails orderDetail = new OrderDetails(
                        resultSet.getInt(SchemeDB.COLUMN_ID),
                        resultSet.getInt(SchemeDB.COLUMN_ID_ORDER),
                        resultSet.getInt(SchemeDB.COLUMN_ID_PRODUCT),
                        resultSet.getInt(SchemeDB.COLUMN_AMOUNT),
                        resultSet.getDouble(SchemeDB.COLUMN_UNIT_PRICE)
                );
                orderDetails.add(orderDetail);
            }
        } catch (SQLException e) {
            System.out.println("Failed connection to the database");
        } finally {
            DBConnection.closeConnection();
            connection = null;
        }

        return orderDetails;
    }

    public OrderDetails getOrderDetails(int idOrder, int idProduct) {
        OrderDetails orderDetails = null;
        connection = DBConnection.getConnection();
        Statement statement = null;
        try {
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(String.format("SELECT * FROM %s WHERE %s=%s AND %s=%s",
                    SchemeDB.TAB_ORDER_DETAILS,
                    SchemeDB.COLUMN_ID_ORDER, idOrder,
                    SchemeDB.COLUMN_ID_PRODUCT, idProduct));
            while (resultSet.next()) {
                orderDetails = new OrderDetails(
                        resultSet.getInt(SchemeDB.COLUMN_ID),
                        resultSet.getInt(SchemeDB.COLUMN_ID_ORDER),
                        resultSet.getInt(SchemeDB.COLUMN_ID_PRODUCT),
                        resultSet.getInt(SchemeDB.COLUMN_AMOUNT),
                        resultSet.getDouble(SchemeDB.COLUMN_UNIT_PRICE)
                );
            }
            statement.close();
        } catch (SQLException e) {
            System.out.println("Failed connection to the database");
        } finally {
            DBConnection.closeConnection();
            connection = null;
        }
        return orderDetails;

    }

    public void updateAmount(int id, int amount) {
        connection = DBConnection.getConnection();
        Statement statement = null;

        try {
            statement = connection.createStatement();
            statement.execute(String.format("UPDATE %s SET %s=%s WHERE %s=%s",
                    SchemeDB.TAB_ORDER_DETAILS,
                    SchemeDB.COLUMN_AMOUNT, amount,
                    SchemeDB.COLUMN_ID_PRODUCT, id));
            statement.close();
        } catch (SQLException e) {
            System.out.println("Failed connection to the database");
            System.out.println("Fallo amount +" + e.getMessage());
        } finally {
            DBConnection.closeConnection();
            connection = null;
        }
    }
}
