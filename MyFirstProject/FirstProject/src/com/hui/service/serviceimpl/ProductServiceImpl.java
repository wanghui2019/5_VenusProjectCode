package com.hui.service.serviceimpl;

import com.hui.dao.ProductDao;
import com.hui.dao.impl.ProductDaoImpl;
import com.hui.domain.CalcuModel;
import com.hui.domain.Product;
import com.hui.service.ProductService;

import java.sql.SQLException;
import java.util.List;

public class ProductServiceImpl implements ProductService {
    ProductDaoImpl productDao=new ProductDaoImpl();
    //热门商品
    @Override
    public List<Product> findHost() throws SQLException {
        return productDao.findHost();
    }
    //最新商品
    @Override
    public List<Product> findNew() throws SQLException {
        return productDao.findNew();
    }

    @Override
    public Product findProductOne(String pid) throws SQLException {
        return productDao.findProductOne(pid);
    }

    @Override
    public CalcuModel findProduct(String cid, int curNum) throws SQLException {
        //获取某个商品的总数
        int totalNum = productDao.findTotal(cid);
        //根据当前页，商品总数和每页显示的数目来得到startNum,totalPageNum,startPage,endPage
        CalcuModel calcuModel=new CalcuModel(curNum,totalNum,12);
        List<Product> product = productDao.findProduct(cid, calcuModel.getStartNum(), calcuModel.getShowNum());
        calcuModel.setList(product);

        calcuModel.setUrl("ProductServlet?method=findProduct&cid="+cid);

        return calcuModel;
    }



    //查询所有商品信息
    @Override
    public CalcuModel showProduct(int curNum) throws SQLException {
        int totalNum=productDao.showTotal();
        CalcuModel calcuModel=new CalcuModel(curNum,totalNum,5);
        List<Product> products = productDao.showProduct(calcuModel.getStartNum(), calcuModel.getShowNum());

        calcuModel.setList(products);
        calcuModel.setUrl("AdminProductServlet?method=showProduct");
        return calcuModel;
    }

    //添加商品信息到数据库
    @Override
    public void saveProduct(Product product) throws SQLException {
        ProductDao productDao=new ProductDaoImpl();
        productDao.saveProduct(product);
    }





}
