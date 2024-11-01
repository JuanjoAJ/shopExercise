package shopexercise.shopexercise.repositories;

import shopexercise.shopexercise.database.DBConnection;
import shopexercise.shopexercise.database.SchemeDB;
import shopexercise.shopexercise.model.Customer;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class CustomerRepository implements SchemeDB {
    private Connection connection;

    public void registerCustomer(Customer customer, String hashPass) {
        connection = DBConnection.getConnection();
        Statement statement = null;

        try {
            statement = connection.createStatement();
            String query = String.format("INSERT INTO %s(%s, %s, %s, %s,%s,%s) VALUES('%s', '%s', '%s', '%s','%s','%s')",
                    SchemeDB.TAB_CUSTOMER,
                    SchemeDB.COLUMN_NAME, SchemeDB.COLUMN_SURNAME, SchemeDB.COLUMN_ADDRESS, SchemeDB.COLUMN_PASSWORD,
                    SchemeDB.COLUMN_EMAIL, SchemeDB.COLUMN_PHONE,
                    customer.getName(), customer.getSurname(), customer.getAddress(), hashPass, customer.getEmail(),
                    customer.getPhone());
            statement.execute(query);
            statement.close();
        } catch (SQLException e) {
            System.out.println("Failed connection to the database");
        } finally {
            connection = null;
            DBConnection.closeConnection();
        }
    }

    public ArrayList<Customer> getCustomers() {
        ArrayList<Customer> customersDB = new ArrayList<>();
        connection = DBConnection.getConnection();
        Statement statement = null;

        try {
            statement = connection.createStatement();
            ResultSet result = statement.executeQuery(String.format("Select * FROM %s",
                    SchemeDB.TAB_CUSTOMER));

            while (result.next()) {
                Customer customer = new Customer(
                        result.getInt(SchemeDB.COLUMN_ID),
                        result.getString(SchemeDB.COLUMN_NAME),
                        result.getString(SchemeDB.COLUMN_SURNAME),
                        result.getString(SchemeDB.COLUMN_ADDRESS),
                        result.getString(SchemeDB.COLUMN_PASSWORD),
                        result.getString(SchemeDB.COLUMN_EMAIL),
                        result.getString(SchemeDB.COLUMN_PHONE));
                customersDB.add(customer);
            }
            statement.close();
        } catch (SQLException e) {
            System.out.println("Failed connection to the database");
        } finally {
            DBConnection.closeConnection();
            connection = null;
        }
        return customersDB;
    }


    public Customer getCustomer(String email) {
        Customer customerDB = null;
        connection = DBConnection.getConnection();
        String queryCustomer = String.format("Select * FROM %s WHERE %s='%s'",
                SchemeDB.TAB_CUSTOMER,
                SchemeDB.COLUMN_EMAIL, email);
        try {
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery(queryCustomer);
            while (result.next()) {
                customerDB = new Customer(
                        result.getInt(SchemeDB.COLUMN_ID),
                        result.getString(SchemeDB.COLUMN_NAME),
                        result.getString(SchemeDB.COLUMN_SURNAME),
                        result.getString(SchemeDB.COLUMN_ADDRESS),
                        result.getString(SchemeDB.COLUMN_PASSWORD),
                        result.getString(SchemeDB.COLUMN_EMAIL),
                        result.getString(SchemeDB.COLUMN_PHONE)
                );
            }
            statement.close();
        } catch (SQLException e) {
            System.out.println("Failed connection to the database");
        } finally {
            DBConnection.closeConnection();
            connection = null;
        }
        return customerDB;
    }

    public Customer getCustomer(int id) {
        Customer customerDB = null;
        connection = DBConnection.getConnection();
        String queryCustomer = String.format("Select * FROM %s WHERE %s='%s'",
                SchemeDB.TAB_CUSTOMER,
                SchemeDB.COLUMN_ID, id);
        try {
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery(queryCustomer);
            while (result.next()) {
                customerDB = new Customer(
                        result.getInt(SchemeDB.COLUMN_ID),
                        result.getString(SchemeDB.COLUMN_NAME),
                        result.getString(SchemeDB.COLUMN_SURNAME),
                        result.getString(SchemeDB.COLUMN_ADDRESS),
                        result.getString(SchemeDB.COLUMN_PASSWORD),
                        result.getString(SchemeDB.COLUMN_EMAIL),
                        result.getString(SchemeDB.COLUMN_PHONE)
                );
            }
            statement.close();
        } catch (SQLException e) {
            System.out.println("Failed connection to the database");
        } finally {
            DBConnection.closeConnection();
            connection = null;
        }
        return customerDB;
    }

}
