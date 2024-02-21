module org.example.mensajescliente {
    requires javafx.controls;
    requires javafx.fxml;


    opens org.example.mensajescliente to javafx.fxml;
    exports org.example.mensajescliente;
}