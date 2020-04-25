// Java 2 Week 3 1412
// Jeremiah Bonham

package com.example.jbonham81.java2_week3;

import java.io.Serializable;

public class Items implements Serializable {

    private static long serialVersionUID = 585254214;

    public static Items newInstance(String _title, String _type, String _borrower) {

        Items item = new Items();
        item.setTitle(_title);
        item.setType(_type);
        item.setBorrower(_borrower);
        return item;
    }

    private String title;
    private String type;
    private String borrower;

    public Items() {
        title = "";
        type = "";
        borrower = "";

    }

    public Items(String _title, String _type, String _borrower) {
        title = _title;
        type = _type;
        borrower = _borrower;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getBorrower() {
        return borrower;
    }

    public void setBorrower(String borrower) {
        this.borrower = borrower;
    }

    public String toString() {
        return title;
    }

}