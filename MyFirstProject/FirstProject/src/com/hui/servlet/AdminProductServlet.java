package com.hui.servlet;

import com.hui.domain.CalcuModel;
import com.hui.domain.Category;
import com.hui.domain.Product;
import com.hui.service.CategoryService;
import com.hui.service.ProductService;
import com.hui.service.serviceimpl.CategoryServiceImpl;
import com.hui.service.serviceimpl.ProductServiceImpl;
import com.hui.utils.BaseServlet;
import com.hui.utils.UUIDUtil;
import com.hui.utils.UploadUtils;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.IOUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet(name = "AdminProductServlet")
public class AdminProductServlet extends BaseServlet {
    public String showProduct(HttpServletRequest request,HttpServletResponse response) throws SQLException {
        //根据当前页查询商品信息，并以分页形式显示
        int curNum=Integer.parseInt(request.getParameter("num"));

        ProductService productService=new ProductServiceImpl();
        //查询获取所有的商品的某一页集合
        CalcuModel calcuModel = productService.showProduct(curNum);

        request.setAttribute("cm",calcuModel);

        return "admin/product/list.jsp";
    }

    //添加商品列表
    public String addProductUI(HttpServletRequest request,HttpServletResponse response) throws SQLException {
        //查询数据库得到商品分类信息
        CategoryService categoryService=new CategoryServiceImpl();
        List<Category> allCats = categoryService.findAllCats();

        request.setAttribute("allCats",allCats);

        return "admin/product/add.jsp";
    }

    //获取提交来的商品信息
    public String addProduct(HttpServletRequest request,HttpServletResponse response) throws SQLException, InvocationTargetException, IllegalAccessException, IOException {
        //用于存键值对
        Map<String,String> map= new HashMap<>();
        try {
            //以下三步的功能。1，获取一个工厂。2，创建一个要上传的对象。3，该对象获取请求，得到一个FileItem的集合
            //该集合包含了所有form表单里的内容，其中每一个内容就是一个对象存在FileItem集合里
            FileItemFactory fif=new DiskFileItemFactory();
            ServletFileUpload upload=new ServletFileUpload(fif);
            List<FileItem> list = upload.parseRequest(request);

            for (FileItem item:list) {
                //如果是普通格式的
                if (item.isFormField()){
                    map.put(item.getFieldName(),item.getString("utf-8"));
                }else {
                    //获取到原始的文件名称 ID.pdf
                    String oldName = item.getName();

                    //获得要保存的文件名称  一个uuid生成的32位的随机数+.pdf
                    String newName = UploadUtils.getUUIDName(oldName);

                    /*System.out.println(getServletContext().getRealPath("products"));路径为
                    /Users/wanghui/wanghui_Data/2_Personal/application/IntelliJ IDEA/5_VenusProjectCode/MyFirstProject/out/artifacts/FirstProject_war_exploded/products
                     */
                    //获取当前项目products/3下的真实路径...
                    String realPath = getServletContext().getRealPath("/products/3");

                    //获得的是一个/0/9/a/1/6/d/5/8类型的字符串
                    String dir = UploadUtils.getDir(newName);

                    //拼接路径+/0/9/a/1/6/d/5/8类型的字符串，得到一个最终的文件夹路径
                    ///Users/wanghui/wanghui_Data/2_Personal/application/IntelliJ IDEA/5_VenusProjectCode/MyFirstProject/out/artifacts/FirstProject_war_exploded/products/3/4/4/d/3/a/f/6/1
                    String newDir=realPath+dir;

                    //将该文件夹路径创建位文件对象
                    File file=new File(newDir);

                    //判断该路径下存在不存在文件夹，最初一定是没有的，所以要先创建，有了就不用管了
                    if (!file.exists()){
                        file.mkdirs();
                    }

                    //有了文件夹，文件夹里还没有问价，先创建一个文件用于保存要写进来的流，这个文件的名称使用newName，因为它是唯一的
                    File wenjian=new File(newDir,newName);

                    //判断一下newDir路径下有没有newName文件，第一次来肯定是没有的，所以还是要创建
                    if (!wenjian.exists()){
                        wenjian.createNewFile();
                    }

                    //到这里我的路径下就有了/Users/wanghui/wanghui_Data/2_Personal/application/IntelliJ IDEA/5_VenusProjectCode/MyFirstProject/out/artifacts/FirstProject_war_exploded/products/3/4/4/d/3/a/f/6/1/XXX.pdf
                    //但是里面的文件内容是空的，所以此时应该获取到上传的数据的二进制形式
                    InputStream is = item.getInputStream();

                    //创建一个流用于写入
                    OutputStream os=new FileOutputStream(wenjian);
                    //System.out.println(wenjian.getAbsolutePath());

                    //然后把这个流写入到空文件里
                    IOUtils.copy(is,os);

                    //关流
                    IOUtils.closeQuietly(is);
                    IOUtils.closeQuietly(os);


                    //最后一步，想map中存入pimage=products/img/...这个的键指对数据
                    map.put(item.getFieldName(),"products/3"+dir+"/"+newName);

                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        Product product=new Product();
        BeanUtils.populate(product,map);
        //检查数据库，还有pid,pdate,pflag没有设置
        product.setPid(UUIDUtil.getUUID());
        product.setPdate(new Date());
        product.setPflag(0);

        //将product存到数据库里
        ProductService productService=new ProductServiceImpl();
        productService.saveProduct(product);

        response.sendRedirect("AdminProductServlet?method=showProduct&num=1");
        return null;
    }
}
