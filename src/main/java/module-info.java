module com.example.conecta4dehacendado {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.conecta4dehacendado to javafx.fxml;
    exports com.example.conecta4dehacendado;
    exports com.example.conecta4dehacendado.controlador;
    opens com.example.conecta4dehacendado.controlador to javafx.fxml;
}