package com.example.irving.lini.bean;

import java.util.Date;

/**
 * Created by Irving on 2018/5/3.
 */

public class ChatMessage {
    private String msg;
    private Type type;
    private Date date;
    private String name;

    public ChatMessage(){}

    public ChatMessage(String msg,Date date,Type type){
        this.msg = msg;
        this.date = date;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public enum Type{
        INCOMING,OUTCOMING
    }
}
