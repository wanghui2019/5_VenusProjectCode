package com.hui.domain;

public class CartItem {
    private Product product;         //商品
    private int num;                 //商品数量
    private double productprice;     //商品价格

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public double getProductprice() {
        return product.getMarket_price()*num;
    }

    public void setProductprice(double productprice) {
        this.productprice = productprice;
    }
}
