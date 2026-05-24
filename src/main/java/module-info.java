module br.maua {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires java.dotenv;
    requires java.desktop;
    requires java.logging;

    opens br.maua to javafx.fxml;
    opens br.maua.presentation to javafx.fxml;

    exports br.maua;
    exports br.maua.presentation;
}