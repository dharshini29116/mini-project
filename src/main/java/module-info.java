module com.expense {
    requires javafx.controls;
    requires javafx.fxml;
    
    opens com.expense to javafx.fxml;
    opens com.expense.controller to javafx.fxml;
    opens com.expense.model to javafx.base;
    
    exports com.expense;
    exports com.expense.controller;
    exports com.expense.model;
}


