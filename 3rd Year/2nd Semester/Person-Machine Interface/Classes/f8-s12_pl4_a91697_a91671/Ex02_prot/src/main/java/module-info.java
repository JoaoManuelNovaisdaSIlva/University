module com.example.demoteorica {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.demoteorica to javafx.fxml;
    exports com.example.demoteorica;
    exports com.example.demoteorica.blogic;
    opens com.example.demoteorica.blogic to javafx.fxml;
    exports com.example.demoteorica.ui;
    opens com.example.demoteorica.ui to javafx.fxml;
}