package com.hui.domain;

import java.util.List;

public class CalcuModel {
    private int totalNum;     //某一个商品总数
    private int showNum;      //一页显示的商品数量
    private int totalPageNum; //商品的总页数
    private int startNum;     //该页的第一个数
    private int curNum;       //当前页，由用于指定

    private int prePageNum;   //上一页
    private int nextPageNum;  //下一页

    private List list;   //要获取的集合

    private String url;           //要跳转的路径

    //显示的页码，大于9个时用的到
    private int startPage;
    private int endPage;

   public CalcuModel(int curNum,int totalNum,int showNum){
       this.curNum=curNum;
       this.totalNum=totalNum;
       this.showNum=showNum;


       //计算该页的第一个数
       startNum=(curNum-1)*showNum;

       //计算一共多少页
       totalPageNum=totalNum%showNum==0?(totalNum/showNum):(totalNum/showNum+1);


       startPage=curNum-8;
       endPage=curNum+8;
       //根据总页数够不够9页，来进行判断决定
       if (totalPageNum>9){
           //大于9页
           if (startPage<1){
               startPage=1;
               endPage=startPage+8;
           }else if (endPage>=totalPageNum){
               endPage=totalPageNum;
               startPage=endPage-8;
           }
       }else {
           //不够9页
           startPage=1;
           endPage=totalPageNum;
       }
   }

    public int getTotalNum() {
        return totalNum;
    }

    public void setTotalNum(int totalNum) {
        this.totalNum = totalNum;
    }

    public int getShowNum() {
        return showNum;
    }

    public void setShowNum(int showNum) {
        this.showNum = showNum;
    }

    public int getTotalPageNum() {
        return totalPageNum;
    }

    public void setTotalPageNum(int totalPageNum) {
        this.totalPageNum = totalPageNum;
    }

    public int getStartNum() {
        return startNum;
    }

    public void setStartNum(int startNum) {
        this.startNum = startNum;
    }

    public int getCurNum() {
        return curNum;
    }

    public void setCurNum(int curNum) {
        this.curNum = curNum;
    }

    public int getPrePageNum() {
       prePageNum=curNum-1;
       if (prePageNum<1){
           prePageNum=1;
       }
        return prePageNum;
    }


    public int getNextPageNum() {
       nextPageNum=curNum+1;
       if (nextPageNum>totalPageNum){
           nextPageNum=totalPageNum;
       }
        return nextPageNum;
    }



    public List getList() {
        return list;
    }

    public void setList(List list) {
        this.list = list;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getStartPage() {
        return startPage;
    }

    public void setStartPage(int startPage) {
        this.startPage = startPage;
    }

    public int getEndPage() {
        return endPage;
    }

    public void setEndPage(int endPage) {
        this.endPage = endPage;
    }
}
