package com.hui.utils;

import com.hui.domain.User;

import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/*
功能分析：
由map得到键值对对象，然后将map中的值存入到user里的set方法中
 */
public class MyBeanUtil {
    public static void populate(User user, Map<String,String[]> map){
        //1。遍历map,得到键值对
        Set<String> keys = map.keySet();
        Iterator<String> it=keys.iterator();
        while (it.hasNext()){
            String key = it.next();
            String[] values = map.get(key);
            for (String value:values) {
                //得到User的字节码对象
                Class clazz = user.getClass();
                //将字符串拼接为setXXX的样子
                StringBuffer sb=new StringBuffer();
                sb.append(Character.toUpperCase(key.charAt(0))).append(key.substring(1)).toString();
                String Ukey=new String(sb);
                String method="set".concat(Ukey);
                //判断user中是否有setXXX的方法，如果有，就执行
                try {
                    Method md = clazz.getMethod(method, String.class);
                    if (md!=null){

                        md.invoke(user, value);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }

    }

}
