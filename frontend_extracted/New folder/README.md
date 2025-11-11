# Expense Categorization System

A modern, bright, and advanced Java GUI application for managing and categorizing expenses. Built with JavaFX, this application provides an intuitive interface for tracking your financial expenses with beautiful visuals and comprehensive features.

## Features

- ✨ **Modern UI Design**: Bright, colorful interface with gradient backgrounds and smooth animations
- 📊 **Expense Management**: Add, update, and delete expenses with ease
- 🏷️ **Category System**: Organize expenses into predefined categories
- 🔍 **Search & Filter**: Quickly find expenses by description or category
- 📈 **Statistics Dashboard**: View total expenses and category breakdown with percentages
- 💾 **Real-time Updates**: Statistics update automatically as you manage expenses
- 📅 **Date Picker**: Easy date selection for expense tracking
- 📝 **Notes Field**: Add additional notes to your expenses

## Prerequisites

- Java JDK 17 or higher
- Maven 3.6 or higher (for building)

## Installation

1. Clone or download this repository
2. Navigate to the project directory
3. Build the project using Maven:

```bash
mvn clean compile
```

## Running the Application

### Using Maven:

```bash
mvn javafx:run
```

### Using Java directly:

```bash
# Compile the project first
mvn clean compile

# Run the application
java --module-path /path/to/javafx/lib --add-modules javafx.controls,javafx.fxml -cp target/classes com.expense.ExpenseApp
```

### Creating Executable JAR:

```bash
mvn clean package
java -jar target/expense-categorization-1.0.0.jar
```

## Project Structure

```
expense-categorization/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/
│   │   │       └── expense/
│   │   │           ├── ExpenseApp.java          # Main application class
│   │   │           ├── controller/
│   │   │           │   └── ExpenseController.java  # Controller for UI logic
│   │   │           └── model/
│   │   │               └── Expense.java         # Expense model class
│   │   └── resources/
│   │       └── com/
│   │           └── expense/
│   │               ├── expense-view.fxml        # UI layout file
│   │               └── styles.css               # CSS styling
├── pom.xml                                       # Maven configuration
└── README.md                                     # This file
```

## Usage

1. **Add Expense**: Fill in the description, amount, category, and date. Click "Add Expense" to save.
2. **Edit Expense**: Click on an expense in the table to load it into the form. Make changes and click "Update Expense".
3. **Delete Expense**: Select an expense and click "Delete Expense" to remove it.
4. **Search**: Use the search field to filter expenses by description or category.
5. **Filter by Category**: Select a category from the dropdown to view only expenses in that category.
6. **View Statistics**: Check the statistics panel to see total expenses and category breakdown.

## Categories

The application includes the following predefined categories:
- Food & Dining
- Transportation
- Shopping
- Entertainment
- Bills & Utilities
- Healthcare
- Education
- Travel
- Personal Care
- Groceries
- Insurance
- Gifts & Donations
- Other

You can also add custom categories by typing in the category combo box.

## Technologies Used

- **JavaFX 17**: For the modern GUI
- **FXML**: For UI layout definition
- **CSS**: For styling and theming
- **Maven**: For project management and building
- **Java 17**: Programming language

## Screenshots

The application features:
- Gradient header with emoji icons
- Colorful form panels with smooth transitions
- Modern button styles with hover effects
- Beautiful table with alternating row colors
- Statistics panel with category breakdown
- Responsive layout that adapts to window size

## Future Enhancements

Potential features for future versions:
- Data persistence (save to database or file)
- Export functionality (CSV, PDF)
- Charts and graphs for visualization
- Budget tracking and alerts
- Multi-currency support
- Receipt image upload
- Monthly/yearly reports

## License

This project is open source and available for educational purposes.

## Contributing

Feel free to fork this project and submit pull requests for any improvements.

## Support

For issues or questions, please open an issue in the repository.

---

**Note**: Make sure you have JavaFX installed and configured properly. If you're using Java 11+, you may need to download JavaFX separately and include it in your module path.


