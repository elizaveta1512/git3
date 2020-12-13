package sample;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import Shared.Phone;
import Shared.Sale;
import Shared.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import jfx.messagebox.MessageBox;

public class AdminPaneController {

    @FXML
    private ResourceBundle resources;

    ArrayList<Phone> phonesList = null;

    @FXML
    private URL location;

    @FXML
    private Pane mainPane;

    @FXML
    private TableView<User> table;

    @FXML
    private TableColumn<User, String> loginColumn;

    @FXML
    private TableColumn<User, String> passwordColumn;

    @FXML
    private TableColumn<User, String> typeColumn;

    @FXML
    private Button applyBtn;

    @FXML
    private Button exitBtn;

    @FXML
    private Button addBtn;

    @FXML
    private TextField newLoginField;

    @FXML
    private TextField newTypeField;

    @FXML
    private TextField newPasswordField;
    @FXML
    private TableView<Sale> salesTable;

    @FXML
    private TableColumn<Sale, String> labelColumn;

    @FXML
    private TableColumn<Sale, String> modelColumn;

    @FXML
    private TableColumn<Sale, String> dateColumn;

    @FXML
    private TableColumn<Sale, Integer> amountColumn;

    @FXML
    private TextField labelField;

    @FXML
    private TextField modelField;

    @FXML
    private TextField dateField;

    @FXML
    private TextField amountField;

    @FXML
    void applyBtnClicked(ActionEvent event) {
        Client.SendMessage("editUser");
        try {
            Client.EditUser(table.getSelectionModel().getSelectedItem(),
                    new User(newLoginField.getText(),
                            newPasswordField.getText(),
                            newTypeField.getText())
            );
        } catch (IOException e) {
            e.printStackTrace();
        }
        initialize();
    }

    @FXML
    void exitBtnClicked(ActionEvent event) {
        Client.SendMessage("exit");
        Stage stage = (Stage)mainPane.getScene().getWindow();
        stage.close();
    }

    @FXML
    void setForEdit(MouseEvent event) {
        User user = table.getSelectionModel().getSelectedItem();
        newLoginField.setText(user.getLogin());
        newPasswordField.setText(user.getPassword());
        newTypeField.setText(user.getType());
    }

    @FXML
    void deleteBtnCLicked(ActionEvent event) {
        Client.SendMessage("deleteUser");
        Client.SendMessage(table.getSelectionModel().getSelectedItem());
        initialize();
    }

    @FXML
    void addBtnClicked(ActionEvent event) {
        Client.SendMessage("addUser");
        Client.SendMessage(new User(newLoginField.getText(),
                newPasswordField.getText(),
                newTypeField.getText())
        );
        initialize();
    }

    @FXML
    void addSaleBtnClicked(ActionEvent event) {
        Client.SendMessage("addSale");
        Client.SendMessage(new Sale(
                labelField.getText(),
                modelField.getText(),
                dateField.getText(),
                Integer.parseInt(amountField.getText()))
        );
        try {
            if((String)Client.AcceptMessage() == null)
                MessageBox.show(null,
                        "Ошибка при добавлении.\nПроверьте введенные данные.",
                        "Ошибка",
                        MessageBox.ICON_INFORMATION | MessageBox.OK);
            initialize();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void deleteSaleBtnClicked(ActionEvent event) {
        Client.SendMessage("deleteSale");
        Client.SendMessage(salesTable.getSelectionModel().getSelectedItem());
        initialize();
    }


    @FXML
    void initialize() {
        try
        {
            Client.SendMessage("loadUsers");
            ArrayList<User> list = (ArrayList<User>)Client.AcceptMessage();
            ObservableList<User> oList = FXCollections.observableArrayList();
            oList.addAll(list);
            table.setItems(oList);

            loginColumn.setCellValueFactory(new PropertyValueFactory<User, String>("login"));
            passwordColumn.setCellValueFactory(new PropertyValueFactory<User, String>("password"));
            typeColumn.setCellValueFactory(new PropertyValueFactory<User, String>("type"));

            Client.SendMessage("loadSales");
            phonesList = (ArrayList<Phone>)Client.AcceptMessage();
            ArrayList<Sale> salesList = (ArrayList<Sale>)Client.AcceptMessage();

            ObservableList<Sale> sList = FXCollections.observableArrayList();
            sList.addAll(salesList);
            salesTable.setItems(sList);

            labelColumn.setCellValueFactory(new PropertyValueFactory<Sale, String>("label"));
            modelColumn.setCellValueFactory(new PropertyValueFactory<Sale, String>("model"));
            dateColumn.setCellValueFactory(new PropertyValueFactory<Sale, String>("date"));
            amountColumn.setCellValueFactory(new PropertyValueFactory<Sale, Integer>("amount"));

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
