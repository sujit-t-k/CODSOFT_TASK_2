module codsoft.sujit.studentgradecalculator {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;
    requires java.desktop;

    opens codsoft.sujit.studentgradecalculator to javafx.fxml;
    exports codsoft.sujit.studentgradecalculator;
}