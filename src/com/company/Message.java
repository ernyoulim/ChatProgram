package com.company;

import java.io.Serializable;
import java.text.DateFormat;
import java.util.Date;

public class Message implements Serializable {
    private String sender;
    private String text;
    private Date time;

    public Message (String sender, String text){
        this.sender = sender;
        this.text = text;
        time = new Date();
    }

    public Date getTime() {
        return time;
    }

    public String getSender() {
        return sender;
    }

    public String getText() {
        return text;
    }

    @Override
    public String toString() {
        String timestr = DateFormat.getTimeInstance(DateFormat.SHORT).format(time);
        return sender + " ( " + timestr + " ) " + text;
    }
}
