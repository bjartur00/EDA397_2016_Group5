package se.chalmers.agile.pairprogrammingapp.model;

/**
 * Created by Omar on 5.4.2016.
 */
public class TestCase {
    private String title;
    private String description;
    // 1 = Test passed
    // 2 = Test probably passed
    // 3 = Test not passed
    private int status;
    private int id;

    public TestCase(String title, String description, int status, int id) {
        this.title = title;
        this.description = description;
        this.status = status;
        this.id = id;
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
