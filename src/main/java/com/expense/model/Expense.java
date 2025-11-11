package com.expense.model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Expense model class representing an expense entry
 */
public class Expense {
    private String id;
    private String description;
    private double amount;
    private String category;
    private LocalDate date;
    private String notes;
    
    private static int idCounter = 1;
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    
    public Expense(String description, double amount, String category, LocalDate date, String notes) {
        this.id = "EXP-" + String.format("%04d", idCounter++);
        this.description = description;
        this.amount = amount;
        this.category = category;
        this.date = date;
        this.notes = notes;
    }
    
    public Expense(String id, String description, double amount, String category, LocalDate date, String notes) {
        this.id = id;
        this.description = description;
        this.amount = amount;
        this.category = category;
        this.date = date;
        this.notes = notes;
    }
    
    // Getters and Setters
    public String getId() {
        return id;
    }
    
    public void setId(String id) {
        this.id = id;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public double getAmount() {
        return amount;
    }
    
    public void setAmount(double amount) {
        this.amount = amount;
    }
    
    public String getCategory() {
        return category;
    }
    
    public void setCategory(String category) {
        this.category = category;
    }
    
    public LocalDate getDate() {
        return date;
    }
    
    public void setDate(LocalDate date) {
        this.date = date;
    }
    
    public String getNotes() {
        return notes;
    }
    
    public void setNotes(String notes) {
        this.notes = notes;
    }
    
    public String getFormattedDate() {
        return date.format(DATE_FORMATTER);
    }
    
    @Override
    public String toString() {
        return String.format("%s - %s: $%.2f (%s)", 
            getFormattedDate(), description, amount, category);
    }
}


