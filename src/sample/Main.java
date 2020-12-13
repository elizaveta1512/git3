package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import jfx.messagebox.MessageBox;

import static sample.Client.connector;

public class Main extends Application {

    public Parent root;
    public static Stage window;
    public static Scene mainScene;

    public void changeStage(Scene scene, Parent root){
        this.mainScene=scene;
        this.root=root;
        window.setScene(scene);
        window.show();
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        window = primaryStage;
//        window.initStyle(StageStyle.UNDECORATED);
        if(connector.connect())
        {
            Parent root = FXMLLoader.load(getClass().getResource("Autorization.fxml"));
            primaryStage.setTitle("Парихмахерская|Вход");
            primaryStage.setScene(new Scene(root, 400, 400));
            primaryStage.setResizable(true);
            primaryStage.show();
        }
        else
        {
            MessageBox.show(primaryStage,
                "Невозможно подключиться к серверу.\nПовторите попытку позже.",
                "Ошибка",
                MessageBox.ICON_INFORMATION | MessageBox.OK);
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
