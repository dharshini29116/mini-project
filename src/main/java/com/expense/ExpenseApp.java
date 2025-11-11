package com.expense;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.util.Objects;

/**
 * Main Application class for Expense Categorization System
 */
public class ExpenseApp extends Application {
    
    @Override
    public void start(Stage primaryStage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(ExpenseApp.class.getResource("/com/expense/expense-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1200, 750);
        
        // Apply CSS styling
        String css = Objects.requireNonNull(getClass().getResource("/com/expense/styles.css")).toExternalForm();
        scene.getStylesheets().add(css);
        
        primaryStage.setTitle("Expense Categorization System");
        primaryStage.setScene(scene);
        primaryStage.setMinWidth(1000);
        primaryStage.setMinHeight(700);
        primaryStage.show();
        
        // Center the window
        primaryStage.centerOnScreen();
    }
    
    public static void main(String[] args) {
        launch(args);
    }
}


