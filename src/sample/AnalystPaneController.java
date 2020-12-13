package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;

public class AnalystPaneController {

    @FXML
    private BorderPane mainPane;

    @FXML
    private Button analysisBtn;

    @FXML
    private Button reportBtn;

    @FXML
    private Button exitBtn;

    @FXML
    private Pane changePane;

    private void LoadUI(String ui)
    {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource(ui+".fxml"));
            Pane load = loader.load();
            switch(ui)
            {
                case "Analysis":
                    AnalysisPaneController analysisController = loader.getController();
                    break;
                case "Report":
                    ReportPaneController reportController = loader.getController();
                    break;
            }
            mainPane.setCenter(load);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void analysisBtnClicked(ActionEvent event) {
        LoadUI("Analysis");
    }

    @FXML
    void reportBtnClicked(ActionEvent event) {
        LoadUI("Report");
    }

    @FXML
    void exitBtnClicked(ActionEvent event) {
        Client.SendMessage("exit");
        Stage stage = (Stage)mainPane.getScene().getWindow();
        stage.close();
    }
}
