package org.example.demo2.DB;


import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Employee {
    private final StringProperty status;
    private final StringProperty name;
    private final StringProperty phone;
    private final StringProperty post;
    private final StringProperty time;

    public Employee() {
        this.status = new SimpleStringProperty();
        this.name = new SimpleStringProperty();
        this.phone = new SimpleStringProperty();
        this.post = new SimpleStringProperty();
        this.time = new SimpleStringProperty();
    }

    public StringProperty statusProperty() {
        return status;
    }

    public void setStatus(String status) {
        this.status.set(status);
    }

    public String getStatus() {
        return status.get();
    }

    // Repeat for each field...
    public StringProperty nameProperty() {
        return name;
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public String getName() {
        return name.get();
    }
    public StringProperty phoneProperty() {
        return phone;
    }
    public void setPhone(String phone) {
        this.phone.set(phone);
    }
    public String getPhone() {
        return phone.get();
    }
    public StringProperty postProperty() {
        return post;
    }
    public void setPost(String post) {
        this.post.set(post);
    }
    public String getPost() {
        return post.get();
    }
    public StringProperty timeProperty() {
        return time;

    }
    public void setTime(String time) {
        this.time.set(time);
    }
    public String getTime() {
        return time.get();
    }


    //...
}