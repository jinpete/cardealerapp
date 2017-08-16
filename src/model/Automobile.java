package model;

import javafx.beans.property.*;

public class Automobile {
    private IntegerProperty automobileID;
    private StringProperty make;
    private StringProperty model;
    private StringProperty style;
    private StringProperty condition;
    private IntegerProperty modelYear;
    private DoubleProperty price;
    private IntegerProperty mileage;


    //Constructor
    public Automobile(int automobileID, String make, String model, String style, String condition, int modelYear, double price, int mileage) {
        this.automobileID = new SimpleIntegerProperty(automobileID);
        this.make = new SimpleStringProperty(make);
        this.model = new SimpleStringProperty(model);
        this.style = new SimpleStringProperty(style);
        this.condition = new SimpleStringProperty(condition);
        this.modelYear = new SimpleIntegerProperty(modelYear);
        this.price = new SimpleDoubleProperty(price);
        this.mileage = new SimpleIntegerProperty(mileage);
    }

    public int getAutomobileID() {
        return automobileID.get();
    }

    public void setAutomobileID(int automobileID){
        this.automobileID.set(automobileID);
    }

    public IntegerProperty automobileIdProperty(){
        return automobileID;
    }

    public String getMake() {
        return make.get();
    }

    public void setMake(String make){ this.make.set(make); }

    public StringProperty makeProperty() {
        return make;
    }

    public String getModel() {
        return model.get();
    }

    public void setModel(String model){
        this.model.set(model);
    }

    public StringProperty modelProperty() {
        return model;
    }

    public String getStyle() {
        return style.get();
    }

    public void setStyleName(String style){
        this.style.set(style);
    }

    public StringProperty styleProperty() {
        return style;
    }

    public String getCondition() {
        return condition.get();
    }

    public void setCondition(String condition){
        this.condition.set(condition);
    }

    public StringProperty conditionProperty() {
        return condition;
    }

    public int getModelYear() {
        return modelYear.get();
    }

    public void setModelYear(int modelYear){ this.modelYear.set(modelYear); }

    public IntegerProperty modelYearProperty(){
        return modelYear;
    }

    public double getPrice() {
        return price.get();
    }

    public void setPrice(double price){
        this.price.set(price);
    }

    public DoubleProperty priceProperty(){
        return price;
    }

    public int getMileage() {
        return mileage.get();
    }

    public void setMileage(int mileage){ this.mileage.set(mileage); }

    public IntegerProperty mileageProperty(){
        return mileage;
    }
}