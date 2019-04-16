package com.hui.domain;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Cart {
    private CartItem cartItem;
    private double integral;
    private double productsPrice=0;

    Map<String,CartItem> map=new HashMap<>();

    //添加商品,将获取到的商品存到一个map集合里
    public void addProduct(CartItem cartItem){     //新加进来的商品项目
        //获取加入商品的pid
        String pid=cartItem.getProduct().getPid();
        //存的时候先判断这个商品以前有没有加入过购物车
        //判断方法，获取map集合的键，对键进行遍历，查看这个键中是否有刚加入商品的pid
        if (map.containsKey(pid)){
            //开始计算，将相对应的pid商品的商品数量num=以前的+现在的
            //根据键获取值，再根据值找到旧商品的数量
            int nowNum=cartItem.getNum()+map.get(pid).getNum();
            //把新的值存入到原先的购物项里
            CartItem oldCartItem=map.get(pid);
            oldCartItem.setNum(nowNum);

        }else {
            map.put(pid,cartItem);
        }
    }


    //删除某个订单
    public void removeProduct(String pid){
        //根据pid移除某一项
        map.remove(pid);
    }

    //请空购物车
    public void clearProduct(){
        map.clear();
    }

    //改变购物车某个商品的数量
    public void changeProductNum(String pid,int num){
        //根据pid获取到要改变的商品的CartItem
        CartItem cartItem = map.get(pid);
        //将CartItem的num设置为现在的num
        cartItem.setNum(num);
    }

    //将map集合里的值返回
    public Collection<CartItem> getCartItems(){
        return map.values();
    }



    public CartItem getCartItem() {
        return cartItem;
    }

    public void setCartItem(CartItem cartItem) {
        this.cartItem = cartItem;
    }

    public double getIntegral() {
        return integral;
    }

    public void setIntegral(double integral) {
        this.integral = integral;
    }


    public double getProductsPrice() {
        productsPrice=0;
        //商品的总价格=每个商品的列表对象的总价格相加
        Set<String> keys = map.keySet();
        //根据键遍历，获取cartItem
        for (String key:keys) {
            productsPrice=map.get(key).getProductprice()+productsPrice;
        }
        return productsPrice;
    }

    public void setProductsPrice(double productsPrice) {
        this.productsPrice = productsPrice;
    }
}
