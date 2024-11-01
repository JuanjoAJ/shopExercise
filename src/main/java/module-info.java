module shopexercise.shopexercise {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.kordamp.bootstrapfx.core;
    requires java.sql;
    requires mysql.connector.j;
    requires static lombok;
    requires jbcrypt;
    requires org.json;
    requires javax.mail.api;

    opens shopexercise.shopexercise to javafx.fxml;
    exports shopexercise.shopexercise;
    exports shopexercise.shopexercise.controller;
    opens shopexercise.shopexercise.controller to javafx.fxml;
    opens shopexercise.shopexercise.model to javafx.base;
}