module com.blackjackgame {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.blackjackgame to javafx.fxml;
    exports com.blackjackgame;
}