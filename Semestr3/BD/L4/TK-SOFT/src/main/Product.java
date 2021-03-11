package main;


import javafx.beans.property.SimpleStringProperty;

public class Product {
    private SimpleStringProperty name;
    private SimpleStringProperty producentName;
    private int count;
    private float price;

    public String getName() {
        return name.get();
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public String getProducentName() {
        return producentName.get();
    }


    public void setProducentName(String producentName) {
        this.producentName.set(producentName);
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public Product(String name, String producentName, int count, float price) {
        this.name = new SimpleStringProperty(name);
        this.producentName = new SimpleStringProperty(producentName);
        this.count = count;
        this.price = price;
    }
}
