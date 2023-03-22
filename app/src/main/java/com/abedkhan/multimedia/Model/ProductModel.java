package com.abedkhan.multimedia.Model;

public class ProductModel {

    String cetagoryName;
    int cetegoryImg;

    public ProductModel(String cetagoryName, int cetegoryImg) {
        this.cetagoryName = cetagoryName;
        this.cetegoryImg = cetegoryImg;
    }


    public String getCetagoryName() {
        return cetagoryName;
    }

    public void setCetagoryName(String cetagoryName) {
        this.cetagoryName = cetagoryName;
    }

    public int getCetegoryImg() {
        return cetegoryImg;
    }

    public void setCetegoryImg(int cetegoryImg) {
        this.cetegoryImg = cetegoryImg;
    }
}
