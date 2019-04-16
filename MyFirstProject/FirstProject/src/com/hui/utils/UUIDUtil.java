package com.hui.utils;

import java.util.UUID;

public class UUIDUtil {
    //得到32位的uuid随机码
    public static String getUUID(){
        return UUID.randomUUID().toString().replace("-","").toUpperCase();
    }
    //得到64位的uuid随机码
    public static String getUUID64(){
        return getUUID()+getUUID();
    }

}
