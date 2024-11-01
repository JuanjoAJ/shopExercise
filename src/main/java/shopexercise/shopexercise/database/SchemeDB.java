package shopexercise.shopexercise.database;

public interface SchemeDB {
    String DB_NAME = "shopexercise";
    String TAB_CUSTOMER = "customer";
    String TAB_ORDER = "orders";
    String TAB_PRODUCTS = "products";
    String TAB_ORDER_DETAILS = "orders_details";


    String COLUMN_ID = "id";
    String COLUMN_NAME = "name";
    String COLUMN_SURNAME = "surname";
    String COLUMN_ADDRESS = "address";
    String COLUMN_PASSWORD = "password";
    String COLUMN_EMAIL = "email";
    String COLUMN_PHONE = "phone";
    String COLUMN_DATE = "date_order";
    String COLUMN_TITLE = "title";
    String COLUMN_DESCRIPTION = "description";
    String COLUMN_CATEGORY = "category";
    String COLUMN_PRICE = "price";
    String COLUMN_STOCK = "stock";
    String COLUMN_ID_CUSTOMER = "id_customer_FK";
    String COLUMN_FINISHED = "finished";
    String COLUMN_ID_PRODUCT = "id_product_FK";
    String COLUMN_ID_ORDER = "id_order_FK";
    String COLUMN_AMOUNT = "amount";
    String COLUMN_UNIT_PRICE = "unit_price";


}
