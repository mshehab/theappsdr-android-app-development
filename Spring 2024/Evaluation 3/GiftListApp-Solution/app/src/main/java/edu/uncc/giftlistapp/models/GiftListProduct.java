package edu.uncc.giftlistapp.models;

import org.json.JSONObject;

public class GiftListProduct {
    String pid, name, img_url;
    double price_per_item;
    int count;


    public GiftListProduct() {
    }

    public GiftListProduct(JSONObject json) {
        this.pid = json.optString("pid");
        this.name = json.optString("name");
        this.img_url = json.optString("img_url");

        try{
            this.price_per_item = Double.valueOf(json.optString("price_per_item"));
        } catch (NumberFormatException ex){
            this.price_per_item = 0.0;
            ex.printStackTrace();
        }
        this.count = json.optInt("count");
    }

    public void incrementCount(){
        count++;
    }

    public void decrementCount(){
        if (count > 0){
            count--;
        }
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImg_url() {
        return img_url;
    }

    public void setImg_url(String img_url) {
        this.img_url = img_url;
    }

    public double getPrice_per_item() {
        return price_per_item;
    }

    public void setPrice_per_item(double price_per_item) {
        this.price_per_item = price_per_item;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
