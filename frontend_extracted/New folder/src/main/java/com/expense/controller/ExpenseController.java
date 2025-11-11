package com.expense.controller;

import com.expense.model.Expense;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.util.StringConverter;
import javafx.util.converter.DoubleStringConverter;

import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Controller for the Expense Categorization System
 */
public class ExpenseController implements Initializable {
    
    @FXML
    private TextField descriptionField;
    
    @FXML
    private TextField amountField;
    
    @FXML
    private ComboBox<String> categoryComboBox;
    
    @FXML
    private DatePicker datePicker;
    
    @FXML
    private TextArea notesArea;
    
    @FXML
    private Button addButton;
    
    @FXML
    private Button updateButton;
    
    @FXML
    private Button deleteButton;
    
    @FXML
    private Button clearButton;
    
    @FXML
    private TableView<Expense> expenseTable;
    
    @FXML
    private TableColumn<Expense, String> idColumn;
    
    @FXML
    private TableColumn<Expense, String> descriptionColumn;
    
    @FXML
    private TableColumn<Expense, Double> amountColumn;
    
    @FXML
    private TableColumn<Expense, String> categoryColumn;
    
    @FXML
    private TableColumn<Expense, String> dateColumn;
    
    @FXML
    private TextField searchField;
    
    @FXML
    private ComboBox<String> filterCategoryComboBox;
    
    @FXML
    private VBox statisticsBox;
    
    @FXML
    private Text totalExpensesText;
    
    @FXML
    private Text categoryBreakdownText;
    
    private ObservableList<Expense> expenseList;
    private FilteredList<Expense> filteredExpenseList;
    private Expense selectedExpense;
    
    // Predefined categories
    private final List<String> categories = Arrays.asList(
        "Food & Dining",
        "Transportation",
        "Shopping",
        "Entertainment",
        "Bills & Utilities",
        "Healthcare",
        "Education",
        "Travel",
        "Personal Care",
        "Groceries",
        "Insurance",
        "Gifts & Donations",
        "Other"
    );
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Initialize expense list
        expenseList = FXCollections.observableArrayList();
        filteredExpenseList = new FilteredList<>(expenseList, p -> true);
        
        // Setup categories
        categoryComboBox.setItems(FXCollections.observableArrayList(categories));
        categoryComboBox.setEditable(true);
        
        filterCategoryComboBox.setItems(FXCollections.observableArrayList("All Categories"));
        filterCategoryComboBox.getItems().addAll(categories);
        filterCategoryComboBox.setValue("All Categories");
        
        // Setup date picker
        datePicker.setValue(LocalDate.now());
        datePicker.setEditable(false);
        
        // Setup table columns
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        amountColumn.setCellValueFactory(new PropertyValueFactory<>("amount"));
        categoryColumn.setCellValueFactory(new PropertyValueFactory<>("category"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("formattedDate"));
        
        // Format amount column
        amountColumn.setCellFactory(column -> new TableCell<Expense, Double>() {
            @Override
            protected void updateItem(Double amount, boolean empty) {
                super.updateItem(amount, empty);
                if (empty || amount == null) {
                    setText(null);
                } else {
                    setText(String.format("$%.2f", amount));
                }
            }
        });
        
        // Setup table with filtered list
        SortedList<Expense> sortedList = new SortedList<>(filteredExpenseList);
        sortedList.comparatorProperty().bind(expenseTable.comparatorProperty());
        expenseTable.setItems(sortedList);
        
        // Setup table selection
        expenseTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                selectedExpense = newSelection;
                loadExpenseToForm(newSelection);
                updateButton.setDisable(false);
                deleteButton.setDisable(false);
            } else {
                selectedExpense = null;
                updateButton.setDisable(true);
                deleteButton.setDisable(true);
            }
        });
        
        // Setup search functionality
        searchField.textProperty().addListener((observable, oldValue, newValue) -> {
            filterExpenses();
        });
        
        // Setup category filter
        filterCategoryComboBox.valueProperty().addListener((observable, oldValue, newValue) -> {
            filterExpenses();
        });
        
        // Setup buttons
        updateButton.setDisable(true);
        deleteButton.setDisable(true);
        
        // Add sample data
        addSampleData();
        
        // Update statistics
        updateStatistics();
    }
    
    @FXML
    private void handleAddExpense() {
        if (validateInput()) {
            Expense expense = new Expense(
                descriptionField.getText().trim(),
                Double.parseDouble(amountField.getText().trim()),
                categoryComboBox.getValue(),
                datePicker.getValue(),
                notesArea.getText().trim()
            );
            
            expenseList.add(expense);
            clearForm();
            updateStatistics();
            showAlert("Success", "Expense added successfully!", Alert.AlertType.INFORMATION);
        }
    }
    
    @FXML
    private void handleUpdateExpense() {
        if (selectedExpense != null && validateInput()) {
            selectedExpense.setDescription(descriptionField.getText().trim());
            selectedExpense.setAmount(Double.parseDouble(amountField.getText().trim()));
            selectedExpense.setCategory(categoryComboBox.getValue());
            selectedExpense.setDate(datePicker.getValue());
            selectedExpense.setNotes(notesArea.getText().trim());
            
            expenseTable.refresh();
            clearForm();
            updateStatistics();
            showAlert("Success", "Expense updated successfully!", Alert.AlertType.INFORMATION);
        }
    }
    
    @FXML
    private void handleDeleteExpense() {
        if (selectedExpense != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirm Delete");
            alert.setHeaderText("Delete Expense");
            alert.setContentText("Are you sure you want to delete this expense?");
            
            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                expenseList.remove(selectedExpense);
                clearForm();
                updateStatistics();
                showAlert("Success", "Expense deleted successfully!", Alert.AlertType.INFORMATION);
            }
        }
    }
    
    @FXML
    private void handleClearForm() {
        clearForm();
        expenseTable.getSelectionModel().clearSelection();
    }
    
    private void loadExpenseToForm(Expense expense) {
        descriptionField.setText(expense.getDescription());
        amountField.setText(String.format("%.2f", expense.getAmount()));
        categoryComboBox.setValue(expense.getCategory());
        datePicker.setValue(expense.getDate());
        notesArea.setText(expense.getNotes());
    }
    
    private void clearForm() {
        descriptionField.clear();
        amountField.clear();
        categoryComboBox.setValue(null);
        datePicker.setValue(LocalDate.now());
        notesArea.clear();
        selectedExpense = null;
        updateButton.setDisable(true);
        deleteButton.setDisable(true);
    }
    
    private boolean validateInput() {
        if (descriptionField.getText().trim().isEmpty()) {
            showAlert("Validation Error", "Please enter a description.", Alert.AlertType.ERROR);
            return false;
        }
        
        try {
            double amount = Double.parseDouble(amountField.getText().trim());
            if (amount <= 0) {
                showAlert("Validation Error", "Amount must be greater than 0.", Alert.AlertType.ERROR);
                return false;
            }
        } catch (NumberFormatException e) {
            showAlert("Validation Error", "Please enter a valid amount.", Alert.AlertType.ERROR);
            return false;
        }
        
        if (categoryComboBox.getValue() == null || categoryComboBox.getValue().trim().isEmpty()) {
            showAlert("Validation Error", "Please select a category.", Alert.AlertType.ERROR);
            return false;
        }
        
        if (datePicker.getValue() == null) {
            showAlert("Validation Error", "Please select a date.", Alert.AlertType.ERROR);
            return false;
        }
        
        return true;
    }
    
    private void filterExpenses() {
        filteredExpenseList.setPredicate(expense -> {
            String searchText = searchField.getText().toLowerCase();
            String filterCategory = filterCategoryComboBox.getValue();
            
            boolean matchesSearch = searchText.isEmpty() ||
                expense.getDescription().toLowerCase().contains(searchText) ||
                expense.getCategory().toLowerCase().contains(searchText) ||
                expense.getId().toLowerCase().contains(searchText);
            
            boolean matchesCategory = filterCategory == null ||
                filterCategory.equals("All Categories") ||
                expense.getCategory().equals(filterCategory);
            
            return matchesSearch && matchesCategory;
        });
        
        updateStatistics();
    }
    
    private void updateStatistics() {
        double total = filteredExpenseList.stream()
            .mapToDouble(Expense::getAmount)
            .sum();
        
        totalExpensesText.setText(String.format("Total Expenses: $%.2f", total));
        
        // Category breakdown
        Map<String, Double> categoryTotals = filteredExpenseList.stream()
            .collect(Collectors.groupingBy(
                Expense::getCategory,
                Collectors.summingDouble(Expense::getAmount)
            ));
        
        StringBuilder breakdown = new StringBuilder("Category Breakdown:\n");
        categoryTotals.entrySet().stream()
            .sorted(Map.Entry.<String, Double>comparingByValue().reversed())
            .forEach(entry -> {
                double percentage = (entry.getValue() / total) * 100;
                breakdown.append(String.format("  • %s: $%.2f (%.1f%%)\n",
                    entry.getKey(), entry.getValue(), percentage));
            });
        
        categoryBreakdownText.setText(breakdown.toString());
    }
    
    private void addSampleData() {
        LocalDate today = LocalDate.now();
        expenseList.add(new Expense("Lunch at Restaurant", 45.50, "Food & Dining", today, "Business lunch"));
        expenseList.add(new Expense("Uber Ride", 25.00, "Transportation", today, "Airport trip"));
        expenseList.add(new Expense("Grocery Shopping", 120.75, "Groceries", today.minusDays(1), "Weekly groceries"));
        expenseList.add(new Expense("Netflix Subscription", 15.99, "Entertainment", today.minusDays(2), "Monthly subscription"));
        expenseList.add(new Expense("Electric Bill", 85.00, "Bills & Utilities", today.minusDays(3), "November bill"));
    }
    
    private void showAlert(String title, String message, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}


