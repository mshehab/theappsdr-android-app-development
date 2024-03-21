package edu.uncc.giftlistapp.models;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

public class GiftList implements Serializable {
    String gid, name;
    ArrayList<GiftListProduct> items = new ArrayList<>();
    double totalCost = 0.0;
    int totalCount = 0;

    public GiftList() {
    }

    public GiftList(JSONObject json) {
        this.gid = json.optString("gid");
        this.name = json.optString("name");
        JSONArray jsonArrayItems = json.optJSONArray("items");
        this.totalCost = 0.0;
        this.totalCount = 0;
        for (int i = 0; i < jsonArrayItems.length(); i++) {
            JSONObject jsonObject = jsonArrayItems.optJSONObject(i);
            GiftListProduct product = new GiftListProduct(jsonObject);
            items.add(product);
            this.totalCount = this.totalCount + product.getCount();
            this.totalCost = this.totalCost + (product.getCount() * product.getPrice_per_item());
        }
    }

    public double getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(double totalCost) {
        this.totalCost = totalCost;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public String getGid() {
        return gid;
    }

    public void setGid(String gid) {
        this.gid = gid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<GiftListProduct> getItems() {
        return items;
    }

    public void setItems(ArrayList<GiftListProduct> items) {
        this.items = items;
    }
}
