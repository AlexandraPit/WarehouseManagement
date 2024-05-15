module org.example.pt2024_30423_pitaru_alexandra_assignment_3 {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.logging;
    requires java.sql;
    requires java.desktop;
    requires org.apache.pdfbox;
    requires itextpdf;


    opens org.example.model to javafx.base;


    opens org.example to javafx.fxml;
    opens org.example.presentation to javafx.fxml;
    exports org.example;
    exports org.example.presentation;

}