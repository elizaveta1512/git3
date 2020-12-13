package sample;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import Shared.Period;
import Shared.Phone;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.cell.PropertyValueFactory;
import jfx.messagebox.MessageBox;

public class ReportPaneController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TableView<Phone> topGoods;

    @FXML
    private TableColumn<Phone, String> labelColumn1;

    @FXML
    private TableColumn<Phone, String> modelColumn1;

    @FXML
    private TableColumn<Phone, Float> incomeColumn1;

    @FXML
    private TableColumn<Phone, String> labelColumn;

    @FXML
    private TableColumn<Phone, String> modelColumn;

    @FXML
    private TableColumn<Phone, Float> incomeColumn;

    @FXML
    private TableView<Phone> bottomGoods;

    @FXML
    private TableView<Period> topMonths;

    @FXML
    private TableColumn<Period, String> monthColumn1;

    @FXML
    private TableColumn<Period, Float> monthIncomeColumn1;

    @FXML
    private TableColumn<Period, String> monthColumn;

    @FXML
    private TableColumn<Period, Float> monthIncomeColumn;

    @FXML
    private TableView<Period> bottomMonths;

    @FXML
    private Label yearGrowSumm;

    @FXML
    private Label yearGrowPercent;

    @FXML
    private Label topYear;

    @FXML
    private Label bottomYear;

    @FXML
    private Label nextYearExpectGlobal;

    @FXML
    private Label nextYearExpectLast;

    @FXML
    private Label allTimeGrowSumm;

    @FXML
    private Label allTimeGrowPercent;

    @FXML
    private TextArea personalTips;

    Float fstMonthIncome;
    Float lastMonthIncome;
    ArrayList<Phone> topGoodsList = new ArrayList<Phone>();
    ArrayList<Phone> bottomGoodsList = new ArrayList<Phone>();
    ArrayList<Period> topMonthsList = new ArrayList<Period>();
    ArrayList<Period> bottomMonthsList = new ArrayList<Period>();
    ArrayList<Period> yearsIncome = new ArrayList<Period>();

    void getTopGoods() throws IOException, ClassNotFoundException {
        Client.SendMessage("topGoods");
        topGoodsList = (ArrayList<Phone>)Client.AcceptMessage();
        if(topGoodsList == null)
            throw new IOException();
    }

    void getBottomGoods() throws IOException, ClassNotFoundException {
        Client.SendMessage("bottomGoods");
        bottomGoodsList = (ArrayList<Phone>)Client.AcceptMessage();
        if(bottomGoodsList == null)
            throw new IOException();
    }

    void getTopMonths() throws IOException, ClassNotFoundException {
        Client.SendMessage("topMonths");
        topMonthsList = (ArrayList<Period>)Client.AcceptMessage();
        if(topMonthsList == null)
            throw new IOException();
    }

    void getBottomMonths() throws IOException, ClassNotFoundException {
        Client.SendMessage("bottomMonths");
        bottomMonthsList = (ArrayList<Period>)Client.AcceptMessage();
        if(bottomMonthsList == null)
            throw new IOException();
    }

    void getFstMonthIncome() throws IOException, ClassNotFoundException {
        Client.SendMessage("fstMonth");
        fstMonthIncome = (Float)Client.AcceptMessage();
        if(fstMonthIncome == null)
            throw new IOException();
    }

    void getLastMonthIncome() throws IOException, ClassNotFoundException {
        Client.SendMessage("lastMonth");
        lastMonthIncome = (Float)Client.AcceptMessage();
        if(lastMonthIncome == null)
            throw new IOException();
    }

    void getYearsIncome() throws IOException, ClassNotFoundException {
        Client.SendMessage("yearsIncome");
        yearsIncome = (ArrayList<Period>)Client.AcceptMessage();
        if(yearsIncome == null)
            throw new IOException();
    }

    int getMaxYear(ArrayList<Period> yearsIncome)
    {
        int maxYear = 0;
        float maxIncome = 0;
        for(Period i:yearsIncome)
        {
            if(i.getIncome() > maxIncome){
                maxIncome = i.getIncome();
                maxYear = Integer.parseInt(i.getPeriod());
            }

        };
        return maxYear;
    }

    int getBottomYear(ArrayList<Period> yearsIncome)
    {
        int minYear = 0;
        float minIncome = Float.MAX_VALUE;
        for(Period i:yearsIncome)
        {
            if(i.getIncome() < minIncome) {
                minIncome = i.getIncome();
                minYear = Integer.parseInt(i.getPeriod());
            }
        };
        return minYear;
    }

    float getGlobalTrend(ArrayList<Period> yearsIncome)
    {
        float trend = 0;
        int size = yearsIncome.size();
        trend = (yearsIncome.get(size-1).getIncome()/yearsIncome.get(0).getIncome())/size;
        return trend;
    }

    float getCurrentTrend()
    {
        return lastMonthIncome/fstMonthIncome;
    }

    void downloadReport() throws IOException, ClassNotFoundException {
        Client.SendMessage("downloadReport");
        String text = (String)Client.AcceptMessage();
        if(text == null) {
            MessageBox.show(null,
                    "Ошибка при загрузке отчета\nПовторите попытку позже или обратитесь к администратору.",
                    "Ошибка",
                    MessageBox.ICON_INFORMATION | MessageBox.OK);
            return;
        }
        personalTips.setText(text);
    }

    @FXML
    void saveBtnClicked(ActionEvent event) {
        Client.SendMessage("uploadPersonalReport");
        Client.SendMessage(personalTips.getText());
        try {
            if((String)Client.AcceptMessage() == null)
                MessageBox.show(null,
                        "Ошибка при сохранении отчета\nПовторите попытку позже или обратитесь к администратору.",
                        "Ошибка",
                        MessageBox.ICON_INFORMATION | MessageBox.OK);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void initialize() {
        try {
            getTopGoods();
            getBottomGoods();
            getTopMonths();
            getBottomMonths();
            getFstMonthIncome();
            getLastMonthIncome();
            getYearsIncome();
            downloadReport();
        ObservableList<Phone> phList = FXCollections.observableArrayList();
        phList.addAll(topGoodsList);
        labelColumn.setCellValueFactory(new PropertyValueFactory<Phone, String>("label"));
        modelColumn.setCellValueFactory(new PropertyValueFactory<Phone, String>("model"));
        incomeColumn.setCellValueFactory(new PropertyValueFactory<Phone, Float>("price"));

        labelColumn1.setCellValueFactory(new PropertyValueFactory<Phone, String>("label"));
        modelColumn1.setCellValueFactory(new PropertyValueFactory<Phone, String>("model"));
        incomeColumn1.setCellValueFactory(new PropertyValueFactory<Phone, Float>("price"));
        topGoods.setItems(phList);

        ObservableList<Phone> phList1 = FXCollections.observableArrayList();
        phList1.addAll(bottomGoodsList);
        bottomGoods.setItems(phList1);

        ObservableList<Period> peList = FXCollections.observableArrayList();
        monthColumn.setCellValueFactory(new PropertyValueFactory<Period, String>("period"));
        monthIncomeColumn.setCellValueFactory(new PropertyValueFactory<Period, Float>("income"));
        monthColumn1.setCellValueFactory(new PropertyValueFactory<Period, String>("period"));
        monthIncomeColumn1.setCellValueFactory(new PropertyValueFactory<Period, Float>("income"));
        peList.addAll(topMonthsList);
        topMonths.setItems(peList);

        ObservableList<Period> peList1 = FXCollections.observableArrayList();
        peList1.addAll(bottomMonthsList);
        bottomMonths.setItems(peList1);

        int yearsAmount = yearsIncome.size();

        yearGrowSumm.setText(String.format("%.2f",(yearsIncome.get(yearsAmount-1).getIncome() - yearsIncome.get(yearsAmount-2).getIncome())/1000));
        yearGrowPercent.setText(String.format("%.2f",(yearsIncome.get(yearsAmount-1).getIncome()/yearsIncome.get(yearsAmount-2).getIncome() - 1)*100));

        topYear.setText(Integer.toString(getMaxYear(yearsIncome)));
        bottomYear.setText(Integer.toString(getBottomYear(yearsIncome)));

//        nextYearExpectGlobal.setText(String.format("%.2f",(lastMonthIncome*getGlobalTrend(yearsIncome)/1000)));
//        nextYearExpectLast.setText(String.format("%.2f",(lastMonthIncome*getCurrentTrend()/1000)));

        allTimeGrowSumm.setText(Float.toString(yearsIncome.get(yearsIncome.size()-1).getIncome() - yearsIncome.get(0).getIncome()));
        allTimeGrowPercent.setText(String.format("%.2f",((yearsIncome.get(yearsIncome.size()-1).getIncome()/yearsIncome.get(0).getIncome()) -1)*100 ));
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
