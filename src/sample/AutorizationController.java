package sample;

import Shared.User;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import jfx.messagebox.MessageBox;

import java.io.IOException;

public class AutorizationController {

    @FXML
    private AnchorPane mainPane;

    @FXML
    private TextField loginField;

    @FXML
    private TextField passwordField;

    @FXML
    private Button registerBtn;

    @FXML
    private Button logInBtn;

    @FXML
    private Label closeBtn;

    void loadStage(Parent root, String stageName) throws IOException {
//        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(stageName));
//        Parent root1 = (Parent) fxmlLoader.load();
//        Stage stage = new Stage();
//        stage.setScene(new Scene(root1));
//        stage.show();

        root = FXMLLoader.load(getClass().getResource(stageName));
        Main.mainScene = new Scene(root, 1500, 800);
        Main.window.setScene(Main.mainScene);
        Main.window.show();
    }

    @FXML
    void exitBtnClicked(MouseEvent event) {
        Client.SendMessage("exit");
        Stage stage = (Stage)mainPane.getScene().getWindow();
        stage.close();
    }

    @FXML
    void logInBtnClicked(ActionEvent event) {
        Parent root = null;
        try
        {
            User user = Client.Authorize(loginField.getText(), passwordField.getText());
            switch (user.getType())
            {
                case "admin":
                    loadStage(root, "adminPane.fxml");
                    break;
                case "analyst":
                    loadStage(root, "analystPane.fxml");
                    break;
                case "user":
                    loadStage(root, "userPane.fxml");
                    break;
                default:
                    MessageBox.show(null,
                            "Ошибка авторизации.\nПовторите попытку позже.",
                            "Ошибка",
                            MessageBox.ICON_INFORMATION | MessageBox.OK);
            }
        }
        catch (Exception e) {
            MessageBox.show(null,
                    "Ошибка авторизации.\nПовторите попытку позже.",
                    "Ошибка",
                    MessageBox.ICON_INFORMATION | MessageBox.OK);
        }
    }

    @FXML
    void registerBtnClicked(ActionEvent event) {
        Parent root = null;
        try
        {
            Client.SendMessage("createUser");
            Client.SendMessage(new User(loginField.getText(), passwordField.getText(), "user"));
            String type = ((User)Client.AcceptMessage()).getType();
            if(type != null)
                loadStage(root, "userPane.fxml");
            else
                MessageBox.show(null,
                        "Ошибка регистрации.\nВозможно, такой пользователь уже существует.\nПовторите попытку позже.",
                        "Ошибка",
                        MessageBox.ICON_INFORMATION | MessageBox.OK);
        }
        catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

}
