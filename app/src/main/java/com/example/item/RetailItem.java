package com.example.item;

public class RetailItem {

    private int ID,sellerID,lastbuyerID;

    private String name,picture;
    private Float price,lastprice;

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public int getSellerID() {
        return sellerID;
    }

    public void setSellerID(int sellerID) {
        this.sellerID = sellerID;
    }

    public int getLastbuyerID() {
        return lastbuyerID;
    }

    public void setLastbuyerID(int lastbuyerID) {
        this.lastbuyerID = lastbuyerID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public Float getLastprice() {
        return lastprice;
    }

    public void setLastprice(Float lastprice) {
        this.lastprice = lastprice;
    }


    public RetailItem() {
        super();
        ID=0;
        sellerID=0;
        lastbuyerID=0;
        name="";
        picture="";
        price= Float.valueOf(0);
        lastprice=Float.valueOf(0);


    }
    public RetailItem(int ID,String name,Float price,String picture,
                      Float lastprice,int sellerID,int lastbuyerID) {
        super();
        this.ID = ID;
        this.name = name;
        this.price=price;
        this.picture=picture;
        this.lastprice=lastprice;
        this.sellerID=sellerID;
        this.lastbuyerID=lastbuyerID;
    }



}
