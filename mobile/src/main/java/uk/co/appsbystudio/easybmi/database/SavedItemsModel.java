package uk.co.appsbystudio.easybmi.database;

public class SavedItemsModel {

    private Float bmi;
    private Float weight;
    private Float height;
    private String dateTime;

    public SavedItemsModel() {}

    public SavedItemsModel(Float bmi, Float weight, Float height, String dateTime) {
        this.bmi = bmi != null? bmi : this.bmi;
        this.weight = weight != null? weight : this.weight;
        this.height = height != null? height : this.height;
        this.dateTime = dateTime != null? dateTime : this.dateTime;
    }

    public Float getBmi() {
        return bmi;
    }

    public Float getWeight() {
        return weight;
    }

    public Float getHeight() {
        return height;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setBmi(Float bmi) {
        this.bmi = bmi;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public void setWeight(Float weight) {
        this.weight = weight;
    }

    public void setHeight(Float height) {
        this.height = height;
    }
}
