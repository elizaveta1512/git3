package sample;

import Shared.Phone;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import jfx.messagebox.MessageBox;

import java.io.IOException;
import java.util.ArrayList;

public class UserPaneController {
    @FXML
    private Pane mainPane;

    @FXML
    private TableView<Phone> table;

    @FXML
    private TableColumn<Phone, String> labelColumn;

    @FXML
    private TableColumn<Phone, String> modelColumn;

    @FXML
    private TableColumn<Phone, Float> priceColumn;

    @FXML
    private Button orderBtn;

    @FXML
    private Button exitBtn;

    @FXML
    void ExitBtnClick(ActionEvent event) {
        Client.SendMessage("exit");
        Stage stage = (Stage)mainPane.getScene().getWindow();
        stage.close();
    }

    @FXML
    void OrderBtnClick(ActionEvent event) {
        Phone phone = table.getSelectionModel().getSelectedItem();
        MessageBox.show(null,
                "Ваша запись:\n" + phone.getLabel() + "\n"
                + phone.getModel() + "\nза "
                + phone.getPrice() + " $\nНе опаздывайте",
                "Запись на процедуру",
                MessageBox.ICON_INFORMATION | MessageBox.OK);
    }

    @FXML
    void initialize() {
        try
        {
            Client.SendMessage("loadGoods");
            ArrayList<Phone> list = (ArrayList<Phone>)Client.AcceptMessage();
            ObservableList<Phone> oList = FXCollections.observableArrayList();
            oList.addAll(list);
            table.setItems(oList);

            labelColumn.setCellValueFactory(new PropertyValueFactory<Phone, String>("label"));
            modelColumn.setCellValueFactory(new PropertyValueFactory<Phone, String>("model"));
            priceColumn.setCellValueFactory(new PropertyValueFactory<Phone, Float>("price"));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
