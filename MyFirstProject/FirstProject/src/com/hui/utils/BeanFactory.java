package com.hui.utils;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.util.List;

public class BeanFactory {
    public  static Object createObject(String name){
        //通过dom4j解析xml
        SAXReader reader=new SAXReader();

        try {
            //根据文本路径得到xml文本
            Document docu = reader.read(BeanFactory.class.getClassLoader().getResourceAsStream("application.xml"));
            //得到根元素beans
            Element rootElement = docu.getRootElement();
            //得到根元素下面的元素bean集
            List<Element> beans = rootElement.elements("bean");
            //获取beans里的每个bean
            for (Element ele:beans) {
                //对bean进行遍历,得到每个id
                String id = ele.attribute("id").getText();
                //判断这个id与name是否相等
                if (id.equals(name)){
                    //如果相等，就将class里的内容转换为对象返回
                    //先获取class里的内容
                    String aClass = ele.attribute("class").getText();
                    //获取字节码对象
                    Class<?> clazz = Class.forName(aClass);
                    //将字节码对象转换为源码对象
                    return clazz.newInstance();
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

       return null;
    }

}


