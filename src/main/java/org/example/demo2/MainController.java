package org.example.demo2;

import javafx.beans.binding.DoubleBinding;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import org.example.demo2.DB.DBUtil;
import org.example.demo2.DB.Employee;
import org.example.demo2.DB.EmployeeDAO;
import org.example.demo2.auth.AuthController;
import org.example.demo2.auth.Autorization;
import org.example.demo2.helper.MainHelper;
import javafx.beans.binding.Bindings;

import java.lang.ref.WeakReference;
import java.sql.*;
import java.util.*;


public class MainController {
    @FXML
    private TextArea TextInfo;




    @FXML
    private DatePicker DateSearch;

    @FXML
    private TextField PhoneSearch;

    @FXML
    private TitledPane SearchPane;

    @FXML
    private TextField StatusSearch;
    @FXML
    private TextField nameSearch;

    @FXML
    private TextField PostSearch;
    @FXML
    private ComboBox<String> comboBox;
   
    @FXML
    private DatePicker dateChooser;
    @FXML
    private DatePicker TimeSearch;

    @FXML
    private MenuItem deleteButton;

    @FXML
    private TableColumn<?, ?> nameColumn;

    @FXML
    private TextField nameField;

    @FXML
    private TableColumn<?, ?> phoneColumn;

    @FXML
    private TextField phoneField;

    @FXML
    private TableColumn<?, ?> postColumn;

    @FXML
    private TextField postField;

    @FXML
    private Button saveButton;

    @FXML
    private MenuItem searchButton;

    @FXML
    private SplitMenuButton splitMenuButton;

    @FXML
    private TableColumn<?, ?> statusColumn;

    @FXML
    private TableView<Employee> tableView;
@FXML
    private AnchorPane scene;
    @FXML
    private MenuItem test;

    @FXML
    private TableColumn<?, ?> timeColumn;

    @FXML
    private Button updateButton;

    private static Connection connection;

    private ObservableList<Employee> employees = FXCollections.observableArrayList();

    private EmployeeDAO employeeDAO;

    private ObservableList<Employee> filteredList = FXCollections.observableArrayList();



    //Connect DB
    private String nameSearchText = "";
    private String phoneSearchText = "";
    private String statusSearchText = "";
    private String timeSearchText = "";
    private String postSearchText = "";
    private WeakReference<Stage> mainStageRef;

    @FXML
    private void initialize() {
      
        scene.setOnKeyReleased(new EventHandler<KeyEvent>() {
            public void handle(KeyEvent event) {
                if (event.getCode() == KeyCode.F1) {
                    if (mainStageRef == null || mainStageRef.get() == null ||!mainStageRef.get().isShowing()) {
                        MainHelper mainHelper = new MainHelper();
                        Stage mainStage = new Stage();
                        mainHelper.start(mainStage);
                        mainStageRef = new WeakReference<>(mainStage);
                    } else {
                        mainStageRef.get().close();
                        mainStageRef = null;
                    }
                }
            }
        });

        // Load the JDBC driver
        try {
            Class.forName("com.mysql.cj.jdbc.Driver"); // Replace with your database driver
        } catch (ClassNotFoundException e) {
            System.out.println("Error loading JDBC driver: " + e.getMessage());
            return;
        }
BDName bdName = new BDName();
        // Establish a connection to the database
        try {
            connection = DriverManager.getConnection(
                    "jdbc:mysql://109.71.243.44:3306/Jobs?serverTimezone=UTC", // Replace with your database URL
                    bdName.getName(), // Replace with your database username
                    bdName.getPassword() // Replace with your database password
            );
            System.out.println("Successfully connected to database");
            TextInfo.appendText("Успешное подключение к базе данных!\n");
        } catch (SQLException e) {
            System.out.println("Error connecting to database: " + e.getMessage());
            return;
        }

//Connect DB

//Edit
        ContextMenu contextMenu = new ContextMenu();

        MenuItem editItem = new MenuItem("Редактировать");
        MenuItem removeItem = new MenuItem("Удалить");
        removeItem.setOnAction(actionEvent -> {
            Employee row = (Employee) tableView.getSelectionModel().getSelectedItem();
            if (row!= null) {
                // Confirm deletion
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Удаление данных");
                alert.setContentText("Вы уверены, что хотите удалить данные?");
                Optional<ButtonType> result = alert.showAndWait();
                if (result.isPresent() && result.get() == ButtonType.OK) {
                    // Delete the employee from the database
                    String deleteQuery = "DELETE FROM User WHERE Name =? ";
                    PreparedStatement pstmt = null;
                    try {
                        pstmt = connection.prepareStatement(deleteQuery);
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                    try {
                        pstmt.setString(1, row.getName());
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                    try {
                        pstmt.executeUpdate();
                        TextInfo.appendText("Данные успешно удалены!\n");
                    } catch (SQLException e) {

                        TextInfo.appendText("Ошибка удаления данных: " + e.getMessage()+"\n");
                    }

                    // Remove the employee from the table view
                    tableView.getItems().remove(row);
                    tableView.refresh();
                }
            }
        });
        editItem.setOnAction(event -> {

            Employee row = (Employee) tableView.getSelectionModel().getSelectedItem();
            if (row!= null) {
                // Create a dialog to edit the employee data
                Dialog<Employee> editDialog = new Dialog<>();
                editDialog.setTitle("Изменение данных");


                // Create fields to edit the data
                TextField nameField = new TextField(row.getName());
                TextField phoneNumberField = new TextField(row.getPhone());
                ComboBox<String> statusComboBox = new ComboBox<>();
                statusComboBox.getItems().addAll("Работает", "Отдыхает","В отпуске");
                statusComboBox.setValue(row.getStatus());
                DatePicker timeDatePicker = new DatePicker();
                TextField postField = new TextField(row.getPost());

                // Create a grid pane to layout the fields
                GridPane gridPane = new GridPane();
                gridPane.add(new Label("Имя:"), 0, 0);
                gridPane.add(nameField, 1, 0);
                gridPane.add(new Label("Номер телефона:"), 0, 1);
                gridPane.add(phoneNumberField, 1, 1);
                gridPane.add(new Label("Статус:"), 0, 2);
                gridPane.add(statusComboBox, 1, 2);
                gridPane.add(new Label("До:"), 0, 3);
                gridPane.add(timeDatePicker, 1, 3);
                gridPane.add(new Label("Должность:"), 0, 4);
                gridPane.add(postField, 1, 4);

                // Set the dialog content
                editDialog.getDialogPane().setContent(gridPane);

                // Add buttons to the dialog
                ButtonType saveButtonType = new ButtonType("Сохранить", ButtonBar.ButtonData.OK_DONE);
                ButtonType cancelButtonType = new ButtonType("Закрыть", ButtonBar.ButtonData.CANCEL_CLOSE);
                editDialog.getDialogPane().getButtonTypes().addAll(saveButtonType, cancelButtonType);

                // Show the dialog and wait for the user to respond
                Optional<Employee> result = editDialog.showAndWait();

                // If the user clicked "Save", update the employee data
                if (result.isPresent()) {
                    row.setName(nameField.getText());
                    row.setPhone(phoneNumberField.getText());
                    row.setStatus(statusComboBox.getValue());
                    row.setTime(String.valueOf(timeDatePicker.getValue()));
                    row.setPost(postField.getText());

                    // Update the database
                    String updateQuery = "UPDATE User SET PhoneNumber =?, Status =?, Time =?, Post =? WHERE Name =? ";
                    PreparedStatement pstmt = null;
                    try {
                        pstmt = connection.prepareStatement(updateQuery);
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                    //...
                    try {
                        pstmt.setString(5, row.getName());
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }

                    try {
                        pstmt.setString(1,row.getPhone());
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }

                    try {
                        pstmt.setString(2,row.getStatus());
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }

                    try {
                        pstmt.setString(3,row.getTime());
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }

                    try {
                        pstmt.setString(4,row.getPost());
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                    try {
                        pstmt.executeUpdate();
                        TextInfo.appendText("Данные успешно обновлены!\n");

                    } catch (SQLException e) {
                        System.out.println("Ошибка обновления данных: " + e.getMessage());
                        TextInfo.appendText("Ошибка обновления данных: " + e.getMessage() + "\n");
                    }


                    // Refresh the table view
                    tableView.refresh();
                }
            }
        });
        contextMenu.getItems().add(editItem);
        contextMenu.getItems().add(removeItem);
        tableView.setContextMenu(contextMenu);

        tableView.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            if (event.getButton() == MouseButton.SECONDARY) {
                contextMenu.show(tableView, event.getScreenX(), event.getScreenY());
            }
        });
//Edit






        connection = DBUtil.getConnection();


        employeeDAO = new EmployeeDAO(connection);


        employees = employeeDAO.getEmployees();


        // Загружаем данные из базы данных в таблицу
        tableView.setItems(employeeDAO.getEmployees());

        // Устанавливаем cell value factories для столбцов таблицы
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));
        timeColumn.setCellValueFactory(new PropertyValueFactory<>("time"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        phoneColumn.setCellValueFactory(new PropertyValueFactory<>("phone"));
        postColumn.setCellValueFactory(new PropertyValueFactory<>("post"));

        // Добавляем слушатель для обновления таблицы при изменении данных в базе данных
        tableView.getItems().addListener(new ListChangeListener<Employee>() {
            @Override
            public void onChanged(Change<? extends Employee> change) {
                // Обновляем таблицу при изменении данных в базе данных
                tableView.refresh();
            }
        });


//updatebutton//
        updateButton.setOnAction(event -> {

            tableView.getItems().clear();
            tableView.getItems().addAll(employeeDAO.getEmployees());
            tableView.refresh();});
        //updatebutton//
//combobox//
        ObservableList<String> options =
                FXCollections.observableArrayList(
                        "Работает",
                        "Отдахыет",
                        "В отпуске"
                );
        comboBox.getItems().addAll(options);
//combobox//



        saveButton.setOnAction(event -> {
            String updateQuery = "INSERT INTO User(Name, PhoneNumber, Status, Time, Post) VALUES(?, ?, ?, ?,?) ";
            PreparedStatement pstmt = null;
            try {
                pstmt = connection.prepareStatement(updateQuery);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            //...
            try {
                pstmt.setString(1, nameField.getText());
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

            try {
                pstmt.setString(2, phoneField.getText());
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

            try {
                String combo = comboBox.getValue()!= null? comboBox.getValue().toString() : "";
                pstmt.setString(3, combo);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

            try {

                String time = dateChooser.getValue()!= null? dateChooser.getValue().toString() : "";
                pstmt.setString(4, time);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

            try {

                pstmt.setString(5, postField.getText());
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            try {
                pstmt.executeUpdate();
                TextInfo.appendText("Данные добавлены!\n");

                // Update the table
                tableView.getItems().clear();
                tableView.getItems().addAll(employeeDAO.getEmployees());
                tableView.refresh();
            } catch (SQLException e) {
                System.out.println("Ошибка обновления данных: " + e.getMessage());

                TextInfo.appendText("Ошибка обновления данных: " + e.getMessage() + "\n");
            }

            // Load the data after updating the contact

           });

        nameSearch.textProperty().addListener((observable, oldValue, newValue) -> {
            tableView.refresh();
            nameSearchText = newValue;
            filterList(employees);

        });

        PhoneSearch.textProperty().addListener((observable, oldValue, newValue) -> {
            tableView.refresh();
            phoneSearchText = newValue;
            filterList(employees);

        });

        StatusSearch.textProperty().addListener((observable, oldValue, newValue) -> {
            tableView.refresh();
            statusSearchText = newValue;
            filterList(employees);

        });

        TimeSearch.valueProperty().addListener((observable, oldValue, newValue) -> {
            tableView.refresh();
            timeSearchText = newValue == null ? "" : newValue.toString();
            filterList(employees);

        });

        PostSearch.textProperty().addListener((observable, oldValue, newValue) -> {
            tableView.refresh();
            postSearchText = newValue;
            filterList(employees);

        });



    }

    private void filterList(ObservableList<Employee> list) {
        tableView.refresh();
        filteredList.clear();
        for (Employee employee : list) {
            if (matches(employee, nameSearchText, phoneSearchText, statusSearchText,timeSearchText,postSearchText)) {
                filteredList.add(employee);
                tableView.refresh();

            }
        }
        tableView.refresh();
        tableView.setItems(filteredList);
    }

    private boolean matches(Employee employee, String nameSearchText, String phoneSearchText, String statusSearchText, String timeSearchText, String postSearchText) {
        tableView.refresh();
        boolean nameMatch = nameSearchText.isEmpty() || employee.getName().contains(nameSearchText);
        boolean phoneMatch = phoneSearchText.isEmpty() || employee.getPhone().contains(phoneSearchText);
        boolean statusMatch = statusSearchText.isEmpty() || employee.getStatus().contains(statusSearchText);
        boolean timeMatch = timeSearchText.isEmpty() || employee.getTime().contains(timeSearchText);
        boolean postMatch = postSearchText.isEmpty() || employee.getPost().contains(postSearchText);
        return nameMatch && phoneMatch && statusMatch && timeMatch && postMatch;

    }
    // ... existing code ...


}