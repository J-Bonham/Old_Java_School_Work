// Jeremiah Bonham
// MDF 3 1501
// Week 3 - Widget

package com.fullsail.android.collectionwidgetdemo;

import java.io.Serializable;

public class Items implements Serializable {

	private static final long serialVersionUID = 517116325584656891L;

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