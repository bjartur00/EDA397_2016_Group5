package se.chalmers.agile.pairprogrammingapp.model;

import java.util.Date;

/**
 * Created by wissam on 09/04/16.
 */
public class Note {
    private String id;
    private String content;
    private Date date;

    public Note(String content) {
        this.id = "";
        this.content = content;
        this.date = new Date();
    }

    public Note(String id, String content, Date date) {
        this.id = id;
        this.content = content;
        this.date = date;
    }

    public String getId() {
        return id;
    }

    public String getContent() {
        return content;
    }

    public Date getDate() {
        return date;
    }

    public void setContent(String content) {
        this.content = content;
        // Set date to current whenever the content is changed
        this.date = new Date();
    }
}
