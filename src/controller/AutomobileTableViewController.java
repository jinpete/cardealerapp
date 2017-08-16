package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import model.*;
import tool.DbOps;

import javax.sql.rowset.FilteredRowSet;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class AutomobileTableViewController implements Initializable {

    // configure table
    @FXML private TableView<Automobile> automobileTableView;
    @FXML private TableColumn<Automobile, Integer> automobileIdColumn;
    @FXML private TableColumn<Automobile, String> makeColumn;
    @FXML private TableColumn<Automobile, String> modelColumn;
    @FXML private TableColumn<Automobile, String> styleColumn;
    @FXML private TableColumn<Automobile, String> conditionColumn;
    @FXML private TableColumn<Automobile, Integer> modelYearColumn;
    @FXML private TableColumn<Automobile, Double> priceColumn;
    @FXML private TableColumn<Automobile, Integer> mileageColumn;
    @FXML private ListView<String> makeListView;
    @FXML private ListView<String> styleListView;
    @FXML private ListView<String> conditionListView;
    @FXML private TextField minPriceTextField;
    @FXML private TextField maxPriceTextField;
    @FXML private TextField maxMilesTextField;

    private FilteredRowSet automobileResultSet;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        automobileIdColumn.setCellValueFactory(new PropertyValueFactory<Automobile, Integer>("automobileID"));
        makeColumn.setCellValueFactory(new PropertyValueFactory<Automobile, String>("make"));
        modelColumn.setCellValueFactory(new PropertyValueFactory<Automobile, String>("model"));
        styleColumn.setCellValueFactory(new PropertyValueFactory<Automobile, String>("style"));
        conditionColumn.setCellValueFactory(new PropertyValueFactory<Automobile, String>("condition"));
        modelYearColumn.setCellValueFactory(new PropertyValueFactory<Automobile, Integer>("modelYear"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<Automobile, Double>("price"));
        mileageColumn.setCellValueFactory(new PropertyValueFactory<Automobile, Integer>("mileage"));

        try {
            initAutomobileResultSet();
            refreshTableView();
            refreshManufacturerListView();
            refreshStyleListView();
            refreshConditionListView();
            initPriceTextFields();
            initMaxMilesTextFields();
        }
        catch (SQLException sqlex) {
            sqlex.printStackTrace();
        }
    }

    private void initMaxMilesTextFields() {
        maxMilesTextField.setText("999999");
    }

    private void refreshConditionListView() {
        ObservableList<String> conditions = FXCollections.observableArrayList();
        conditions.add("New");
        conditions.add("Used");
        conditionListView.setItems(conditions);
    }

    private void refreshTableView() throws SQLException {
        automobileTableView.setItems(getAutomobiles());
    }

    private void refreshManufacturerListView() throws SQLException {
        ObservableList<String> makes = FXCollections.observableArrayList();

        automobileResultSet.beforeFirst();
        while (automobileResultSet.next()) {
            String newMake = automobileResultSet.getString("make");
            if(!makes.contains(newMake))
                makes.add(newMake);
        }
        makes.sorted();
        makeListView.setItems(makes.sorted());
    }

    private void refreshStyleListView() throws SQLException {
        ObservableList<String> styles = FXCollections.observableArrayList();

        automobileResultSet.beforeFirst();
        while (automobileResultSet.next()) {
            String style = automobileResultSet.getString("style");
            if(!styles.contains(style))
                styles.add(style);
        }
        styleListView.setItems(styles.sorted());
    }

    private void initAutomobileResultSet() throws SQLException  {
        DbOps dbOps = new DbOps();
        dbOps.getSql("automobile");
        automobileResultSet = dbOps.execQuery();
    }

    private void initPriceTextFields() {
        minPriceTextField.setText("0");
        maxPriceTextField.setText("999999");
    }

    private ObservableList<Automobile> getAutomobiles() throws SQLException {
        ObservableList<Automobile> automobiles = FXCollections.observableArrayList();
        automobileResultSet.beforeFirst();
        while (automobileResultSet.next()) {
            Automobile car = new Automobile(
                    automobileResultSet.getInt("automobileID"),
                    automobileResultSet.getString("make"),
                    automobileResultSet.getString("model"),
                    automobileResultSet.getString("style"),
                    automobileResultSet.getString("condition"),
                    automobileResultSet.getInt("modelYear"),
                    automobileResultSet.getDouble("price"),
                    automobileResultSet.getInt("mileage")
            );
            automobiles.add(car);
        }
        return automobiles;
    }

    public void makeListViewSelectionChanged() throws SQLException {
        String selectedManufacturer = makeListView.getSelectionModel().getSelectedItem();
        ManufacturerFilter manufacturerFilter = new ManufacturerFilter(selectedManufacturer);
        automobileResultSet.setFilter(manufacturerFilter);
        automobileTableView.setItems(getAutomobiles());
    }

    public void styleListViewSelectionChanged() throws SQLException {
        String selectedStyle = styleListView.getSelectionModel().getSelectedItem();
        StyleFilter styleFilter = new StyleFilter(selectedStyle);
        automobileResultSet.setFilter(styleFilter);
        automobileTableView.setItems(getAutomobiles());
    }

    public void conditionListViewSelectionChanged() throws SQLException {
        String selectedCondition = conditionListView.getSelectionModel().getSelectedItem();
        ConditionFilter conditionFilter = new ConditionFilter(selectedCondition);
        automobileResultSet.setFilter(conditionFilter);
        automobileTableView.setItems(getAutomobiles());
    }

    public void priceRangeChanged() throws SQLException{
        boolean priceRangeChanged = false;
        double minPrice = 0;
        double maxPrice = 999999;
        try {
            minPrice = Double.parseDouble(minPriceTextField.getText());
            maxPrice = Double.parseDouble(maxPriceTextField.getText());
            if (maxPrice > minPrice)
                priceRangeChanged = true;
            else
                showError("Price range error", "Minimum price must be less than maximum price.");
        }
        catch (Exception ex) {
            showError("Error", "Prices must be in numeric format.");
            priceRangeChanged = false;
        }

        if(priceRangeChanged) {
            PriceFilter priceFilter = new PriceFilter(minPrice, maxPrice);
            automobileResultSet.setFilter(priceFilter);
            automobileTableView.setItems(getAutomobiles());
        }
        else {
            initPriceTextFields();
        }
    }

    public void maxMileageChanged() throws SQLException{
        boolean mileageChanged = false;
        int maxMiles = 999999;
        try {
            maxMiles = Integer.parseInt(maxMilesTextField.getText());
            if (maxMiles >= 0)
                mileageChanged = true;
            else
                showError("Max Mileage Error", "Maximum mileage must be a positive integer.");
        }
        catch (Exception ex) {
            showError("Max Mileage Error", "Maximum mileage must be a number.");
            mileageChanged = false;
        }

        if(mileageChanged) {
            MileageFilter mileageFilter = new MileageFilter(maxMiles);
            automobileResultSet.setFilter(mileageFilter);
            automobileTableView.setItems(getAutomobiles());
        }
        else {
            initMaxMilesTextFields();
        }
    }
    private void showError(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
