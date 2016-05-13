package se.chalmers.agile.pairprogrammingapp.model;

/*
 * Copyright (C), Owner, Omar Thor Omarsson and co.
*/
public class TestCase implements java.io.Serializable {
    public static final int PASSED = 1;
    public static final int TESTING = 2;
    public static final int NOT_PASSED = 3;

    private String title;
    private String description;
    private int status;
    private String id;

    public String getListID() {
        return listID;
    }

    public void setListID(String listID) {
        this.listID = listID;
    }

    private String listID;

    public TestCase(String title, String description, int status, String id, String listID) {
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
