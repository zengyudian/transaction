package com.example.item;


public class UserItem {

    private int curName;
    private String curPassword;

    public UserItem() {
        super();
        curName = 0;
        curPassword = "";
    }
    public UserItem(int curName, String curPassword) {
        super();
        this.curName = curName;
        this.curPassword = curPassword;
    }

    public int getCurName() {
        return curName;
    }
    public void setCurName(int curName) {
        this.curName = curName;
    }

    public String getCurPassword() {
        return curPassword;
    }
    public void setCurPassword(String curPassword) {
        this.curPassword = curPassword;
    }
}

