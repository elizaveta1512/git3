package sample;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.ResourceBundle;

import Shared.Period;
import Shared.Phone;
import javafx.fxml.FXML;
import javafx.scene.chart.*;
import jfx.messagebox.MessageBox;

public class AnalysisPaneController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private LineChart<?, ?> lineChart;

    @FXML
    private CategoryAxis xLine;

    @FXML
    private NumberAxis yLine;

    @FXML
    private BarChart<?, ?> barChart;

    @FXML
    private CategoryAxis xBar;

    @FXML
    private NumberAxis yBar;

    void getAllYearsIncome() throws IOException, ClassNotFoundException {
        Client.SendMessage("allYearsIncome");
        ArrayList<Period> list = (ArrayList<Period>)Client.AcceptMessage();
        if(list.get(0).getIncome() == null)
            throw new IOException();
        xLine.setLabel("Month");
        yLine.setLabel("$");
        int i = -1;
        int year = 2017;
        while(i < list.size()-1)
        {
            XYChart.Series lineSeries = new XYChart.Series();
            int k = -1;
            do
            {
                i++;
                k++;
                lineSeries.getData().add(new XYChart.Data(list.get(i).getPeriod(), list.get(i).getIncome()));
            }
            while(k < 11 && list.get(i).getPeriod() != "12");
            lineSeries.setName(Integer.toString(year));
            lineChart.getData().addAll(lineSeries);
            year++;
        }
    }

    void getAllYearsGoods() throws IOException, ClassNotFoundException {
        Client.SendMessage("allYearsGoods");
        ArrayList<Phone> list = (ArrayList<Phone>)Client.AcceptMessage();
        int i = 0;
        if(list.get(0).getLabel() == null)
            throw new IOException();
        XYChart.Series lineSeries = new XYChart.Series();
        while(i < list.size())
        {
            lineSeries.getData().add(new XYChart.Data(list.get(i).getLabel() + list.get(i).getModel() , list.get(i).getPrice()));
            i++;
        }
        yBar.setAutoRanging(false);
        yBar.setLowerBound(list.get(list.size()-1).getPrice()*0.95);
        yBar.setUpperBound(list.get(0).getPrice()*1.05);
        yBar.setTickUnit(50);
        yBar.setLabel("Количество");
        barChart.getData().addAll(lineSeries);
    }

    @FXML
    void initialize() {
        try {
            getAllYearsIncome();
            getAllYearsGoods();
        } catch (IOException e) {
            MessageBox.show(null,
                    "Ошибка при соединении с сервером.\nПовторите попытку позже.",
                    "Ошибка",
                    MessageBox.ICON_INFORMATION | MessageBox.OK);
//            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            MessageBox.show(null,
                    "Ошибка при соединении с сервером.\nПовторите попытку позже.",
                    "Ошибка",
                    MessageBox.ICON_INFORMATION | MessageBox.OK);
            //            e.printStackTrace();
        }
    }
}
