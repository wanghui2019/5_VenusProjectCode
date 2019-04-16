package com.hui.utils;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.converters.DateConverter;

import java.util.Map;

public class MyBeanUtils {
    public static void populate(Object obj, Map<String,String[]> map){
        //将字符串转换为日期，并设置日期的格式
        //1.创建时间类型的转换器
        DateConverter dt=new DateConverter();
        //2。设置转换格式
        dt.setPattern("yyyy-MM-dd");
        //3.注册转换器
        ConvertUtils.register(dt,java.util.Date.class);

        try {
            BeanUtils.populate(obj,map);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
