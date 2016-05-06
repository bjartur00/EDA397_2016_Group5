package se.chalmers.agile.pairprogrammingapp.model;

/*
 * Copyright (C), Owner, Omar Thor Omarsson and co.
*/
public class TestCase implements java.io.Serializable{
    private String title;
    private String description;
    // 1 = Test passed
    // 2 = Test probably passed
    // 3 = Test not passed
    private int status;
    private int id;

    public String getListID() {
        return listID;
    }

    public void setListID(String listID) {
        this.listID = listID;
    }

    private String listID;

    public TestCase(String title, String description, int status, int id, String listID) {
        this.title = title;
        this.description = description;
        this.status = status;
        this.id = id;
        this.listID = listID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
