package dashboard;
import controllers.LoginController;
import models.Employee;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Optional;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class employeeDashboardController implements Initializable {

    public Button markAtt_btn;
    @FXML
    private AnchorPane main_form;

    @FXML
    private Button close;

    @FXML
    private Button minimize;

    @FXML
    private Label username;

    @FXML
    private Button home_btn;

    @FXML
    private Button logout;

    @FXML
    private AnchorPane home_form;

    @FXML
    private Label home_empName;

    @FXML
    private Label home_empEmail;

    @FXML
    private Label home_empRole;


    @FXML
    private AnchorPane markAttendance;

    @FXML
    private TableView<employeeAttendanceData> addEmployee_tableView;

    @FXML
    private TableColumn<employeeAttendanceData, String> addEmployee_col_datein;

    @FXML
    private TableColumn<employeeAttendanceData, String> addEmployee_col_timein;

    @FXML
    private TableColumn<employeeAttendanceData, String> addEmployee_col_dateout;

    @FXML
    private TableColumn<employeeAttendanceData, String> addEmployee_col_timeout;

    @FXML
    private Connection connect;
    private Statement statement;
    private PreparedStatement prepare;
    private ResultSet result;


    private Employee currLoggedInUser = LoginController.currLoggedInEmployee;

    public void getEmployeeInfo() {

        String sql = "SELECT emp_name, emp_email,role FROM employee, emp_account WHERE employee.emp_id = ? AND empaccount.emp_id = ?";

        connect = database.connectDb();
        try {

            prepare = connect.prepareStatement(sql);
            prepare.setString(1, currLoggedInUser.getDob());    //edit this
            prepare.setString(2, currLoggedInUser.getDob());    //edit this
            result = prepare.executeQuery();

            System.out.println(result);     //arrange this
//            home_totalPresents.setText(String.valueOf(countData));

        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    public ObservableList<employeeAttendanceData> addEmployeeListData() {

        ObservableList<employeeAttendanceData> listData = FXCollections.observableArrayList();
        String sql = "SELECT * FROM clockinrecord"; //include where clause

        connect = database.connectDb();

        try {
            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();
            employeeAttendanceData employeeD;

            while (result.next()) {
                employeeD = new employeeAttendanceData(result.getDate("date-in"),
                        result.getDate("time-in"));
                listData.add(employeeD);

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        sql = "SELECT * FROM clockoutrecord"; //include where clause

        try {
            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();
            employeeAttendanceData employeeD;

            while (result.next()) {
                employeeD = new employeeAttendanceData(result.getDate("date-out"),
                        result.getDate("time-out"));
                listData.add(employeeD);

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return listData;
    }
    private ObservableList<employeeAttendanceData> addEmployeeList;

    public void addEmployeeShowListData() {
        addEmployeeList = addEmployeeListData();

        addEmployee_col_datein.setCellValueFactory(new PropertyValueFactory<>("datein"));
        addEmployee_col_timein.setCellValueFactory(new PropertyValueFactory<>("timein"));
        addEmployee_col_dateout.setCellValueFactory(new PropertyValueFactory<>("dateout"));
        addEmployee_col_timeout.setCellValueFactory(new PropertyValueFactory<>("timeout"));
        addEmployee_tableView.setItems(addEmployeeList);

    }


    public void defaultNav() {
        home_btn.setStyle("-fx-background-color:linear-gradient(to bottom right, #3a4368, #28966c);");
    }

    public void displayUsername() {
        username.setText(getData.username);
    }

    public void switchForm(ActionEvent event) {

        if (event.getSource() == home_btn) {
            home_form.setVisible(true);
            markAttendance.setVisible(false);


            home_btn.setStyle("-fx-background-color:linear-gradient(to bottom right, #3a4368, #28966c);");
            markAttendance.setStyle("-fx-background-color:transparent");


            getEmployeeInfo();
            addEmployeeShowListData();//add employee history

        } else if (event.getSource() == markAtt_btn ){
            home_form.setVisible(false);
            markAttendance.setVisible(true);


            markAttendance.setStyle("-fx-background-color:linear-gradient(to bottom right, #3a4368, #28966c);");
            home_btn.setStyle("-fx-background-color:transparent");


        }
    }

    private double x = 0;
    private double y = 0;

    public void logout() {

        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Confirmation Message");
        alert.setHeaderText(null);
        alert.setContentText("Are you sure you want to logout?");
        Optional<ButtonType> option = alert.showAndWait();
        try {
            if (option.get().equals(ButtonType.OK)) {

                logout.getScene().getWindow().hide();
                Parent root = FXMLLoader.load(getClass().getResource("FXMLDocument.fxml"));
                Stage stage = new Stage();
                Scene scene = new Scene(root);

                root.setOnMousePressed((MouseEvent event) -> {
                    x = event.getSceneX();
                    y = event.getSceneY();
                });

                root.setOnMouseDragged((MouseEvent event) -> {
                    stage.setX(event.getScreenX() - x);
                    stage.setY(event.getScreenY() - y);

                    stage.setOpacity(.8);
                });

                root.setOnMouseReleased((MouseEvent event) -> {
                    stage.setOpacity(1);
                });

                stage.initStyle(StageStyle.TRANSPARENT);

                stage.setScene(scene);
                stage.show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void close() {
        System.exit(0);
    }

    public void minimize() {
        Stage stage = (Stage) main_form.getScene().getWindow();
        stage.setIconified(true);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        displayUsername();
        defaultNav();
        getEmployeeInfo();
        addEmployeeShowListData();

    }

}
