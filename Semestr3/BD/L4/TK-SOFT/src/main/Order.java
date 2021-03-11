package main;

import javafx.beans.property.SimpleStringProperty;

public class Order {
    private int id;
    private SimpleStringProperty product;
    private SimpleStringProperty status;
    private SimpleStringProperty client;
    private int count;
    private SimpleStringProperty details;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getProduct() {
        return product.get();
    }

    public void setProduct(String product) {
        this.product.set(product);
    }

    public String getStatus() {
        return status.get();
    }

    public void setStatus(String status) {
        this.status.set(status);
    }

    public String getClient() {
        return client.get();
    }

    public void setClient(String client) {
        this.client.set(client);
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getDetails() {
        return details.get();
    }

    public void setDetails(String details) {
        this.details.set(details);
    }

    public Order(int id, String product, String status, String client, int count, String details) {
        this.id = id;
        this.product = new SimpleStringProperty(product);
        this.status = new SimpleStringProperty(status);
        this.client = new SimpleStringProperty(client);
        this.count = count;
        this.details = new SimpleStringProperty(details);
    }
}
