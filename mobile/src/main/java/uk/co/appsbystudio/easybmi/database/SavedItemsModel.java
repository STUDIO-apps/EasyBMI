package uk.co.appsbystudio.easybmi.database;

public class SavedItemsModel {

    Integer id;
    Float bmi;
    Float weight;
    Float height;
    String dateTime;

    public SavedItemsModel() {}

    public SavedItemsModel(Float bmi) {
        this.bmi = bmi;
    }

    public SavedItemsModel(Integer id, Float bmi) {
        this.id = id;
        this.bmi = bmi;
    }

    public SavedItemsModel(Integer id, Float bmi, String dateTime) {
        this.id = id;
        this.bmi = bmi;
        this.dateTime = dateTime;
    }

    public SavedItemsModel(Integer id, Float bmi, Float weight, Float height, String dateTime) {
        this.id = id;
        this.bmi = bmi;
        this.weight = weight;
        this.height = height;
        this.dateTime = dateTime;
    }

    public void setBmi(Float bmi) {
        this.bmi = bmi;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public Float getBmi() {
        return bmi;
    }

    public Integer getId() {
        return id;
    }

    public String getDateTime() {
        return dateTime;
    }

    public Float getWeight() {
        return weight;
    }

    public Float getHeight() {
        return height;
    }
}
