module org.example.mensajesclienteservidor {
    requires javafx.controls;
    requires javafx.fxml;


    opens org.example.mensajesServidor to javafx.fxml;
    exports org.example.mensajesServidor;
}