package edu.charlotte.evaluation02.models;

// Purchase.java
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

public class Purchase  implements Serializable {
    Date purchaseDate;
    ArrayList<Item> items;

    public Purchase() {
    }

    public Purchase(Date purchaseDate, ArrayList<Item> items) {
        this.purchaseDate = purchaseDate;
        this.items = items;
    }

    public Date getPurchaseDate() {
        return purchaseDate;
    }

    public void setPurchaseDate(Date purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    public ArrayList<Item> getItems() {
        return items;
    }

    public void setItems(ArrayList<Item> items) {
        this.items = items;
    }
}
