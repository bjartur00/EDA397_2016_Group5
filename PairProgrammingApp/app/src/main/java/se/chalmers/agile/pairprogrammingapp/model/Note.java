package se.chalmers.agile.pairprogrammingapp.model;

import java.util.Date;

/**
 * Created by wissam on 09/04/16.
 */
public class Note {
    private String content;
    private Date date;

    public Note(String content) {
        this.content = content;
        this.date = new Date();
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
