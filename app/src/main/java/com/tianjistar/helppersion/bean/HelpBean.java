package com.tianjistar.helppersion.bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/11/15.
 */

public class HelpBean implements Serializable {

    /**
     * "type": "1"
     * "id": "45",
     * title : 测试
     * word : 测试
     * ts : 2017-11-17 15:50:20
     */

    private String title;
    private String word;
    private String ts;
    private int type;
    private int id;
    private String photo;
    private int sendobj;
    private int issuetype;
    private int typetime;
    private String issuetime;

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public int getSendobj() {
        return sendobj;
    }

    public void setSendobj(int sendobj) {
        this.sendobj = sendobj;
    }

    public int getIssuetype() {
        return issuetype;
    }

    public void setIssuetype(int issuetype) {
        this.issuetype = issuetype;
    }

    public int getTypetime() {
        return typetime;
    }

    public void setTypetime(int typetime) {
        this.typetime = typetime;
    }

    public String getIssuetime() {
        return issuetime;
    }

    public void setIssuetime(String issuetime) {
        this.issuetime = issuetime;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String getTs() {
        return ts;
    }

    public void setTs(String ts) {
        this.ts = ts;
    }
}
